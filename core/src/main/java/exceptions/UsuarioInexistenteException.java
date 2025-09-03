package exceptions;

public class UsuarioInexistenteException extends ValidationInputException {
	public UsuarioInexistenteException(String message) {
		super(message);
	}

}
