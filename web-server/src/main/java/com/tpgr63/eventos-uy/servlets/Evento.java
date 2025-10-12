package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import datatypes.DTEventoDetallado;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEventoController;

/**
 * Servlet implementation class Evento
 */
@WebServlet("/evento")
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
		String nombre = request.getParameter("nombre"); // obtiene "nombreDelEvento" de "/nombreDelEvento"
		IEventoController eventoController = Factory.get().getIEventoController();
		try {
			DTEventoDetallado evento = eventoController.obtenerDatosDetalladosEvento(nombre);
			request.setAttribute("data_evento", evento);
			
			request.getRequestDispatcher("/WEB-INF/eventos/consultaDetalladaEvento.jsp").forward(request, response);
		} catch(ValidationInputException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("inicio");
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
