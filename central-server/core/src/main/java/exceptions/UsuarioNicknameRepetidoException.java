package exceptions;

public class UsuarioNicknameRepetidoException extends ValidationInputException {

  private static final long serialVersionUID = 1L;

  public UsuarioNicknameRepetidoException(String nickname) {
    super("El nickname " + nickname + " ya está registrado en el sistema.");
  }
}
