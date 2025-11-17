package mercadolibre.com.postgresql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.domain.gateway.ProductRepository;
import mercadolibre.com.reactive.dto.ProductDto;
import mercadolibre.com.reactive.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static mercadolibre.com.postgresql.enums.PostgresEnum.RETRY_COUNT;

@Slf4j
@Component
public class ProductRepositoryAdapter implements ProductRepository {

    @Value("${json.file.path}")
    private String jsonFilePath;

    @Value("${maximum.retries}")
    private Integer maximumRetries;

    @Value("${retry.cycle}")
    private Integer retryCycle;

    @PostConstruct
    public void initializeStore() {
        loadStoreFromJson(jsonFilePath);
    }

    private ConcurrentHashMap<String, AtomicReference<ProductDto>> store;

    @Override
    public Mono<Integer> findItem(String sku) {
        return Mono.fromSupplier(() ->
               store.values().stream()
                        .map(AtomicReference::get)
                        .map(ProductMapper.INSTANCE::toDomain)
                        .filter(product -> product.getSku().equals(sku))
                        .mapToInt(Product::getQuantity)
                        .sum())
                .retryWhen(getRetrySpec());
    }

    @Override
    public Mono<Boolean> updateItem(Product product) {
        return Mono.fromSupplier(() -> {
            String uniqueKey = product.getStoreId() + "-" + product.getSku();
            AtomicReference<ProductDto> atomicReference = store.get(uniqueKey);

            if (atomicReference == null) {
                return false;
            }

            ProductDto productDto = atomicReference.get();
            if (productDto.getVersion() == product.getVersion()) {
                ProductDto updatedDto = ProductMapper.INSTANCE.toDto(product);
                updatedDto.setVersion(product.getVersion() + 1); // Increment version
                return atomicReference.compareAndSet(productDto, updatedDto);
            } else {
                log.info("Versión no coincidente para el SKU {}. Se esperaba: {}, se encontró: {}",
                        product.getSku(), product.getVersion(), productDto.getVersion());
                return false;
            }
        }).retryWhen(getRetrySpec());
    }

    private RetryBackoffSpec getRetrySpec() {
        return Retry.fixedDelay(maximumRetries, Duration.ofMillis(retryCycle))
                .doAfterRetry(retrySignal -> log.info(RETRY_COUNT.getMessage(),
                        retrySignal.totalRetriesInARow() + 1))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    private void loadStoreFromJson(String jsonFilePath) {
        store = new ConcurrentHashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new ClassPathResource(jsonFilePath).getFile();
            List<ProductDto> products = objectMapper.readValue(file, new TypeReference<>() {}
            );

            products.forEach(productDto -> {
                String uniqueKey = productDto.getStoreId() + "-" + productDto.getSku();
                store.put(uniqueKey, new AtomicReference<>(productDto));
            });

            log.info("Store successfully loaded from JSON.");
        } catch (Exception e) {
            log.error("Error loading store from JSON: {}", e.getMessage(), e);
        }
    }
}
