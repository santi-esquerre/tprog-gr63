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

  @OneToMany(mappedBy = "organizador")
  private Set<Edicion> ediciones = new LinkedHashSet<>();

  protected Organizador() {
  }

  public Organizador(String nickname, String nombre, String correo, String clave, String foto, String desc, String linkWeb) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
    this.sitioWeb = linkWeb;
  }

  public Organizador(String nickname, String nombre, String correo, String clave, String foto,  String desc) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  @Override
  public DTOrganizador toDataType() {
    return new DTOrganizador(nickname, nombre, correo, foto, sitioWeb, descripcion);
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(nickname, correo, foto, TipoUsuario.ORGANIZADOR);
  }

}
