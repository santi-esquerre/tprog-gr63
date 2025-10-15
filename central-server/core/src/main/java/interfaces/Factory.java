package interfaces;

import logica.EdicionController;
import logica.EventoController;
import logica.InstitucionController;
import logica.SesionController;
import logica.UsuarioController;
import repos.Repository;

public class Factory {
  // Singleton
  public static final Factory INSTANCE = new Factory();

  private Factory() {}

  public static Factory get() {
    return INSTANCE;
  }

  public IEventoController getIEventoController() {
    return EventoController.get();
  }

  public IEdicionController getIEdicionController() {
    return EdicionController.get();
  }

  public IUsuarioController getIUsuarioController() {
    return UsuarioController.get();
  }

  public IInstitucionController getIInstitucionController() {
    return InstitucionController.get();
  }

  public IRepository getIRepository() {
    return Repository.get();
  }

  public ISesionController getISesionController() {
    return SesionController.get();
  }
}
