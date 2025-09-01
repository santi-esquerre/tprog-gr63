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
  private Set<Edicion> ediciones = new LinkedHashSet<>(); // Asociación con edicion 
  
  protected Organizador() {}
  
  public Organizador(String nick, String nom, String mail, String desc, String linkWeb) { 
    super(nick, nom, mail);
    this.descripcion = desc;
    this.sitioWeb = linkWeb;
  }
  
  public Organizador(String nick, String nom, String mail, String desc) { 
	super(nick, nom, mail);
	this.descripcion = desc;
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
