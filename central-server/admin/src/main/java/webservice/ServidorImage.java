package webservice;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;

import interfaces.Factory;

public class ServidorImage {
	private static final ServidorImage INSTANCE = new ServidorImage();
	Server server;
	ServerConnector connector;
	int port = 8090; 
	
	public static ServidorImage get() {
		return INSTANCE;
	}
	
	public synchronized void iniciarServidor() {
		try {
			
			if (server != null && server.isStarted()) {
				return;
			}
			
			server = new Server();
			connector = new ServerConnector(server);
			
			connector.setPort(port);
			server.addConnector(connector);
			
			ResourceHandler resourceHandler = new ResourceHandler();
			resourceHandler.setResourceBase(Factory.get().getIImageController().getDefaultDirectory());
			server.setHandler(resourceHandler);
			
			server.start();
			System.out.println("Servidor de imagenes iniciado en el puerto " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getURLBase() {

		if (server == null || !server.isStarted()) {
			iniciarServidor();
		}
		String host = connector.getHost();
		if (host == null || host.isEmpty() || host.equals("0.0.0.0") ) {
			try {
				host = java.net.InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "http://" + host + ":" + port + "/";
	}
	
}
