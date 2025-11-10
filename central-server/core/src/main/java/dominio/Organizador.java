package dominio;

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

  public Organizador(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String desc,
      String linkWeb) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
    this.sitioWeb = linkWeb;
  }

  public Organizador(
      String nickname, String nombre, String correo, String clave, String foto, String desc) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public void setLinkSitioWeb(String sitioWeb) {
    this.sitioWeb = sitioWeb;
  }

  @Override
  public DTOrganizador toDataType() {
    DTOrganizador dtOrg = new DTOrganizador(
        getNickname(), getNombre(), getCorreo(), getFoto(), sitioWeb, descripcion);
    dtOrg.setTipo(TipoUsuario.ORGANIZADOR);
    return dtOrg;
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), getFoto(), TipoUsuario.ORGANIZADOR);
  }
}
