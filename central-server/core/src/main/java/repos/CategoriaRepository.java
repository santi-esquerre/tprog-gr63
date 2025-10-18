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
