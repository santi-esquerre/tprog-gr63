package logica;
import java.time.LocalDate;

import interfaces.IUsuarioController;
import repos.UsuarioRepository;
import repos.InstitucionRepository;
import dominio.Asistente;
import dominio.Organizador;
import exceptions.InstitucionRepetidaException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;
import infra.Tx;
import dominio.Institucion;


public class UsuarioController implements IUsuarioController {
	
	private static UsuarioController INSTANCE = new UsuarioController();
	private UsuarioController() {}
	
	private final UsuarioRepository repoU = UsuarioRepository.get();
	private final UsuarioFactory faU = UsuarioFactory.get();
	private final InstitucionRepository repoI = InstitucionRepository.get();

	public static UsuarioController get() { return INSTANCE; }
	
	public boolean verificarNoExistenciaNickname(String nickname) {  
		return Tx.inTx(em -> repoU.noExisteNickname(em, nickname));
	}
	
	public boolean verificarNoExistenciaCorreo(String correo) {
		return Tx.inTx(em -> repoU.noExisteCorreo(em, correo));
	}
	

	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion) throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
		if (!verificarNoExistenciaNickname(nickname)) {
			throw new UsuarioNicknameRepetidoException(nickname);
		}
		
		if (!verificarNoExistenciaCorreo(correo)) {
			throw new UsuarioCorreoRepetidoException(nickname);
		}
		Tx.inTx(em -> { 
			var i = (Institucion) repoI.buscarInstitucion(em, nombreInstitucion);
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento, i); 
			return null;
			});
	}
	
	
	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
		if (!verificarNoExistenciaNickname(nickname)) {
			throw new UsuarioNicknameRepetidoException(nickname);
		}
		
		if (!verificarNoExistenciaCorreo(correo)) {
			throw new UsuarioCorreoRepetidoException(nickname);
		}
		Tx.inTx(em -> { 
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento); 
			return null;
			});
	} 
	
	
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb)throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
		if (!verificarNoExistenciaNickname(nickname)) {
			throw new UsuarioNicknameRepetidoException(nickname);
		}
		
		if (!verificarNoExistenciaCorreo(correo)) {
			throw new UsuarioCorreoRepetidoException(nickname);
		}
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion, linkSitioWeb); 
			return null;
			});
	}
	
	
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion) throws UsuarioNicknameRepetidoException, UsuarioCorreoRepetidoException {
		if (!verificarNoExistenciaNickname(nickname)) {
			throw new UsuarioNicknameRepetidoException(nickname);
		}
		
		if (!verificarNoExistenciaCorreo(correo)) {
			throw new UsuarioCorreoRepetidoException(nickname);
		}		
		
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion); 
			return null;
			});
	}

}
