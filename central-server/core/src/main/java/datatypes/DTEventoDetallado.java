package datatypes;

import java.io.Serializable;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTEventoDetallado(DTEvento evento, Set<String> categorias, Set<DTEdicion> ediciones)
    implements Serializable {}
