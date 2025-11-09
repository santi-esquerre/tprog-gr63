package webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import exceptions.InstitucionRepetidaException;
import interfaces.Factory;
import interfaces.IInstitucionController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class InstitucionService {
	
	private IInstitucionController institucionController = Factory.get().getIInstitucionController();
	
	//Constructor
	public InstitucionService() {}
	
	@WebMethod
	public boolean crearInstitucion(String nombre, String descripcion, String sitioWeb, String foto) throws InstitucionRepetidaException {
		return institucionController.crearInstitucion(nombre, descripcion, sitioWeb, foto);
	}
	
	@WebMethod
	public DTInstitucionArray listarInstituciones() {
		return new DTInstitucionArray(new ArrayList<>(institucionController.listarInstituciones()));
	}
	
	@WebMethod
	public DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion) {
		return institucionController.obtenerDTPatrocinio(nombreEdicionEvento, nombreInstitucion);
	}
	
	@WebMethod
	public boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion) {
		return institucionController.afiliarAsistenteAInstitucion(nombreAsistente, nombreInstitucion);
	}
}
