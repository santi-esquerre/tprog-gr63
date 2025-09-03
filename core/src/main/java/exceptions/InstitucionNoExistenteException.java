package exceptions;

public class InstitucionNoExistenteException extends ValidationInputException {

	private static final long serialVersionUID = 1L;

	public InstitucionNoExistenteException(String string) {
		super(string);
	}
}
