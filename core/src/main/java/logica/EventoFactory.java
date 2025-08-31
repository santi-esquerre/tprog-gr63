package logica;

import java.util.Set;

import dominio.Categoria;
import dominio.Evento;
import jakarta.persistence.EntityManager;
import repos.EventoRepository;

public final class EventoFactory {
  private static final EventoFactory INSTANCE = new EventoFactory();
  private EventoFactory() {}
  public static EventoFactory get() { return INSTANCE; }

  public boolean crearEvento(EntityManager em,
                             String nombre, String descripcion,
                             java.util.Date fechaAlta, String sigla,
                             Set<Categoria> categorias){
    if (EventoRepository.get().existeEvento(em, nombre)) return false;
    var ev = new Evento(nombre, descripcion, fechaAlta, sigla, categorias);
    em.persist(ev);
    return true;
  }
}
