package interfaces;
import java.time.LocalDate;
import java.util.Set;

import datatypes.DTAsistente;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;

public interface IUsuarioController {
	boolean verificarNoExistenciaNickname(String nickname) throws Exception;
	boolean verificarNoExistenciaCorreo(String correo) throws Exception;
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion) throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion) throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException;
	Set<DTAsistente> mostrarAsistentes();
}
