package mercadolibre.com.usecase;

import lombok.RequiredArgsConstructor;
import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.domain.factories.ProductFactory;
import mercadolibre.com.domain.gateway.ProductRepository;
import mercadolibre.com.domain.gateway.StockItemService;
import mercadolibre.com.domain.log.TraceabilityAndLogging;
import reactor.core.publisher.Mono;

import static mercadolibre.com.domain.common.enums.Constants.*;

@RequiredArgsConstructor
public class StockItemUseCase extends TraceabilityAndLogging implements StockItemService, ProductFactory {

    private final ProductRepository productRepository

    @Override
    public Mono<Product> findItemProcess(String sku) {
        return traceIn(sku, INPUT_FIND_ITEM.getMessage(), FIND_ITEM_PROCESS.getMessage())
                .then(consultItem(sku));
    }

    private Mono<Product> consultItem(String sku) {
        return productRepository.findItem(sku)
                .flatMap(integer -> buildFindProduct(sku, integer))
                .flatMap(product -> traceOut(product, OUTPUT_FIND_ITEM.getMessage(), CONSULT_ITEM.getMessage())
                        .thenReturn(product))
                .onErrorResume(throwable -> traceOutErrorTechnical(sku, throwable.getMessage(), CONSULT_ITEM.getMessage())
                        .then(Mono.error(throwable)));
    }

    @Override
    public Mono<Void> updateItemProcess(Product product) {
        return traceIn(product, INPUT_UPDATE_ITEM.getMessage(), UPDATE_ITEM_PROCESS.getMessage())
                .thenReturn(product)
                .flatMap(this::updateItem);
    }

    private Mono<Void> updateItem(Product product) {
        return productRepository.updateItem(product)
                .flatMap(aBoolean -> validateResponseUpdate(aBoolean, product))
                .onErrorResume(throwable -> traceOutErrorTechnical(product, throwable.getMessage(), UPDATE_ITEM.getMessage())
                        .then(Mono.empty()));
    }

    private Mono<Void> validateResponseUpdate(Boolean aBoolean, Product product) {
        return Boolean.TRUE.equals(aBoolean)
                ? traceOut(product, OUTPUT_UPDATE_ITEM.getMessage(), VALIDATE_RESPONSE_UPDATE.getMessage())
                : traceOutErrorBrule(product, BRULE_UPDATE_ITEM.getMessage(), VALIDATE_RESPONSE_UPDATE.getMessage());
    }
}
