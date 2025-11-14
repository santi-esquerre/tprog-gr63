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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
          usoCodigo, registro.isAsistenciaConfirmada())
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
    ArrayList<datatypes.DTTipoRegistro> dtTiposRegistro = this.listarDTTiposDeRegistro().stream()
    	    .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<datatypes.DTPatrocinio> dtPatrocinios = new ArrayList<>();

    // // Convert registros to DTRegistro
    ArrayList<datatypes.DTRegistro> dtRegistros = this.obtenerDTRegistros().stream()
		.collect(Collectors.toCollection(ArrayList::new));

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }
}
