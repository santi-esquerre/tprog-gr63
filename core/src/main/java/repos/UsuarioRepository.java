package repos;

import jakarta.persistence.EntityManager;
import dominio.Asistente;

public final class UsuarioRepository {
  private static final UsuarioRepository INSTANCE = new UsuarioRepository();
  private UsuarioRepository() {}
  public static UsuarioRepository get() { return INSTANCE; }

  public Asistente obtenerAsistente(EntityManager em, String nickname){
    return em.createQuery(
      "select a from Asistente a where a.nickname = :n", Asistente.class)
      .setParameter("n", nickname)
      .getResultStream().findFirst().orElse(null);
  }
}
