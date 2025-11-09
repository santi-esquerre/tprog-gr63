package webservice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuario;
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
            Date fechaNacimiento,
            String nombreInstitucion)
            throws UsuarioCorreoRepetidoException,
            UsuarioNicknameRepetidoException,
            InstitucionNoExistenteException {

    LocalDate localDate = fechaNacimiento.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
		usuarioController.crearAsistente(nickname, nombre, correo, clave, foto, apellido, localDate, nombreInstitucion);
	}
	
	@WebMethod
	public void crearAsistente(
			String nickname,
			String nombre,
			String correo,
			String clave,
			String foto,
			String apellido,
			Date fechaNacimiento)
			throws UsuarioCorreoRepetidoException, UsuarioNicknameRepetidoException {

    LocalDate localDate = fechaNacimiento.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
		usuarioController.crearAsistente(nickname, nombre, correo, clave, foto, apellido, localDate);
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
	public DTAsistenteArray mostrarAsistentes() {
		return new DTAsistenteArray(new ArrayList<DTAsistente>(usuarioController.mostrarAsistentes()));
	}
	
	@WebMethod
	public DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion) {
		return usuarioController.obtenerRegistroDetallado(nicknameAsistente, nombreEdicion);
	}
	
	@WebMethod
	public DTRegistroArray obtenerRegistrosUsuario(String nickname) {
		return new DTRegistroArray(usuarioController.obtenerRegistrosUsuario(nickname));
	}
	
	@WebMethod
	public DTUsuarioItemListadoArray obtenerUsuarios() {
		return new DTUsuarioItemListadoArray(new ArrayList<>(usuarioController.obtenerUsuarios()));
	}
	
	@WebMethod
	public DTUsuarioItemListadoArray obtenerUsuariosPorTipo(TipoUsuario tipoUsuario) {
		return new DTUsuarioItemListadoArray(new ArrayList<>(usuarioController.obtenerUsuarios(tipoUsuario)));
	}
	
 @WebMethod
 public DTAsistente seleccionarAsistente(String nickname) throws Exception {
     return usuarioController.seleccionarAsistente(nickname);
 }
 
 @WebMethod
 public DTOrganizador seleccionarOrganizador(String nickname) throws Exception {
     return usuarioController.seleccionarOrganizador(nickname);
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
            Date fechaNacimiento,
            String foto,
            String clave) throws UsuarioInexistenteException, UsuarioCorreoRepetidoException, Exception {
	  LocalDate localDate = fechaNacimiento.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
		usuarioController.actualizarAsistente(nickname, nombre, apellido, correo, localDate, foto, clave);
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
