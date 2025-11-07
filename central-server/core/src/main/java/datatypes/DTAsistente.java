package datatypes;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DTAsistente")
public class DTAsistente extends DTUsuario {
  private String apellido;
  private Date fechaNacimiento;

  public DTAsistente() {
	super();
  }
  
  public DTAsistente(
      String nickname,
      String nombre,
      String correo,
      String foto,
      String apellido,
      Date fechaNacimiento) {
    super(nickname, nombre, correo, foto);
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
  }

  public TipoUsuario getTipoUsuario() {
    return TipoUsuario.ASISTENTE;
  }

  public String apellido() {
    return apellido;
  }

  public Date fechaNacimiento() {
    return fechaNacimiento;
  }
}
