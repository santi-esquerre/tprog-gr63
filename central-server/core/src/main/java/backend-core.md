# datatypes\DTAsistente.java

```java
package datatypes;

import java.util.Date;

@SuppressWarnings("serial")
public class DTAsistente extends DTUsuario {
  private String apellido;
  private Date fechaNacimiento;

  public DTAsistente(
      String nickname,
      String nombre,
      String correo,
      String foto,
      String apellido,
      Date fechaNacimiento) {
    super(nickname, nombre, correo, foto);
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
  }

  public TipoUsuario getTipoUsuario() {
    return TipoUsuario.ASISTENTE;
  }

  public String apellido() {
    return apellido;
  }

  public Date fechaNacimiento() {
    return fechaNacimiento;
  }
}

```

# datatypes\DTEdicion.java

```java
package datatypes;

import java.util.Date;

public record DTEdicion(
    String nombre,
    String sigla,
    Date fechaInicio,
    Date fechaFin,
    Date fechaAlta,
    String ciudad,
    String pais,
    String foto,
    EstadoEdicion estado) {}

```

# datatypes\DTEdicionDetallada.java

```java
// core/src/main/java/datatypes/DTEdicionDetallada.java
package datatypes;

import java.io.Serializable;
import java.util.Set;

/**
 * Versión detallada de una edición. Nota: En UML hereda de DTEdicion; en Java (records) lo
 * modelamos por composición.
 */
public record DTEdicionDetallada(
    DTEdicion edicion, // datos base de la edición
    DTOrganizador organizador, // organizador
    Set<DTTipoRegistro> tiposRegistro, // tipos de registro de la edición
    Set<DTPatrocinio> patrocinios, // patrocinios asociados
    Set<DTRegistro> registros // registros existentes
    ) implements Serializable {}

```

# datatypes\DTEvento.java

```java
package datatypes;

import java.util.Date;

public record DTEvento(
    String nombre, String sigla, String descripcion, Date fechaAlta, String foto) {}

```

# datatypes\DTEventoAlta.java

```java
package datatypes;

import java.util.Date;
import java.util.Set;

public record DTEventoAlta(
    String nombre,
    String descripcion,
    Date fechaAlta,
    String sigla,
    String foto,
    Set<String> categorias) {}

```

# datatypes\DTEventoDetallado.java

```java
package datatypes;

import java.io.Serializable;
import java.util.Set;

public record DTEventoDetallado(DTEvento evento, Set<String> categorias, Set<DTEdicion> ediciones)
    implements Serializable {}

```

# datatypes\DTInstitucion.java

```java
package datatypes;

import java.io.Serializable;

public record DTInstitucion(
    String nombre,
    String descripcion,
    String sitioWeb,
    String foto
) implements Serializable { }

```

# datatypes\DTOrganizador.java

```java
package datatypes;

@SuppressWarnings("serial")
public class DTOrganizador extends DTUsuario {
  private String linkSitioWeb;
  private String descripcion;

  public DTOrganizador(
      String nickname,
      String nombre,
      String correo,
      String foto,
      String linkSitioWeb,
      String descripcion) {
    super(nickname, nombre, correo, foto);
    this.linkSitioWeb = linkSitioWeb;
    this.descripcion = descripcion;
  }

  public TipoUsuario getTipoUsuario() {
    return TipoUsuario.ORGANIZADOR;
  }

  public String linkSitioWeb() {
    return linkSitioWeb;
  }

  public String descripcion() {
    return descripcion;
  }
}

```

# datatypes\DTPatrocinio.java

```java
package datatypes;

import java.io.Serializable;
import java.util.Date;

public record DTPatrocinio(
    Date fechaRealizacion,
    float monto,
    String codigo,
    NivelPatrocinio nivel,
    DTInstitucion institucion
    ) implements Serializable {}

```

# datatypes\DTRegistro.java

```java
package datatypes;

import java.util.Date;

public record DTRegistro(
        Date fecha,
        float costo,
        String nombreEvento,
        String nombreEdicion,
        String nicknameAsistente,
        String fotoAsistente,
        DTTipoRegistro tipoRegistro,
		Boolean usoCodigo) { 
}

```

# datatypes\DTRegistroDetallado.java

```java
package datatypes;

import java.util.Date;
import java.util.Optional;

public record DTRegistroDetallado(
    Date fecha,
    float costo,
    DTEvento evento,
    DTEdicion edicion,
    DTAsistente asistente,
    DTTipoRegistro tipoRegistro,
    Optional<DTPatrocinio> patrocinio) {}

```

# datatypes\DTRegistrosOtorgados.java

```java
package datatypes;

import java.io.Serializable;

public record DTRegistrosOtorgados(DTTipoRegistro tipoRegistro, int cupos)
    implements Serializable {}

```

# datatypes\DTTipoRegistro.java

```java
package datatypes;

public record DTTipoRegistro(String nombre, String descripcion, float costo, int cupo) {

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public float getCosto() {
    return costo;
  }

  public int getCupo() {
    return cupo;
  }
}

```

# datatypes\DTUsuario.java

```java
package datatypes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DTUsuario implements Serializable {
  private String nickname;
  private String nombre;
  private String correo;
  protected String foto;

  public DTUsuario(String nickname, String nombre, String correo, String foto) {
    this.nickname = nickname;
    this.nombre = nombre;
    this.correo = correo;
    this.foto = foto;
  }

  public String nickname() {
    return nickname;
  }

  public String nombre() {
    return nombre;
  }

  public String correo() {
    return correo;
  }

	@Override
	public String toString() {
		return nickname;
	}
	
	public String foto() {
    return foto;
  }
	
	public TipoUsuario getTipo(){ 
		  if (this.getClass().getSimpleName().equals("DTAsistente")) {
			  return TipoUsuario.ASISTENTE;
		  } else {
			  return TipoUsuario.ORGANIZADOR; 
		  }
	
	}
}

```

# datatypes\DTUsuarioItemListado.java

```java
package datatypes;

import java.io.Serializable;

public record DTUsuarioItemListado(
    String nickname, String correo, String foto, TipoUsuario tipoUsuario) implements Serializable {
  @Override
  public String toString() {
    return nickname + " (" + tipoUsuario + ")";
  }
}

```

# datatypes\EstadoEdicion.java

```java
package datatypes;

public enum EstadoEdicion {
  INGRESADA,
  ACEPTADA,
  RECHAZADA
}

```

# datatypes\NivelPatrocinio.java

```java
package datatypes;

public enum NivelPatrocinio {
  PLATINO,
  ORO,
  PLATA,
  BRONCE
}

```

# datatypes\package-info.java

```java
package datatypes;

```

# datatypes\TipoUsuario.java

```java
package datatypes;

public enum TipoUsuario {
  ASISTENTE,
  ORGANIZADOR
}

```

# dominio\Asistente.java

```java
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
    return new DTAsistente(
        getNickname(),
        getNombre(),
        getCorreo(),
        getFoto(),
        apellido,
        Date.from(fechaNacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), getFoto(), TipoUsuario.ASISTENTE);
  }
}

```

# dominio\BaseEntity.java

```java
package dominio;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
  @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
  private Long identifier;

  @Version private long version;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    var now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public Long getId() {
    return identifier;
  }

  public long getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    BaseEntity that = (BaseEntity) object;
    return identifier != null && Objects.equals(identifier, that.identifier);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}

```

# dominio\Categoria.java

```java
package dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "categoria",
    uniqueConstraints = @UniqueConstraint(name = "uk_categoria_nombre", columnNames = "nombre"))
public class Categoria extends BaseEntity {

  @Column(nullable = false, length = 120)
  private String nombre;

  protected Categoria() {}

  public Categoria(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }
}

```

# dominio\Edicion.java

```java
package dominio;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTTipoRegistro;
import datatypes.EstadoEdicion;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
    name = "edicion",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_edicion_nombre_evento",
            columnNames = {"nombre", "evento_id"}))
public class Edicion extends BaseEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "evento_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_edicion_evento"))
  private Evento evento;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 20)
  private String sigla;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private EstadoEdicion estado;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date fechaInicio;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date fechaFin;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date fechaAlta;

  @Column(nullable = false, length = 120)
  private String ciudad;

  @Column(nullable = false, length = 120)
  private String pais;

  @OneToMany(mappedBy = "edicion", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TipoRegistro> tipos = new LinkedHashSet<>();

  @OneToMany(mappedBy = "edicion", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Registro> registros = new LinkedHashSet<>();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "organizador_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_edicion_organizador"))
  private Organizador organizador; // Asociación con Organizador

  @OneToMany(mappedBy = "edicion", fetch = FetchType.LAZY)
  private Set<Patrocinio> patrocinios = new LinkedHashSet<>(); // Asociación con Patrocinio

  @Column(name = "foto", length = 230, nullable = false)
  private String foto;

  protected Edicion() {}

  public DTEvento obtenerDTEvento() {
    return evento.obtenerDTEvento();
  }

  // DCD
  public DTEdicion obtenerDTEdicion() {
    return new DTEdicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, foto, estado);
  }

  public Set<DTTipoRegistro> listarDTTiposDeRegistro() {
    Set<DTTipoRegistro> out = new LinkedHashSet<>();
    for (TipoRegistro tipoRegistro : tipos) out.add(tipoRegistro.obtenerDTTipoRegistro());
    return out;
  }

  public Set<DTAsistente> obtenerDTAsistentes() {
    Set<DTAsistente> out = new LinkedHashSet<>();
    for (Registro registro : registros) out.add(registro.obtenerDTAsistente());
    return out;
  }

  public Set<datatypes.DTRegistro> obtenerDTRegistros() {
    Set<datatypes.DTRegistro> out = new LinkedHashSet<>();
    for (Registro registro : registros) {
      boolean usoCodigo = registro.getPatrocinio()!= null;
      out.add(new datatypes.DTRegistro(
          registro.getFecha(),
          registro.getCosto(),
          this.getEvento().getNombre(),
          this.getNombre(),
          registro.getAsistente().getNickname(),
          registro.getAsistente().getFoto(),
          registro.getTipo().obtenerDTTipoRegistro(),
          usoCodigo)
		  );
    }
    return out;
  }

  public boolean cupoDisponible(String nombreTipo) {
    var tipoRegistro = buscarTipoRegistro(nombreTipo);
    if (tipoRegistro == null) return false;
    long insc = registros.stream().filter(registro -> registro.getTipo().equals(tipoRegistro)).count();
    return insc < tipoRegistro.getCupo();
  }

  public boolean verificarNoRegistro(String nickname) {
    return registros.stream().noneMatch(registro -> registro.getAsistente().getNickname().equals(nickname));
  }

  public void agregarTipoRegistro(TipoRegistro tipoRegistro) {
    tipos.add(tipoRegistro);
  }

  public TipoRegistro buscarTipoRegistro(String nombreTipo) {
    return tipos.stream().filter(tipoRegistro -> tipoRegistro.getNombre().equals(nombreTipo)).findFirst().orElse(null);
  }

  void setEvento(Evento evento) {
    this.evento = evento;
  }

  public String getNombre() {
    return nombre;
  }

  public void addPatrocinio(Patrocinio patrocinio) {
    this.patrocinios.add(patrocinio);
  }

  public datatypes.DTOrganizador obtenerDTOrganizador() {
    return organizador.toDataType();
  }

  public Evento getEvento() {
    return evento;
  }

  public EstadoEdicion getEstado() {
    return estado;
  }

  public void setEstado(EstadoEdicion estado) {
    this.estado = estado;
  }

  public DTEdicion toDTEdicion() {
    return new DTEdicion(
        nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, foto, estado);
  }

  // core/src/main/java/dominio/Edicion.java
  public Edicion(
      Evento evento,
      String nombre,
      String sigla,
      Date fechaInicio,
      Date fechaFin,
      Date fechaAlta,
      String ciudad,
      String pais,
      String foto,
      Organizador organizador) {
    this.evento = Objects.requireNonNull(evento);
    this.nombre = Objects.requireNonNull(nombre);
    this.sigla = Objects.requireNonNull(sigla);
    this.fechaInicio = Objects.requireNonNull(fechaInicio);
    this.fechaFin = Objects.requireNonNull(fechaFin);
    this.fechaAlta = Objects.requireNonNull(fechaAlta);
    this.ciudad = Objects.requireNonNull(ciudad);
    this.pais = Objects.requireNonNull(pais);
    this.organizador = Objects.requireNonNull(organizador);
    this.foto = Objects.requireNonNull(foto);
    this.estado = EstadoEdicion.INGRESADA;
  }

  public datatypes.DTEdicionDetallada toDTEdicionDetallada() {
    // Convert edition
    datatypes.DTEdicion dtEdicion = this.toDTEdicion();

    // Convert organizador
    datatypes.DTOrganizador dtOrganizador = this.obtenerDTOrganizador();

    // Convert tipos de registro
    Set<datatypes.DTTipoRegistro> dtTiposRegistro = this.listarDTTiposDeRegistro();

    Set<datatypes.DTPatrocinio> dtPatrocinios = new LinkedHashSet<>();

    // // Convert registros to DTRegistro
    Set<datatypes.DTRegistro> dtRegistros = this.obtenerDTRegistros();

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }
}

```

