package dominio;

import jakarta.persistence.*;
import java.util.*;
import datatypes.*;

@Entity
@Table(name = "edicion",
       uniqueConstraints = @UniqueConstraint(name = "uk_edicion_nombre_evento",
                                             columnNames = {"nombre","evento_id"}))
public class Edicion extends BaseEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "evento_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_edicion_evento"))
  private Evento evento;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 20)
  private String sigla;

  @Temporal(TemporalType.DATE) @Column(nullable = false) private Date fechaInicio;
  @Temporal(TemporalType.DATE) @Column(nullable = false) private Date fechaFin;
  @Temporal(TemporalType.DATE) @Column(nullable = false) private Date fechaAlta;

  @Column(nullable = false, length = 120) private String ciudad;
  @Column(nullable = false, length = 120) private String pais;

  @OneToMany(mappedBy = "edicion", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TipoRegistro> tipos = new LinkedHashSet<>();

  @OneToMany(mappedBy = "edicion", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Registro> registros = new LinkedHashSet<>();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "organizador_id", nullable = false,
              foreignKey = @ForeignKey(name = "fk_edicion_organizador"))
  private Organizador organizador; // Asociación con Organizador
  
  protected Edicion() {}

  // DCD
  public DTEdicion obtenerDTEdicion() {
    return new DTEdicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
  }
  public Set<DTTipoRegistro> listarDTTiposDeRegistro() {
    Set<DTTipoRegistro> out = new LinkedHashSet<>();
    for (TipoRegistro t : tipos) out.add(t.obtenerDTTipoRegistro());
    return out;
  }
  public Set<DTAsistente> obtenerDTAsistentes() {
    Set<DTAsistente> out = new LinkedHashSet<>();
    for (Registro r : registros) out.add(r.obtenerDTAsistente());
    return out;
  }
  public boolean cupoDisponible(String nombreTipo) {
    var t = buscarTipoRegistro(nombreTipo);
    if (t == null) return false;
    long insc = registros.stream().filter(r -> r.getTipo().equals(t)).count();
    return insc < t.getCupo();
  }
  public boolean verificarNoRegistro(String nickname) {
    return registros.stream().noneMatch(r -> r.getAsistente().getNickname().equals(nickname));
  }
  public TipoRegistro buscarTipoRegistro(String nombreTipo) {
    return tipos.stream().filter(t -> t.getNombre().equals(nombreTipo)).findFirst().orElse(null);
  }

  void setEvento(Evento e){ this.evento = e; }
  public String getNombre(){ return nombre; }
}
