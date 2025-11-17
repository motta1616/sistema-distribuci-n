package mercadolibre.com.reactive;

import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.domain.gateway.StockItemService;
import mercadolibre.com.reactive.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private StockItemService stockItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findProcessShouldReturnProductDtoWhenItemExists() {
        Product product = Product.builder().sku("123").quantity(10).build();
        when(stockItemService.findItemProcess("123")).thenReturn(Mono.just(product));

        Mono<ResponseEntity<ProductDto>> result = inventoryController.findProcess("123");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() &&
                        response.getBody() != null &&
                        "123".equals(response.getBody().getSku()) &&
                        response.getBody().getQuantity() == 10)
                .verifyComplete();

        verify(stockItemService, times(1)).findItemProcess("123");
    }

    @Test
    void findProcessShouldReturnErrorResponseWhenExceptionOccurs() {
        when(stockItemService.findItemProcess("123"))
                .thenReturn(Mono.error(new IllegalArgumentException("Invalid ID")));

        Mono<ResponseEntity<ProductDto>> result = inventoryController.findProcess("123");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCodeValue() == 400 &&
                        response.getBody() != null &&
                        "400".equals(response.getBody().getStatus()) &&
                        "Invalid ID".equals(response.getBody().getErrorMessage()))
                .verifyComplete();

        verify(stockItemService, times(1)).findItemProcess("123");
    }
}