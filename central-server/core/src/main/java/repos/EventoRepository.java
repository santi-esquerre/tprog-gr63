package repos;

import dominio.Evento;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class EventoRepository {
  private static final EventoRepository INSTANCE = new EventoRepository();

  private EventoRepository() {}

  public static EventoRepository get() {
    return INSTANCE;
  }

  public boolean existeEvento(EntityManager entityManager, String nombre) {
    Long rowCount =
        entityManager.createQuery("select count(e) from Evento e where e.nombre = :n", Long.class)
            .setParameter("n", nombre)
            .getSingleResult();
    return rowCount > 0;
  }

  public Evento buscarEvento(EntityManager entityManager, String nombreEvento) {
    return entityManager.createQuery(
            """
        select e from Evento e
        left join fetch e.categorias
        where e.nombre = :n
      """,
            Evento.class)
        .setParameter("n", nombreEvento)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Set<datatypes.DTEvento> listarEventos(EntityManager entityManager) {
    return entityManager.createQuery("select e from Evento e order by e.nombre", Evento.class)
        .getResultStream()
        .map(Evento::obtenerDTEvento)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public Set<datatypes.DTEvento> listarUltimosEventos(
	  EntityManager entityManager, Integer cantidad, java.util.Date ultimaFechaAlta, Long ultimoIdentifier) {
	if (ultimoIdentifier == null || ultimaFechaAlta == null || ultimoIdentifier <= 0) {
		return entityManager
			.createQuery(
				"""
					select e from Evento e
					where e.finalizado = false
					order by e.fechaAlta desc, e.identifier desc
					""",
					Evento.class)
			.setMaxResults(cantidad)
			.getResultStream()
			.map(Evento::obtenerDTEvento)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	return entityManager
		.createQuery(
			"""
		select e from Evento e
		where (e.fechaAlta < :fechaAltaDesde
		  or (e.fechaAlta = :fechaAltaDesde and e.identifier < :ultimoIdentifier)) and e.finalizado = false
		order by e.fechaAlta desc, e.identifier desc
	  """,
			Evento.class)
		.setParameter("fechaAltaDesde", new java.sql.Date(ultimaFechaAlta.getTime()))
		.setParameter("ultimoIdentifier", ultimoIdentifier)
		.setMaxResults(cantidad)
		.getResultStream()
		.map(Evento::obtenerDTEvento)
		.collect(Collectors.toCollection(LinkedHashSet::new));
  }
  
  
  /** Obtiene los datos detallados de un evento, incluyendo categorías y ediciones */
  public datatypes.DTEventoDetallado obtenerDatosDetalladosEvento(
      EntityManager entityManager, String nombreEvento) {
    Evento evento =
        entityManager.createQuery(
                """
        select e from Evento e
        left join fetch e.categorias
        left join fetch e.ediciones
        where e.nombre = :n
      """,
                Evento.class)
            .setParameter("n", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    return (evento != null) ? evento.toDTEventoDetallado() : null;
  }
  
  public datatypes.DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(
      EntityManager entityManager, String nombreEvento) {
    Evento evento =
        entityManager.createQuery(
                """
        select e from Evento e
        left join fetch e.categorias
        left join fetch e.ediciones
        where e.nombre = :n
      """,
                Evento.class)
            .setParameter("n", nombreEvento)
            .getResultStream()
            .findFirst()
            .orElse(null);

    return (evento != null) ? evento.toDTEventoDetallado(true) : null;
  }
}
