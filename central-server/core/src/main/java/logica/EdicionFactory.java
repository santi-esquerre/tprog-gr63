package logica;

import datatypes.DTEdicion;
import dominio.Edicion;
import dominio.Evento;
import dominio.Organizador;
import jakarta.persistence.EntityManager;

public final class EdicionFactory {
    private static final EdicionFactory INSTANCE = new EdicionFactory();

    private EdicionFactory() {
    }

    public static EdicionFactory get() {
        return INSTANCE;
    }

    public boolean crearEdicion(EntityManager em, Evento evento, Organizador org, DTEdicion dte) {
        Long c = em.createQuery(
                "select count(ed) from Edicion ed where ed.evento = :ev and ed.nombre = :n", Long.class)
                .setParameter("ev", evento)
                .setParameter("n", dte.nombre())
                .getSingleResult();
        if (c > 0)
            return false;

        var ed = new Edicion(evento,
                dte.nombre(), dte.sigla(),
                dte.fechaInicio(), dte.fechaFin(), dte.fechaAlta(),
                dte.ciudad(), dte.pais(),
                org);
        evento.addEdicion(ed); // mantiene bidireccional
        em.persist(ed);
        return true;
    }
}
