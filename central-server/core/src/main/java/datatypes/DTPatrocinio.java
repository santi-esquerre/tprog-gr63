package datatypes;

import java.io.Serializable;
import java.util.Date;

public record DTPatrocinio(
    Date fechaRealizacion,
    float monto,
    String codigo,
    NivelPatrocinio nivel,
    DTInstitucion institucion)
    implements Serializable {}
