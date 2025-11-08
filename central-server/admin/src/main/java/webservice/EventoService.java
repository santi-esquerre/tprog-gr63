package webservice;

import java.util.ArrayList;
import java.util.List;
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
	public ArrayList<String> obtenerCategorias() {
		return new ArrayList<>(eventoController.obtenerCategorias());
	}
	
	@WebMethod
	boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException {
		return eventoController.altaEvento(datosEventoAlta);
	}
	
	@WebMethod
	public ArrayList<DTEvento> listarEventos() {
		return new ArrayList<>(eventoController.listarEventos());
	}
	
	@WebMethod
	public ArrayList<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException {
		return new ArrayList<>(eventoController.mostrarEdiciones(nombreEvento));
	}
	
	@WebMethod
	public ArrayList<DTEdicion> mostrarEdicionesPorEstado(String nombreEvento, EstadoEdicion estado)
		      throws ValidationInputException {
		return new ArrayList<>(eventoController.mostrarEdiciones(nombreEvento, estado));
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
