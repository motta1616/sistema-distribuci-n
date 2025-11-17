package mercadolibre.com.usecase;

import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.domain.gateway.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StockItemUseCaseTest {

    @InjectMocks
    private StockItemUseCase stockItemUseCase;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    private static final String TEST = "TEST";
    private static final Integer TEST_INTEGER = 1;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .sku(TEST)
                .quantity(10)
                .build();
    }

    @Test
    void findItemSuccess() {

        when(productRepository.findItem(TEST))
                .thenReturn(Mono.just(TEST_INTEGER));

        Mono<Product> productMono = stockItemUseCase.findItemProcess(TEST);

        StepVerifier.create(productMono)
                .expectNextMatches(products -> products.getSku().equals(TEST)
                        && products.getQuantity().equals(TEST_INTEGER))
                .verifyComplete();

        verify(productRepository, times(1)).findItem(TEST);
    }

    @Test
    void findItemError() {

        when(productRepository.findItem(TEST))
                .thenReturn(Mono.error(new RuntimeException("Error finding item")));

        Mono<Product> productMono = stockItemUseCase.findItemProcess(TEST);

        StepVerifier.create(productMono)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Error finding item"))
                .verify();

        verify(productRepository, times(1)).findItem(TEST);
    }

    @Test
    void updateItemSuccess() {

        when(productRepository.updateItem(product))
                .thenReturn(Mono.just(true));

        Mono<Void> voidMono = stockItemUseCase.updateItemProcess(product);

        StepVerifier.create(voidMono)
                .verifyComplete();

        verify(productRepository, times(1)).updateItem(product);
    }

    @Test
    void updateItemError() {

        when(productRepository.updateItem(product))
                .thenReturn(Mono.error(new RuntimeException("Error updating item")));

        Mono<Void> result = stockItemUseCase.updateItemProcess(product);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository, times(1)).updateItem(product);
    }

    @Test
    void updateItemBrule() {

        when(productRepository.updateItem(product))
                .thenReturn(Mono.just(false));

        Mono<Void> voidMono = stockItemUseCase.updateItemProcess(product);

        StepVerifier.create(voidMono)
                .verifyComplete();

        verify(productRepository, times(1)).updateItem(product);
    }


}