package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EstadoSesion;
import webservice.DtEdicionDetallada;
import webservice.DtRegistro;
import webservice.DtUsuario;
import webservice.EdicionService;
import webservice.EstadoEdicion;
import webservice.PortFactory;
import webservice.TipoUsuario;
import webservice.ValidationInputException;
import webservice.ValidationInputException_Exception;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import controllers.SessionManager;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo(); // /nombreDeLaEdicion
		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}

		String nombreEdicionUrl = pathInfo.substring(1); // nombreDeLaEdicion
		String nombreEdicion = nombreEdicionUrl.replace('-', ' ');

		PortFactory factory = PortFactory.get();

		boolean usuarioRegistrado = false;
		String mensajeUsuarioNoLogueado = "Inicie sesión para registrarse en la edición!";

		SessionManager.initSesion(request);
		EstadoSesion estado_sesion = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		try {
			EdicionService edicionPort = factory.getEdicionPort();
			DtEdicionDetallada dtEdicionDetallada = edicionPort.obtenerDatosDetalladosEdicion(nombreEdicion);

			if ((dtEdicionDetallada != null)
					&& dtEdicionDetallada.getEdicion().getEstado() != EstadoEdicion.ACEPTADA) {
				response.sendRedirect(request.getContextPath() + "/inicio");
				return;
			}

			if (estado_sesion == EstadoSesion.LOGIN_CORRECTO) {
				Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
				DtUsuario dtUsuarioTemp = null;

				if (usuarioObj instanceof DtUsuario) {
				  dtUsuarioTemp = (DtUsuario) usuarioObj;
				} else if (usuarioObj instanceof String) {
					// Retrocompatibilidad: si todavía es String, obtenerlo de la BD
					var usuarioPort = factory.getUsuarioPort();
					try {
						dtUsuarioTemp = usuarioPort.obtenerUsuario((String) usuarioObj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				final DtUsuario dtUsuario = dtUsuarioTemp;

				if (dtUsuario == null) {
					request.setAttribute("errorRegistro", mensajeUsuarioNoLogueado);
				}
				request.setAttribute("usuario", dtUsuario);
				if ((dtEdicionDetallada != null) && (dtUsuario != null)
						&& (dtUsuario.getTipo() == TipoUsuario.ASISTENTE)) {
					usuarioRegistrado = !factory.getEdicionPort()
							.asistenteNoRegistrado(nombreEdicion, dtUsuario.getNickname());
					request.setAttribute("usuarioRegistrado", usuarioRegistrado);
				} else if ((dtEdicionDetallada != null) && (dtUsuario != null)
						&& (dtUsuario.getTipo() == TipoUsuario.ORGANIZADOR)) {
					request.setAttribute("errorRegistro", "Solo los asistentes pueden registrarse en una edición.");
				}
				if (usuarioRegistrado && (dtEdicionDetallada != null)) {
					DtRegistro registroUsuario = dtEdicionDetallada.getRegistros().stream()
							.filter(r -> r.getNicknameAsistente().equals(dtUsuario.getNickname()))
							.findFirst()
							.orElse(null);
					request.setAttribute("datosRegistroUsuario", registroUsuario);
				}
			} else {
				request.setAttribute("errorRegistro", mensajeUsuarioNoLogueado);
			}
			request.setAttribute("edicionDetallada", dtEdicionDetallada);
			request.getRequestDispatcher("/WEB-INF/ediciones/consultaDetalladaEdicion.jsp").forward(request, response);

		} catch (ValidationInputException_Exception e) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String nombreEdicion = request.getParameter("nombreEdicion");
		String nombreTipoRegistro = request.getParameter("nombreTipoRegistro");
		String nicknameAsistente = request.getParameter("nickname");
		System.out.println("Nickname asistente en edicion servlet: " + nicknameAsistente);
		String codigo = request.getParameter("codigoPatrocinio");

		if (action.equals("validarCodigo")) {
			EdicionService edicionPort = PortFactory.get().getEdicionPort();
			boolean codigoValido = false;
			try {
				codigoValido = edicionPort.verificarCodigoPatrocinio(
						nombreEdicion,
						nombreTipoRegistro,
						nicknameAsistente,
						codigo);
			} catch (ValidationInputException_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (codigoValido) {
				request.setAttribute("abrirModal", nombreTipoRegistro);
				request.setAttribute("codigoValido", true);
				request.setAttribute("codigoIngresado", codigo);
				doGet(request, response);
				return;
			} else {
				System.out.println("El código ingresado no es válido.");
				request.setAttribute("abrirModal", nombreTipoRegistro);
				request.setAttribute("codigoValido", false);
				request.setAttribute("codigoIngresado", codigo);
				doGet(request, response);
				return;
			}
			// Handle registration action
			// You can add your registration logic here
		} else if (action.equals("confirmarRegistro")) {
			EdicionService edicionPort = PortFactory.get().getEdicionPort();
			LocalDate fechaActual = LocalDate.now();
			GregorianCalendar gCalendar = GregorianCalendar.from(fechaActual.atStartOfDay(ZoneId.systemDefault()));
			try {
	      XMLGregorianCalendar fechaRegistro = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
				if (codigo.isEmpty()) {
					edicionPort.altaRegistroEdicionEvento(nombreEdicion, nombreTipoRegistro, nicknameAsistente,
							fechaRegistro);
				} else {
					edicionPort.altaRegistroEdicionEventoConCodigo(nombreEdicion, nombreTipoRegistro, nicknameAsistente,
							fechaRegistro, codigo);
				}
			} catch (ValidationInputException_Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("errorRegistro", e.getMessage());
				doGet(request, response);
				return;
			} catch (IllegalArgumentException ie) {
				// TODO Auto-generated catch block
				request.setAttribute("errorRegistro", ie.getMessage());
				doGet(request, response);
				return;
			} catch (Exception e) {
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