# dominio\Evento.java

```java
package dominio;

import datatypes.EstadoEdicion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
    name = "evento",
    uniqueConstraints = @UniqueConstraint(name = "uk_evento_nombre", columnNames = "nombre"))
public class Evento extends BaseEntity {

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(length = 300)
  private String descripcion;

  @Temporal(TemporalType.DATE)
  @Column(name = "fecha_alta", nullable = false)
  private Date fechaAlta;

  @Column(nullable = false, length = 20)
  private String sigla;

  @Column(nullable = false, length = 300)
  private String foto;

  @ManyToMany
  @JoinTable(
      name = "evento_categoria",
      joinColumns =
          @JoinColumn(name = "evento_id", foreignKey = @ForeignKey(name = "fk_ev_cat_evento")),
      inverseJoinColumns =
          @JoinColumn(
              name = "categoria_id",
              foreignKey = @ForeignKey(name = "fk_ev_cat_categoria")))
  private Set<Categoria> categorias = new LinkedHashSet<>();

  @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Edicion> ediciones = new LinkedHashSet<>();

  protected Evento() {}

  public Evento(
      String nombre,
      String descripcion,
      Date fechaAlta,
      String sigla,
      String foto,
      Set<Categoria> cats) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fechaAlta = fechaAlta;
    this.sigla = sigla;
    this.foto = foto;
    if (cats != null) categorias.addAll(cats);
  }

  // DCD
  public datatypes.DTEvento obtenerDTEvento() {
    return new datatypes.DTEvento(nombre, sigla, descripcion, fechaAlta, foto);
  }

  public Set<datatypes.DTEdicion> listarDTEdiciones() {
    Set<datatypes.DTEdicion> out = new LinkedHashSet<>();
    for (Edicion edicion : ediciones) out.add(edicion.obtenerDTEdicion());
    return out;
  }

  public Set<datatypes.DTEdicion> listarDTEdiciones(EstadoEdicion estado) {
    Set<datatypes.DTEdicion> out = new LinkedHashSet<>();
    for (Edicion edicion : ediciones) {
      if (edicion.getEstado() == estado) {
        out.add(edicion.obtenerDTEdicion());
      }
    }
    return out;
  }

  public void addEdicion(Edicion edicion) {
    ediciones.add(edicion);
    edicion.setEvento(this);
  }

  public String getNombre() {
    return nombre;
  }

  public Set<Categoria> getCategorias() {
    return categorias;
  }

  public String getFoto() {
    return foto;
  }

  public datatypes.DTEvento toDTEvento() {
    return new datatypes.DTEvento(nombre, sigla, descripcion, fechaAlta, foto);
  }

  public datatypes.DTEventoDetallado toDTEventoDetallado() {
    return toDTEventoDetallado(false);
  }
  
  public datatypes.DTEventoDetallado toDTEventoDetallado(boolean soloAprobadas) {
    // Convert categories to names
    Set<String> categoriasNombres = new LinkedHashSet<>();
    for (Categoria cat : categorias) {
      categoriasNombres.add(cat.getNombre());
    }

    // Convert editions to DTEdicion
    Set<datatypes.DTEdicion> dtEdiciones = new LinkedHashSet<>();
    for (Edicion ed : ediciones) {
      if (!soloAprobadas || ed.getEstado() == EstadoEdicion.ACEPTADA) dtEdiciones.add(ed.toDTEdicion());
    }

    return new datatypes.DTEventoDetallado(this.toDTEvento(), categoriasNombres, dtEdiciones);
  }

  public void setEdiciones(Set<Edicion> ediciones) {
    this.ediciones = ediciones;
  }
}

```

# dominio\Institucion.java

```java
package dominio;

import datatypes.DTInstitucion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
    name = "institucion",
    uniqueConstraints = @UniqueConstraint(name = "uk_institucion_nombre", columnNames = "nombre"))
public class Institucion extends BaseEntity {

	@Column(nullable = false, length = 120)
	private String nombre;
	
	@Column(nullable = false, length = 300)
	private String descripcion;
	
	@Column(length = 120)
	private String sitioWeb;
	
	@Column(name = "foto", length = 230, nullable = false)
	private String foto;

	protected Institucion() {}
	
	@OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
	private Set<Asistente> asistentes = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
	private Set<Patrocinio> patrocinios = new LinkedHashSet<>();

	
	public Institucion(String nombre, String descripcion, String sitioWeb, String foto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
		this.foto = foto;
	}
	
	public DTInstitucion obtenerDTInstitucion() {
		return new DTInstitucion(nombre, descripcion, sitioWeb, foto);
	}


	// DTO conversion
	public DTInstitucion toDTInstitucion() {
		return new DTInstitucion(nombre, descripcion, sitioWeb, foto);
	}


  public void addAsistente(Asistente asistente) {
    this.asistentes.add(asistente);
  }

  public void addPatrocinio(Patrocinio patrocinio) {
    this.patrocinios.add(patrocinio);
  }

  // Getters
  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public Set<Asistente> getAsistentes() {
    return asistentes;
  }

  public Set<Patrocinio> getPatrocinios() {
    return patrocinios;
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

```

# dominio\Organizador.java

```java
package dominio;

import java.util.LinkedHashSet;
import java.util.Set;

import datatypes.DTOrganizador;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("ORGANIZADOR")
public class Organizador extends Usuario {

  @Column(nullable = false, length = 300)
  private String descripcion;

  @Column(length = 120)
  private String sitioWeb;

  @OneToMany(mappedBy = "organizador")
  private Set<Edicion> ediciones = new LinkedHashSet<>();

  protected Organizador() {
  }

  public Organizador(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String desc,
      String linkWeb) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
    this.sitioWeb = linkWeb;
  }

  public Organizador(
      String nickname, String nombre, String correo, String clave, String foto, String desc) {
    super(nickname, nombre, correo, clave, foto);
    this.descripcion = desc;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public void setLinkSitioWeb(String sitioWeb) {
    this.sitioWeb = sitioWeb;
  }

  @Override
  public DTOrganizador toDataType() {
    return new DTOrganizador(
        getNickname(), getNombre(), getCorreo(), getFoto(), sitioWeb, descripcion);
  }

  @Override
  public DTUsuarioItemListado toDTUsuarioItemListado() {
    return new DTUsuarioItemListado(getNickname(), getCorreo(), getFoto(), TipoUsuario.ORGANIZADOR);
  }
}

```

# dominio\Otorga.java

```java
package dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Otorga extends BaseEntity {
	@Column(nullable = false)
	private int cantidad;
	protected Otorga() {}
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_registro_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_ortoga_tiporegistro"))
	private TipoRegistro tipoRegistro; // Asociación con TipoRegistro
	
	public Otorga(int cant, TipoRegistro tipoRegistro) {
		this.cantidad = cant;
		this.tipoRegistro = tipoRegistro;
	}
	public int getCantidad() { return cantidad; }
	
	public TipoRegistro getTipoRegistro() { return tipoRegistro; }
	
	public void decrementarCantidad() { this.cantidad--; }
	
}

```

# dominio\Patrocinio.java

```java
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
import jakarta.persistence.OneToOne;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "otorga_id", foreignKey = @ForeignKey(name = "fk_otorga_patrocinio"))
	private Otorga otorgan; // Asociación con Otorga
	
	public void addOtorga(Otorga otorga) {
		this.otorgan = otorga;
	}

	public DTPatrocinio toDTPatrocinio(DTInstitucion dtInstitucion) {
        String cod = codigo;
        Date date = Date.from(fechaRealizacion.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DTPatrocinio dtPatrocinio = new DTPatrocinio(date, monto, cod, nivel, dtInstitucion);
        return dtPatrocinio; 
	}
	
	

    public Otorga getOtorgan() { 
    	return otorgan; 
    }
    

  public Patrocinio(
      float monto,
      String codigo,
      NivelPatrocinio nivel,
      LocalDate fechaRealizacion,
      Institucion institucion,
      Edicion edicion) {
    this.monto = monto;
    this.codigo = codigo;
    this.nivel = nivel;
    this.institucion = institucion;
    this.edicion = edicion;
    this.fechaRealizacion = fechaRealizacion;
  }


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


  public void setInstitucion(Institucion institucion) {
    this.institucion = institucion;
  }

  public void setEdicion(Edicion edicion) {
    this.edicion = edicion;
  }


  public void agregarRegistro(Registro registro) {
    this.registro.add(registro);
    registro.setPatrocinio(this);
  }
}

```

# dominio\Registro.java

```java
package dominio;

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
import java.util.Date;

@Entity
@Table(
    name = "registro",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_registro_asistente_edicion",
            columnNames = {"asistente_id", "edicion_id"}))
public class Registro extends BaseEntity {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date fecha;

  @Column(nullable = false)
  private float costo;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "asistente_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_reg_asistente"))
  private Asistente asistente;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "edicion_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_reg_edicion"))
  private Edicion edicion;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "tipo_registro_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_reg_tiporeg"))
  private TipoRegistro tipo;

  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "patrocinio_id",
      nullable = true,
      foreignKey = @ForeignKey(name = "fk_reg_patrocinio"))
  private Patrocinio patrocinio;

  public Registro() {}

  public TipoRegistro getTipo() {
    return tipo;
  }

  public Asistente getAsistente() {
    return asistente;
  }

  public Date getFecha() {
    return fecha;
  }

  public float getCosto() {
    return costo;
  }

  public Edicion getEdicion() {
    return edicion;
  }

  public Patrocinio getPatrocinio() {
    return patrocinio;
  }

  public DTAsistente obtenerDTAsistente() {
    return asistente.toDataType();
  }

  public void setPatrocinio(Patrocinio patrocinio) {
    this.patrocinio = patrocinio;
  }
}

```

# dominio\TipoRegistro.java

```java
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
@Table(
    name = "tipo_registro",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_tiporeg_nombre_edicion",
            columnNames = {"nombre", "edicion_id"}))
public class TipoRegistro extends BaseEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "edicion_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_tiporeg_edicion"))
  private Edicion edicion;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 300)
  private String descripcion;

  @Column(nullable = false)
  private float costo;

  @Column(nullable = false)
  private int cupo;

  protected TipoRegistro() {}

  public TipoRegistro(String nombre, Edicion edicion, String descripcion, float costo, int cupo) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.costo = costo;
    this.cupo = cupo;
    this.edicion = edicion;
  }

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

  public String getNombre() {
    return nombre;
  }

  public int getCupo() {
    return cupo;
  }

  public float getCosto() {
    return costo;
  }

  public void decrementarCupo() {
    cupo--;
  }
}

```

# dominio\Usuario.java

```java
package dominio;

import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import utils.Cryptography;

@Entity
@Table(name = "usuario", indexes = @Index(name = "ix_usuario_nickname", columnList = "nickname"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario extends BaseEntity {

  @Column(nullable = false, unique = true, length = 40)
  private String nickname;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 120)
  private String clave;

  @Column(nullable = false, length = 255)
  private String foto;

  @Column(nullable = false, length = 120)
  private String correo;

  protected Usuario() {
  }

  protected Usuario(String nickname, String nombre, String correo, String clave, String foto) {
    this.nickname = nickname;
    this.nombre = nombre;
    this.correo = correo;
    this.clave = Cryptography.getHash(clave);
    this.foto = foto;
  }

  public String getNickname() {
    return nickname;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public void setClave(String clave) {
    this.clave = Cryptography.getHash(clave);
  }

  public TipoUsuario getTipo() {
    if (this.getClass().getSimpleName().equals("Asistente")) {
      return TipoUsuario.ASISTENTE;
    } else {
      return TipoUsuario.ORGANIZADOR;
    }
  }

  public Boolean validarClave(String clave) {
    return Cryptography.validateHash(clave, this.clave);
  }

  // Métodos abstractos para conversión a DTOs
  public abstract DTUsuario toDataType();

  public abstract DTUsuarioItemListado toDTUsuarioItemListado();

}

```

