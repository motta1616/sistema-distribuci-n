package mercadolibre.com.domain.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {
    private final String storeId;
    private final String sku;
    private final String name;
    private final Integer quantity;
    private final Integer version;
}