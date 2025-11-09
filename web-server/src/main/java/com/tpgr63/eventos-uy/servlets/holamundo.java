package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservice.PortFactory;
import webservice.UsuarioService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class holamundo
 */
@WebServlet("/holamundo")
public class holamundo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public holamundo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	  PortFactory portFactory = PortFactory.get();
	  UsuarioService usuarioPort = PortFactory.get().getUsuarioPort();
	  try {
	    response.getWriter().append("Served at: ").append(usuarioPort.obtenerUsuario("atorres").getNickname());
    }
    catch (Exception e) {
      // TODO: handle exception
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
