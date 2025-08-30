package exceptions;

public class EmptyInputException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyInputException(String fieldName) {
		super("El campo " + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + " no puede ser vacío.");
	}

}