# exceptions\CantidadCuposDisponiblesException.java

```java
package exceptions;

public class CantidadCuposDisponiblesException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public CantidadCuposDisponiblesException(int cuposDisponibles, String nombreTipoRegistro) {
    super(
        cuposDisponibles > 0
            ? "No hay suficientes cupos disponibles para el tipo de registro " + nombreTipoRegistro
            : "No hay cupos disponibles para el tipo de registro " + nombreTipoRegistro);
  }
}

```

# exceptions\CategoriaRepetidaException.java

```java
package exceptions;

public class CategoriaRepetidaException extends ValidationInputException {
  /** */
  private static final long serialVersionUID = 1L;

  public CategoriaRepetidaException(String message) {
    super(message);
  }
}

```

# exceptions\CategoriasInvalidasException.java

```java
package exceptions;

public class CategoriasInvalidasException extends ValidationInputException {
  /** */
  private static final long serialVersionUID = 1L;

  public CategoriasInvalidasException(String messageString) {
    super(messageString);
  }

  public CategoriasInvalidasException() {
    super("Alguna de las categorías seleccionadas no es válida");
  }
}

```

# exceptions\CostoRegistrosGratuitosException.java

```java
package exceptions;

public class CostoRegistrosGratuitosException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public CostoRegistrosGratuitosException() {
    super("El costo de los registros gratuitos no puede ser mayor al 20% del aporte hecho.");
  }
}

```

# exceptions\EdicionInexistenteException.java

```java
package exceptions;

@SuppressWarnings("serial")
public class EdicionInexistenteException extends ValidationInputException {
  public EdicionInexistenteException(String message) {
    super(message);
  }
}

```

# exceptions\EventoRepetidoException.java

```java
package exceptions;

public class EventoRepetidoException extends ValidationInputException {
  /** */
  private static final long serialVersionUID = 1L;

  public EventoRepetidoException(String messageString) {
    super(messageString);
  }

  public EventoRepetidoException() {
    super("Evento repetido");
  }
}

```

# exceptions\ExistePatrocinioException.java

```java
package exceptions;

public class ExistePatrocinioException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public ExistePatrocinioException(String nombreInstitucion, String nombreEdicion) {
    super(
        "Ya existe un patrocinio de la institucion "
            + nombreInstitucion
            + " con la edicion "
            + nombreEdicion);
  }
}

```

# exceptions\InstitucionNoExistenteException.java

```java
package exceptions;

public class InstitucionNoExistenteException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public InstitucionNoExistenteException(String string) {
    super(string);
  }
}

```

# exceptions\InstitucionRepetidaException.java

```java
package exceptions;

public class InstitucionRepetidaException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public InstitucionRepetidaException(String string) {
    super(string);
  }
}

```

# exceptions\NingunaCategoriaSeleccionadaException.java

```java
package exceptions;

public class NingunaCategoriaSeleccionadaException extends ValidationInputException {
  /** */
  private static final long serialVersionUID = 1L;

  public NingunaCategoriaSeleccionadaException(String messageString) {
    super(messageString);
  }

  public NingunaCategoriaSeleccionadaException() {
    super("No se seleccionó ninguna categoría");
  }
}

```

# exceptions\TipoRegistroInexistenteException.java

```java
package exceptions;

@SuppressWarnings("serial")
public class TipoRegistroInexistenteException extends ValidationInputException {
  public TipoRegistroInexistenteException(String message) {
    super(message);
  }
}

```

# exceptions\TipoRegistroRepetidoException.java

```java
package exceptions;

public class TipoRegistroRepetidoException extends ValidationInputException {
  public TipoRegistroRepetidoException(String mensaje) {
    super(mensaje);
  }
}

```

# exceptions\UsuarioCorreoRepetidoException.java

```java
package exceptions;

public class UsuarioCorreoRepetidoException extends Exception {
  private static final long serialVersionUID = 1L;

  public UsuarioCorreoRepetidoException(String correo) {
    super("El correo electrónico " + correo + " ya está registrado en el sistema.");
  }
}

```

# exceptions\UsuarioInexistenteException.java

```java
package exceptions;

public class UsuarioInexistenteException extends ValidationInputException {
  public UsuarioInexistenteException(String message) {
    super(message);
  }
}

```

# exceptions\UsuarioNicknameRepetidoException.java

```java
package exceptions;

public class UsuarioNicknameRepetidoException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public UsuarioNicknameRepetidoException(String nickname) {
    super("El nickname " + nickname + " ya está registrado en el sistema.");
  }
}

```

# exceptions\ValidationInputException.java

```java
package exceptions;

public class ValidationInputException extends Exception {
  /** */
  private static final long serialVersionUID = -865909574585913612L;

  public ValidationInputException(String message) {
    super(message);
  }

  public ValidationInputException() {
    super("Error de validación");
  }
}

```

# infra\JPA.java

```java
// core/src/main/java/infra/JPA.java
package infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPA {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("eventosPU");

  private JPA() {}

  public static void switchPU(String puName) {
    if (emf != null && emf.isOpen()) {
      emf.close();
      emf = null;
    }
    emf = Persistence.createEntityManagerFactory(puName);
  }

  public static EntityManager entityManager() {
    return emf.createEntityManager();
  }

  public static void close() {
    emf.close();
  }
}

```

# infra\Tx.java

```java
// core/src/main/java/infra/Tx.java
package infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Function;

public final class Tx {
  private Tx() {}

  public static <T> T inTx(Function<EntityManager, T> work) {
    EntityManager entityManager = JPA.entityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      T out = work.apply(entityManager);
      transaction.commit();
      return out;
    } catch (RuntimeException e) {
      if (transaction.isActive()) transaction.rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }
}

```

# interfaces\Factory.java

```java
package interfaces;

import logica.EdicionController;
import logica.EventoController;
import logica.InstitucionController;
import logica.SesionController;
import logica.UsuarioController;
import repos.Repository;

public class Factory {
  // Singleton
  public static final Factory INSTANCE = new Factory();

  private Factory() {}

  public static Factory get() {
    return INSTANCE;
  }

  public IEventoController getIEventoController() {
    return EventoController.get();
  }

  public IEdicionController getIEdicionController() {
    return EdicionController.get();
  }

  public IUsuarioController getIUsuarioController() {
    return UsuarioController.get();
  }

  public IInstitucionController getIInstitucionController() {
    return InstitucionController.get();
  }

  public IRepository getIRepository() {
    return Repository.get();
  }

  public ISesionController getISesionController() {
    return SesionController.get();
  }
}

```

# interfaces\IEdicionController.java

```java
package interfaces;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTTipoRegistro;
import datatypes.EstadoEdicion;
import datatypes.NivelPatrocinio;
import exceptions.CantidadCuposDisponiblesException;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.ExistePatrocinioException;
import exceptions.InstitucionNoExistenteException;
import exceptions.ValidationInputException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface IEdicionController {
  void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro)
      throws ValidationInputException;

  DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro)
      throws ValidationInputException;

  Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion); // fija edicionRecordada

  Set<DTAsistente> mostrarAsistentes(String nombreEdicion); // usa edicionRecordada

  boolean cupoDisponible(String nombreEdicion, String nombreTipoRegistro); // idem

  boolean asistenteNoRegistrado(String nombreEdicion, String nickname); // idem

  void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha);
  
  void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha, String codigo) throws ValidationInputException, IllegalArgumentException;

  void cancelarRegistroEdicionEvento();

  void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte,
      String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
      throws ExistePatrocinioException, CostoRegistrosGratuitosException, CantidadCuposDisponiblesException, InstitucionNoExistenteException;
  
  boolean verificarCodigoPatrocinio(String nombreEdicion, String nombreTipoRegistro, String nickname, String codigo) throws ValidationInputException;

  // boolean altaEdicion(String nombreEvento,
  // String nicknameOrganizador,
  // datatypes.DTEdicion datos) throws exceptions.ValidationInputException;

  // Obtener datos detallados de una edición
  DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEvento, String nombreEdicion)
      throws ValidationInputException;

  // Obtener datos detallados de una edición, identifcada solo por su nombre
  DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEdicion)
      throws ValidationInputException;

  Set<DTEdicion> obtenerEdicionesPorOrganizador(String nicknameOrganizador)
      throws ValidationInputException;

  void decisionSeleccion(String nombreEdicion, EstadoEdicion decision)
      throws ValidationInputException;
}

```

# interfaces\IEventoController.java

```java
package interfaces;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import datatypes.EstadoEdicion;
import exceptions.ValidationInputException;
import java.util.Set;

public interface IEventoController {
  boolean altaCategoria(String nombre) throws ValidationInputException;

  Set<String> obtenerCategorias();

  boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException;

  Set<DTEvento> listarEventos();

  Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException;

  void altaDeCategoria(String nombreCategoria) throws ValidationInputException;

  Set<DTEdicion> mostrarEdiciones(String nombreEvento, EstadoEdicion estado)
      throws ValidationInputException;

  boolean agregarEdicionAEvento(String nombreEvento, String nicknameOrganizador, DTEdicion datos)
      throws ValidationInputException;

  // New detailed data retrieval operation
  DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento)
      throws ValidationInputException;

  DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(String nombreEvento)
      throws ValidationInputException;
}

```

# interfaces\IInstitucionController.java

```java
package interfaces;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import exceptions.InstitucionRepetidaException;
import java.util.Set;

public interface IInstitucionController {
    boolean crearInstitucion(String nombre, String descripcion, String sitioWeb, String foto) throws InstitucionRepetidaException;
    
    Set<DTInstitucion> listarInstituciones();
    
    DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion);
    
    boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion);
}

```

# interfaces\IRepository.java

```java
package interfaces;

public interface IRepository {
  public void switchToTesting();

  public void switchToDefault();

  public void reset();
}

```

# interfaces\ISesionController.java

```java
package interfaces;

public interface ISesionController {
  public boolean iniciarSesion(String username, String password);

  public void cerrarSesion();
}

```

# interfaces\IUsuarioController.java

```java
package interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import exceptions.InstitucionNoExistenteException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioInexistenteException;
import exceptions.UsuarioNicknameRepetidoException;

public interface IUsuarioController {
    // Set<DTUsuarioListado> listadoUsuarios(String nickname) throws Exception;
    DTAsistente seleccionarAsistente(String nickname) throws Exception;

    DTOrganizador seleccionarOrganizador(String nickname) throws Exception;

    boolean verificarNoExistenciaNickname(String nickname) throws Exception;

    boolean verificarNoExistenciaCorreo(String correo) throws Exception;

    void crearAsistente(
            String nickname,
            String nombre,
            String correo,
            String clave,
            String foto,
            String apellido,
            LocalDate fechaNacimiento,
            String nombreInstitucion)
            throws UsuarioCorreoRepetidoException,
            UsuarioNicknameRepetidoException,
            InstitucionNoExistenteException;

    void crearAsistente(
            String nickname,
            String nombre,
            String correo,
            String clave,
            String foto,
            String apellido,
            LocalDate fechaNacimiento)
            throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

    void crearOrganizador(
            String nickname,
            String nombre,
            String correo,
            String clave,
            String foto,
            String descripcion,
            String linkSitioWeb)
            throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

    void crearOrganizador(
            String nickname, String nombre, String correo, String clave, String foto, String descripcion)
            throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

    Set<DTAsistente> mostrarAsistentes();

    DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion);

    // Método para obtener registro detallado de un asistente en una edición
    // específica
    List<DTRegistro> obtenerRegistrosUsuario(String nickname);

    // Método para obtener registros de un usuario
    List<DTUsuarioItemListado> obtenerUsuarios();

    List<DTUsuarioItemListado> obtenerUsuarios(TipoUsuario tipoUsuario);

    DTUsuario obtenerUsuario(String usuario) throws Exception;

    // Métodos de actualización de usuarios
    void actualizarAsistente(
            String nickname,
            String nombre,
            String apellido,
            String correo,
            LocalDate fechaNacimiento,
            String foto,
            String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception;

    void actualizarOrganizador(
            String nickname,
            String nombre,
            String correo,
            String descripcion,
            String linkSitioWeb,
            String foto,
            String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception;

}

```

