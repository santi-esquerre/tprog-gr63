package datatypes;

import java.io.Serializable;

public record DTUsuario(
        String nickname,
        String nombre,
        String correo
) implements Serializable {
    @Override public String toString() { return nickname; }
}
