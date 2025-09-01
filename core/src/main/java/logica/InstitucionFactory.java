package logica;

import dominio.Institucion;
import jakarta.persistence.EntityManager;

public class InstitucionFactory {
	
	private final static InstitucionFactory INSTANCE = new InstitucionFactory();
	private InstitucionFactory() {}
	public static InstitucionFactory get() { return INSTANCE; }
	
	public void crearInstitucion(EntityManager em, String nombre, String sitioweb, String descripcion) {
		var i = new Institucion(nombre, sitioweb, descripcion);
		em.persist(i);
	}

}
