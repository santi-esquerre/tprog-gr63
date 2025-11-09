package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DTOrganizador")
public class DTOrganizador extends DTUsuario {
  private String linkSitioWeb;
  private String descripcion;
  
  public DTOrganizador() {
	  super();
  }

  public DTOrganizador(
      String nickname,
      String nombre,
      String correo,
      String foto,
      String linkSitioWeb,
      String descripcion) {
    super(nickname, nombre, correo, foto);
    this.linkSitioWeb = linkSitioWeb;
    this.descripcion = descripcion;
  }

  public TipoUsuario getTipoUsuario() {
    return TipoUsuario.ORGANIZADOR;
  }

  public String linkSitioWeb() {
    return linkSitioWeb;
  }

  public String descripcion() {
    return descripcion;
  }

  // JavaBeans-style getters for JSP EL compatibility
  public String getLinkSitioWeb() {
    return linkSitioWeb;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
