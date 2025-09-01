package datatypes;

import java.io.Serializable;
import java.util.Set;

public record DTEventoDetallado(
    DTEvento evento,
    Set<String> categorias,
    Set<DTEdicion> ediciones
) implements Serializable {}
