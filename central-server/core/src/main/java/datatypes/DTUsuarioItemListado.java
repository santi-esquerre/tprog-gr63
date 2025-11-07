package datatypes;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTUsuarioItemListado(
    String nickname, String correo, String foto, TipoUsuario tipoUsuario) implements Serializable {
  @Override
  public String toString() {
    return nickname + " (" + tipoUsuario + ")";
  }
}
