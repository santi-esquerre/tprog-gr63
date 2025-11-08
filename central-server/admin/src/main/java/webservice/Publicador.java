package webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import java.io.FileInputStream;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {
	private List<Endpoint> endpoints = new ArrayList<>();
  public Publicador() {}
  
  @WebMethod(exclude = true)
  public void publicar() {
    Properties props = new Properties();
    try {

      props.load(new FileInputStream(System.getProperty("user.home") + "/.eventos-uy/config.properties"));

      String endpointUrl = props.getProperty("webservice.endpoint", "http://localhost:9050/publicador");
      System.out.println("Publicando en: " + endpointUrl);
      System.out.println("Primero");
      endpoints.add(Endpoint.publish(endpointUrl.strip() + "/ws/edicion", new EdicionService()));
      System.out.println("Segundo");
      endpoints.add(Endpoint.publish(endpointUrl.strip() + "/ws/evento", new EventoService()));
      System.out.println("Tercero");
      endpoints.add(Endpoint.publish(endpointUrl.strip() + "/ws/institucion", new InstitucionService()));
      System.out.println("Cuarto");
      endpoints.add(Endpoint.publish(endpointUrl.strip() + "/ws/sesion", new SesionService()));
      System.out.println("Quinto");
      endpoints.add(Endpoint.publish(endpointUrl.strip() + "/ws/usuario", new UsuarioService()));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @WebMethod(exclude = true)
  public List<Endpoint>  getEndpoints() {
    return endpoints;
  }
  
  @WebMethod(exclude = true)
  public void detener() {
      for (Endpoint ep : endpoints) {
          ep.stop();
      }
  }
    
}
