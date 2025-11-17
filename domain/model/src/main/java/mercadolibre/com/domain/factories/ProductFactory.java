package mercadolibre.com.domain.factories;

import mercadolibre.com.domain.entities.Product;
import reactor.core.publisher.Mono;

public interface ProductFactory {

    default Mono<Product> buildFindProduct(String sku, Integer quantity) {
        return Mono.just(Product.builder()
                .sku(sku)
                .quantity(quantity)
                .build());
    }
}
