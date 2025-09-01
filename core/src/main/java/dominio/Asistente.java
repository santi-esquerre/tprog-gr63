package dominio;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTAsistente;
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
  
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "institucion_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_asistente_institucion"))
  private Institucion institucion; // Asociación con Institucion
  
  @OneToMany(mappedBy = "asistente")
  private Set<Registro> registros = new LinkedHashSet<>(); // Asociación con Registro
  
  protected Asistente() {}
  
  public Asistente(String nick, String nom, String ape, String mail, LocalDate fnac) { 
    super(nick, nom, mail);
    this.apellido = ape;
    this.fechaNacimiento = fnac;
  }
  
  public String getApellido() { return apellido; }
  
  public LocalDate getFechaNacimiento() { return fechaNacimiento; }
  
  public void setInstitucion(Institucion inst) { this.institucion = inst; }

  @Override
  public DTAsistente toDataType() {
    // Convertir LocalDate a Date para compatibilidad con el record DTAsistente
    Date fechaNac = java.sql.Date.valueOf(fechaNacimiento);
    return new DTAsistente(getNickname(), getNombre(), getCorreo(), apellido, fechaNac);
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), TipoUsuario.ASISTENTE);
  }
}
