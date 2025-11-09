package webservice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEdicionController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class EdicionService {
	
	private IEdicionController edController = Factory.get().getIEdicionController();
	
	//Constructor
	public EdicionService() {}
	
	//Operaciones las cuales quiero publicar
	
	@WebMethod
	public void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro)
		      throws ValidationInputException {
		edController.altaTipoRegistro(nombreEdicion, datosTipoRegistro);
	}
	
	@WebMethod
	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro)
		      throws ValidationInputException {
		return edController.consultaTipoRegistro(nombreEdicion, nombreTipoRegistro);
	}
	
	@WebMethod
	public DTTipoRegistroArray mostrarTiposDeRegistro(String nombreEdicion) {
		return new DTTipoRegistroArray(new ArrayList<>(edController.mostrarTiposDeRegistro(nombreEdicion)));
	}
	
	@WebMethod
	public DTAsistenteArray mostrarAsistentes(String nombreEdicion) {
		return new DTAsistenteArray(new ArrayList<>(edController.mostrarAsistentes(nombreEdicion)));
	}
	
	@WebMethod
	public boolean cupoDisponible(String nombreEdicion, String nombreTipoRegistro) {
		return edController.cupoDisponible(nombreEdicion, nombreTipoRegistro);
	}
	
	@WebMethod
	public boolean asistenteNoRegistrado(String nombreEdicion, String nickname) {
		return edController.asistenteNoRegistrado(nombreEdicion, nickname);
	}
	
	@WebMethod
	public void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, java.util.Date fecha) {
		edController.altaRegistroEdicionEvento(nombreEdicion, nombreTipoRegistro, nickname, fecha);
	}
	
	@WebMethod
	public void altaRegistroEdicionEventoConCodigo(String nombreEdicion, String nombreTipoRegistro, String nickname, java.util.Date fecha, String codigo) throws ValidationInputException, IllegalArgumentException {
		edController.altaRegistroEdicionEvento(nombreEdicion, nombreTipoRegistro, nickname, fecha, codigo);
	}
	
	@WebMethod
	public void altaPatrocinio(Date fecha, String nombreEdicion, String nombreInstitucion, Float aporte,
	       String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
	       throws exceptions.ExistePatrocinioException, exceptions.CostoRegistrosGratuitosException, exceptions.CantidadCuposDisponiblesException, exceptions.InstitucionNoExistenteException {

    LocalDate localDate = fecha.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
		edController.altaPatrocinio(localDate, nombreEdicion, nombreInstitucion, aporte, nombreTipoRegistro, cantGratuitos, codigo, nivelPatrocinio);
	}
	
	@WebMethod
	public boolean verificarCodigoPatrocinio(String nombreEdicion, String nombreTipoRegistro, String nickname, String codigo) throws ValidationInputException {
		return edController.verificarCodigoPatrocinio(nombreEdicion, nombreTipoRegistro, nickname, codigo);
	}
	
	@WebMethod
	public DTEdicionDetallada obtenerDatosDetalladosEdicionPorEvento(String nombreEvento, String nombreEdicion)
	       throws ValidationInputException {
		return edController.obtenerDatosDetalladosEdicion(nombreEvento, nombreEdicion);
	}
	
	@WebMethod
	public DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEdicion)
	       throws ValidationInputException {
		return edController.obtenerDatosDetalladosEdicion(nombreEdicion);
	}
	
	@WebMethod
	public DTEdicionArray obtenerEdicionesPorOrganizador(String nicknameOrganizador)
		   throws ValidationInputException {
		return new DTEdicionArray(new ArrayList<>(edController.obtenerEdicionesPorOrganizador(nicknameOrganizador)));
	}
	

}
