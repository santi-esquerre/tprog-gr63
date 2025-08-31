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
  
  public boolean noExisteNickname(EntityManager em, String nickname) {
	Long c = em.createQuery(
		"select count(u) from Usuario u where u.nickname = :n", Long.class)
		.setParameter("n", nickname).getSingleResult();
	return c == 0;
  }
  
  public boolean noExisteCorreo(EntityManager em, String correo) {
	  Long c = em.createQuery(
			 "select count(u) from Usuario u where u.correo = :c", Long.class)
			  .setParameter("c", correo).getSingleResult();
	  return c == 0;
  }
}
