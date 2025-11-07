package webservice;

import java.util.Properties;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

import java.io.FileInputStream;

@WebService
public class Publicador {
  private Endpoint endpoint;
  public Publicador() {}
  
  @WebMethod(exclude = true)
  public void publicar() {
    Properties props = new Properties();
    try {

      props.load(new FileInputStream(System.getProperty("user.home") + "/.eventos-uy/config.properties"));

      String endpointUrl = props.getProperty("webservice.endpoint", "http://localhost:9050/publicador");
      System.out.println("Publicando en: " + endpointUrl);
      Endpoint.publish(endpointUrl.strip(), this);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @WebMethod(exclude = true)
  public Endpoint getEndpoint() {
    return endpoint;
  }
  
  @WebMethod
  public String holaMundo(String nombre) {
    return "Hola " + nombre + ", bienvenido al servicio web!";
  }
}
