package datatypes;

import java.util.Date;
import java.util.Set;

public record DTEventoAlta(String nombre, String descripcion, Date fechaAlta,
                           String sigla, String foto, Set<String> categorias) {}