# interfaces\package-info.java

```java
package interfaces;

```

# logica\CategoriaFactory.java

```java
package logica;

import dominio.Categoria;
import jakarta.persistence.EntityManager;
import repos.CategoriaRepository;

public class CategoriaFactory {
  private static final CategoriaFactory INSTANCE = new CategoriaFactory();

  private CategoriaFactory() {}

  public static CategoriaFactory get() {
    return INSTANCE;
  }

  public boolean crearCategoria(EntityManager entityManager, String nombre) {
    // Chequeo si ya existe una categoria con ese nombre
    if (CategoriaRepository.get().buscarPorNombre(entityManager, nombre) != null) return false;
    var categoriaNueva = new Categoria(nombre);
    entityManager.persist(categoriaNueva);
    return true;
  }
}

```

# logica\EdicionController.java

```java
package logica;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Hibernate;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTTipoRegistro;
import datatypes.EstadoEdicion;
import datatypes.NivelPatrocinio;
import dominio.Edicion;
import dominio.TipoRegistro;
import exceptions.CantidadCuposDisponiblesException;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.EdicionInexistenteException;
import exceptions.ExistePatrocinioException;
import exceptions.InstitucionNoExistenteException;
import exceptions.TipoRegistroRepetidoException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEdicionController;
import repos.EdicionRepository;
import repos.InstitucionRepository;
import repos.TipoRegistroRepository;
import repos.UsuarioRepository;

public final class EdicionController implements IEdicionController {
  private static final EdicionController INSTANCE = new EdicionController();
  private final EdicionRepository edicionRepo = EdicionRepository.get();
  private final UsuarioRepository usuarioRepo = UsuarioRepository.get();
  private final RegistroFactory registroFactory = RegistroFactory.get();
  private final TipoRegistroRepository tipoRegistroRepo = TipoRegistroRepository.get();
  private final PatrocinioFactory patrocinioFactory = PatrocinioFactory.get();

  private EdicionController() {}

  public static EdicionController get() {
    return INSTANCE;
  }

  /**
   * Da de alta un nuevo tipo de registro en una edición existente.
   *
   * @param nombreEdicion El nombre de la edición donde se dará de alta el tipo de registro.
   * @param datosTipoRegistro Los datos del tipo de registro a dar de alta.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws TipoRegistroRepetidoException Si ya existe un tipo de registro con el mismo nombre en
   *     la edición.
   */
  @Override
  public void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro)
      throws ValidationInputException, EdicionInexistenteException, TipoRegistroRepetidoException {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Objects.requireNonNull(datosTipoRegistro, "datosTipoRegistro requerido");

    // Truncate input lengths if they exceed database limits
    String nombre = datosTipoRegistro.nombre();
    String descripcion = datosTipoRegistro.descripcion();

    if (nombre.length() > 120) {
      nombre = nombre.substring(0, 120);
    }
    if (descripcion.length() > 300) {
      descripcion = descripcion.substring(0, 300);
    }

    // Create truncated DTTipoRegistro if necessary
    DTTipoRegistro datosTruncados =
        (!nombre.equals(datosTipoRegistro.nombre())
                || !descripcion.equals(datosTipoRegistro.descripcion()))
            ? new DTTipoRegistro(
                nombre, descripcion, datosTipoRegistro.costo(), datosTipoRegistro.cupo())
            : datosTipoRegistro;

    // Verificar si la edición existe
    var edicion =
        Tx.inTx(
            entityManager -> {
              return edicionRepo.buscarEdicion(entityManager, nombreEdicion);
            });

    if (edicion == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }
    if (Tx.inTx(
        entityManager -> {
          // Verificar si ya existe el tipo de registro
          var tipoRegistro = edicion.buscarTipoRegistro(datosTruncados.nombre());
          // Si no existe, dar de alta el tipo de registro
          return tipoRegistro != null;
        }))
      throw new TipoRegistroRepetidoException(
          "Ya existe el tipo de registro de nombre "
              + datosTruncados.nombre()
              + " en la edición "
              + nombreEdicion);

    Tx.inTx(
        entityManager -> {
          // Alta del tipo de registro
          TipoRegistroFactory.get().altaTipoRegistro(entityManager, edicion, datosTruncados);
          return null;
        });
  }

  /**
   * Consulta un tipo de registro en una edición existente.
   *
   * @param nombreEdicion El nombre de la edición donde se encuentra el tipo de registro.
   * @param nombreTipoRegistro El nombre del tipo de registro a consultar.
   * @return Los datos del tipo de registro consultado.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws ValidationInputException Si el tipo de registro no existe en la edición.
   */
  @Override
  public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro)
      throws EdicionInexistenteException, ValidationInputException {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Objects.requireNonNull(nombreTipoRegistro, "nombreTipoRegistro requerido");
    // Verificar si la edición existe
    var edicion =
        Tx.inTx(
            entityManager -> {
              return edicionRepo.buscarEdicion(entityManager, nombreEdicion);
            });

    if (edicion == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }

    var tipoRegistro =
        Tx.inTx(
            entityManager -> {
              return edicion.buscarTipoRegistro(nombreTipoRegistro);
            });

    if (tipoRegistro == null) {
      throw new ValidationInputException(
          "No existe el tipo de registro de nombre "
              + nombreTipoRegistro
              + " en la edición "
              + nombreEdicion);
    }

    DTTipoRegistro dtTipoRegistro = tipoRegistro.obtenerDTTipoRegistro();

    return dtTipoRegistro;
  }

  @Override
  public Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return (edicion == null) ? Set.of() : edicion.listarDTTiposDeRegistro();
        });
  }

  @Override
  public Set<DTAsistente> mostrarAsistentes(String nombreEdicion) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          // refresco para evitar lazy issues
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return (edicion == null) ? Set.of() : edicion.obtenerDTAsistentes();
        });
  }

  @Override
  public boolean cupoDisponible(String nombreEdicion, String nombreTipoRegistro) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return edicion != null && edicion.cupoDisponible(nombreTipoRegistro);
        });
  }

  @Override
  public boolean asistenteNoRegistrado(String nombreEdicion, String nickname) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return edicion != null && edicion.verificarNoRegistro(nickname);
        });
  }

  @Override
  public void altaRegistroEdicionEvento(
      String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          if (edicion == null) throw new IllegalArgumentException("Edición inexistente");
          var tipoRegistro = edicion.buscarTipoRegistro(nombreTipoRegistro);
          if (tipoRegistro == null) throw new IllegalArgumentException("Tipo de registro inexistente");
          var asistente = usuarioRepo.obtenerAsistente(entityManager, nickname);
          if (asistente == null) throw new IllegalArgumentException("Asistente inexistente");
          if (!edicion.cupoDisponible(nombreTipoRegistro)) throw new IllegalStateException("Sin cupo");
          if (!edicion.verificarNoRegistro(nickname)) throw new IllegalStateException("Ya registrado");
          registroFactory.altaRegistro(entityManager, edicion, asistente, tipoRegistro, tipoRegistro.obtenerDTTipoRegistro().costo(), fecha);
          tipoRegistro.decrementarCupo();
          entityManager.merge(tipoRegistro);
          return null;
        });
  }
  
  @Override
  public void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha, String codigo) throws ValidationInputException, IllegalArgumentException {
	  if (verificarCodigoPatrocinio(nombreEdicion, nombreTipoRegistro, nickname, codigo)) {
		  Tx.inTx(em -> {
			  var edic = edicionRepo.buscarEdicion(em, nombreEdicion);
			  var tiporeg = edic.buscarTipoRegistro(nombreTipoRegistro);
			  var asistente = usuarioRepo.obtenerAsistente(em, nickname);
			  if (!edic.cupoDisponible(nombreTipoRegistro))
				  throw new IllegalStateException("No hay cupo disponible para el tipo de registro '" + nombreTipoRegistro + "' en la edición '" + nombreEdicion + "'");
			  if (!edic.verificarNoRegistro(nickname))
				  throw new IllegalStateException("El asistente '" + nickname + "' ya está registrado en la edición '" + nombreEdicion + "'");
			  var registro = registroFactory.altaRegistro(em, edic, asistente, tiporeg, 0.0f, fecha);
			  var institucion = asistente.getInstitucion();
			  var patrocinio = edicionRepo.obtenerPatrocinio(em, nombreEdicion, institucion.getNombre());
			  var otorga = patrocinio.getOtorgan();
			  tiporeg.decrementarCupo();
			  otorga.decrementarCantidad();
			  patrocinio.agregarRegistro(registro);
			  em.merge(tiporeg);
			  em.merge(otorga);
			  em.merge(patrocinio);
			  em.merge(registro);
			  return null;
		  });
	  }else {
		  throw new ValidationInputException("Código de patrocinio inválido");
	  }
  }

  @Override
  public void altaPatrocinio(
      LocalDate fecha,
      String nombreEdicion,
      String nombreInstitucion,
      Float aporte,
      String nombreTipoRegistro,
      Integer cantGratuitos,
      String codigo,
      NivelPatrocinio nivelPatrocinio)
      throws ExistePatrocinioException,
          CostoRegistrosGratuitosException,
          CantidadCuposDisponiblesException,
          InstitucionNoExistenteException {

    // Chequear si existe la institución
    Objects.requireNonNull(nombreInstitucion, "nombreInstitucion requerido");
    if (Tx.inTx(
        entityManager -> {
          return InstitucionRepository.get().noExisteInstitucion(entityManager, nombreInstitucion);
        })) {
      throw new InstitucionNoExistenteException(
          "No existe la institución '" + nombreInstitucion + "'");
    }

    if (Tx.inTx(
        entityManager -> {
          return edicionRepo.existePatrocinio(entityManager, nombreEdicion, nombreInstitucion);
        })) {
      throw new ExistePatrocinioException(nombreInstitucion, nombreEdicion);
    }

    TipoRegistro tipoRegistro =
        Tx.inTx(
            emt -> {
              return tipoRegistroRepo.buscarTipoRegistro(emt, nombreTipoRegistro, nombreEdicion);
            });
    int cupos = tipoRegistro.getCupo();
    if (cupos < cantGratuitos) {
      throw new CantidadCuposDisponiblesException(cupos, nombreTipoRegistro);
    }

    System.out.println(
        ((tipoRegistro.getCosto() * cantGratuitos) / aporte)
            + "Datos: "
            + tipoRegistro.getCosto()
            + ", "
            + cantGratuitos
            + ", "
            + aporte);

    if (((tipoRegistro.getCosto() * cantGratuitos) / aporte) > 0.2f) {
      throw new CostoRegistrosGratuitosException();
    }

    Tx.inTx(
        entityManager -> {
          patrocinioFactory.crearPatrocinio(
              entityManager,
              fecha,
              nombreEdicion,
              nombreInstitucion,
              aporte,
              tipoRegistro,
              cantGratuitos,
              codigo,
              nivelPatrocinio);
          return null;
        });
  }
  
  @Override
  public boolean verificarCodigoPatrocinio(String nombreEdicion, String nombreTipoRegistro, String nickname, String codigo) throws ValidationInputException {
	  System.out.println("Verificando código de patrocinio...");
	  var asistente = Tx.inTx(em -> {  
			  var asistenteARetornar =  usuarioRepo.obtenerAsistente(em, nickname);
			  if (asistenteARetornar != null) {
				  Hibernate.initialize(asistenteARetornar.getInstitucion());
			  }
			  return asistenteARetornar; });
	  if (asistente == null) {
		  throw new ValidationInputException("No existe el asistente '" + nickname + "'");
	  }
	  var institucion = asistente.getInstitucion();
	  if (institucion == null) {
		  throw new ValidationInputException("El asistente '" + nickname + "' no pertenece a ninguna institución");
	  }
	  var patrocinio = Tx.inTx(em -> {  
			  var patrocinioARetornar = edicionRepo.obtenerPatrocinio(em, nombreEdicion, institucion.getNombre()); 
			  if (patrocinioARetornar != null) {
				  Hibernate.initialize(patrocinioARetornar.getOtorgan());
				  Hibernate.initialize(patrocinioARetornar.getOtorgan().getTipoRegistro());
			  }
			  return patrocinioARetornar;
	  });
	  if (patrocinio == null) {
		  throw new ValidationInputException("No existe un patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "'");
	  }
	  var otorga = patrocinio.getOtorgan();
	  if (otorga.getCantidad() <= 0) {
		  throw new ValidationInputException("El patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "' no tiene registros gratuitos disponibles");
	  }
	  var tiporeg = otorga.getTipoRegistro();
	  if (!tiporeg.getNombre().equals(nombreTipoRegistro)) {
		  throw new ValidationInputException("El patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "' no incluye el tipo de registro '" + nombreTipoRegistro + "'");
	  }
	  return patrocinio.getCodigo().equals(codigo);	  
	  
  }

  @Override
  public void cancelarRegistroEdicionEvento() {
  }

  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEdicion)
      throws ValidationInputException {
    java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    var resultado = Tx.inTx(entityManager -> edicionRepo.obtenerDatosDetalladosEdicion(entityManager, nombreEdicion));
    if (resultado == null) {
      throw new EdicionInexistenteException("No existe la edición '" + nombreEdicion + "'");
    }
    return resultado;
  }

  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(
      String nombreEvento, String nombreEdicion) throws ValidationInputException {
    java.util.Objects.requireNonNull(nombreEvento, "nombreEvento requerido");
    java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    var resultado =
        Tx.inTx(entityManager -> edicionRepo.obtenerDatosDetalladosEdicion(entityManager, nombreEvento, nombreEdicion));
    if (resultado == null) {
      throw new ValidationInputException(
          "No existe la edición '" + nombreEdicion + "' para el evento '" + nombreEvento + "'");
    }
    return resultado;
  }

  @Override
  public Set<datatypes.DTEdicion> obtenerEdicionesPorOrganizador(String nicknameOrganizador)
      throws ValidationInputException {
    java.util.Objects.requireNonNull(nicknameOrganizador, "nicknameOrganizador requerido");
    Set<DTEdicion> resultado =
        Set.of(
            Tx.inTx(entityManager -> edicionRepo.obtenerEdicionesPorOrganizador(entityManager, nicknameOrganizador))
                .stream()
                .map(e -> e.toDTEdicion())
                .toArray(DTEdicion[]::new));
    return resultado;
  }

  @Override
  public void decisionSeleccion(String nombreEdicion, EstadoEdicion decision)
      throws ValidationInputException {
    boolean existeEd =
        Tx.inTx(
            entityManager -> {
              Edicion edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
              if (edicion != null) {
                edicion.setEstado(decision);
                entityManager.merge(edicion);
                return true;
              } else {
                return false;
              }
            });

    if (!existeEd) {
      throw new ValidationInputException("No existe la edición '" + nombreEdicion + "'");
    }
  }
}

```

