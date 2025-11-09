package repos;

import datatypes.DTEdicionDetallada;
import dominio.Edicion;
import dominio.Patrocinio;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class EdicionRepository {
  private static final EdicionRepository INSTANCE = new EdicionRepository();

  private EdicionRepository() {}

  public static EdicionRepository get() {
    return INSTANCE;
  }

  public Edicion buscarEdicion(EntityManager entityManager, String nombreEdicion) {
    return entityManager.createQuery(
            """
      select ed from Edicion ed
      left join fetch ed.tipos
      left join fetch ed.registros r
      left join fetch ed.patrocinios
      where ed.nombre = :n
    """,
            Edicion.class)
        .setParameter("n", nombreEdicion)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public TipoRegistro obtenerTipoRegistro(EntityManager entityManager, String nombre) {
    // Stub: in real integration would search for TipoRegistro by name in specific
    // edition
    return entityManager.createQuery(
            "SELECT tr FROM TipoRegistro tr WHERE tr.nombre = :nombre", TipoRegistro.class)
        .setParameter("nombre", nombre)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public DTEdicionDetallada obtenerDatosDetalladosEdicion(EntityManager entityManager, String nombreEdicion) {
    // Query to get the edition with all related data
    var edicion =
        entityManager.createQuery(
                """
		  select ed from Edicion ed
		  left join fetch ed.organizador
		  left join fetch ed.tipos
		  left join fetch ed.registros r
		  left join fetch r.asistente
		  left join fetch r.tipo
		  where ed.nombre = :nombreEdicion
		""",
                dominio.Edicion.class)
            .setParameter("nombreEdicion", nombreEdicion)
            .getResultStream()
            .findFirst()
            .orElse(null);

    if (edicion == null) return null;

    // Get patrocinios for this edition
    var patrocinios =
        entityManager.createQuery(
                """
		  select p from Patrocinio p
		  left join fetch p.institucion
		  where p.edicion = :edicion
		""",
                dominio.Patrocinio.class)
            .setParameter("edicion", edicion)
            .getResultList();

    // Create DTEdicionDetallada manually with patrocinios included
    var dtEdicion = edicion.toDTEdicion();
    var dtOrganizador = edicion.obtenerDTOrganizador();

    var dtTiposRegistro = edicion.listarDTTiposDeRegistro().stream()
    		.collect(Collectors.toCollection(ArrayList::new));

    ArrayList<datatypes.DTPatrocinio> dtPatrocinios = new ArrayList<>();
    for (var p : patrocinios) {
      dtPatrocinios.add(p.toDTPatrocinio(p.getInstitucion().toDTInstitucion()));
    }

    var dtRegistros = edicion.obtenerDTRegistros().stream()
		.collect(Collectors.toCollection(ArrayList::new));

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }

  /** Obtiene los datos detallados de una edición específica de un evento */
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(
      EntityManager entityManager, String nombreEvento, String nombreEdicion) {
    // Query to get the edition with all related data
    var edicion =
        entityManager.createQuery(
                """
          select ed from Edicion ed
          left join fetch ed.organizador
          left join fetch ed.tipos
          left join fetch ed.registros r
          left join fetch r.asistente
          left join fetch r.tipo
          where ed.nombre = :nombreEdicion
          and ed.evento.nombre = :nombreEvento
        """,
                dominio.Edicion.class)
            .setParameter("nombreEdicion", nombreEdicion)
            .setParameter("nombreEvento", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    if (edicion == null) return null;

    // Get patrocinios for this edition
    var patrocinios =
        entityManager.createQuery(
                """
          select p from Patrocinio p
          left join fetch p.institucion
          where p.edicion = :edicion
        """,
                dominio.Patrocinio.class)
            .setParameter("edicion", edicion)
            .getResultList();

    // Create DTEdicionDetallada manually with patrocinios included
    var dtEdicion = edicion.toDTEdicion();
    var dtOrganizador = edicion.obtenerDTOrganizador();

    var dtTiposRegistro = edicion.listarDTTiposDeRegistro().stream()
		.collect(Collectors.toCollection(ArrayList::new));

    ArrayList<datatypes.DTPatrocinio> dtPatrocinios = new ArrayList<>();
    for (var p : patrocinios) {
      dtPatrocinios.add(p.toDTPatrocinio(p.getInstitucion().toDTInstitucion()));
    }

    var dtRegistros = edicion.obtenerDTRegistros().stream().collect(Collectors.toCollection(ArrayList::new));

    return new datatypes.DTEdicionDetallada(
        dtEdicion, dtOrganizador, dtTiposRegistro, dtPatrocinios, dtRegistros);
  }

  public boolean existeNombre(EntityManager entityManager, String nombre) {
    return entityManager.createQuery("select count(e) from Edicion e where e.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult()
        > 0;
  }

  public boolean existePatrocinio(
      EntityManager entityManager, String nombreEdicion, String nombreInstitucion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(p) from Patrocinio p where p.edicion.nombre = :ne and p.institucion.nombre = :ni",
                Long.class)
            .setParameter("ne", nombreEdicion)
            .setParameter("ni", nombreInstitucion)
            .getSingleResult();
    return rowCount > 0;
  }
  
  public Patrocinio obtenerPatrocinio(EntityManager entityManager, String nombreEdicion, String nombreInstitucion) {
	  return entityManager.createQuery("select p from Patrocinio p where p.edicion.nombre = :ne and p.institucion.nombre = :ni", Patrocinio.class)
			  .setParameter("ne", nombreEdicion)
			  .setParameter("ni", nombreInstitucion)
			  .getResultStream().findFirst().orElse(null);
  }
  
  public Set<Edicion> obtenerEdicionesPorOrganizador(EntityManager entityManager, String nicknameOrganizador) {
	return new LinkedHashSet<>(entityManager.createQuery("select e from Edicion e where e.organizador.nickname = :ne", Edicion.class)
		.setParameter("ne", nicknameOrganizador)
		.getResultList());
  }
}
