package logica;

import dominio.Institucion;
import jakarta.persistence.EntityManager;

public class InstitucionFactory {

	private final static InstitucionFactory INSTANCE = new InstitucionFactory();

	private InstitucionFactory() {
	}

	public static InstitucionFactory get() {
		return INSTANCE;
	}

	public void crearInstitucion(EntityManager em, String nombre, String sitioweb, String descripcion, String foto) {
		var i = new Institucion(nombre, descripcion, sitioweb, foto);
		em.persist(i);
	}

}
