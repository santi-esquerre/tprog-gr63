package datatypes;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTEdicion(
    String nombre,
    String sigla,
    Date fechaInicio,
    Date fechaFin,
    Date fechaAlta,
    String ciudad,
    String pais,
    String foto,
    EstadoEdicion estado) {}
