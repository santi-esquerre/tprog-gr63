package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EstadoSesion;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import controllers.SessionManager;
import interfaces.Factory;
import interfaces.IEdicionController;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTUsuario;
import datatypes.DTRegistro;
import datatypes.TipoUsuario;
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
		String nombreEdicion = nombreEdicionUrl.replace('-',' '); 
		
		Factory factory = Factory.get();
		
		boolean usuarioRegistrado = false;
		String mensajeUsuarioNoLogueado = "Inicie sesión para registrarse en la edición!";
		
		SessionManager.initSesion(request);
		EstadoSesion estado_sesion = (EstadoSesion) request.getSession().getAttribute("estado_sesion");
		
		try {
			IEdicionController edicionController = factory.getIEdicionController();
			DTEdicionDetallada dtEdicionDetallada = edicionController.obtenerDatosDetalladosEdicion(nombreEdicion);
			
			if (estado_sesion == EstadoSesion.LOGIN_CORRECTO) {
				String usuario = (String) request.getSession().getAttribute("usuario_logueado");
				var usuarioController = factory.getIUsuarioController();
				final DTUsuario dtUsuario = usuarioController.obtenerUsuario(usuario);
				if (dtUsuario == null) {
					request.setAttribute("errorRegistro", mensajeUsuarioNoLogueado);
				}
				request.setAttribute("usuario", dtUsuario);
				
				if ((dtEdicionDetallada != null) && (dtUsuario != null) && (dtUsuario.getTipo() == TipoUsuario.ASISTENTE)) {
					usuarioRegistrado = !factory.getIEdicionController()
							.asistenteNoRegistrado(nombreEdicion, dtUsuario.nickname());
					request.setAttribute("usuarioRegistrado", usuarioRegistrado);
				} else if ((dtEdicionDetallada != null) && (dtUsuario != null) && (dtUsuario.getTipo() == TipoUsuario.ORGANIZADOR)) {
					request.setAttribute("errorRegistro", "Solo los asistentes pueden registrarse en una edición.");
				}
				if (usuarioRegistrado && (dtEdicionDetallada != null)) {
					DTRegistro registroUsuario = dtEdicionDetallada.registros().stream()
							.filter(r -> r.nicknameAsistente().equals(dtUsuario.nickname()))
							.findFirst()
							.orElse(null);
					request.setAttribute("datosRegistroUsuario", registroUsuario);
				}
			} else {
				request.setAttribute("errorRegistro", mensajeUsuarioNoLogueado);
			}
			request.setAttribute("edicionDetallada", dtEdicionDetallada);
			request.getRequestDispatcher("/WEB-INF/ediciones/consultaDetalladaEdicion.jsp").forward(request, response);			
		
		} 
		catch (ValidationInputException e) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		} 
		catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String nombreEdicion = request.getParameter("nombreEdicion");
		String nombreTipoRegistro = request.getParameter("nombreTipoRegistro");
		String nicknameAsistente = request.getParameter("nickname");
		System.out.println("Nickname asistente en edicion servlet: " + nicknameAsistente);
		String codigo = request.getParameter("codigoPatrocinio");
		
		if (action.equals("validarCodigo")) {
			IEdicionController edicionController = Factory.get().getIEdicionController();
			boolean codigoValido = false;
			try {
				codigoValido = edicionController.verificarCodigoPatrocinio(
						nombreEdicion,
						nombreTipoRegistro,
						nicknameAsistente,
						codigo
				);
			} catch (ValidationInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (codigoValido) {
				request.setAttribute("abrirModal", nombreTipoRegistro);
				request.setAttribute("codigoValido", true);
				request.setAttribute("codigoIngresado", codigo);
				doGet(request, response);
				return;	
			}
			else {
				System.out.println("El código ingresado no es válido.");
				request.setAttribute("abrirModal", nombreTipoRegistro);
				request.setAttribute("codigoValido", false);
				request.setAttribute("codigoIngresado", codigo);
				doGet(request, response);
				return;
			}
			// Handle registration action
			// You can add your registration logic here
		}
		else if (action.equals("confirmarRegistro")) {
			IEdicionController edicionController = Factory.get().getIEdicionController();
			LocalDate fechaActual = LocalDate.now();
			Date fechaRegistro = java.sql.Date.valueOf(fechaActual);
			try {
				if (codigo.isEmpty()) {
						edicionController.altaRegistroEdicionEvento(nombreEdicion, nombreTipoRegistro, nicknameAsistente, fechaRegistro);
					}
				else {
						edicionController.altaRegistroEdicionEvento(nombreEdicion, nombreTipoRegistro, nicknameAsistente, fechaRegistro, codigo);
					}
			}
			catch (ValidationInputException e) {
				// TODO Auto-generated catch block
				request.setAttribute("errorRegistro", e.getMessage());
				doGet(request, response);
				return;
			}
			catch (IllegalArgumentException ie) {
				// TODO Auto-generated catch block
				request.setAttribute("errorRegistro", ie.getMessage());
				doGet(request, response);
				return;
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("errorRegistro", e.getMessage());
				doGet(request, response);
				return;
			}
			System.out.println("Registro exitoso.");
			request.setAttribute("registroExitoso", true);
			doGet(request, response);
			return;
	
		}
		doGet(request, response);
	}

}
