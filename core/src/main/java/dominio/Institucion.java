package dominio;

import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTInstitucion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "institucion", uniqueConstraints = @UniqueConstraint(name = "uk_institucion_nombre", columnNames = "nombre"))
public class Institucion extends BaseEntity {

	@Column(nullable = false, length = 120)
	private String nombre;
	
	@Column(nullable = false, length = 300)
	private String descripcion;
	
	@Column(length = 120)
	private String sitioWeb;

	protected Institucion() {}
	
	@OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
	private Set<Asistente> asistentes = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
	private Set<Patrocinio> patrocinios = new LinkedHashSet<>();
	
	public Institucion(String nombre, String descripcion, String sitioWeb) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
	}
	
	public Institucion(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	// Getters
	public String getNombre() { return nombre; }
	public String getDescripcion() { return descripcion; }
	public String getSitioWeb() { return sitioWeb; }
	public Set<Asistente> getAsistentes() { return asistentes; }
	public Set<Patrocinio> getPatrocinios() { return patrocinios; }

	// DTO conversion
	public DTInstitucion toDTInstitucion() {
		return new DTInstitucion(nombre, descripcion, sitioWeb);
	}

	// Business methods
	public void agregarAsistente(Asistente asistente) {
		this.asistentes.add(asistente);
		asistente.setInstitucion(this);
	}
	
	public void agregarPatrocinio(Patrocinio patrocinio) {
		this.patrocinios.add(patrocinio);
		patrocinio.setInstitucion(this);
	}
}
