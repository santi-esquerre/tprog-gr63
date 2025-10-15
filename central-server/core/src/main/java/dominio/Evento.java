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
    // Convert categories to names
    Set<String> categoriasNombres = new LinkedHashSet<>();
    for (Categoria cat : categorias) {
      categoriasNombres.add(cat.getNombre());
    }

    // Convert editions to DTEdicion
    Set<datatypes.DTEdicion> dtEdiciones = new LinkedHashSet<>();
    for (Edicion ed : ediciones) {
      dtEdiciones.add(ed.toDTEdicion());
    }

    return new datatypes.DTEventoDetallado(this.toDTEvento(), categoriasNombres, dtEdiciones);
  }
}
