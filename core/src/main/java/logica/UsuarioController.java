package logica;
import java.time.LocalDate;

import interfaces.IUsuarioController;
import repos.UsuarioRepository;
import repos.InstitucionRepository;
import dominio.Asistente;
import dominio.Organizador;
import infra.Tx;
import dominio.Institucion;


public class UsuarioController implements IUsuarioController {
	
	private static UsuarioController INSTANCE = new UsuarioController();
	private UsuarioController() {}
	
	private final UsuarioRepository repoU = UsuarioRepository.get();
	private final UsuarioFactory faU = UsuarioFactory.get();

	public static UsuarioController get() { return INSTANCE; }
	
	public boolean verificarNoExistenciaNickname(String nickname) {  
		return Tx.inTx(em -> repoU.noExisteNickname(em, nickname));
	}
	
	public boolean verificarNoExistenciaCorreo(String correo) {
		return Tx.inTx(em -> repoU.noExisteCorreo(em, correo));
	}
	

	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion) {
		Tx.inTx(em -> { 
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento, nombreInstitucion); 
			return null;
			});
	}
	
	
	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) {
		Tx.inTx(em -> { 
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento); 
			return null;
			});
	} 
	
	
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion, linkSitioWeb); 
			return null;
			});
	}
	
	
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion) {
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion); 
			return null;
			});
	}

}
