package repos;

import datatypes.DTAsistente;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import dominio.Asistente;
import dominio.Organizador;
import dominio.Registro;
import dominio.Usuario;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class UsuarioRepository {
  private static final UsuarioRepository INSTANCE = new UsuarioRepository();

  private UsuarioRepository() {}

  public static UsuarioRepository get() {
    return INSTANCE;
  }

  public Asistente obtenerAsistente(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select a from Asistente a where a.nickname = :n", Asistente.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Organizador obtenerOrganizador(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select o from Organizador o where o.nickname = :n", Organizador.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public boolean noExisteNickname(EntityManager entityManager, String nickname) {
    Long rowCount =
        entityManager.createQuery("select count(u) from Usuario u where u.nickname = :n", Long.class)
            .setParameter("n", nickname)
            .getSingleResult();
    return rowCount == 0;
  }

  public boolean noExisteCorreo(EntityManager entityManager, String correo) {
    Long rowCount =
        entityManager.createQuery("select count(u) from Usuario u where u.correo = :c", Long.class)
            .setParameter("c", correo)
            .getSingleResult();
    return rowCount == 0;
  }

  public Set<DTAsistente> listarAsistentes(EntityManager entityManager) {
    return entityManager.createQuery("select a from Asistente a order by a.nombre", Asistente.class)
        .getResultStream()
        .map(Asistente::toDataType)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Obtiene usuarios filtrados por tipo y los convierte a DTUsuarioItemListado
   *
   * @param entityManager EntityManager
   * @param tipoUsuario ASISTENTE, ORGANIZADOR
   * @return Lista de DTUsuarioItemListado
   */
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager entityManager, TipoUsuario tipoUsuario) {
    String jpql;

    if (tipoUsuario == TipoUsuario.ASISTENTE) {
      // Obtener solo asistentes
      jpql = "select a from Asistente a order by a.nickname";
    } else {
      // Obtener solo organizadores
      jpql = "select o from Organizador o order by o.nickname";
    }

    return entityManager.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  // Sobrecarga para obtener todos los usuarios sin filtrar por tipo
  public List<DTUsuarioItemListado> obtenerUsuarios(EntityManager entityManager) {
    String jpql;

    // Obtener todos los usuarios
    jpql = "select u from Usuario u order by u.nickname";

    return entityManager.createQuery(jpql, Usuario.class)
        .getResultStream()
        .map(Usuario::toDTUsuarioItemListado)
        .toList();
  }

  /**
   * Obtiene todos los registros de un usuario por nickname y los convierte a DTRegistro
   *
   * @param entityManager EntityManager
   * @param nickname Nickname del usuario
   * @return Lista de DTRegistro del usuario
   */
  public List<DTRegistro> obtenerRegistrosUsuario(EntityManager entityManager, String nickname) {
    String jpql =
        "SELECT r FROM Registro r "
            + "JOIN r.asistente a "
            + "JOIN r.edicion ed "
            + "JOIN ed.evento ev "
            + "WHERE a.nickname = :nickname "
            + "ORDER BY r.fecha DESC";

    return entityManager.createQuery(jpql, Registro.class)
        .setParameter("nickname", nickname)
        .getResultStream()
        .map(registro -> new DTRegistro(
            registro.getFecha(),
            registro.getCosto(),
            registro.getEdicion().getEvento().getNombre(),
            registro.getEdicion().getNombre(),
            registro.getAsistente().getNickname(),
            registro.getAsistente().getFoto(),
            registro.getTipo().obtenerDTTipoRegistro(),
            registro.getPatrocinio() != null ? true: false))
        .toList();
  }

  /**
   * Obtiene el registro detallado de un asistente en una edición específica
   *
   * @param entityManager EntityManager
   * @param nicknameAsistente Nickname del asistente
   * @param nombreEdicion Nombre de la edición del evento
   * @return DTRegistroDetallado si existe el registro, null si no existe
   */
  public DTRegistroDetallado obtenerRegistroDetallado(
      EntityManager entityManager, String nicknameAsistente, String nombreEdicion) {
    String jpql =
        "SELECT r FROM Registro r "
            + "JOIN FETCH r.asistente a "
            + "JOIN FETCH r.edicion ed "
            + "JOIN FETCH ed.evento ev "
            + "JOIN FETCH r.tipo tr "
            + "LEFT JOIN FETCH r.patrocinio p "
            + "LEFT JOIN FETCH p.institucion i "
            + "WHERE a.nickname = :nickname AND ed.nombre = :nombreEdicion";

    return entityManager.createQuery(jpql, Registro.class)
        .setParameter("nickname", nicknameAsistente)
        .setParameter("nombreEdicion", nombreEdicion)
        .getResultStream()
        .findFirst()
        .map(
            registro -> {
              java.util.Optional<datatypes.DTPatrocinio> dtPatrocinio = java.util.Optional.empty();

              if (registro.getPatrocinio() != null) {
                // Convertir la institución del patrocinio a DTInstitucion
                datatypes.DTInstitucion dtInst =
                    registro.getPatrocinio().getInstitucion().toDTInstitucion();
                dtPatrocinio =
                    java.util.Optional.of(registro.getPatrocinio().toDTPatrocinio(dtInst));
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
  public Organizador buscarOrganizadorPorNickname(EntityManager entityManager, String nickname) {
    return entityManager.createQuery("select o from Organizador o where o.nickname = :n", Organizador.class)
        .setParameter("n", nickname)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  public Boolean comprobarCredenciales(EntityManager entityManager, String usuario, String clave) {
    Usuario usuarioNuevo =
        entityManager.createQuery(
                "select u from Usuario u where u.nickname = :n or u.correo = :n", Usuario.class)
            .setParameter("n", usuario)
            .getResultStream()
            .findFirst()
            .orElse(null);
    if (usuarioNuevo == null) return false;
    return usuarioNuevo.validarClave(clave);
  }
  
  public Usuario obtenerUsuario(EntityManager entityManager, String usuario) {
	  return entityManager.createQuery(
		        "select u from Usuario u where u.nickname = :n or u.correo = :n", Usuario.class)
		        .setParameter("n", usuario)
		        .getResultStream().findFirst().orElse(null);
  }
  
}
