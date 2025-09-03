package repos;

import java.util.List;

import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import dominio.Asistente;
import dominio.Organizador;
import dominio.Registro;
import dominio.Usuario;
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

  private UsuarioRepository() {
  }

  public static UsuarioRepository get() {
    return INSTANCE;
  }

  public Asistente obtenerAsistente(EntityManager em, String nickname) {
    return em.createQuery(
        "select a from Asistente a where a.nickname = :n", Asistente.class)
        .setParameter("n", nickname)
        .getResultStream().findFirst().orElse(null);
  }
  
  public Organizador obtenerOrganizador(EntityManager em, String nickname) {
	return em.createQuery(
		"select o from Organizador o where o.nickname = :n", Organizador.class)
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

  /**
   * Obtiene usuarios filtrados por tipo y los convierte a DTUsuarioItemListado
   * 
   * @param em          EntityManager
   * @param tipoUsuario ASISTENTE, ORGANIZADOR
   * @return Lista de DTUsuarioItemListado
   */
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager em, TipoUsuario tipoUsuario) {
    String jpql;

    if (tipoUsuario == TipoUsuario.ASISTENTE) {
      // Obtener solo asistentes
      jpql = "select a from Asistente a order by a.nickname";
    } else {
      // Obtener solo organizadores
      jpql = "select o from Organizador o order by o.nickname";
    }

    return em.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  // Sobrecarga para obtener todos los usuarios sin filtrar por tipo
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager em) {
    String jpql;

    // Obtener todos los usuarios
    jpql = "select u from Usuario u order by u.nickname";

    return em.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  /**
   * Obtiene todos los registros de un usuario por nickname y los convierte a
   * DTRegistro
   * 
   * @param em       EntityManager
   * @param nickname Nickname del usuario
   * @return Lista de DTRegistro del usuario
   */
  public List<DTRegistro> obtenerRegistrosUsuario(EntityManager em, String nickname) {
    String jpql = "SELECT r FROM Registro r " +
        "JOIN r.asistente a " +
        "JOIN r.edicion ed " +
        "JOIN ed.evento ev " +
        "WHERE a.nickname = :nickname " +
        "ORDER BY r.fecha DESC";

    return em.createQuery(jpql, Registro.class)
        .setParameter("nickname", nickname)
        .getResultStream()
        .map(registro -> new DTRegistro(
            registro.getFecha(),
            registro.getCosto(),
            registro.getEdicion().getEvento().getNombre(),
            registro.getEdicion().getNombre(),
            registro.getAsistente().getNickname(),
            registro.getTipo().obtenerDTTipoRegistro()))
        .toList();
  }

  /**
   * Obtiene el registro detallado de un asistente en una edición específica
   * 
   * @param em                EntityManager
   * @param nicknameAsistente Nickname del asistente
   * @param nombreEdicion     Nombre de la edición del evento
   * @return DTRegistroDetallado si existe el registro, null si no existe
   */
  public DTRegistroDetallado obtenerRegistroDetallado(EntityManager em, String nicknameAsistente,
      String nombreEdicion) {
    String jpql = "SELECT r FROM Registro r " +
        "JOIN FETCH r.asistente a " +
        "JOIN FETCH r.edicion ed " +
        "JOIN FETCH ed.evento ev " +
        "JOIN FETCH r.tipo tr " +
        "LEFT JOIN FETCH r.patrocinio p " +
        "LEFT JOIN FETCH p.institucion i " +
        "WHERE a.nickname = :nickname AND ed.nombre = :nombreEdicion";

    return em.createQuery(jpql, Registro.class)
        .setParameter("nickname", nicknameAsistente)
        .setParameter("nombreEdicion", nombreEdicion)
        .getResultStream()
        .findFirst()
        .map(registro -> {
          java.util.Optional<datatypes.DTPatrocinio> dtPatrocinio = java.util.Optional.empty();

          if (registro.getPatrocinio() != null) {
            // Convertir la institución del patrocinio a DTInstitucion
            datatypes.DTInstitucion dtInst = registro.getPatrocinio().getInstitucion().toDTInstitucion();
            dtPatrocinio = java.util.Optional.of(registro.getPatrocinio().toDTPatrocinio(dtInst));
          }

          return new DTRegistroDetallado(
              registro.getFecha(),
              registro.getCosto(),
              registro.getEdicion().getEvento().toDTEvento(),
              registro.getEdicion().toDTEdicion(),
              registro.getAsistente().toDataType(),
              registro.getTipo().obtenerDTTipoRegistro(),
              dtPatrocinio);
        })
        .orElse(null);
  }

  // core/src/main/java/repos/UsuarioRepository.java
  public Organizador buscarOrganizadorPorNickname(EntityManager em, String nickname) {
    return em.createQuery("select o from Organizador o where o.nickname = :n", Organizador.class)
        .setParameter("n", nickname)
        .getResultStream().findFirst().orElse(null);
  }

}
