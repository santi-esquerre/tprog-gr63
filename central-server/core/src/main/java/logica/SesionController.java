package logica;

import infra.Tx;
import interfaces.ISesionController;
import repos.UsuarioRepository;

public class SesionController implements ISesionController {
  public static final SesionController INSTANCE = new SesionController();

  public static SesionController get() {
    return INSTANCE;
  }

  public boolean iniciarSesion(String username, String password) {
    return Tx.inTx(entityManager -> UsuarioRepository.get().comprobarCredenciales(entityManager, username, password));
  }

  public void cerrarSesion() {
    // TODO Implemntar cerrarSesion
  }
}
