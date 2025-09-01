package dominio;

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
        int codInt;
        try { 
            codInt = Integer.parseInt(codigo); 
        } catch (NumberFormatException e) { 
            codInt = 0; 
        }
        return new DTPatrocinio(fechaRealizacion, monto, codInt, nivel, dtInst);
    }
}
