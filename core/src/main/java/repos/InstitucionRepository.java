package repos;
import dominio.Institucion;

import infra.Tx;
import jakarta.persistence.EntityManager;

public final class InstitucionRepository {
  private static final InstitucionRepository INSTANCE = new InstitucionRepository();
  private InstitucionRepository() {}
  public static InstitucionRepository get() { return INSTANCE; }

  public Institucion buscarInstitucion(String nombre) {
	return Tx.inTx(em -> em.createQuery(
		"select i from Institucion i where i.nombre = :n", Institucion.class)
		.setParameter("n", nombre)
		.getResultStream().findFirst().orElse(null));
  }
  
  public boolean noExisteInstitucion(EntityManager em, String nombre) {
		Long c = em.createQuery(
				"select count(i) from Institucion i where i.nombre = :n", Long.class)
				.setParameter("n", nombre).getSingleResult();
			return c == 0;
  }
   
}
