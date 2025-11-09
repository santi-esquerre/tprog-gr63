package datatypes;

import java.util.Date;
import java.util.Optional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTRegistroDetallado(
    Date fecha,
    float costo,
    DTEvento evento,
    DTEdicion edicion,
    DTAsistente asistente,
    DTTipoRegistro tipoRegistro,
    Optional<DTPatrocinio> patrocinio) {}
