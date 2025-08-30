package exceptions;

public class InvalidSelectionInputException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSelectionInputException(String elementName) {
		super("Al menos un(a) " + elementName + " es inválido(a).");
	}

}
