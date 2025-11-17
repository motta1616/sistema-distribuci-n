package mercadolibre.com.domain.gateway;

import mercadolibre.com.domain.entities.Product;
import reactor.core.publisher.Mono;

public interface StockItemService {
    Mono<Product> findItemProcess(String sku);
    Mono<Void> updateItemProcess(Product product);
}
