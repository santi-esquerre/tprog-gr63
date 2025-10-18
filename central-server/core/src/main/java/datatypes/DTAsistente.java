package datatypes;

import java.util.Date;

@SuppressWarnings("serial")
public class DTAsistente extends DTUsuario {
  private String apellido;
  private Date fechaNacimiento;

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
