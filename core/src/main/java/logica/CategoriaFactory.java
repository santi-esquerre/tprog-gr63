package logica;

import dominio.Categoria;
import jakarta.persistence.EntityManager;

public class CategoriaFactory {
	
	private final static CategoriaFactory INSTANCE = new CategoriaFactory();
	private CategoriaFactory() {}
	public static CategoriaFactory get() { return INSTANCE; }
	
	public void crearCategoria(EntityManager em, String nombre) {
		var c = new Categoria(nombre);
		em.persist(c);
	}

}
