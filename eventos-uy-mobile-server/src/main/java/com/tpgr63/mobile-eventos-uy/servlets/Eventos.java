package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservice.EventoService;
import webservice.PortFactory;
import webservice.ValidationInputException_Exception;
import webservice.XMLGregorianCalendarAdapter;
import webservice.DtEventoArray;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Servlet implementation class Eventos
 */
@WebServlet(urlPatterns = { "/eventos", "/api/eventos/*" })
public class Eventos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Eventos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String servletPath = request.getServletPath();
		if (servletPath.startsWith("/api/eventos")) {
			// Manejar solicitudes API aquí
			handleEventosApiGet(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/eventos/listadoEventos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void handleEventosApiGet(HttpServletRequest request, HttpServletResponse response) 
	        throws IOException {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    String identifier = request.getParameter("identifier");
	    String fechaAlta = request.getParameter("fecha");
	    
	    if (identifier.isEmpty()) {
	        identifier = null;
	    }
	    
	    if (fechaAlta.isEmpty()) {
	        fechaAlta = null;
	    }

	    XMLGregorianCalendar fecha = null;
	    Long id = null;

	    try {
	        // Si viene fecha vacía o null, NO parsear
	        if (fechaAlta != null && !fechaAlta.isBlank() && !"undefined".equals(fechaAlta)) {
	            Instant instant = Instant.parse(fechaAlta);
	            GregorianCalendar gcal = GregorianCalendar.from(
	                    ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
	            fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
	        }

	        // Si viene id vacío o null, NO parsear
	        if (identifier != null && !identifier.isBlank() && !"undefined".equals(identifier)) {
	            id = Long.parseLong(identifier);
	        }

	        // Llamada al WS con parámetros null permitidos
	        EventoService eventoService = PortFactory.get().getEventoPort();
	        System.out.println("Llamando a listarUltimosEventos con fecha: " + fecha + " e id: " + id);
	        DtEventoArray eventos = eventoService.listarUltimosEventos(12, fecha, id);

	        Gson gson = new GsonBuilder()
	        		.registerTypeAdapter(javax.xml.datatype.XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
	        	    .registerTypeHierarchyAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
	        	    .setDateFormat("yyyy-MM-dd")
	        	    .setPrettyPrinting()
	        	    .create();

	        String jsonResponse = gson.toJson(eventos);
	        response.getWriter().write(jsonResponse);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(500);
	        response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
	    }
	}

	

}
