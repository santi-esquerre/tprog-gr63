package exceptions;

public class NingunaCategoriaSeleccionadaException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NingunaCategoriaSeleccionadaException(String messageString) {
		super(messageString);
	}
	
	public NingunaCategoriaSeleccionadaException() {
		super("No se seleccionó ninguna categoría");
	}

}
