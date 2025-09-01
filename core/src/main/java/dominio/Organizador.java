package dominio;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTOrganizador;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("ORGANIZADOR")
public class Organizador extends Usuario {

  @Column(nullable = false, length = 300)
  private String descripcion;

  @Column(length = 120)
  private String sitioWeb;

  // Campos necesarios para el esquema SINGLE_TABLE (valores por defecto)
  @Column(nullable = false)
  private LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);

  @Column(nullable = false, length = 180)
  private String apellido = "N/A";

  @OneToMany(mappedBy = "organizador")
  private Set<Edicion> ediciones = new LinkedHashSet<>();

  protected Organizador() {
  }

  public Organizador(String nick, String nom, String mail, String desc, String linkWeb) {
    super(nick, nom, mail);
    this.descripcion = desc;
    this.sitioWeb = linkWeb;
  }

  public Organizador(String nick, String nom, String mail, String desc) {
    super(nick, nom, mail);
    this.descripcion = desc;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  @Override
  public DTOrganizador toDataType() {
    return new DTOrganizador(getNickname(), getNombre(), getCorreo(), sitioWeb, descripcion);
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), TipoUsuario.ORGANIZADOR);
  }

}
