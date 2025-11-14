package webservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PortFactory {
	  final static PortFactory INSTANCE = new PortFactory();
	  private static String wsdlLocation = "http://localhost:9050";
	  
	  private PortFactory() {
		    try (InputStream input = new FileInputStream(System.getProperty("user.home")  + "/.eventos-uy/config.properties")) {
		        java.util.Properties prop = new java.util.Properties();
		        prop.load(input);
		        wsdlLocation = prop.getProperty("webservice.endpoint", "http://localhost:9050").strip();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		  }
		  
		  public static  PortFactory get() {
		    return INSTANCE;
		  }
		  
		  public  InstitucionService getInstitucionPort() {
		    URL wsdlUrl;
		    try {
		      wsdlUrl = new URI(wsdlLocation + "/ws/institucion?wsdl").toURL();
		      InstitucionServiceService service = new InstitucionServiceService(wsdlUrl);
		      return service.getInstitucionServicePort();
		    }
		    catch (MalformedURLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    catch (URISyntaxException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    return null;
		  }
		  
		  public  EdicionService getEdicionPort() {
		    URL wsdlUrl;
		    try {
		      wsdlUrl = new URI(wsdlLocation + "/ws/edicion?wsdl").toURL();
		      EdicionServiceService service = new EdicionServiceService(wsdlUrl);
		      return service.getEdicionServicePort();
		    }
		    catch (MalformedURLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    catch (URISyntaxException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    return null;
		  }
		  
		  public  EventoService getEventoPort() {
		    URL wsdlUrl;
		    try {
		      wsdlUrl = new URI(wsdlLocation + "/ws/evento?wsdl").toURL();
		      EventoServiceService service = new EventoServiceService(wsdlUrl);
		      return service.getEventoServicePort();
		    }
		    catch (MalformedURLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    catch (URISyntaxException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    return null;
		  }
		  
		  public  SesionService getSesionPort() {
		    URL wsdlUrl;
		    try {
		      wsdlUrl = new URI(wsdlLocation + "/ws/sesion?wsdl").toURL();
		      SesionServiceService service = new SesionServiceService(wsdlUrl);
		      return service.getSesionServicePort();
		    }
		    catch (MalformedURLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    catch (URISyntaxException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    return null;
		  }
		  
		  public  UsuarioService getUsuarioPort() {
		    URL wsdlUrl;
		    try {
		      wsdlUrl = new URI(wsdlLocation + "/ws/usuario?wsdl").toURL();
		      UsuarioServiceService service = new UsuarioServiceService(wsdlUrl);
		      return service.getUsuarioServicePort();
		    }
		    catch (MalformedURLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    catch (URISyntaxException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		    return null;
		  }
		  
		  public  ImageService getImagePort() {
			    URL wsdlUrl;
			    try {
			      wsdlUrl = new URI(wsdlLocation + "/ws/image?wsdl").toURL();
			      ImageServiceService service = new ImageServiceService(wsdlUrl);
			      return service.getImageServicePort();
			    }
			    catch (MalformedURLException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }
			    catch (URISyntaxException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }
			    return null;
			  }	  

}