# logica\EdicionFactory.java

```java
package logica;

import datatypes.DTEdicion;
import dominio.Edicion;
import dominio.Evento;
import dominio.Organizador;
import jakarta.persistence.EntityManager;

public final class EdicionFactory {
  private static final EdicionFactory INSTANCE = new EdicionFactory();

  private EdicionFactory() {}

  public static EdicionFactory get() {
    return INSTANCE;
  }

  public boolean crearEdicion(EntityManager entityManager, Evento evento, Organizador org, DTEdicion dtEdicion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(ed) from Edicion ed where ed.evento = :ev and ed.nombre = :n",
                Long.class)
            .setParameter("ev", evento)
            .setParameter("n", dtEdicion.nombre())
            .getSingleResult();
    if (rowCount > 0) return false;

    var edicion =
        new Edicion(
            evento,
            dtEdicion.nombre(),
            dtEdicion.sigla(),
            dtEdicion.fechaInicio(),
            dtEdicion.fechaFin(),
            dtEdicion.fechaAlta(),
            dtEdicion.ciudad(),
            dtEdicion.pais(),
            dtEdicion.foto(),
            org);
    edicion.setEstado(dtEdicion.estado());
    evento.addEdicion(edicion); // mantiene bidireccional
    entityManager.persist(edicion);
    return true;
  }
}

```

# logica\EventoController.java

```java
package logica;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import datatypes.EstadoEdicion;
import dominio.Categoria;
import exceptions.CategoriaRepetidaException;
import exceptions.CategoriasInvalidasException;
import exceptions.EventoRepetidoException;
import exceptions.NingunaCategoriaSeleccionadaException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEventoController;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import repos.CategoriaRepository;
import repos.EventoRepository;

public final class EventoController implements IEventoController {
  private static final EventoController INSTANCE = new EventoController();
  private final CategoriaRepository catRepo = CategoriaRepository.get();
  private final CategoriaFactory catFactory = CategoriaFactory.get();

  private EventoController() {}

  public static EventoController get() {
    return INSTANCE;
  }

  @Override
  public boolean altaCategoria(String nombre) throws ValidationInputException {
    if (nombre == null) throw new ValidationInputException("Nombre de categoria requerido");
    if (nombre.length() > 120)
      throw new ValidationInputException("Nombre de categoria no puede exceder 120 caracteres");
    CategoriaFactory categoriaFactory = CategoriaFactory.get();
    // CategoriaRepository categoriaRepository = CategoriaRepository.get();
    // Validaciones de entrada
    if (this.obtenerCategorias().contains(nombre))
      throw new CategoriaRepetidaException("Ya existe la categoria " + nombre + "");
    // TO DO: Validar entrada vacía o nula
    return Tx.inTx(entityManager -> categoriaFactory.crearCategoria(entityManager, nombre));
  }

  /**
   * Obtiene los nombres de todas las categorías existentes.
   *
   * @return Set con los nombres de las categorías.
   */
  @Override
  public Set<String> obtenerCategorias() {
    CategoriaRepository catRepo = CategoriaRepository.get();
    return Tx.inTx(
        entityManager ->
            catRepo.obtenerCategorias(entityManager).stream()
                .map(Categoria::getNombre)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
  }

  /**
   * Crea un nuevo evento con los datos proporcionados.
   *
   * @param dta Datos del evento a crear.
   * @return true si el evento fue creado exitosamente, false si ya existe un evento con el mismo
   *     nombre.
   * @throws ValidationInputException si los datos de entrada no son válidos.
   */
  @Override
  public boolean altaEvento(DTEventoAlta dta) throws ValidationInputException {
    EventoFactory eventoFactory = EventoFactory.get();
    // Validaciones de entrada
    if (dta == null) throw new ValidationInputException("DTEventoAlta requerido");
    if (dta.nombre() == null) throw new ValidationInputException("Nombre del evento requerido");
    if (dta.nombre() != null && dta.nombre().length() > 120)
      throw new ValidationInputException("Nombre del evento no puede exceder 120 caracteres");
    if (dta.categorias() == null || dta.categorias().isEmpty())
      throw new NingunaCategoriaSeleccionadaException("Debe seleccionar al menos una categoria");
    if (this.obtenerCategorias().containsAll(dta.categorias()) == false)
      throw new CategoriasInvalidasException("Alguna de las categorias seleccionadas no es válida");
    // Transacción de alta de evento

    boolean res =
        Tx.inTx(
            entityManager -> {
              var cats = mapCategorias(entityManager, dta.categorias());
              return eventoFactory.crearEvento(
                  entityManager,
                  dta.nombre(),
                  dta.descripcion(),
                  dta.fechaAlta(),
                  dta.sigla(),
                  dta.foto(),
                  cats);
            });
    if (!res)
      throw new EventoRepetidoException("Ya existe el evento de nombre " + dta.nombre() + "");
    return res;
  }

  @Override
  public Set<DTEvento> listarEventos() {
    EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(eventoRepo::listarEventos);
  }

  @Override
  public Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException {
    if (nombreEvento == null) throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(
        entityManager -> {
          var evento = eventoRepo.buscarEvento(entityManager, nombreEvento);
          return (evento == null) ? Set.of() : evento.listarDTEdiciones();
        });
  }

  @Override
  public Set<DTEdicion> mostrarEdiciones(String nombreEvento, EstadoEdicion estado)
      throws ValidationInputException {
    if (nombreEvento == null) throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(
        entityManager -> {
          var evento = eventoRepo.buscarEvento(entityManager, nombreEvento);
          return (evento == null) ? Set.of() : evento.listarDTEdiciones(estado);
        });
  }

  @Override
  public DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento) throws ValidationInputException {
	System.out.println("Obteniendo datos detallados del evento: " + nombreEvento);
    if (nombreEvento == null)
      throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    DTEventoDetallado resultado =
        Tx.inTx(entityManager -> eventoRepo.obtenerDatosDetalladosEvento(entityManager, nombreEvento));
    if (resultado == null) throw new ValidationInputException("Evento no encontrado");
    return resultado;
  }
  
  @Override
  public DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(String nombreEvento) throws ValidationInputException {
  System.out.println("Obteniendo datos detallados del evento: " + nombreEvento);
    if (nombreEvento == null)
      throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    DTEventoDetallado resultado =
        Tx.inTx(entityManager -> eventoRepo.obtenerDatosDetalladosEventoAprobadas(entityManager, nombreEvento));
    if (resultado == null) throw new ValidationInputException("Evento no encontrado");
    return resultado;
  }

  private Set<Categoria> mapCategorias(EntityManager entityManager, Set<String> nombres) {
    if (nombres == null || nombres.isEmpty()) return Set.of();
    Set<Categoria> out = new LinkedHashSet<>();
    CategoriaRepository catRepo = CategoriaRepository.get();
    for (String n : nombres) {
      var categoria = catRepo.buscarPorNombre(entityManager, n);
      if (categoria != null) out.add(categoria);
    }
    return out;
  }

  @Override
  public boolean agregarEdicionAEvento(
      String nombreEvento, String nicknameOrganizador, DTEdicion dte)
      throws ValidationInputException {
    if (nombreEvento == null) throw new ValidationInputException("nombreEvento requerido");
    if (nicknameOrganizador == null)
      throw new ValidationInputException("nicknameOrganizador requerido");
    if (dte == null) throw new ValidationInputException("DTEdicion requerido");

    // regla mínima de fechas (coincide con el sentido del CU)
    if (dte.fechaInicio().after(dte.fechaFin()))
      throw new ValidationInputException("fechaInicio no puede ser posterior a fechaFin");

    var edicionFactory = EdicionFactory.get();
    var eventoRepo = repos.EventoRepository.get();
    var usuarioRepo = repos.UsuarioRepository.get();

    try {
      return Tx.inTx(
          entityManager -> {
            var evento = eventoRepo.buscarEvento(entityManager, nombreEvento);
            if (evento == null) throw new RuntimeException("Evento inexistente: " + nombreEvento);

            var org = usuarioRepo.buscarOrganizadorPorNickname(entityManager, nicknameOrganizador);
            if (org == null)
              throw new RuntimeException("Organizador inexistente: " + nicknameOrganizador);

            boolean createOk = edicionFactory.crearEdicion(entityManager, evento, org, dte);
            if (!createOk)
              throw new RuntimeException(
                  "Ya existe la edición '"
                      + dte.nombre()
                      + "' para el evento '"
                      + nombreEvento
                      + "'");

            return true;
          });
    } catch (RuntimeException e) {
      throw new ValidationInputException(e.getMessage());
    }
  }

  @Override
  public void altaDeCategoria(String nombreCategoria) throws ValidationInputException {
    if (nombreCategoria == null)
      throw new ValidationInputException("Nombre de categoria requerido");

    if (Tx.inTx(
        entityManager -> {
          try {
            var categoria = catRepo.buscarPorNombre(entityManager, nombreCategoria);
            return categoria == null;
          } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
          }
        })) {
      Tx.inTx(
          emt -> {
            catFactory.crearCategoria(emt, nombreCategoria);
            return null;
          });
    } else
      throw new ValidationInputException(
          "La categoria " + nombreCategoria + " ya está registrada.");
  }
}

```

# logica\EventoFactory.java

```java
package logica;

import dominio.Categoria;
import dominio.Evento;
import jakarta.persistence.EntityManager;
import java.util.Set;
import repos.EventoRepository;

public final class EventoFactory {
  private static final EventoFactory INSTANCE = new EventoFactory();

  private EventoFactory() {}

  public static EventoFactory get() {
    return INSTANCE;
  }

  public boolean crearEvento(
      EntityManager entityManager,
      String nombre,
      String descripcion,
      java.util.Date fechaAlta,
      String sigla,
      String foto,
      Set<Categoria> categorias) {
    if (EventoRepository.get().existeEvento(entityManager, nombre)) return false;
    var evento = new Evento(nombre, descripcion, fechaAlta, sigla, foto, categorias);
    entityManager.persist(evento);
    return true;
  }
}

```

