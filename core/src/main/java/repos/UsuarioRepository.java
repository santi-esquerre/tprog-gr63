package repos;

import jakarta.persistence.EntityManager;
import dominio.Asistente;
import dominio.Evento;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTAsistente;
import infra.Tx;

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
  
  public Set<DTAsistente>listarAsistentes(EntityManager em) {
	    return em.createQuery(
	            "select a from Asistente a order by a.nombre", Asistente.class)
	          .getResultStream()
	          .map(Asistente::obtenerDTAsistente)
	          .collect(Collectors.toCollection(LinkedHashSet::new));	  
  }
}
