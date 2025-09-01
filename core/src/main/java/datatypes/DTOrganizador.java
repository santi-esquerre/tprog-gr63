package datatypes;

import java.io.Serializable;
import java.util.Date;

public class DTOrganizador extends DTUsuario implements Serializable {
    String linkSitioWeb;
    String descripcion;
    
    public DTOrganizador(String nickname, String nombre, String correo, String linkSitioWeb, String descripcion) {
    	super(nickname, nombre, correo);
		this.linkSitioWeb = linkSitioWeb;
		this.descripcion = descripcion;
    }
    
    public TipoUsuario getTipoUsuario() { return TipoUsuario.ORGANIZADOR; }
    
  @Override public String nickname() { return nickname; }
  @Override public String nombre() { return nombre; }
  @Override public String correo() { return correo; }
  @Override public String toString() { return nickname; }
}
