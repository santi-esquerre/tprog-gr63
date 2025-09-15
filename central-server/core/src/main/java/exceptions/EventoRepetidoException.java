package exceptions;

public class EventoRepetidoException extends ValidationInputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventoRepetidoException(String messageString) {
		super(messageString);
	}
	
	public EventoRepetidoException() {
		super("Evento repetido");
	}

}
