package factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import logica.EdicionController;
import logica.EventoController;
import logica.RegistroFactory;


public class Factory {
	private static final Factory INSTANCE = new Factory();
	private Factory() {}
	public static Factory get() { return INSTANCE; }
	public IEdicionController getIEdicionController() { return EdicionController.get(); }
	public IEventoController getIEventoController() { return EventoController.get(); }
	
}
