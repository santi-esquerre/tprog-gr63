package datatypes;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTRegistrosOtorgados(DTTipoRegistro tipoRegistro, int cupos)
    implements Serializable {}
