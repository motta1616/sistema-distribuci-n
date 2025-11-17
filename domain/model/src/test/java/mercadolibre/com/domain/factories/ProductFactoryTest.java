package mercadolibre.com.domain.factories;

import mercadolibre.com.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductFactoryTest {

    @InjectMocks
    private final ProductFactory productFactory = new ProductFactory() {};

    @Test
    void buildFindProductShouldReturnProductWithCorrectSkuAndQuantity() {
        Mono<Product> productMono = productFactory.buildFindProduct("SKU123", 10);

        StepVerifier.create(productMono)
                .expectNextMatches(product -> "SKU123".equals(product.getSku()) && product.getQuantity() == 10)
                .verifyComplete();
    }

    @Test
    void buildFindProductShouldHandleNullSku() {
        Mono<Product> productMono = productFactory.buildFindProduct(null, 10);

        StepVerifier.create(productMono)
                .expectNextMatches(product -> product.getSku() == null && product.getQuantity() == 10)
                .verifyComplete();
    }

    @Test
    void buildFindProductShouldHandleNegativeQuantity() {
        Mono<Product> productMono = productFactory.buildFindProduct("SKU123", -5);

        StepVerifier.create(productMono)
                .expectNextMatches(product -> "SKU123".equals(product.getSku()) && product.getQuantity() == -5)
                .verifyComplete();
    }
}