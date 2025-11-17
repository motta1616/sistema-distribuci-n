package mercadolibre.com.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductDto {
    private String storeId;
    private String sku;
    private String name;
    private Integer quantity;
    private String status;
    private String errorMessage;
    private Integer version;

}
