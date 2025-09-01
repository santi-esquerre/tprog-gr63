package logica;
import java.time.LocalDate;
import java.util.List;

import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import infra.Tx;
import interfaces.IUsuarioController;
import repos.UsuarioRepository;


public class UsuarioController implements IUsuarioController {
	
	private static UsuarioController INSTANCE = new UsuarioController();
	private UsuarioController() {}
	
	private final UsuarioRepository repoU = UsuarioRepository.get();
	private final UsuarioFactory faU = UsuarioFactory.get();

	public static UsuarioController get() { return INSTANCE; }
	
    @Override
	public boolean verificarNoExistenciaNickname(String nickname) {  
		return Tx.inTx(em -> repoU.noExisteNickname(em, nickname));
	}
	
    @Override
	public boolean verificarNoExistenciaCorreo(String correo) {
		return Tx.inTx(em -> repoU.noExisteCorreo(em, correo));
	}
	

    @Override
	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion) {
		Tx.inTx(em -> { 
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento, nombreInstitucion); 
			return null;
			});
	}
	
	
    @Override
	public void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) {
		Tx.inTx(em -> { 
			faU.altaAsistente(em, nickname, nombre, apellido, correo, fechaNacimiento); 
			return null;
			});
	} 
	
	
    @Override
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion, linkSitioWeb); 
			return null;
			});
	}
	
	
    @Override
	public void crearOrganizador(String nickname, String nombre, String correo, String descripcion) {
		Tx.inTx(em -> { 
			faU.altaOrganizador(em, nickname, nombre, correo, descripcion); 
			return null;
			});
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

}
