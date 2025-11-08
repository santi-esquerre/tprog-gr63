package webservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import exceptions.InstitucionNoExistenteException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioInexistenteException;
import exceptions.UsuarioNicknameRepetidoException;
import interfaces.Factory;
import interfaces.IUsuarioController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class UsuarioService {
	
	IUsuarioController usuarioController = Factory.get().getIUsuarioController();
	
	//Constructor
	public UsuarioService() {}
	
	@WebMethod
	public boolean verificarNoExistenciaNickname(String nickname) throws Exception {
		return usuarioController.verificarNoExistenciaNickname(nickname);
	}
	
	@WebMethod
	public boolean verificarNoExistenciaCorreo(String correo) throws Exception {
		return usuarioController.verificarNoExistenciaCorreo(correo);
	}
	
	@WebMethod
	public void crearAsistenteConInstitucion(
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
            InstitucionNoExistenteException {
		usuarioController.crearAsistente(nickname, nombre, correo, clave, foto, apellido, fechaNacimiento, nombreInstitucion);
	}
	
	@WebMethod
	public void crearAsistente(
			String nickname,
			String nombre,
			String correo,
			String clave,
			String foto,
			String apellido,
			LocalDate fechaNacimiento)
			throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException {
		usuarioController.crearAsistente(nickname, nombre, correo, clave, foto, apellido, fechaNacimiento);
	}
	
	@WebMethod
	public void crearOrganizadorConSitioWeb(
			String nickname,
			String nombre,
			String correo,
			String clave,
			String foto,
			String descripcion,
			String linkSitioWeb)
			throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException {
		usuarioController.crearOrganizador(nickname, nombre, correo, clave, foto, descripcion, linkSitioWeb);
	}
	
	@WebMethod
	public void crearOrganizador(
			String nickname, String nombre, String correo, String clave, String foto, String descripcion)
			throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException {
		usuarioController.crearOrganizador(nickname, nombre, correo, clave, foto, descripcion);
	}
	
	@WebMethod
	public ArrayList<DTAsistente> mostrarAsistentes() {
		return new ArrayList<>(usuarioController.mostrarAsistentes());
	}
	
	@WebMethod
	public DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion) {
		return usuarioController.obtenerRegistroDetallado(nicknameAsistente, nombreEdicion);
	}
	
	@WebMethod
	public ArrayList<DTRegistro> obtenerRegistrosUsuario(String nickname) {
		return new ArrayList<>(usuarioController.obtenerRegistrosUsuario(nickname));
	}
	
	@WebMethod
	public ArrayList<DTUsuarioItemListado> obtenerUsuarios() {
		return new ArrayList<>(usuarioController.obtenerUsuarios());
	}
	
	@WebMethod
	public ArrayList<DTUsuarioItemListado> obtenerUsuariosPorTipo(TipoUsuario tipoUsuario) {
		return new ArrayList<>(usuarioController.obtenerUsuarios(tipoUsuario));
	}
	
	@WebMethod
	public DTUsuario obtenerUsuario(String usuario) throws Exception {
		return usuarioController.obtenerUsuario(usuario);
	}
	
	@WebMethod
	public void actualizarAsistente(
            String nickname,
            String nombre,
            String apellido,
            String correo,
            LocalDate fechaNacimiento,
            String foto,
            String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception {
		usuarioController.actualizarAsistente(nickname, nombre, apellido, correo, fechaNacimiento, foto, clave);
	}
	
	@WebMethod
	public void actualizarOrganizador(
			String nickname,
			String nombre,
			String correo,
			String descripcion,
			String linkSitioWeb,
			String foto,
			String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception {
		usuarioController.actualizarOrganizador(nickname, nombre, correo, descripcion, linkSitioWeb, foto, clave);
	}
	
}
