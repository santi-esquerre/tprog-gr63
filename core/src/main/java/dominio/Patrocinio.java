package dominio;

import java.time.LocalDate;
import java.util.Set;

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
	private Set<Otorga> otorgan;
	
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

}
