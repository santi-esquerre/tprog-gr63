package interfaces;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import exceptions.InstitucionNoExistenteException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IUsuarioController {
  // Set<DTUsuarioListado> listadoUsuarios(String nickname) throws Exception;
  DTAsistente seleccionarAsistente(String nickname) throws Exception;

  DTOrganizador seleccionarOrganizador(String nickname) throws Exception;

  boolean verificarNoExistenciaNickname(String nickname) throws Exception;

  boolean verificarNoExistenciaCorreo(String correo) throws Exception;

  void crearAsistente(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento,
      String nombreInstitucion)
      throws UsuarioCorreoRepetidoException,
          UsuarioNicknameRepetidoException,
          InstitucionNoExistenteException;

  void crearAsistente(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento)
      throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

  void crearOrganizador(
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion,
      String linkSitioWeb)
      throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

  void crearOrganizador(
      String nickname, String nombre, String correo, String clave, String foto, String descripcion)
      throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;

  Set<DTAsistente> mostrarAsistentes();

  DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion);

  // Método para obtener registro detallado de un asistente en una edición
  // específica
  List<DTRegistro> obtenerRegistrosUsuario(String nickname);

  // Método para obtener registros de un usuario
  List<DTUsuarioItemListado> obtenerUsuarios();

	List<DTUsuarioItemListado> obtenerUsuarios(TipoUsuario tipoUsuario);
	
	DTUsuario obtenerUsuario(String usuario) throws Exception;
	
}
