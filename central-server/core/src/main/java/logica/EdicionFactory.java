package logica;

import datatypes.DTEdicion;
import dominio.Edicion;
import dominio.Evento;
import dominio.Organizador;
import jakarta.persistence.EntityManager;

public final class EdicionFactory {
  private static final EdicionFactory INSTANCE = new EdicionFactory();

  private EdicionFactory() {}

  public static EdicionFactory get() {
    return INSTANCE;
  }

  public boolean crearEdicion(EntityManager entityManager, Evento evento, Organizador org, DTEdicion dtEdicion) {
    Long rowCount =
        entityManager.createQuery(
                "select count(ed) from Edicion ed where ed.evento = :ev and ed.nombre = :n",
                Long.class)
            .setParameter("ev", evento)
            .setParameter("n", dtEdicion.nombre())
            .getSingleResult();
    if (rowCount > 0) return false;

    var edicion =
        new Edicion(
            evento,
            dtEdicion.nombre(),
            dtEdicion.sigla(),
            dtEdicion.fechaInicio(),
            dtEdicion.fechaFin(),
            dtEdicion.fechaAlta(),
            dtEdicion.ciudad(),
            dtEdicion.pais(),
            dtEdicion.foto(),
            org);
    edicion.setEstado(dtEdicion.estado());
    evento.addEdicion(edicion); // mantiene bidireccional
    entityManager.persist(edicion);
    return true;
  }
}
