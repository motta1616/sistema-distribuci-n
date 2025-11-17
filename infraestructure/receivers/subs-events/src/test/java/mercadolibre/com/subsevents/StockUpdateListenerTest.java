package mercadolibre.com.subsevents;

import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.domain.gateway.StockItemService;
import mercadolibre.com.reactive.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockUpdateListenerTest {

    @InjectMocks
    private StockUpdateListener stockUpdateListener;

    @Mock
    private StockItemService stockItemService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = ProductDto.builder().sku("123").quantity(10).build();
        product = Product.builder().sku("123").quantity(10).build();
    }

    @Test
    void handleStockUpdateSuccessfullyUpdatesStock() {
        when(stockItemService.updateItemProcess(product)).thenReturn(Mono.empty());

        Mono<Void> result = stockUpdateListener.handleStockUpdate(productDto);

        StepVerifier.create(result)
                .verifyComplete();

        verify(stockItemService, times(1)).updateItemProcess(product);
    }

    @Test
    void handleStockUpdateLogsErrorWhenExceptionOccurs() {
        when(stockItemService.updateItemProcess(product))
                .thenReturn(Mono.error(new RuntimeException("Update failed")));

        Mono<Void> result = stockUpdateListener.handleStockUpdate(productDto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Update failed"))
                .verify();

        verify(stockItemService, times(1)).updateItemProcess(product);
    }
}