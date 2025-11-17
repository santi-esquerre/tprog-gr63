package webservice;

import java.util.ArrayList;
import java.util.Date;
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
import jakarta.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class EventoService {
	
	private IEventoController eventoController = Factory.get().getIEventoController();
	
	//Constructor
	public EventoService() {}
	
	@WebMethod
	public List<String> obtenerCategorias() {
		return eventoController.obtenerCategorias().stream().toList();
	}
	
	@WebMethod
	boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException {
		return eventoController.altaEvento(datosEventoAlta);
	}
	
	@WebMethod
	public DTEventoArray listarEventos() {
		return new DTEventoArray(new ArrayList<>(eventoController.listarEventos()));
	}
	
	@WebMethod
	public DTEventoArray listarUltimosEventos(Integer cantidad, Date ultimaFechaAlta, Long ultimoIdentifier)
			  throws ValidationInputException { 
		return new DTEventoArray(new ArrayList<>(eventoController.listarUltimosEventos(cantidad, ultimaFechaAlta, ultimoIdentifier)));
	}
	
	@WebMethod
	public DTEdicionArray mostrarEdiciones(String nombreEvento) throws ValidationInputException {
		return new DTEdicionArray(new ArrayList<>(eventoController.mostrarEdiciones(nombreEvento)));
	}
	
	@WebMethod
	public DTEdicionArray mostrarEdicionesPorEstado(String nombreEvento, EstadoEdicion estado)
			  throws ValidationInputException {
		return new DTEdicionArray(new ArrayList<>(eventoController.mostrarEdiciones(nombreEvento, estado)));
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
	
	@WebMethod
	public boolean AltaEvento(DTEventoAlta evento) throws ValidationInputException {
    return eventoController.altaEvento(evento);
  }
}
