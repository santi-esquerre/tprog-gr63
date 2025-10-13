package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import interfaces.Factory;
import interfaces.IEdicionController;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import exceptions.ValidationInputException;

/**
 * Servlet implementation class Edicion
 */
@WebServlet("/edicion/*")
public class Edicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edicion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo(); // /nombreDeLaEdicion
		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		String nombreEdicionUrl = pathInfo.substring(1); // nombreDeLaEdicion
		String nombreEdicion = nombreEdicionUrl.replaceAll("-+"," "); 
		
		Factory factory = Factory.get();
		IEdicionController edicionController = factory.getIEdicionController();
		
		try {
			DTEdicionDetallada dtEdicion = edicionController.obtenerDatosDetalladosEdicion(nombreEdicion);
			request.setAttribute("edicion", dtEdicion);
			request.getRequestDispatcher("/WEB-INF/ediciones/consultaDetalladaEdicion.jsp").forward(request, response);
			
		} catch (ValidationInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		request.getRequestDispatcher("/WEB-INF/ediciones/consultaDetalladaEdicion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
