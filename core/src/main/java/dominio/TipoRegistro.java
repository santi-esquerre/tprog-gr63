package dominio;

import datatypes.DTTipoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tipo_registro",
       uniqueConstraints = @UniqueConstraint(name = "uk_tiporeg_nombre_edicion",
                                             columnNames = {"nombre","edicion_id"}))
public class TipoRegistro extends BaseEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "edicion_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_tiporeg_edicion"))
  private Edicion edicion;

  @Column(nullable = false, length = 120) private String nombre;
  @Column(nullable = false, length = 300) private String descripcion;
  @Column(nullable = false) private float costo;
  @Column(nullable = false) private int cupo;

  protected TipoRegistro() {}
  public DTTipoRegistro obtenerDTTipoRegistro() {
    return new DTTipoRegistro(nombre, descripcion, costo, cupo);
  }

  public TipoRegistro(Edicion edicion, String nombre, String descripcion, float costo, int cupo) {
	this.edicion = edicion;
	this.nombre = nombre;
	this.descripcion = descripcion;
	this.costo = costo;
	this.cupo = cupo;
  }
  
  public String getNombre(){ return nombre; }
  public int getCupo(){ return cupo; }
  public float getCosto() { return costo; }

}
