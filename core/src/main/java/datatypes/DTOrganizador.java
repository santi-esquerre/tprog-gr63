<<<<<<< HEAD
package datatypes;

import java.util.Date;

public record DTOrganizador(String nickname, String nombre, String linkSitioWeb,
        String correo, String descripcion) {

}
=======
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
>>>>>>> santi
