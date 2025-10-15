package logica;

import dominio.Institucion;
import jakarta.persistence.EntityManager;

public class InstitucionFactory {

  private static final InstitucionFactory INSTANCE = new InstitucionFactory();

  private InstitucionFactory() {}

  public static InstitucionFactory get() {
    return INSTANCE;
  }

  public void crearInstitucion(
      EntityManager entityManager, String nombre, String sitioweb, String descripcion) {
    var institucion = new Institucion(nombre, descripcion, sitioweb);
    entityManager.persist(institucion);
  }
}
