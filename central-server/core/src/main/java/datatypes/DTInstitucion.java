package datatypes;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTInstitucion(
    String nombre,
    String descripcion,
    String sitioWeb,
    String foto
) implements Serializable { }
