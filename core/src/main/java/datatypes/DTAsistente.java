package datatypes;

import java.io.Serializable;
import java.util.Date;

public record DTAsistente(
        String nickname,
        String nombre,
        String correo,
        String apellido,
        Date fechaNacimiento
) implements Serializable {
    @Override public String toString() { return nickname; }
}
