package repos;

import dominio.Edicion;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class EdicionRepository {
  private static final EdicionRepository INSTANCE = new EdicionRepository();
  private EdicionRepository() {}
  public static EdicionRepository get() { return INSTANCE; }

  public Edicion buscarEdicion(EntityManager em, String nombreEdicion){
    return em.createQuery("""
      select ed from Edicion ed
      left join fetch ed.tipos
      left join fetch ed.registros r
      left join fetch r.asistente
      where ed.nombre = :n
    """, Edicion.class).setParameter("n", nombreEdicion)
      .getResultStream().findFirst().orElse(null);
  }

  public TipoRegistro obtenerTipoRegistro(EntityManager em, String nombre) {
    // Stub: in real integration would search for TipoRegistro by name in specific edition
    return em.createQuery(
      "SELECT tr FROM TipoRegistro tr WHERE tr.nombre = :nombre", TipoRegistro.class)
      .setParameter("nombre", nombre)
      .getResultStream().findFirst().orElse(null);
  }
}
