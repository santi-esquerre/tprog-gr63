package repos;
import dominio.Institucion;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTInstitucion;

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
  
  public Institucion buscarInstitucion(EntityManager em, String nombre) {
		return em.createQuery(
			"select i from Institucion i where i.nombre = :n", Institucion.class)
			.setParameter("n", nombre)
			.getResultStream().findFirst().orElse(null);
	  }
  
  public boolean noExisteInstitucion(EntityManager em, String nombre) {
		Long c = em.createQuery(
				"select count(i) from Institucion i where i.nombre = :n", Long.class)
				.setParameter("n", nombre).getSingleResult();
			return c == 0;
  }
  
  public boolean noExistePatrocinio(EntityManager em, String nombreInstitucion, String nombreEdicion) {
	  Long c = em.createQuery(
			  "select count(p.edicion) from Patrocinio p where p.institucion.nombre = :ni and p.edicion.nombre = :ne", Long.class)
			  .setParameter("ni", nombreInstitucion).setParameter("ne", nombreEdicion).getSingleResult();
	  return c == 0;
  }
  
  public Set<DTInstitucion> listarInstituciones(EntityManager em) {
	  return em.createQuery(
			  "select i from Institucion i order by i.nombre", Institucion.class)
			  .getResultStream()
			  .map(Institucion::obtenerDTInstitucion)
			  .collect(Collectors.toCollection(LinkedHashSet::new));
  }
   
}
