package dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria",
       uniqueConstraints = @UniqueConstraint(name = "uk_categoria_nombre", columnNames = "nombre"))
public class Categoria extends BaseEntity {

  @Column(nullable = false, length = 120)
  private String nombre;

  protected Categoria() {}
  public Categoria(String nombre) { this.nombre = nombre; }

  public String getNombre() { return nombre; }
}
