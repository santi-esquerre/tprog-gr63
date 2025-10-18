package logica;

import dominio.Categoria;
import dominio.Evento;
import jakarta.persistence.EntityManager;
import java.util.Set;
import repos.EventoRepository;

public final class EventoFactory {
  private static final EventoFactory INSTANCE = new EventoFactory();

  private EventoFactory() {}

  public static EventoFactory get() {
    return INSTANCE;
  }

  public boolean crearEvento(
      EntityManager entityManager,
      String nombre,
      String descripcion,
      java.util.Date fechaAlta,
      String sigla,
      String foto,
      Set<Categoria> categorias) {
    if (EventoRepository.get().existeEvento(entityManager, nombre)) return false;
    var evento = new Evento(nombre, descripcion, fechaAlta, sigla, foto, categorias);
    entityManager.persist(evento);
    return true;
  }
}
