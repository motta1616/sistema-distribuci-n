package mercadolibre.com.reactive.mapper;

import mercadolibre.com.reactive.dto.ProductDto;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BuildMessageTest {

    private final BuildMessage buildMessage = new BuildMessage() {};

    @Test
    void buildResponseErrorShouldReturnProductDtoWithStatus400ForIllegalArgumentException() {
        Throwable throwable = new IllegalArgumentException("Invalid argument");

        Mono<ProductDto> result = buildMessage.buildResponseError(throwable);

        StepVerifier.create(result)
                .expectNextMatches(productDto -> "400".equals(productDto.getStatus())
                        && "Invalid argument".equals(productDto.getErrorMessage()))
                .verifyComplete();
    }

    @Test
    void buildResponseErrorShouldReturnProductDtoWithStatus500ForOtherExceptions() {
        Throwable throwable = new RuntimeException("Unexpected error");

        Mono<ProductDto> result = buildMessage.buildResponseError(throwable);

        StepVerifier.create(result)
                .expectNextMatches(productDto -> "500".equals(productDto.getStatus())
                        && "Unexpected error".equals(productDto.getErrorMessage()))
                .verifyComplete();
    }

    @Test
    void buildResponseErrorShouldHandleNullMessageInThrowable() {
        Throwable throwable = new RuntimeException();

        Mono<ProductDto> result = buildMessage.buildResponseError(throwable);

        StepVerifier.create(result)
                .expectNextMatches(productDto -> "500".equals(productDto.getStatus())
                        && productDto.getErrorMessage() == null)
                .verifyComplete();
    }
}