package dominio;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import datatypes.NivelPatrocinio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "patrocinio")
public class Patrocinio extends BaseEntity {

	@Column(nullable = false)
	private LocalDate fechaRealizacion;
	
	@Column(nullable = false)
	private	float monto;
	
	@Column(nullable = false, length = 120)
	private	String codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private NivelPatrocinio nivel;
	
	protected Patrocinio() {}
	
	@OneToMany(mappedBy = "patrocinio", fetch = FetchType.LAZY)
	private Set<Registro> registro = new LinkedHashSet<>(); // Asociación con Registro
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "institucion_id", nullable = false, 
			foreignKey = @ForeignKey(name = "fk_patrocinio_institucion"))
	private Institucion institucion; // Asociación con Institucion
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "edicion_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_patrocinio_edicion"))
	private Edicion edicion; // Asociación con Edicion
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "otorga_id", foreignKey = @ForeignKey(name = "fk_otorga_patrocinio"))
	private Otorga otorgan; // Asociación con Otorga
	
	/*@OneToMany(mappedBy = "patrocinio")
    private Set<RegistroOtorgado> registrosOtorgados = new LinkedHashSet<>();*/
	
	public Patrocinio(float monto, String codigo, NivelPatrocinio nivel, LocalDate fechaRealizacion, Institucion institucion, Edicion edicion) {
		this.monto = monto;
		this.codigo = codigo;
		this.nivel = nivel;
		this.institucion = institucion;
		this.edicion = edicion;
		this.fechaRealizacion = fechaRealizacion;
	}
	
	public void addOtorga(Otorga object) {
		this.otorgan = object;
	}

	public DTPatrocinio toDTPatrocinio(DTInstitucion dtInstitucion) {
        String cod = codigo;
        Date date = Date.from(fechaRealizacion.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DTPatrocinio dtPatrocinio = new DTPatrocinio(date, monto, cod, nivel, dtInstitucion);
        return dtPatrocinio; 
	}
	
    /*public Set<RegistroOtorgado> getRegistrosOtorgados() { return registrosOtorgados; }*/
    public Otorga getOtorgan() { return otorgan; }

   /* public void agregarRegistroOtorgado(RegistroOtorgado registro) {
        this.registrosOtorgados.add(registro);
        registro.setPatrocinio(this);
    }*/

  @OneToMany(mappedBy = "patrocinio")
  private Set<RegistroOtorgado> registrosOtorgados = new LinkedHashSet<>();

  public Institucion getInstitucion() {
    return institucion;
  }

  public LocalDate getFechaRealizacion() {
    return fechaRealizacion;
  }

  public float getMonto() {
    return monto;
  }

  public String getCodigo() {
    return codigo;
  }

  public NivelPatrocinio getNivel() {
    return nivel;
  }

  public Edicion getEdicion() {
    return edicion;
  }

  public Set<RegistroOtorgado> getRegistrosOtorgados() {
    return registrosOtorgados;
  }

  public void setInstitucion(Institucion institucion) {
    this.institucion = institucion;
  }

  public void setEdicion(Edicion edicion) {
    this.edicion = edicion;
  }

  public void agregarRegistroOtorgado(RegistroOtorgado registro) {
    this.registrosOtorgados.add(registro);
    registro.setPatrocinio(this);
  }

  public void agregarRegistro(Registro registro) {
    this.registro.add(registro);
    registro.setPatrocinio(this);
  }
}
