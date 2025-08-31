package interfaces;
import java.time.LocalDate;
import java.util.List;

import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;

public interface IUsuarioController {
	boolean verificarNoExistenciaNickname(String nickname) throws Exception;
	boolean verificarNoExistenciaCorreo(String correo) throws Exception;
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion);
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento);
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb);
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion);
	
	// Métodos para obtener usuarios como DTUsuarioItemListado
	List<DTUsuarioItemListado> obtenerUsuarios(TipoUsuario tipoUsuario);
	List<DTUsuarioItemListado> obtenerUsuarios();
	
	// Método para obtener registros de un usuario
	List<DTRegistro> obtenerRegistrosUsuario(String nickname);
	
	// Método para obtener registro detallado de un asistente en una edición específica
	DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion);
}
