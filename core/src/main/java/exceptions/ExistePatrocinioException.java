package exceptions;

public class ExistePatrocinioException extends ValidationInputException {
	
	private static final long serialVersionUID = 1L;
	
	public ExistePatrocinioException(String nombreInstitucion, String nombreEdicion) {
		super("Ya existe un patrocinio de la institucion " + nombreInstitucion + " con la edicion " + nombreEdicion);
	}

}
