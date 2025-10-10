package logica;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import dominio.Categoria;
import infra.Tx;
import interfaces.ISesionController;
import jakarta.persistence.EntityManager;
import repos.UsuarioRepository;

public class SesionController implements ISesionController{
	public static final SesionController INSTANCE = new SesionController();
	
	public static SesionController get() { return INSTANCE; }
	public boolean iniciarSesion(String username, String password) {
		return Tx.inTx(em -> UsuarioRepository.get().comprobarCredenciales(em, username, password));
  }
	
	public void cerrarSesion() {
		// TODO Implemntar cerrarSesion
	}
}