# logica\InstitucionController.java

```java
package logica;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import exceptions.InstitucionRepetidaException;
import infra.Tx;
import java.util.Set;
import repos.InstitucionRepository;

public class InstitucionController implements interfaces.IInstitucionController {

	private static final InstitucionController INSTANCE = new InstitucionController();
	private final InstitucionRepository institucionRepo = InstitucionRepository.get();
	
	private InstitucionController() {}
	public static InstitucionController get() { return INSTANCE; }
	
	// Legacy method - keeping for compatibility but adapting to interface
	@Override
	public boolean crearInstitucion(String nombre, String descripcion, String sitioWeb, String foto) throws InstitucionRepetidaException {
		if (Tx.inTx(em -> {
			try {
				return institucionRepo.noExisteInstitucion(em, nombre);
			} catch (RuntimeException e) {
				return false;
			}
		})) {
			// Institution doesn't exist, create it
			InstitucionFactory factory = InstitucionFactory.get();
			Tx.inTx(emt -> {
				factory.crearInstitucion(emt, nombre, sitioWeb, descripcion, foto);
				return null;
			});
			return true;
		} else {
			// Institution already exists, throw exception
			throw new InstitucionRepetidaException("La institución " + nombre + " ya está registrada.");
		}
	}
	

  public Set<DTInstitucion> listarInstituciones() {
    return Tx.inTx(entityManager -> institucionRepo.listarInstituciones(entityManager));
  }

  @Override
  public DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion) {
    return Tx.inTx(
        entityManager -> {
          try {
            var inst = institucionRepo.findByNombre(entityManager, nombreInstitucion).orElse(null);
            if (inst == null) return null;

            var patrocinio = inst.getPatrocinios().stream().findFirst().orElse(null);
            if (patrocinio == null) return null;
            return patrocinio.toDTPatrocinio(inst.toDTInstitucion());
          } catch (RuntimeException e) {
            return null;
          }
        });
  }

  @Override
  public boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion) {
    return Tx.inTx(
        entityManager -> {
          try {
            var inst = institucionRepo.findByNombre(entityManager, nombreInstitucion).orElse(null);
            if (inst == null) return false;

            // Would need proper user repository integration
            // For now, placeholder implementation
            return true;
          } catch (RuntimeException e) {
            return false;
          }
        });
  }
}

```

# logica\InstitucionFactory.java

```java
package logica;

import dominio.Institucion;
import jakarta.persistence.EntityManager;

public class InstitucionFactory {

  private static final InstitucionFactory INSTANCE = new InstitucionFactory();

  private InstitucionFactory() {}

  public static InstitucionFactory get() {
    return INSTANCE;
  }

  public void crearInstitucion(
      EntityManager entityManager, String nombre, String sitioweb, String descripcion, String foto) {
    var institucion = new Institucion(nombre, descripcion, sitioweb, foto);
    entityManager.persist(institucion);
  }
}

```

# logica\package-info.java

```java
package logica;

```

# logica\PatrocinioFactory.java

```java
package logica;

import datatypes.NivelPatrocinio;
import dominio.Otorga;
import dominio.Patrocinio;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import repos.EdicionRepository;
import repos.InstitucionRepository;

public class PatrocinioFactory {
  private static final PatrocinioFactory INSTANCE = new PatrocinioFactory();

  private PatrocinioFactory() {}

  public static PatrocinioFactory get() {
    return INSTANCE;
  }

  public void crearPatrocinio(
      EntityManager entityManager,
      LocalDate fecha,
      String nombreEdicion,
      String nombreInstitucion,
      float aporte,
      TipoRegistro tipoRegistro,
      int cantGratuitos,
      String codigo,
      NivelPatrocinio nivelPatrocinio) {
    var repoI = InstitucionRepository.get();
    var repoE = EdicionRepository.get();
    var institucion = repoI.buscarInstitucion(entityManager, nombreInstitucion);
    var edicion = repoE.buscarEdicion(entityManager, nombreEdicion);
    var patrocinio = new Patrocinio(aporte, codigo, nivelPatrocinio, fecha, institucion, edicion);
    institucion.addPatrocinio(patrocinio);
    edicion.addPatrocinio(patrocinio);

    entityManager.merge(institucion);
    entityManager.merge(edicion);

    var otorga = new Otorga(cantGratuitos, tipoRegistro);
    patrocinio.addOtorga(otorga);

    entityManager.persist(otorga);
    entityManager.persist(patrocinio);
  }
}

```

# logica\RegistroFactory.java

```java
package logica;

import dominio.Asistente;
import dominio.Edicion;
import dominio.Registro;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;
import java.util.Date;

public final class RegistroFactory {
  private static final RegistroFactory INSTANCE = new RegistroFactory();

  private RegistroFactory() {}

  public static RegistroFactory get() {
    return INSTANCE;
  }

  public Registro altaRegistro(
      EntityManager entityManager, Edicion edicion, Asistente asistente, TipoRegistro tipoRegistro, float costo, Date fecha) {
    var registro = new Registro();
    // Seteo por reflexión para mantener entidades sin setters públicos
    try {
      var fechaRegistro = Registro.class.getDeclaredField("fecha");
      fechaRegistro.setAccessible(true);
      fechaRegistro.set(registro, fecha);
      var costoRegistro = Registro.class.getDeclaredField("costo");
      costoRegistro.setAccessible(true);
      costoRegistro.set(registro, costo);
      var asistenteRegistro = Registro.class.getDeclaredField("asistente");
      asistenteRegistro.setAccessible(true);
      asistenteRegistro.set(registro, asistente);
      var edicionRegistro = Registro.class.getDeclaredField("edicion");
      edicionRegistro.setAccessible(true);
      edicionRegistro.set(registro, edicion);
      var tipoRegistroNuevo = Registro.class.getDeclaredField("tipo");
      tipoRegistroNuevo.setAccessible(true);
      tipoRegistroNuevo.set(registro, tipoRegistro);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(e);
    }
    entityManager.persist(registro);
    return registro;
  }
}

```

# logica\SesionController.java

```java
package logica;

import infra.Tx;
import interfaces.ISesionController;
import repos.UsuarioRepository;

public class SesionController implements ISesionController {
  public static final SesionController INSTANCE = new SesionController();

  public static SesionController get() {
    return INSTANCE;
  }

  public boolean iniciarSesion(String username, String password) {
    return Tx.inTx(entityManager -> UsuarioRepository.get().comprobarCredenciales(entityManager, username, password));
  }

  public void cerrarSesion() {
    // TODO Implemntar cerrarSesion
  }
}

```

# logica\TipoRegistroFactory.java

```java
package logica;

import datatypes.DTTipoRegistro;
import dominio.Edicion;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class TipoRegistroFactory {
  private static final TipoRegistroFactory INSTANCE = new TipoRegistroFactory();

  public static TipoRegistroFactory get() {
    return INSTANCE;
  }

  public void altaTipoRegistro(EntityManager entityManager, Edicion edicion, DTTipoRegistro datosTipoRegistro) {
    var registro =
        new TipoRegistro(
            edicion,
            datosTipoRegistro.nombre(),
            datosTipoRegistro.descripcion(),
            datosTipoRegistro.costo(),
            datosTipoRegistro.cupo());
    entityManager.persist(registro);
  }
}

```

# logica\UsuarioController.java

```java
package logica;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import dominio.Asistente;
import dominio.Institucion;
import dominio.Organizador;
import dominio.Usuario;
import exceptions.InstitucionNoExistenteException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioInexistenteException;
import exceptions.UsuarioNicknameRepetidoException;
import infra.Tx;
import interfaces.IUsuarioController;
import repos.InstitucionRepository;
import repos.UsuarioRepository;

public class UsuarioController implements IUsuarioController {

  private static UsuarioController INSTANCE = new UsuarioController();

  private UsuarioController() {
  }

  private final UsuarioRepository repoU = UsuarioRepository.get();
  private final UsuarioFactory faU = UsuarioFactory.get();
  private final InstitucionRepository repoI = InstitucionRepository.get();

  public static UsuarioController get() {
    return INSTANCE;
  }

  @Override
  public boolean verificarNoExistenciaNickname(String nickname) {
    return Tx.inTx(entityManager -> repoU.noExisteNickname(entityManager, nickname));
  }

  @Override
  public boolean verificarNoExistenciaCorreo(String correo) {
    return Tx.inTx(entityManager -> repoU.noExisteCorreo(entityManager, correo));
  }

  @Override
  public void crearAsistente(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento,
      String nombreInstitucion)
      throws UsuarioNicknameRepetidoException,
      UsuarioCorreoRepetidoException,
      InstitucionNoExistenteException {
    if (!verificarNoExistenciaNickname(nickname)) {

      throw new UsuarioNicknameRepetidoException(nickname);
    }

    if (!verificarNoExistenciaCorreo(correo)) {
      throw new UsuarioCorreoRepetidoException(nickname);
    }

    try {
      Tx.inTx(
          entityManager -> {
            Institucion institucion = repoI.buscarInstitucion(entityManager, nombreInstitucion);
            if (institucion == null) {
              throw new RuntimeException(new InstitucionNoExistenteException(nombreInstitucion));
            }
            faU.altaAsistente(
                entityManager, nickname, nombre, correo, clave, foto, apellido, fechaNacimiento, institucion);
            return null;
          });
    } catch (RuntimeException e) {
      if (e.getCause() instanceof InstitucionNoExistenteException institucionNoExistenteException) {
        throw institucionNoExistenteException;
      }
      throw e;
    }
  }

  @Override
  public void crearAsistente(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento)
      throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
    if (!verificarNoExistenciaNickname(nickname)) {
      throw new UsuarioNicknameRepetidoException(nickname);
    }

    if (!verificarNoExistenciaCorreo(correo)) {
      throw new UsuarioCorreoRepetidoException(nickname);
    }
    Tx.inTx(
        entityManager -> {
          faU.altaAsistente(entityManager, nickname, nombre, correo, clave, foto, apellido, fechaNacimiento);
          return null;
        });
  }

  public void crearOrganizador(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion,
      String linkSitioWeb)
      throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
    if (!verificarNoExistenciaNickname(nickname)) {
      throw new UsuarioNicknameRepetidoException(nickname);
    }

    if (!verificarNoExistenciaCorreo(correo)) {
      throw new UsuarioCorreoRepetidoException(nickname);
    }
    Tx.inTx(
        entityManager -> {
          faU.altaOrganizador(entityManager, nickname, nombre, correo, clave, foto, descripcion, linkSitioWeb);
          return null;
        });
  }

  public void crearOrganizador(
      String nickname, String nombre, String correo, String clave, String foto, String descripcion)
      throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
    if (!verificarNoExistenciaNickname(nickname)) {
      throw new UsuarioNicknameRepetidoException(nickname);
    }

    if (!verificarNoExistenciaCorreo(correo)) {
      throw new UsuarioCorreoRepetidoException(nickname);
    }

    Tx.inTx(
        entityManager -> {
          faU.altaOrganizador(entityManager, nickname, nombre, correo, clave, foto, descripcion);
          return null;
        });
  }

  @Override
  public Set<DTAsistente> mostrarAsistentes() {
    return Tx.inTx(repoU::listarAsistentes);
  }

  @Override
  public List<DTUsuarioItemListado> obtenerUsuarios(TipoUsuario tipoUsuario) {
    return Tx.inTx(entityManager -> repoU.obtenerUsuarios(entityManager, tipoUsuario));
  }

  @Override
  public List<DTUsuarioItemListado> obtenerUsuarios() {
    return Tx.inTx(entityManager -> repoU.obtenerUsuarios(entityManager));
  }

  @Override
  public List<DTRegistro> obtenerRegistrosUsuario(String nickname) {
    return Tx.inTx(entityManager -> repoU.obtenerRegistrosUsuario(entityManager, nickname));
  }

  @Override
  public DTRegistroDetallado obtenerRegistroDetallado(
      String nicknameAsistente, String nombreEdicion) {
    return Tx.inTx(entityManager -> repoU.obtenerRegistroDetallado(entityManager, nicknameAsistente, nombreEdicion));
  }

  @Override
  public DTAsistente seleccionarAsistente(String nickname) throws Exception {

    Asistente asistente = Tx.inTx(
        entityManager -> {
          return repoU.obtenerAsistente(entityManager, nickname);
        });

    if (asistente == null) {
      throw new UsuarioInexistenteException("No existe un asistente con el nickname: " + nickname);
    }

    return asistente.toDataType();
  }

  @Override
  public DTOrganizador seleccionarOrganizador(String nickname) throws Exception {
    Organizador organizador = Tx.inTx(
        entityManager -> {
          return repoU.obtenerOrganizador(entityManager, nickname);
        });

    if (organizador == null) {
      throw new UsuarioInexistenteException(
          "No existe un organizador con el nickname: " + nickname);
    }

    return organizador.toDataType();
  }

  @Override
  public DTUsuario obtenerUsuario(String usuario) throws Exception {
    Usuario usr = Tx.inTx(em -> {
      return repoU.obtenerUsuario(em, usuario);
    });
    if (usr == null) {
      throw new UsuarioInexistenteException("No existe un usuario con el nickname o correo: " + usuario);
    }

    return usr.toDataType();
  }

  @Override
  public void actualizarAsistente(
      String nickname,
      String nombre,
      String apellido,
      String correo,
      LocalDate fechaNacimiento,
      String foto,
      String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception {

    try {
      Tx.inTx(entityManager -> {
        // 1. Buscar el asistente
        Asistente asistente = repoU.obtenerAsistente(entityManager, nickname);
        if (asistente == null) {
          throw new RuntimeException(
              new UsuarioInexistenteException("No existe el asistente con nickname: " + nickname));
        }

        // 2. Validar correo único (si cambió)
        if (!asistente.getCorreo().equals(correo)) {
          if (!repoU.noExisteCorreo(entityManager, correo)) {
            throw new RuntimeException(new UsuarioCorreoRepetidoException("El correo ya está en uso"));
          }
        }

        // 3. Actualizar datos
        asistente.setNombre(nombre);
        asistente.setApellido(apellido);
        asistente.setCorreo(correo);
        asistente.setFechaNacimiento(fechaNacimiento);

        if (foto != null && !foto.isEmpty()) {
          asistente.setFoto(foto);
        }

        if (clave != null && !clave.isEmpty()) {
          asistente.setClave(clave);
        }

        // 4. Persistir cambios (merge automático en transacción)
        entityManager.merge(asistente);

        return null;
      });
    } catch (RuntimeException e) {
      if (e.getCause() instanceof UsuarioInexistenteException) {
        throw (UsuarioInexistenteException) e.getCause();
      }
      if (e.getCause() instanceof UsuarioCorreoRepetidoException) {
        throw (UsuarioCorreoRepetidoException) e.getCause();
      }
      throw e;
    }
  }

  @Override
  public void actualizarOrganizador(
      String nickname,
      String nombre,
      String correo,
      String descripcion,
      String linkSitioWeb,
      String foto,
      String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception {

    try {
      Tx.inTx(entityManager -> {
        // 1. Buscar el organizador
        Organizador organizador = repoU.obtenerOrganizador(entityManager, nickname);
        if (organizador == null) {
          throw new RuntimeException(
              new UsuarioInexistenteException("No existe el organizador con nickname: " + nickname));
        }

        // 2. Validar correo único (si cambió)
        if (!organizador.getCorreo().equals(correo)) {
          if (!repoU.noExisteCorreo(entityManager, correo)) {
            throw new RuntimeException(new UsuarioCorreoRepetidoException("El correo ya está en uso"));
          }
        }

        // 3. Actualizar datos
        organizador.setNombre(nombre);
        organizador.setCorreo(correo);
        organizador.setDescripcion(descripcion);

        if (linkSitioWeb != null && !linkSitioWeb.isEmpty()) {
          organizador.setLinkSitioWeb(linkSitioWeb);
        }

        if (foto != null && !foto.isEmpty()) {
          organizador.setFoto(foto);
        }

        if (clave != null && !clave.isEmpty()) {
          organizador.setClave(clave);
        }

        // 4. Persistir cambios
        entityManager.merge(organizador);

        return null;
      });
    } catch (RuntimeException e) {
      if (e.getCause() instanceof UsuarioInexistenteException) {
        throw (UsuarioInexistenteException) e.getCause();
      }
      if (e.getCause() instanceof UsuarioCorreoRepetidoException) {
        throw (UsuarioCorreoRepetidoException) e.getCause();
      }
      throw e;
    }
  }

}

```

