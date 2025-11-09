package datatypes;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuarioItemListado implements Serializable {
  private String nickname;
  private String correo;
  private String foto;
  private TipoUsuario tipoUsuario;

  public DTUsuarioItemListado() {} // Constructor sin args requerido por JAXB

  public DTUsuarioItemListado(String nickname, String correo, String foto, TipoUsuario tipoUsuario) {
    this.nickname = nickname;
    this.correo = correo;
    this.foto = foto;
    this.tipoUsuario = tipoUsuario;
  }

  @Override
  public String toString() {
    return nickname + " (" + tipoUsuario + ")";
  }
  
  public String nickname() {
    return nickname;
  }
  
  public String correo() {
    return correo;
  }
  
  public String foto() {
    return foto;
  }
  
  public TipoUsuario tipoUsuario() {
    return tipoUsuario;
  }
}