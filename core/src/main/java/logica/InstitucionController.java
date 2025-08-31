package logica;

import infra.Tx;
import repos.InstitucionRepository;
import exceptions.InstitucionRepetidaException;
import java.util.Set;

import datatypes.DTInstitucion;

public class InstitucionController implements interfaces.IInstitucionController {

	private static final InstitucionController INSTANCE = new InstitucionController();
	private final InstitucionRepository institucionRepo = InstitucionRepository.get();
	
	private InstitucionController() {}
	public static InstitucionController get() { return INSTANCE; }
	
	@Override
	public void crearInstitucion(String nombre, String sitioweb, String descripcion) throws InstitucionRepetidaException {
		if(Tx.inTx(em -> {
			try {
				return institucionRepo.noExisteInstitucion(em, nombre);
			} catch (Exception e) {
				return false;
			}
		})) // Se verifica que no exista una institucion con ese nombre
		{
				
			InstitucionFactory factory = InstitucionFactory.get();
			Tx.inTx(emt -> {
				factory.crearInstitucion(emt, nombre, sitioweb, descripcion);
				return null;
			}); // Se crea la institucion

		}
		else {
			throw new InstitucionRepetidaException("La institución " + nombre + " ya está registrada.");  // Si ya existe, se lanza una excepcion
		}
					
	}
	
	public Set<DTInstitucion> listarInstituciones() {
		return Tx.inTx(em -> institucionRepo.listarInstituciones(em));
	}
				
}