# logica\UsuarioFactory.java

```java
package logica;

import dominio.Asistente;
import dominio.Institucion;
import dominio.Organizador;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public final class UsuarioFactory {
  private static final UsuarioFactory INSTANCE = new UsuarioFactory();

  private UsuarioFactory() {}

  public static UsuarioFactory get() {
    return INSTANCE;
  }

  public void altaAsistente(
      EntityManager entm,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento,
      Institucion institucion) {
    var asistente = new Asistente(nickname, nombre, apellido, clave, foto, correo, fechaNacimiento);
    asistente.setInstitucion(institucion);
    institucion.addAsistente(asistente);
    entm.persist(asistente);
    entm.merge(institucion);
  }

  public void altaAsistente(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento) {
    var asistente = new Asistente(nickname, nombre, correo, clave, foto, apellido, fechaNacimiento);
    entityManager.persist(asistente);
  }

  public void altaOrganizador(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion,
      String linkSitioWeb) {
    var organizador = new Organizador(nickname, nombre, correo, clave, foto, descripcion, linkSitioWeb);
    entityManager.persist(organizador);
  }

  public void altaOrganizador(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion) {
    var usuario = new Organizador(nickname, nombre, correo, clave, foto, descripcion);
    entityManager.persist(usuario);
  }
}

```

# repos\CategoriaRepository.java

```java
package repos;

import dominio.Categoria;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

public final class CategoriaRepository {
  private static final CategoriaRepository INSTANCE = new CategoriaRepository();

  private CategoriaRepository() {}

  public static CategoriaRepository get() {
    return INSTANCE;
  }

  public Set<Categoria> obtenerCategorias(EntityManager entityManager) {
    return new LinkedHashSet<>(
        entityManager
            .createQuery("select c from Categoria c order by c.nombre", Categoria.class)
            .getResultList());
  }

  public Categoria buscarPorNombre(EntityManager entityManager, String nombre) {
    return entityManager
        .createQuery("select c from Categoria c where c.nombre = :n", Categoria.class)
        .setParameter("n", nombre)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }
}

```

# repos\EdicionRepository.java

```java
package repos;

import datatypes.DTEdicionDetallada;
import dominio.Edicion;
import dominio.Patrocinio;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

public final class EdicionRepository {
  private static final EdicionRepository INSTANCE = new EdicionRepository();

  private EdicionRepository() {}

  public static EdicionRepository get() {
    return INSTANCE;
  }

  public Edicion buscarEdicion(EntityManager entityManager, String nombreEdicion) {
    return entityManager.createQuery(
            """
      select ed from Edicion ed
      left join fetch ed.tipos
      left join fetch ed.registros r
      left join fetch ed.patrocinios
      where ed.nombre = :n
    """,
            Edicion.class)
        .setParameter("n", nombreEdicion)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public TipoRegistro obtenerTipoRegistro(EntityManager entityManager, String nombre) {
    // Stub: in real integration would search for TipoRegistro by name in specific
    // edition
    return entityManager.createQuery(
            "SELECT tr FROM TipoRegistro tr WHERE tr.nombre = :nombre", TipoRegistro.class)
        .setParameter("nombre", nombre)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public DTEdicionDetallada obtenerDatosDetalladosEdicion(EntityManager entityManager, String nombreEdicion) {
    // Query to get the edition with all related data
    var edicion =
        entityManager.createQuery(
                """
		  select ed from Edicion ed
		  left join fetch ed.organizador
		  left join fetch ed.tipos
		  left join fetch ed.registros r
		  left join fetch r.asistente
		  left join fetch r.tipo
		  where ed.nombre = :nombreEdicion
		""",
                dominio.Edicion.class)
            .setParameter("nombreEdicion", nombreEdicion)
            .getResultStream()
            .findFirst()
            .orElse(null);

    if (edicion == null) return null;

    // Get patrocinios for this edition
    var patrocinios =
        entityManager.createQuery(
                """
		  select p from Patrocinio p
		  left join fetch p.institucion
		  where p.edicion = :edicion
		""",
                dominio.Patrocinio.class)
            .setParameter("edicion", edicion)
            .getResultList();

    // Create DTEdicionDetallada manually with patrocinios included
    var dtEdicion = edicion.toDTEdicion();
    var dtOrganizador = edicion.obtenerDTOrganizador();

    var dtTiposRegistro = edicion.listarDTTiposDeRegistro();

    Set<datatypes.DTPatrocinio> dtPatrocinios = new LinkedHashSet<>();
    for (var p : patrocinios) {
      dtPatrocinios.add(p.toDTPatrocinio(p.getInstitucion().toDTInstitucion()));
    }

    var dtRegistros = edicion.obtenerDTRegistros();

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }

  /** Obtiene los datos detallados de una edición específica de un evento */
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(
      EntityManager entityManager, String nombreEvento, String nombreEdicion) {
    // Query to get the edition with all related data
    var edicion =
        entityManager.createQuery(
                """
          select ed from Edicion ed
          left join fetch ed.organizador
          left join fetch ed.tipos
          left join fetch ed.registros r
          left join fetch r.asistente
          left join fetch r.tipo
          where ed.nombre = :nombreEdicion
          and ed.evento.nombre = :nombreEvento
        """,
                dominio.Edicion.class)
            .setParameter("nombreEdicion", nombreEdicion)
            .setParameter("nombreEvento", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    if (edicion == null) return null;

    // Get patrocinios for this edition
    var patrocinios =
        entityManager.createQuery(
                """
          select p from Patrocinio p
          left join fetch p.institucion
          where p.edicion = :edicion
        """,
                dominio.Patrocinio.class)
            .setParameter("edicion", edicion)
            .getResultList();

    // Create DTEdicionDetallada manually with patrocinios included
    var dtEdicion = edicion.toDTEdicion();
    var dtOrganizador = edicion.obtenerDTOrganizador();

    var dtTiposRegistro = edicion.listarDTTiposDeRegistro();

    Set<datatypes.DTPatrocinio> dtPatrocinios = new LinkedHashSet<>();
    for (var p : patrocinios) {
      dtPatrocinios.add(p.toDTPatrocinio(p.getInstitucion().toDTInstitucion()));
    }

    var dtRegistros = edicion.obtenerDTRegistros();

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }

  public boolean existeNombre(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("select count(e) from Edicion e where e.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult()
        > 0;
  }

  public boolean existePatrocinio(
      EntityManager entityManager, String nombreEdicion, String nombreInstitucion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(p) from Patrocinio p where p.edicion.nombre = :ne and p.institucion.nombre = :ni",
                Long.class)
            .setParameter("ne", nombreEdicion)
            .setParameter("ni", nombreInstitucion)
            .getSingleResult();
    return rowCount > 0;
  }
  
  public Patrocinio obtenerPatrocinio(EntityManager entityManager, String nombreEdicion, String nombreInstitucion) {
	  return entityManager.createQuery("select p from Patrocinio p where p.edicion.nombre = :ne and p.institucion.nombre = :ni", Patrocinio.class)
			  .setParameter("ne", nombreEdicion)
			  .setParameter("ni", nombreInstitucion)
			  .getResultStream().findFirst().orElse(null);
  }
  
  public Set<Edicion> obtenerEdicionesPorOrganizador(EntityManager entityManager, String nicknameOrganizador) {
	return new LinkedHashSet<>(entityManager.createQuery("select e from Edicion e where e.organizador.nickname = :ne", Edicion.class)
		.setParameter("ne", nicknameOrganizador)
		.getResultList());
  }
}

```

# repos\EventoRepository.java

