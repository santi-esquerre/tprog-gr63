package datatypes;

import java.util.Date;

public record DTRegistro(
        Date fecha,
        float costo,
        String nombreEvento,
        String nombreEdicion,
        String nicknameAsistente,
        DTTipoRegistro tipoRegistro) {
}
