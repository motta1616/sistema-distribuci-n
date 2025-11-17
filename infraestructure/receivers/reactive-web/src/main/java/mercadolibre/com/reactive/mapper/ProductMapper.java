package mercadolibre.com.reactive.mapper;

import mercadolibre.com.domain.entities.Product;
import mercadolibre.com.reactive.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);
    Product toDomain(ProductDto productDto);
}
