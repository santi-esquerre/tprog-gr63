package interfaces;
import java.time.LocalDate;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTOrganizador;
import datatypes.DTUsuarioListado;
import exceptions.ValidationInputException;

public interface IUsuarioController {
	Set<DTUsuarioListado> listadoUsuarios(String nickname) throws Exception;
	DTAsistente seleccionarAsistente(String nickname) throws Exception;
	DTOrganizador seleccionarOrganizador(String nickname) throws Exception;
	boolean verificarNoExistenciaNickname(String nickname) throws Exception;
	boolean verificarNoExistenciaCorreo(String correo) throws Exception;
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String nombreInstitucion);
	void crearAsistente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento);
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb);
	void crearOrganizador(String nickname, String nombre, String correo, String descripcion);
}
