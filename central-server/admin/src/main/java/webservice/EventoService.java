package webservice;

import java.util.Set;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import datatypes.EstadoEdicion;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEventoController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class EventoService {
	
	private IEventoController eventoController = Factory.get().getIEventoController();
	
	//Constructor
	public EventoService() {}
	
	@WebMethod
	public Set<String> obtenerCategorias() {
		return eventoController.obtenerCategorias();
	}
	
	@WebMethod
	boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException {
		return eventoController.altaEvento(datosEventoAlta);
	}
	
	@WebMethod
	public Set<DTEvento> listarEventos() {
		return eventoController.listarEventos();
	}
	
	@WebMethod
	public Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException {
		return eventoController.mostrarEdiciones(nombreEvento);
	}
	
	@WebMethod
	public Set<DTEdicion> mostrarEdiciones(String nombreEvento, EstadoEdicion estado)
		      throws ValidationInputException {
		return eventoController.mostrarEdiciones(nombreEvento, estado);
	}
	
	@WebMethod
	public boolean agregarEdicionAEvento(String nombreEvento, String nicknameOrganizador, DTEdicion datos)
		      throws ValidationInputException {
		return eventoController.agregarEdicionAEvento(nombreEvento, nicknameOrganizador, datos);
	}
	
	@WebMethod
	public DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento)
		      throws ValidationInputException {
		return eventoController.obtenerDatosDetalladosEvento(nombreEvento);
	}
	
	@WebMethod
	public DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(String nombreEvento)
		      throws ValidationInputException {
		return eventoController.obtenerDatosDetalladosEventoAprobadas(nombreEvento);
	}
}
