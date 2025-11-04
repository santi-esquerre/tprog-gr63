package webservice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService
public class Publicador {
  private Endpoint endpoint;
  public Publicador() {}
  
  @WebMethod(exclude = true)
  public void publicar() {
    endpoint = Endpoint.publish("http://localhost:9050/publicador", this);
  }
  
  @WebMethod(exclude = true)
  public Endpoint getEndpoint() {
    return endpoint;
  }
}
