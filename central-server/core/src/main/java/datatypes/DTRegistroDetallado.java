package datatypes;

import java.util.Date;
import java.util.Optional;

public record DTRegistroDetallado(
    Date fecha,
    float costo,
    DTEvento evento,
    DTEdicion edicion,
    DTAsistente asistente,
    DTTipoRegistro tipoRegistro,
    Optional<DTPatrocinio> patrocinio
) {}
