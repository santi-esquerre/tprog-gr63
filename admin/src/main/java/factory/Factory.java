package factory;
import interfaces.*;
import logica.*;

public class Factory {
	// Singleton
	public static final Factory INSTANCE = new Factory();
	
	private Factory() {}
	public static Factory get(){ return INSTANCE; }
	
	public IEventoController getIEventoController(){ return EventoController.get(); }
	
	public IEdicionController getIEdicionController(){ return EdicionController.get(); }
	
	public IUsuarioController getIUsuarioController(){ return UsuarioController.get(); }
}
