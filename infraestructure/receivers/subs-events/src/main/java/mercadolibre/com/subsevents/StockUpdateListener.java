package mercadolibre.com.subsevents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mercadolibre.com.domain.gateway.StockItemService;
import mercadolibre.com.reactive.dto.ProductDto;
import mercadolibre.com.reactive.mapper.ProductMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockUpdateListener {

    private final StockItemService stockItemService;

    @RabbitListener(queues = "update-stock-queue")
    public Mono<Void> handleStockUpdate(ProductDto product) {
        try {
            return stockItemService.updateItemProcess(ProductMapper.INSTANCE.toDomain(product));
        } catch (Exception e) {
            log.error("Error updating stock for product: {}", product.getSku(), e);
            return Mono.error(new RuntimeException("Falla en el envio a Rabbit", e));
        }
    }
}
