package mercadolibre.com;

import mercadolibre.com.domain.gateway.ProductRepository;
import mercadolibre.com.usecase.StockItemUseCase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCaseConfigTest {

    @InjectMocks
    private UseCaseConfig useCaseConfig;

    @Mock
    private ProductRepository productRepository;

    @Test
    void detailController() {
        MatcherAssert.assertThat(useCaseConfig.stockItemService(productRepository),
                Matchers.instanceOf(StockItemUseCase.class));
    }
}