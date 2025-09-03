package logica;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import infra.Tx;
import interfaces.IUsuarioController;
import repos.UsuarioRepository;
import repos.InstitucionRepository;
import dominio.Asistente;
import dominio.Organizador;
import exceptions.UsuarioInexistenteException;
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
	
    @Override
	public boolean verificarNoExistenciaNickname(String nickname) {  
		return Tx.inTx(em -> repoU.noExisteNickname(em, nickname));
	}
	
    @Override
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
			if(nombreInstitucion == null || nombreInstitucion.isBlank()) {
				faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento); 
				return null;
			}
			var i = repoI.buscarInstitucion(em, nombreInstitucion);
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
	
	@Override
	public Set<DTAsistente> mostrarAsistentes() {
		return Tx.inTx(repoU::listarAsistentes);
	}

	@Override
	public List<DTUsuarioItemListado> obtenerUsuarios(TipoUsuario tipoUsuario) {
		return Tx.inTx(em -> repoU.obtenerUsuarios(em, tipoUsuario));
	}

	@Override
	public List<DTUsuarioItemListado> obtenerUsuarios() {
		return Tx.inTx(em -> repoU.obtenerUsuarios(em));
	}

	@Override
	public List<DTRegistro> obtenerRegistrosUsuario(String nickname) {
		return Tx.inTx(em -> repoU.obtenerRegistrosUsuario(em, nickname));
	}

	@Override
	public DTRegistroDetallado obtenerRegistroDetallado(String nicknameAsistente, String nombreEdicion) {
		return Tx.inTx(em -> repoU.obtenerRegistroDetallado(em, nicknameAsistente, nombreEdicion));
	}

	@Override
	public DTAsistente seleccionarAsistente(String nickname) throws Exception {
		
		Asistente asistente = Tx.inTx(em -> {
			return repoU.obtenerAsistente(em, nickname);
		});
		
		if (asistente == null) {
			throw new UsuarioInexistenteException("No existe un asistente con el nickname: " + nickname);
		}
		
		return asistente.toDataType();
	}

	@Override
	public DTOrganizador seleccionarOrganizador(String nickname) throws Exception {
		Organizador organizador = Tx.inTx(em -> {
			return repoU.obtenerOrganizador(em, nickname);
		});
		
		if (organizador == null) {
			throw new UsuarioInexistenteException("No existe un organizador con el nickname: " + nickname);
		}
		
		return organizador.toDataType();
	}

}
