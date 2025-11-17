package mercadolibre.com;

import mercadolibre.com.domain.gateway.ProductRepository;
import mercadolibre.com.usecase.StockItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public StockItemUseCase stockItemService(ProductRepository productRepository) {
        return new StockItemUseCase(productRepository);
    }
}
