package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservice.DtEventoDetallado;
import webservice.EventoService;
import webservice.PortFactory;
import webservice.ValidationInputException_Exception;

import java.io.IOException;

/**
 * Servlet implementation class Evento
 */
@WebServlet("/evento/*")
public class Evento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Evento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nombre = request.getPathInfo().substring(1); // obtiene "nombreDelEvento" de "/nombreDelEvento"
		EventoService eventoController = PortFactory.get().getEventoPort();
    try {
      DtEventoDetallado evento = eventoController.obtenerDatosDetalladosEventoAprobadas(nombre);
      request.setAttribute("data_evento", evento);

      request.getRequestDispatcher("/WEB-INF/eventos/consultaDetalladaEvento.jsp").forward(request, response);
    } catch(ValidationInputException_Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      response.sendRedirect(request.getContextPath());
    }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
