package logica;

import dominio.Categoria;
import jakarta.persistence.EntityManager;
import repos.CategoriaRepository;
public class CategoriaFactory {
	  private static final CategoriaFactory INSTANCE = new CategoriaFactory();
	  private CategoriaFactory() {}
	  public static CategoriaFactory get() { return INSTANCE; }

	  public boolean crearCategoria(EntityManager em,
	                             String nombre){
		 //Chequeo si ya existe una categoria con ese nombre
	    if (CategoriaRepository.get().buscarPorNombre(em, nombre)!=null) return false;
	    var ev = new Categoria(nombre);
	    em.persist(ev);
	    return true;
	  }

}
