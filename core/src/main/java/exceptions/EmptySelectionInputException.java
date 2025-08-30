package exceptions;

public class EmptySelectionInputException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptySelectionInputException(String elementName) {
		super("Debe seleccionar al menos 1 " + elementName + ".");
	}

}
