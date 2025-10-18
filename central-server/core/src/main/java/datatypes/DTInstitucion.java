package datatypes;

import java.io.Serializable;

public record DTInstitucion(
    String nombre,
    String descripcion,
    String sitioWeb,
    String foto
) implements Serializable { }
