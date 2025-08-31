package interfaces;
import java.util.Set;
import datatypes.DTInstitucion;

import exceptions.InstitucionRepetidaException;

public interface IInstitucionController {
	public void crearInstitucion(String nombre, String sitioweb, String descripcion) throws InstitucionRepetidaException;
	
	public Set<DTInstitucion> listarInstituciones();
}
