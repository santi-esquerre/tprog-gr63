package webservice;

import interfaces.Factory;
import interfaces.ISesionController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class SesionService {
	
	ISesionController sesionController = Factory.get().getISesionController();
	
	//Constructor
	public SesionService() {}
	
	@WebMethod
	public boolean iniciarSesion(String username, String password) {
		return sesionController.iniciarSesion(username,  password);
	}
}
