package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EstadoSesion;

import java.io.IOException;

import controllers.SessionManager;
import interfaces.Factory;
import interfaces.ISesionController;
import interfaces.IUsuarioController;

/**
 * Servlet implementation class Sesion
 */
@WebServlet("/sesion")
public class Sesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		HttpSession objSesion = request.getSession();
//		String usuario = request.getParameter("usuario");
//		String clave = request.getParameter("clave");
//		
//		SessionManager.initSesion(request);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sesion/login.jsp");
		dispatcher.forward(request, response);
	
//		if((usuario == null || clave == null) && objSesion.getAttribute("estado_sesion") ==  EstadoSesion.NO_LOGIN) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sesion/login.jsp");
//			dispatcher.forward(request, response);
//			return;
//		}
//		EstadoSesion nuevoEstado;
//		
//		ISesionController sesionController = Factory.get().getISesionController();
//
//		// chequea contraseña
//		if (!sesionController.iniciarSesion(usuario, clave)) {
//			nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sesion/login.jsp");
//			dispatcher.forward(request, response);
//			return;
//		}
//		else {
//			nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
//			
//			IUsuarioController usuarioController = Factory.get().getIUsuarioController();
//			
//			// setea el usuario logueado
//			request.getSession().setAttribute("usuario_logueado", usuario);
//		}
//
//		objSesion.setAttribute("estado_sesion", nuevoEstado);
//
//		// redirige a la página principal para que luego rediriga a la página
//		// que corresponde
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/inicio");
//		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
