package mercadolibre.com.reactive.mapper;

import mercadolibre.com.reactive.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface BuildMessage {
    default Mono<ProductDto> buildResponseError(Throwable throwable) {
        return Mono.just(ProductDto.builder()
                .status((throwable instanceof IllegalArgumentException)
                        ? "400"
                        : "500")
                .errorMessage(throwable.getMessage())
                .build());
    }
}
