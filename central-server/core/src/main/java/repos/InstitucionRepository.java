package repos;

import datatypes.DTInstitucion;
import dominio.Institucion;
import infra.Tx;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class InstitucionRepository {
  private static final InstitucionRepository INSTANCE = new InstitucionRepository();

  private InstitucionRepository() {}

  public static InstitucionRepository get() {
    return INSTANCE;
  }

  public Institucion buscarInstitucion(String nombre) {
    return Tx.inTx(
        entityManager ->
            entityManager.createQuery("select i from Institucion i where i.nombre = :n", Institucion.class)
                .setParameter("n", nombre)
                .getResultStream()
                .findFirst()
                .orElse(null));
  }

  public Institucion buscarInstitucion(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("select i from Institucion i where i.nombre = :n", Institucion.class)
        .setParameter("n", nombre)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public boolean crearInstitucion(EntityManager em, String nombre, String descripcion, String sitioWeb, String foto) {
      if (!noExisteInstitucion(em, nombre)) return false;
      Institucion institucion = new Institucion(nombre, descripcion, sitioWeb, foto);
      em.persist(institucion);
      return true;
  }
  
  public boolean noExisteInstitucion(EntityManager entityManager, String nombre) {
    Long rowCount =
        entityManager.createQuery("select count(i) from Institucion i where i.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult();
    return rowCount == 0;
  }


  public Optional<Institucion> findByNombre(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Institucion.class)
        .setParameter("nombre", nombre)
        .getResultStream()
        .findFirst();
  }

  public List<Institucion> getAllInstituciones(EntityManager entityManager) {
    return entityManager.createQuery("SELECT i FROM Institucion i ORDER BY i.nombre", Institucion.class)
        .getResultList();
  }

  public boolean noExistePatrocinio(
      EntityManager entityManager, String nombreInstitucion, String nombreEdicion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(p.edicion) from Patrocinio p where p.institucion.nombre = :ni and p.edicion.nombre = :ne",
                Long.class)
            .setParameter("ni", nombreInstitucion)
            .setParameter("ne", nombreEdicion)
            .getSingleResult();
    return rowCount == 0;
  }

  public Set<DTInstitucion> listarInstituciones(EntityManager entityManager) {
    return entityManager.createQuery("select i from Institucion i order by i.nombre", Institucion.class)
        .getResultStream()
        .map(Institucion::obtenerDTInstitucion)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
