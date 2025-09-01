package repos;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import dominio.Evento;
import jakarta.persistence.EntityManager;

public final class EventoRepository {
  private static final EventoRepository INSTANCE = new EventoRepository();
  private EventoRepository() {}
  public static EventoRepository get() { return INSTANCE; }

  public boolean existeEvento(EntityManager em, String nombre){
    Long c = em.createQuery(
        "select count(e) from Evento e where e.nombre = :n", Long.class)
        .setParameter("n", nombre).getSingleResult();
    return c > 0;
  }

  public Evento buscarEvento(EntityManager em, String nombreEvento){
    return em.createQuery("""
        select e from Evento e
        left join fetch e.categorias
        where e.nombre = :n
      """, Evento.class).setParameter("n", nombreEvento)
      .getResultStream().findFirst().orElse(null);
  }

  public Set<datatypes.DTEvento> listarEventos(EntityManager em){
    return em.createQuery(
        "select e from Evento e order by e.nombre", Evento.class)
      .getResultStream()
      .map(Evento::obtenerDTEvento)
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
  
  /**
   * Obtiene los datos detallados de un evento, incluyendo categorías y ediciones
   */
  public datatypes.DTEventoDetallado obtenerDatosDetalladosEvento(EntityManager em, String nombreEvento) {
    Evento evento = em.createQuery("""
        select e from Evento e
        left join fetch e.categorias
        left join fetch e.ediciones
        where e.nombre = :n
      """, Evento.class).setParameter("n", nombreEvento)
      .getResultStream().findFirst().orElse(null);
      
    return (evento != null) ? evento.toDTEventoDetallado() : null;
  }
  
}
