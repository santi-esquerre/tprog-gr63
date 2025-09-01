package dominio;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("ASISTENTE")
public class Asistente extends Usuario {

  @Column(nullable = false, length = 180)
  private String apellido;

  @Column(nullable = false)
  private LocalDate fechaNacimiento;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "institucion_id",
              foreignKey = @ForeignKey(name = "fk_asistente_institucion"))
  private Institucion institucion; // Asociación con Institucion

  @OneToMany(mappedBy = "asistente")
  private Set<Registro> registros = new LinkedHashSet<>(); // Asociación con Registro

  protected Asistente() {
  }

  public Asistente(String nick, String nom, String ape, String mail, LocalDate fnac) {
    super(nick, nom, mail);
    this.apellido = ape;
    this.fechaNacimiento = fnac;
  }
  
  public String getApellido() { return apellido; }
  
  public LocalDate getFechaNacimiento() { return fechaNacimiento; }
  
  public void setInstitucion(Institucion inst) { this.institucion = inst; }
  
  public datatypes.DTAsistente obtenerDTAsistente() {
	    return new datatypes.DTAsistente(nickname, nombre, apellido, correo, Date.from(fechaNacimiento.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
	  }

  @Override
  public DTAsistente toDataType() {
	return new DTAsistente(nickname, nombre, apellido, correo, Date.from(fechaNacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
	// TODO Auto-generated method stub
	return null;
  }
  
  
}
