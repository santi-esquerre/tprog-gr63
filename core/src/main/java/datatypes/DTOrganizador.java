package datatypes;

import java.io.Serializable;

public record DTOrganizador(
        String nickname,
        String nombre,
        String correo,
        String linkSitioWeb,
        String descripcion
) implements Serializable {
    @Override public String toString() { return nickname; }
}
