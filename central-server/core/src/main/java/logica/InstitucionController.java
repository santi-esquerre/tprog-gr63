package logica;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import exceptions.InstitucionRepetidaException;
import infra.Tx;
import java.util.Set;
import repos.InstitucionRepository;

public class InstitucionController implements interfaces.IInstitucionController {

	private static final InstitucionController INSTANCE = new InstitucionController();
	private final InstitucionRepository institucionRepo = InstitucionRepository.get();
	
	private InstitucionController() {}
	public static InstitucionController get() { return INSTANCE; }
	
	// Legacy method - keeping for compatibility but adapting to interface
	@Override
	public boolean crearInstitucion(String nombre, String descripcion, String sitioWeb, String foto) throws InstitucionRepetidaException {
		if(Tx.inTx(em -> {
			try {
				return institucionRepo.noExisteInstitucion(em, nombre);
			} catch (Exception e) {
				return false;
			}
		})) {
			// Institution doesn't exist, create it
			InstitucionFactory factory = InstitucionFactory.get();
			Tx.inTx(emt -> {
				factory.crearInstitucion(emt, nombre, sitioWeb, descripcion, foto);
				return null;
			});
			return true;
		} else {
			// Institution already exists, throw exception
			throw new InstitucionRepetidaException("La institución " + nombre + " ya está registrada.");
		}
	}
	
	public Set<DTInstitucion> listarInstituciones() {
		return Tx.inTx(em -> institucionRepo.listarInstituciones(em));
	}

  private InstitucionController() {}

  public static InstitucionController get() {
    return INSTANCE;
  }

  // Legacy method - keeping for compatibility but adapting to interface
  @Override
  public boolean crearInstitucion(String nombre, String descripcion, String sitioWeb)
      throws InstitucionRepetidaException {
    if (Tx.inTx(
        entityManager -> {
          try {
            return institucionRepo.noExisteInstitucion(entityManager, nombre);
          } catch (RuntimeException e) {
            return false;
          }
        })) {
      // Institution doesn't exist, create it
      InstitucionFactory factory = InstitucionFactory.get();
      Tx.inTx(
          emt -> {
            factory.crearInstitucion(emt, nombre, sitioWeb, descripcion);
            return null;
          });
      return true;
    } else {
      // Institution already exists, throw exception
      throw new InstitucionRepetidaException("La institución " + nombre + " ya está registrada.");
    }
  }

  public Set<DTInstitucion> listarInstituciones() {
    return Tx.inTx(entityManager -> institucionRepo.listarInstituciones(entityManager));
  }

  @Override
  public DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion) {
    return Tx.inTx(
        entityManager -> {
          try {
            var inst = institucionRepo.findByNombre(entityManager, nombreInstitucion).orElse(null);
            if (inst == null) return null;

            var patrocinio = inst.getPatrocinios().stream().findFirst().orElse(null);
            if (patrocinio == null) return null;
            return patrocinio.toDTPatrocinio(inst.toDTInstitucion());
          } catch (RuntimeException e) {
            return null;
          }
        });
  }

  @Override
  public boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion) {
    return Tx.inTx(
        entityManager -> {
          try {
            var inst = institucionRepo.findByNombre(entityManager, nombreInstitucion).orElse(null);
            if (inst == null) return false;

            // Would need proper user repository integration
            // For now, placeholder implementation
            return true;
          } catch (RuntimeException e) {
            return false;
          }
        });
  }
}
