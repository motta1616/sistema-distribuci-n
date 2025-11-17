package mercadolibre.com.postgresql;

import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.reactive.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

class ProductRepositoryAdapterTest {

    private ProductRepositoryAdapter productRepositoryAdapter;

    private Product product;

    @BeforeEach
    void setUp() {

        ProductDto productDto = ProductDto.builder()
                .storeId("store1")
                .sku("12345")
                .name("Test Product")
                .quantity(10)
                .status("available")
                .version(1)
                .build();

        product = Product.builder()
                .storeId("store1")
                .sku("12345")
                .name("Test Product")
                .quantity(10)
                .version(1)
                .build();

        ConcurrentHashMap<String, AtomicReference<ProductDto>> store = new ConcurrentHashMap<>();
        store.put("store1-12345", new AtomicReference<>(productDto));

        productRepositoryAdapter = new ProductRepositoryAdapter();
        productRepositoryAdapter.initializeStore();
        ReflectionTestUtils.setField(productRepositoryAdapter, "store",  store);
        ReflectionTestUtils.setField(productRepositoryAdapter, "maximumRetries", 1);
        ReflectionTestUtils.setField(productRepositoryAdapter, "retryCycle", 1);

    }

    @Test
    void findItemShouldReturnCorrectQuantityWhenSkuExists() {

        Mono<Integer> result = productRepositoryAdapter.findItem("12345");

        StepVerifier.create(result)
                .expectNext(10)
                .verifyComplete();
    }

    @Test
    void findItemShouldReturnZeroWhenSkuDoesNotExist() {
        Mono<Integer> result = productRepositoryAdapter.findItem("SKU999");

        StepVerifier.create(result)
                .expectNext(0)
                .verifyComplete();
    }

    @Test
    void updateItemShouldReturnTrueWhenUpdateIsSuccessful() {

        Mono<Boolean> result = productRepositoryAdapter.updateItem(product);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void updateItemShouldReturnFalseWhenVersionMismatchOccurs() {

        product = product.toBuilder()
                .version(2)
                .build();

        Mono<Boolean> result = productRepositoryAdapter.updateItem(product);

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void updateItemShouldReturnFalseWhenProductDoesNotExist() {
        product = Product.builder()
                .sku("SKU123")
                .version(1)
                .quantity(20)
                .build();

        Mono<Boolean> result = productRepositoryAdapter.updateItem(product);

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

}