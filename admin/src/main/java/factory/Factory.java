package factory;
<<<<<<< HEAD
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
	
=======

import interfaces.*;
import logica.*;

public class Factory {
	// Singleton
	public static final Factory INSTANCE = new Factory();
	
	private Factory() {}
	public static Factory get(){ return INSTANCE; }
	
	public IEventoController getIEventoController(){ return EventoController.get(); }
	
	public IEdicionController getIEdicionController(){ return EdicionController.get(); }
>>>>>>> origin/CUAltaEvento
}
