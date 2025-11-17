package mercadolibre.com.domain.common.enums;

import lombok.Getter;

@Getter
public enum Constants {

    INPUT_FIND_ITEM("El mensaje para consultar el sku, se recibio de forma correcta"),
    INPUT_UPDATE_ITEM("El mensaje para actualizar el sku, se recibio de forma correcta"),
    OUTPUT_FIND_ITEM("La consulta se realizo de forma exitosa"),
    OUTPUT_UPDATE_ITEM("La actualizacion se realizo de forma exitosa"),
    BRULE_UPDATE_ITEM("El Producto no se encuentra disponible para actualizar o ya fue actualizado"),
    FIND_ITEM_PROCESS("find-item-process"),
    CONSULT_ITEM("consult-item"),
    UPDATE_ITEM_PROCESS("update-item-process"),
    UPDATE_ITEM("update-item"),
    VALIDATE_RESPONSE_UPDATE("validate-response-update"),;

    private final String message;

    Constants(String message) {
        this.message = message;
    }
}
