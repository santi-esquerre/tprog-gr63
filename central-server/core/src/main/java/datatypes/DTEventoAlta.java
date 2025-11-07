package datatypes;

import java.util.Date;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTEventoAlta(
    String nombre,
    String descripcion,
    Date fechaAlta,
    String sigla,
    String foto,
    Set<String> categorias) {}