```java
package repos;

import dominio.Evento;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class EventoRepository {
  private static final EventoRepository INSTANCE = new EventoRepository();

  private EventoRepository() {}

  public static EventoRepository get() {
    return INSTANCE;
  }

  public boolean existeEvento(EntityManager entityManager, String nombre) {
    Long rowCount =
        entityManager.createQuery("select count(e) from Evento e where e.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult();
    return rowCount > 0;
  }

  public Evento buscarEvento(EntityManager entityManager, String nombreEvento) {
    return entityManager.createQuery(
            """
        select e from Evento e
        left join fetch e.categorias
        where e.nombre = :n
      """,
            Evento.class)
        .setParameter("n", nombreEvento)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Set<datatypes.DTEvento> listarEventos(EntityManager entityManager) {
    return entityManager.createQuery("select e from Evento e order by e.nombre", Evento.class)
        .getResultStream()
        .map(Evento::obtenerDTEvento)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /** Obtiene los datos detallados de un evento, incluyendo categorías y ediciones */
  public datatypes.DTEventoDetallado obtenerDatosDetalladosEvento(
      EntityManager entityManager, String nombreEvento) {
    Evento evento =
        entityManager.createQuery(
                """
        select e from Evento e
        left join fetch e.categorias
        left join fetch e.ediciones
        where e.nombre = :n
      """,
                Evento.class)
            .setParameter("n", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    return (evento != null) ? evento.toDTEventoDetallado() : null;
  }
  
  public datatypes.DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(
      EntityManager entityManager, String nombreEvento) {
    Evento evento =
        entityManager.createQuery(
                """
        select e from Evento e
        left join fetch e.categorias
        left join fetch e.ediciones
        where e.nombre = :n
      """,
                Evento.class)
            .setParameter("n", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    return (evento != null) ? evento.toDTEventoDetallado(true) : null;
  }
}

```

# repos\InstitucionRepository.java

```java
package repos;

import datatypes.DTInstitucion;
import dominio.Institucion;
import infra.Tx;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class InstitucionRepository {
  private static final InstitucionRepository INSTANCE = new InstitucionRepository();

  private InstitucionRepository() {}

  public static InstitucionRepository get() {
    return INSTANCE;
  }

  public Institucion buscarInstitucion(String nombre) {
    return Tx.inTx(
        entityManager ->
            entityManager.createQuery("select i from Institucion i where i.nombre = :n", Institucion.class)
                .setParameter("n", nombre)
                .getResultStream()
                .findFirst()
                .orElse(null));
  }

  public Institucion buscarInstitucion(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("select i from Institucion i where i.nombre = :n", Institucion.class)
        .setParameter("n", nombre)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public boolean crearInstitucion(EntityManager entityManager, String nombre, String descripcion, String sitioWeb, String foto) {
      if (!noExisteInstitucion(entityManager, nombre)) return false;
      Institucion institucion = new Institucion(nombre, descripcion, sitioWeb, foto);
      entityManager.persist(institucion);
      return true;
  }
  
  public boolean noExisteInstitucion(EntityManager entityManager, String nombre) {
    Long rowCount =
        entityManager.createQuery("select count(i) from Institucion i where i.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult();
    return rowCount == 0;
  }


  public Optional<Institucion> findByNombre(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Institucion.class)
        .setParameter("nombre", nombre)
        .getResultStream()
        .findFirst();
  }

  public List<Institucion> getAllInstituciones(EntityManager entityManager) {
    return entityManager.createQuery("SELECT i FROM Institucion i ORDER BY i.nombre", Institucion.class)
        .getResultList();
  }

  public boolean noExistePatrocinio(
      EntityManager entityManager, String nombreInstitucion, String nombreEdicion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(p.edicion) from Patrocinio p where p.institucion.nombre = :ni and p.edicion.nombre = :ne",
                Long.class)
            .setParameter("ni", nombreInstitucion)
            .setParameter("ne", nombreEdicion)
            .getSingleResult();
    return rowCount == 0;
  }

  public Set<DTInstitucion> listarInstituciones(EntityManager entityManager) {
    return entityManager.createQuery("select i from Institucion i order by i.nombre", Institucion.class)
        .getResultStream()
        .map(Institucion::obtenerDTInstitucion)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}

```

# repos\package-info.java

```java
package repos;

```

# repos\Repository.java

```java
package repos;

import infra.JPA;
import infra.Tx;

public class Repository implements interfaces.IRepository {

  private static final Repository INSTANCE = new Repository();

  private Repository() {}

  public static Repository get() {
    return INSTANCE;
  }

  @Override
  public void switchToTesting() {
    JPA.switchPU("testPU");
  }

  @Override
  public void switchToDefault() {
    JPA.switchPU("eventosPU");
  }

  @Override
  public void reset() {
    Tx.inTx(
        entityManager -> {
          entityManager.createNativeQuery(
                  " BEGIN;\r\n"
                      + "    TRUNCATE TABLE registro, edicion, evento_categoria, evento, categoria, tipo_registro, institucion, usuario, patrocinio RESTART IDENTITY CASCADE;\r\n"
                      + "    COMMIT;")
              .executeUpdate();
          return null;
        });
  }
}

```

# repos\TipoRegistroRepository.java

```java
package repos;

import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public class TipoRegistroRepository {
  private static final TipoRegistroRepository INSTANCE = new TipoRegistroRepository();

  private TipoRegistroRepository() {}

  public static TipoRegistroRepository get() {
    return INSTANCE;
  }

  public TipoRegistro buscarTipoRegistro(
      EntityManager entityManager, String nombreTipoRegistro, String nombreEdicion) {
    return entityManager.createQuery(
            "select tr from TipoRegistro tr where tr.nombre = :nt and tr.edicion.nombre = :ne",
            TipoRegistro.class)
        .setParameter("nt", nombreTipoRegistro)
        .setParameter("ne", nombreEdicion)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }
}

```

# repos\UsuarioRepository.java

```java
package repos;

import datatypes.DTAsistente;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import dominio.Asistente;
import dominio.Organizador;
import dominio.Registro;
import dominio.Usuario;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class UsuarioRepository {
  private static final UsuarioRepository INSTANCE = new UsuarioRepository();

  private UsuarioRepository() {}

  public static UsuarioRepository get() {
    return INSTANCE;
  }

  public Asistente obtenerAsistente(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select a from Asistente a where a.nickname = :n", Asistente.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Organizador obtenerOrganizador(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select o from Organizador o where o.nickname = :n", Organizador.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public boolean noExisteNickname(EntityManager entityManager, String nickname) {
    Long rowCount =
        entityManager.createQuery("select count(u) from Usuario u where u.nickname = :n", Long.class)
            .setParameter("n", nickname)
            .getSingleResult();
    return rowCount == 0;
  }

  public boolean noExisteCorreo(EntityManager entityManager, String correo) {
    Long rowCount =
        entityManager.createQuery("select count(u) from Usuario u where u.correo = :c", Long.class)
            .setParameter("c", correo)
            .getSingleResult();
    return rowCount == 0;
  }

  public Set<DTAsistente> listarAsistentes(EntityManager entityManager) {
    return entityManager.createQuery("select a from Asistente a order by a.nombre", Asistente.class)
        .getResultStream()
        .map(Asistente::toDataType)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Obtiene usuarios filtrados por tipo y los convierte a DTUsuarioItemListado
   *
   * @param entityManager EntityManager
   * @param tipoUsuario ASISTENTE, ORGANIZADOR
   * @return Lista de DTUsuarioItemListado
   */
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager entityManager, TipoUsuario tipoUsuario) {
    String jpql;

    if (tipoUsuario == TipoUsuario.ASISTENTE) {
      // Obtener solo asistentes
      jpql = "select a from Asistente a order by a.nickname";
    } else {
      // Obtener solo organizadores
      jpql = "select o from Organizador o order by o.nickname";
    }

    return entityManager.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  // Sobrecarga para obtener todos los usuarios sin filtrar por tipo
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager entityManager) {
    String jpql;

    // Obtener todos los usuarios
    jpql = "select u from Usuario u order by u.nickname";

    return entityManager.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  /**
   * Obtiene todos los registros de un usuario por nickname y los convierte a DTRegistro
   *
   * @param entityManager EntityManager
   * @param nickname Nickname del usuario
   * @return Lista de DTRegistro del usuario
   */
  public List<DTRegistro> obtenerRegistrosUsuario(EntityManager entityManager, String nickname) {
    String jpql =
        "SELECT r FROM Registro r "
            + "JOIN r.asistente a "
            + "JOIN r.edicion ed "
            + "JOIN ed.evento ev "
            + "WHERE a.nickname = :nickname "
            + "ORDER BY r.fecha DESC";

    return entityManager.createQuery(jpql, Registro.class)
        .setParameter("nickname", nickname)
        .getResultStream()
        .map(registro -> new DTRegistro(
            registro.getFecha(),
            registro.getCosto(),
            registro.getEdicion().getEvento().getNombre(),
            registro.getEdicion().getNombre(),
            registro.getAsistente().getNickname(),
            registro.getAsistente().getFoto(),
            registro.getTipo().obtenerDTTipoRegistro(),
            registro.getPatrocinio() != null ? true: false))
        .toList();
  }

  /**
   * Obtiene el registro detallado de un asistente en una edición específica
   *
   * @param entityManager EntityManager
   * @param nicknameAsistente Nickname del asistente
   * @param nombreEdicion Nombre de la edición del evento
   * @return DTRegistroDetallado si existe el registro, null si no existe
   */
  public DTRegistroDetallado obtenerRegistroDetallado(
      EntityManager entityManager, String nicknameAsistente, String nombreEdicion) {
    String jpql =
        "SELECT r FROM Registro r "
            + "JOIN FETCH r.asistente a "
            + "JOIN FETCH r.edicion ed "
            + "JOIN FETCH ed.evento ev "
            + "JOIN FETCH r.tipo tr "
            + "LEFT JOIN FETCH r.patrocinio p "
            + "LEFT JOIN FETCH p.institucion i "
            + "WHERE a.nickname = :nickname AND ed.nombre = :nombreEdicion";

    return entityManager.createQuery(jpql, Registro.class)
        .setParameter("nickname", nicknameAsistente)
        .setParameter("nombreEdicion", nombreEdicion)
        .getResultStream()
        .findFirst()
        .map(
            registro -> {
              java.util.Optional<datatypes.DTPatrocinio> dtPatrocinio = java.util.Optional.empty();

              if (registro.getPatrocinio() != null) {
                // Convertir la institución del patrocinio a DTInstitucion
                datatypes.DTInstitucion dtInst =
                    registro.getPatrocinio().getInstitucion().toDTInstitucion();
                dtPatrocinio =
                    java.util.Optional.of(registro.getPatrocinio().toDTPatrocinio(dtInst));
              }

              return new DTRegistroDetallado(
                  registro.getFecha(),
                  registro.getCosto(),
                  registro.getEdicion().getEvento().toDTEvento(),
                  registro.getEdicion().toDTEdicion(),
                  registro.getAsistente().toDataType(),
                  registro.getTipo().obtenerDTTipoRegistro(),
                  dtPatrocinio);
            })
        .orElse(null);
  }

  // core/src/main/java/repos/UsuarioRepository.java
  public Organizador buscarOrganizadorPorNickname(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select o from Organizador o where o.nickname = :n", Organizador.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Boolean comprobarCredenciales(EntityManager entityManager, String usuario, String clave) {
    Usuario usuarioNuevo =
        entityManager.createQuery(
                "select u from Usuario u where u.nickname = :n or u.correo = :n", Usuario.class)
            .setParameter("n", usuario)
            .getResultStream()
            .findFirst()
            .orElse(null);
    if (usuarioNuevo == null) return false;
    return usuarioNuevo.validarClave(clave);
  }
  
  public Usuario obtenerUsuario(EntityManager entityManager, String usuario) {
	  return entityManager.createQuery(
		        "select u from Usuario u where u.nickname = :n or u.correo = :n", Usuario.class)
		        .setParameter("n", usuario)
		        .getResultStream().findFirst().orElse(null);
  }
  
}

```

# utils\Cryptography.java

```java
package utils;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class Cryptography {

  private static final String SECRET = "9Yq$_OZ%iPeP";
  private static final int ROUNDS = 12;

  public static String getHash(String text) {
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, ROUNDS);
    return Password.hash(text).addPepper(SECRET).with(bcrypt).getResult();
  }

  public static Boolean validateHash(String text, String hash) {
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, ROUNDS);
    return Password.check(text, hash).addPepper(SECRET).with(bcrypt);
  }
}

```

