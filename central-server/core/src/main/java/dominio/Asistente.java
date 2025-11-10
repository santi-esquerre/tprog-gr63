package dominio;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "institucion_id", foreignKey = @ForeignKey(name = "fk_asistente_institucion"))
  private Institucion institucion; // Asociación con Institucion

  @OneToMany(mappedBy = "asistente")
  private Set<Registro> registros = new LinkedHashSet<>(); // Asociación con Registro

  protected Asistente() {
  }

  public Asistente(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacmiento) {
    super(nickname, nombre, correo, clave, foto);
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacmiento;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public Institucion getInstitucion() {
    return institucion;
  }

  public void setInstitucion(Institucion inst) {
    this.institucion = inst;
  }

  @Override
  public DTAsistente toDataType() {
    DTAsistente dtAsistente = new DTAsistente(
        getNickname(),
        getNombre(),
        getCorreo(),
        getFoto(),
        apellido,
        Date.from(fechaNacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    dtAsistente.setTipo(TipoUsuario.ASISTENTE);
    return dtAsistente;
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), getFoto(), TipoUsuario.ASISTENTE);
  }
}
