package exceptions;

@SuppressWarnings("serial")
public class EdicionInexistenteException extends ValidationInputException {
	public EdicionInexistenteException(String message) {
		super(message);
	}
}
