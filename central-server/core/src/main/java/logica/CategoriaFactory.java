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
