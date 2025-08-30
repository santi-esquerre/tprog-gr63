package interfaces;

import exceptions.InstitucionRepetidaException;

public interface IInstitucionController {
	public void crearInstitucion(String nombre, String sitioweb, String descripcion) throws InstitucionRepetidaException;
}
