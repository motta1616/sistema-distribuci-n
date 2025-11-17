package mercadolibre.com.domain.log;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class TraceabilityAndLogging {
    protected <T> Mono<Void> traceIn(T data, String message,  String operation) {
        String logMessage = String.format("[input] [message]: %s.  [operation]: %s. [data]: %s",
                message, operation, data);
        log.info(logMessage);

        return Mono.empty();
    }

    protected <T> Mono<Void> traceOut(T data, String message, String operation) {
        String logMessage = String.format("[output] [message]: %s.  [operation]: %s. [data]: %s",
                message, operation, data);
        log.info(logMessage);

        return Mono.empty();
    }

    protected <T> Mono<Void> traceOutErrorBrule(T data, String message, String operation) {
        String logMessage = String.format("[output error brule] [message]: %s.  [operation]: %s. [data]: %s",
                message, operation, data);
        log.info(logMessage);

        return Mono.empty();
    }

    protected <T> Mono<Void> traceOutErrorTechnical(T data, String message, String operation) {
        String logMessage = String.format("[output error technical]  [message]: %s.  [operation]: %s. [data]: %s",
                message, operation, data);
        log.error(logMessage);

        return Mono.empty();
    }
}
