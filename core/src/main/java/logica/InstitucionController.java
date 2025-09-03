package logica;

import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import datatypes.DTRegistrosOtorgados;
import dominio.Institucion;
import dominio.Patrocinio;
import exceptions.InstitucionNoExistenteException;
import exceptions.InstitucionRepetidaException;
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
	
	// Legacy method - keeping for compatibility but adapting to interface
	@Override
	public boolean crearInstitucion(String nombre, String descripcion, String sitioWeb) throws InstitucionRepetidaException {
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
		return Tx.inTx(em -> institucionRepo.listarInstituciones(em));
	}
	public boolean crearPatrocinio(String nombreEdicionEvento, String nombreInstitucion,
								   DTRegistrosOtorgados registrosAOtorgar, String codigo) {
		// Placeholder implementation - would need full business logic
		return Tx.inTx(em -> {
			try {
				var inst = institucionRepo.findByNombre(em, nombreInstitucion)
						.orElseThrow(() -> new InstitucionNoExistenteException("Institución inexistente: " + nombreInstitucion));
				
				var pat = Patrocinio.crearBasico(codigo);
				inst.agregarPatrocinio(pat);
				em.persist(pat);
				return true;
			} catch (Exception e) {
				return false;
			}
		});
	}

	@Override
	public DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion) {
		return Tx.inTx(em -> {
			try {
				var inst = institucionRepo.findByNombre(em, nombreInstitucion)
						.orElse(null);
				if (inst == null) return null;
				
				var patrocinio = inst.getPatrocinios().stream().findFirst().orElse(null);
				if (patrocinio == null) return null;
				return patrocinio.toDTPatrocinio(inst.toDTInstitucion());
			} catch (Exception e) {
				return null;
			}
		});
	}

	@Override
	public boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion) {
		return Tx.inTx(em -> {
			try {
				var inst = institucionRepo.findByNombre(em, nombreInstitucion)
						.orElse(null);
				if (inst == null) return false;
				
				// Would need proper user repository integration
				// For now, placeholder implementation
				return true;
			} catch (Exception e) {
				return false;
			}
		});
	}
				
}
