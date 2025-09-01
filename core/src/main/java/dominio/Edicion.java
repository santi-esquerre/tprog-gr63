package dominio;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTTipoRegistro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "edicion", uniqueConstraints = @UniqueConstraint(name = "uk_edicion_nombre_evento", columnNames = {
    "nombre", "evento_id" }))
public class Edicion extends BaseEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "evento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_edicion_evento"))
  private Evento evento;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 20)
  private String sigla;

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
  @JoinColumn(name = "organizador_id", nullable = false, foreignKey = @ForeignKey(name = "fk_edicion_organizador"))
  private Organizador organizador; // Asociación con Organizador
  
  @OneToMany(mappedBy = "edicion", fetch = FetchType.LAZY)
  private Set<Patrocinio> patrocinios = new LinkedHashSet<>(); // Asociación con Patrocinio
  
  protected Edicion() {}

  protected Edicion() {
  }

  public DTEvento obtenerDTEvento() {
    return evento.obtenerDTEvento();
  }

  // DCD
  public DTEdicion obtenerDTEdicion() {
    return new DTEdicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
  }

  public Set<DTTipoRegistro> listarDTTiposDeRegistro() {
    Set<DTTipoRegistro> out = new LinkedHashSet<>();
    for (TipoRegistro t : tipos)
      out.add(t.obtenerDTTipoRegistro());
    return out;
  }

  public Set<DTAsistente> obtenerDTAsistentes() {
    Set<DTAsistente> out = new LinkedHashSet<>();
    for (Registro r : registros)
      out.add(r.obtenerDTAsistente());
    return out;
  }

  public Set<datatypes.DTRegistro> obtenerDTRegistros() {
    Set<datatypes.DTRegistro> out = new LinkedHashSet<>();
    for (Registro r : registros) {
      out.add(new datatypes.DTRegistro(
          r.getFecha(),
          r.getCosto(),
          this.getEvento().getNombre(),
          this.getNombre(),
          r.getAsistente().getNickname(),
          r.getTipo().obtenerDTTipoRegistro()));
    }
    return out;
  }

  public boolean cupoDisponible(String nombreTipo) {
    var t = buscarTipoRegistro(nombreTipo);
    if (t == null)
      return false;
    long insc = registros.stream().filter(r -> r.getTipo().equals(t)).count();
    return insc < t.getCupo();
  }

  public boolean verificarNoRegistro(String nickname) {
    return registros.stream().noneMatch(r -> r.getAsistente().getNickname().equals(nickname));
  }

  public void agregarTipoRegistro(TipoRegistro tr) {
    tipos.add(tr);
  }

  public TipoRegistro buscarTipoRegistro(String nombreTipo) {
    return tipos.stream().filter(t -> t.getNombre().equals(nombreTipo)).findFirst().orElse(null);
  }

  
  void setEvento(Evento e){ this.evento = e; }
  public String getNombre(){ return nombre; }
  
  public void addPatrocinio(Patrocinio p) { this.patrocinios.add(p); }

  public datatypes.DTOrganizador obtenerDTOrganizador() {
    return organizador.toDataType();
  }

  void setEvento(Evento e) {
    this.evento = e;
  }

  public String getNombre() {
    return nombre;
  }

  public Evento getEvento() {
    return evento;
  }

  public DTEdicion toDTEdicion() {
    return new DTEdicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
  }

  // core/src/main/java/dominio/Edicion.java
  public Edicion(Evento evento,
      String nombre, String sigla,
      Date fechaInicio, Date fechaFin, Date fechaAlta,
      String ciudad, String pais,
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
  }

  // public datatypes.DTEdicionDetallada toDTEdicionDetallada() {
  // // Convert edition
  // datatypes.DTEdicion dtEdicion = this.toDTEdicion();

  // // Convert organizador
  // datatypes.DTOrganizador dtOrganizador = this.obtenerDTOrganizador();

  // // Convert tipos de registro
  // Set<datatypes.DTTipoRegistro> dtTiposRegistro =
  // this.listarDTTiposDeRegistro();

  // // Get patrocinios - this method should ideally be called from repository
  // with EntityManager
  // // For now, return empty set - the repository method will populate this
  // properly
  // Set<datatypes.DTPatrocinio> dtPatrocinios = new LinkedHashSet<>();

  // // Convert registros to DTRegistro
  // Set<datatypes.DTRegistro> dtRegistros = this.obtenerDTRegistros();

  // return new datatypes.DTEdicionDetallada(
  // dtEdicion,
  // dtOrganizador,
  // dtTiposRegistro,
  // dtPatrocinios,
  // dtRegistros
  // );
  // }

}

