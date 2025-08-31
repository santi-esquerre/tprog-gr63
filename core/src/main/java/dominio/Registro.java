package dominio;

import java.util.Date;

import datatypes.DTAsistente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registro",
       uniqueConstraints = @UniqueConstraint(name = "uk_registro_asistente_edicion",
                                             columnNames = {"asistente_id","edicion_id"}))
public class Registro extends BaseEntity {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date fecha;

  @Column(nullable = false)
  private float costo;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "asistente_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_reg_asistente"))
  private Asistente asistente;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "edicion_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_reg_edicion"))
  private Edicion edicion;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_registro_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_reg_tiporeg"))
  private TipoRegistro tipo;

  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "patrocinio_id", nullable = true,
              foreignKey = @ForeignKey(name = "fk_reg_patrocinio"))
  private Patrocinio patrocinio;

  public Registro() {}

  public DTAsistente obtenerDTAsistente() {
    return new DTAsistente(asistente.getNickname(), asistente.getNombre(),
                           asistente.getApellido(), asistente.getCorreo(), null);
  }

  public TipoRegistro getTipo(){ return tipo; }
  public Asistente getAsistente(){ return asistente; }
  public Date getFecha(){ return fecha; }
  public float getCosto(){ return costo; }
  public Edicion getEdicion(){ return edicion; }
  public Patrocinio getPatrocinio(){ return patrocinio; }
  
  public void setPatrocinio(Patrocinio patrocinio) { 
    this.patrocinio = patrocinio; 
  }
}
