package exceptions;

public class CategoriasInvalidasException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriasInvalidasException(String messageString) {
		super(messageString);
	}
	
	public CategoriasInvalidasException() {
		super("Alguna de las categorías seleccionadas no es válida");
	}

}
