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
    if (this.getClass().getSimpleName().equals("DTAsistente")) {
      return TipoUsuario.ASISTENTE;
    } else {
      return TipoUsuario.ORGANIZADOR;
    }

  }
}
