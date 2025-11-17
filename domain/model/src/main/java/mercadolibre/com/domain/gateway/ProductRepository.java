package mercadolibre.com.domain.gateway;

import mercadolibre.com.domain.entities.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Integer> findItem(String sku);
    Mono<Boolean> updateItem(Product product);
}
