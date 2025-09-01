package datatypes;

import java.io.Serializable;

public record DTUsuarioItemListado(
        String nickname,
        String correo,
        TipoUsuario tipoUsuario
) implements Serializable {
    @Override public String toString() { return nickname + " (" + tipoUsuario + ")"; }
}
