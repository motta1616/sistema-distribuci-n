package mercadolibre.com.postgresql.enums;

import lombok.Getter;

@Getter
public enum PostgresEnum {

    RETRY_COUNT("Error en la conexion a la base de datos. Reintento:  {}");

    private final String message;

    PostgresEnum(String message) {
        this.message = message;
    }
}
