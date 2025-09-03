package datatypes;

import java.util.Date;

public class DTAsistente extends DTUsuario {
  String apellido;
  Date fechaNacimiento;

  public DTAsistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento) {
    super(nickname, nombre, correo);
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
  }

  public TipoUsuario getTipoUsuario() {
    return TipoUsuario.ASISTENTE;
  }

  @Override
  public String nickname() {
    return nickname;
  }

  @Override
  public String nombre() {
    return nombre;
  }

  @Override
  public String correo() {
    return correo;
  }

  @Override
  public String toString() {
    return nickname;
  }

  public String apellido() {
    return apellido;
  }

  public Date fechaNacimiento() {
    return fechaNacimiento;
  }
}
