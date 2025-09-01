package repos;

import java.util.LinkedHashSet;
import java.util.Set;

import dominio.Categoria;
import jakarta.persistence.EntityManager;

public final class CategoriaRepository {
  private static final CategoriaRepository INSTANCE = new CategoriaRepository();
  private CategoriaRepository() {}
  public static CategoriaRepository get() { return INSTANCE; }

  public Set<Categoria> obtenerCategorias(EntityManager em) {
    return new LinkedHashSet<>(em.createQuery(
        "select c from Categoria c order by c.nombre", Categoria.class)
        .getResultList());
  }

  public Categoria buscarPorNombre(EntityManager em, String nombre){
    return em.createQuery(
        "select c from Categoria c where c.nombre = :n", Categoria.class)
        .setParameter("n", nombre)
        .getResultStream().findFirst().orElse(null);
  }
}
