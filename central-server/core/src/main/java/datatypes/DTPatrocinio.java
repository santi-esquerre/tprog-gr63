package datatypes;

import java.io.Serializable;
import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTPatrocinio(
    Date fechaRealizacion,
    float monto,
    String codigo,
    NivelPatrocinio nivel,
    DTInstitucion institucion
    ) implements Serializable {}
