package exceptions;

public class UsuarioCorreoRepetidoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UsuarioCorreoRepetidoException(String correo) {
		super("El correo electrónico " + correo + " ya está registrado en el sistema.");
	}

}
