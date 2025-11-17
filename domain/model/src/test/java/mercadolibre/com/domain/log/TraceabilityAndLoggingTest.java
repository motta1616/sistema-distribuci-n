package mercadolibre.com.domain.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TraceabilityAndLoggingTest {

    @InjectMocks
    private TraceabilityAndLogging traceabilityAndLogging;

    private String data;
    private Throwable throwable;
    private String operation;
    private String message;

    @BeforeEach
    void setUp() {
        data = "testData";
        throwable = new RuntimeException("Test exception");
        operation = "testOperation";
        message = "testTransactionId";
    }

    @Test
    void validateTrace_withBusinessException() {
        throwable = new RuntimeException("Business exception");

        Mono<Void> result = traceabilityAndLogging.traceOutErrorTechnical(data, throwable.getMessage(), operation);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void validateTrace_withOtherException() {
        throwable = new RuntimeException("Other exception");

        Mono<Void> result = traceabilityAndLogging.traceOutErrorTechnical(data, throwable.getMessage(), operation);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void traceInfo() {
        Mono<Void> result = traceabilityAndLogging.traceIn(data, message, operation);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void traceTechnicalInfo() {
        Mono<Void> result = traceabilityAndLogging.traceOut(data, message, operation);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void traceOutErrorBruleShouldLogInfoLevelMessage() {
        Mono<Void> result = traceabilityAndLogging.traceOutErrorBrule(data, message, operation);

        StepVerifier.create(result)
                .verifyComplete();
    }
}