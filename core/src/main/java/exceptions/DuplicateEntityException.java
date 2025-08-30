package exceptions;

public class DuplicateEntityException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String identifier) {
		super("Ya existe una entrada identificada por el campo " + identifier);
	}

}
