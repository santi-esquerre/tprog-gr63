package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EstadoSesion;
import webservice.PortFactory;
import webservice.SesionService;
import webservice.UsuarioService;

import java.io.IOException;

import controllers.SessionManager;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	  SesionService sesionPort=PortFactory.get().getSesionPort();
		// TODO Auto-generated method stub
		HttpSession objSesion = request.getSession();

		if (request.getParameter("cerrar_sesion") != null) {
			objSesion.setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
			response.sendRedirect("inicio");
			return;
		}

		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");

		SessionManager.initSesion(request);

		if (objSesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_CORRECTO) {
			response.sendRedirect("inicio");
			return;
		}

		if ((usuario == null || clave == null) && objSesion.getAttribute("estado_sesion") == EstadoSesion.NO_LOGIN) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sesion/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		EstadoSesion nuevoEstado;

		// chequea contraseña
		if (!sesionPort.iniciarSesion(usuario, clave)) {
			request.setAttribute("alert_message", "Usuario y/o contraseña incorrectos");
			nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
			objSesion.setAttribute("estado_sesion", nuevoEstado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sesion/login.jsp");
			dispatcher.forward(request, response);
			return;
		} else {
			nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
			objSesion.setAttribute("estado_sesion", nuevoEstado);
			UsuarioService usuarioPort = PortFactory.get().getUsuarioPort();

			// Obtener el objeto DTUsuario completo
			try {
				var dtUsuario = usuarioPort.obtenerUsuario(usuario);
				request.getSession().setAttribute("usuario_logueado", dtUsuario);
			} catch (Exception e) {
				e.printStackTrace();
				// Si falla, al menos guardamos el nickname
				request.getSession().setAttribute("usuario_logueado", usuario);
			}

			// redirige a la página principal para que luego rediriga a la página
			// que corresponde
			response.sendRedirect("inicio");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
