package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import controllers.SessionManager;
import datatypes.DTEvento;
import datatypes.DTEventoDetallado;
import datatypes.DTUsuario;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEventoController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EstadoSesion;

/**
 * Servlet implementation class Inicio.
 */
@WebServlet("/inicio")
public class Inicio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEventoController eventoController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Inicio() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		eventoController = Factory.get().getIEventoController();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Inicializar estado de sesión
			SessionManager.initSesion(request);
			HttpSession session = request.getSession();
			EstadoSesion estado = (EstadoSesion) session.getAttribute("estado_sesion");

			// Datos de sesión para el header
			if (estado == EstadoSesion.LOGIN_CORRECTO) {
				Object usuarioObj = session.getAttribute("usuario_logueado");
				if (usuarioObj instanceof DTUsuario) {
					DTUsuario usuario = (DTUsuario) usuarioObj;
					request.setAttribute("usuarioLogueado", usuario);
					request.setAttribute("sesionIniciada", true);
				} else {
					// Si no es DTUsuario, no hay sesión válida
					request.setAttribute("sesionIniciada", false);
				}
			} else {
				request.setAttribute("sesionIniciada", false);
			} // Obtener eventos (retorna Set<DTEvento>)
			Set<DTEvento> todosEventosSet = eventoController.listarEventos();
			List<DTEvento> todosEventos = new ArrayList<>(todosEventosSet);

			// Obtener eventos destacados detallados (primeros 6 eventos)
			List<DTEventoDetallado> eventosDestacados = new ArrayList<>();
			int limite = Math.min(6, todosEventos.size());

			for (int i = 0; i < limite; i++) {
				DTEvento evento = todosEventos.get(i);
				try {
					DTEventoDetallado eventoDetallado = eventoController
							.obtenerDatosDetalladosEventoAprobadas(evento.nombre());
					eventosDestacados.add(eventoDetallado);
				} catch (ValidationInputException e) {
					// Si hay error, continuar con el siguiente
					e.printStackTrace();
				}
			}

			request.setAttribute("eventosDestacados", eventosDestacados);

			// Estadísticas del sistema
			int totalEventos = todosEventos.size();
			request.setAttribute("totalEventos", totalEventos);

			// Calcular total de ediciones (sumando las de cada evento detallado)
			int totalEdiciones = 0;
			for (DTEventoDetallado eventoDetallado : eventosDestacados) {
				if (eventoDetallado.ediciones() != null) {
					totalEdiciones += eventoDetallado.ediciones().size();
				}
			}
			request.setAttribute("totalEdiciones", totalEdiciones);

			// Forward al JSP
			request.getRequestDispatcher("/WEB-INF/inicio/inicio.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/inicio");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
