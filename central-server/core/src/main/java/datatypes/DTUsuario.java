package datatypes;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({DTAsistente.class, DTOrganizador.class})
public class DTUsuario implements Serializable {
  private String nickname;
  private String nombre;
  private String correo;
  protected String foto;
  // Add explicit field so JAXB/wsimport sees the 'tipo' property (fixes missing gettipo during wsimport)
  private TipoUsuario tipo;
  
  public DTUsuario() {
  }

  public DTUsuario(String nickname, String nombre, String correo, String foto) {
    this.nickname = nickname;
    this.nombre = nombre;
    this.correo = correo;
    this.foto = foto;
  }

  public String nickname() {
    return nickname;
  }

  public String nombre() {
    return nombre;
  }

  public String correo() {
    return correo;
  }

  @Override
  public String toString() {
    return nickname;
  }

  public String foto() {
    return foto;
  }

  // JavaBeans-style getters for JSP EL compatibility
  public String getNickname() {
    return nickname;
  }

  public String getNombre() {
    return nombre;
  }

  public String getCorreo() {
    return correo;
  }

  public String getFoto() {
    return foto;
  }

  public TipoUsuario getTipo() {
    // Prefer the explicit field (set by wsimport clients); if unset, compute from runtime subclass as before.
    if (this.tipo != null) {
      return this.tipo;
    }
    if (this.getClass().getSimpleName().equals("DTAsistente")) {
      return TipoUsuario.ASISTENTE;
    } else {
      return TipoUsuario.ORGANIZADOR;
    }
  }
  
  public void setTipo(TipoUsuario tipo) {
    this.tipo = tipo;
  }

}