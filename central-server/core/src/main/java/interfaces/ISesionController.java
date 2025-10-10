package interfaces;

public interface ISesionController {
	public boolean iniciarSesion(String username, String password);
	public void cerrarSesion();
}
