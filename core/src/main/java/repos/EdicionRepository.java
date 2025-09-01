package repos;

import dominio.Edicion;
import jakarta.persistence.EntityManager;

public final class EdicionRepository {
  private static final EdicionRepository INSTANCE = new EdicionRepository();
  private EdicionRepository() {}
  public static EdicionRepository get() { return INSTANCE; }

  public Edicion buscarEdicion(EntityManager em, String nombreEdicion){
    return em.createQuery("""
      select ed from Edicion ed
      left join fetch ed.tipos
      left join fetch ed.registros r
      left join fetch ed.patrocinios
      where ed.nombre = :n
    """, Edicion.class).setParameter("n", nombreEdicion)
      .getResultStream().findFirst().orElse(null);
  }
  
  public boolean existePatrocinio(EntityManager em, String nombreEdicion, String nombreInstitucion) {
	  Long c = em.createQuery("select count(p) from Patrocinio p where p.edicion.nombre = :ne and p.institucion.nombre = :ni", Long.class)
			  .setParameter("ne", nombreEdicion)
			  .setParameter("ni", nombreInstitucion)
			  .getSingleResult();
	  return c > 0;
  }
}
