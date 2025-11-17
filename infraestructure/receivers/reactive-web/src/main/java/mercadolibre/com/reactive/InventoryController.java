package mercadolibre.com.reactive;

import lombok.RequiredArgsConstructor;
import mercadolibre.com.domain.gateway.StockItemService;
import mercadolibre.com.reactive.dto.ProductDto;
import mercadolibre.com.reactive.mapper.BuildMessage;
import mercadolibre.com.reactive.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController implements BuildMessage {

    private final StockItemService stockItemService;

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<ProductDto>> findProcess(@PathVariable("id") String id) {
        return stockItemService.findItemProcess(id)
                .map(product -> ResponseEntity.ok(ProductMapper.INSTANCE.toDto(product)))
                .onErrorResume(throwable -> buildResponseError(throwable)
                        .map(productDtoUpdate -> ResponseEntity
                                .status(Integer.parseInt(productDtoUpdate.getStatus()))
                                .body(productDtoUpdate)));
    }
}