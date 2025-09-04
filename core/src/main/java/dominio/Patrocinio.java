package dominio;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
	@Column(nullable = false)
	NivelPatrocinio nivel;
	
	protected Patrocinio() {}
	
	@OneToMany(mappedBy = "patrocinio", fetch = FetchType.LAZY)
	private Set<Registro> registro;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "institucion_id", nullable = false, 
			foreignKey = @ForeignKey(name = "fk_patrocinio_institucion"))
	private Institucion institucion; // Asociación con Institucion
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "edicion_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_patrocinio_edicion"))
	private Edicion edicion; // Asociación con Edicion
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "patrocinio_id", foreignKey = @ForeignKey(name = "fk_otorga_patrocinio"))
	private Set<Otorga> otorgan = new HashSet<>();
	
	@OneToMany(mappedBy = "patrocinio")
    private Set<RegistroOtorgado> registrosOtorgados = new LinkedHashSet<>();
	
	public Patrocinio(float monto, String codigo, NivelPatrocinio nivel, LocalDate fechaRealizacion, Institucion institucion, Edicion edicion) {
		this.monto = monto;
		this.codigo = codigo;
		this.nivel = nivel;
		this.institucion = institucion;
		this.edicion = edicion;
		this.fechaRealizacion = fechaRealizacion;
	}
	
	public void addOtorga(Otorga o) {
		this.otorgan.add(o);
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public DTPatrocinio toDTPatrocinio(DTInstitucion dtInstitucion) {

        int codInt;
        try { 
            codInt = Integer.parseInt(codigo); 
        } catch (NumberFormatException e) { 
            codInt = 0; 
        }
        Date date = Date.from(fechaRealizacion.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DTPatrocinio dtPatrocinio = new DTPatrocinio(date, monto, codInt, nivel, dtInstitucion);
        return dtPatrocinio; 
	}
	
	
	public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public float getMonto() { return monto; }
    public String getCodigo() { return codigo; }
    public NivelPatrocinio getNivel() { return nivel; }
    public Edicion getEdicion() { return edicion; }
    public Set<RegistroOtorgado> getRegistrosOtorgados() { return registrosOtorgados; }
    public Set<Registro> getRegistros() { return registro; }
    
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

/*

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "patrocinio")
public class Patrocinio extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaRealizacion;

    @Column(nullable = false)
    private float monto;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelPatrocinio nivel;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_patrocinio_institucion"))
    private Institucion institucion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "edicion_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_patrocinio_edicion"))
    private Edicion edicion;

    @OneToMany(mappedBy = "patrocinio")
    private Set<RegistroOtorgado> registrosOtorgados = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patrocinio")
    private Set<Registro> registros = new LinkedHashSet<>();

    protected Patrocinio() {}

    public static Patrocinio crearBasico(String codigo) {
        Patrocinio p = new Patrocinio();
        p.codigo = codigo;
        p.nivel = NivelPatrocinio.BRONCE; // placeholder
        p.fechaRealizacion = new Date();
        p.monto = 0f;
        return p;
    }

    // Getters
    public Date getFechaRealizacion() { return fechaRealizacion; }
    public float getMonto() { return monto; }
    public String getCodigo() { return codigo; }
    public NivelPatrocinio getNivel() { return nivel; }
    public Institucion getInstitucion() { return institucion; }
    public Edicion getEdicion() { return edicion; }
    public Set<RegistroOtorgado> getRegistrosOtorgados() { return registrosOtorgados; }
    public Set<Registro> getRegistros() { return registros; }

    // Setters
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
        this.registros.add(registro);
        registro.setPatrocinio(this);
    }

    public DTPatrocinio toDTPatrocinio(DTInstitucion dtInst) {
    }
} */
