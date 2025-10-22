package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import datatypes.DTEvento;
import datatypes.DTUsuarioItemListado;
import interfaces.Factory;
import interfaces.IEventoController;
import interfaces.IUsuarioController;

/**
 * Servlet implementation class Eventos
 */
@WebServlet("/eventos")
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
		// Obtener controlador y lista completa de usuarios
		IEventoController eventoController = Factory.get().getIEventoController();
		Set<DTEvento> listadoEventos = eventoController.listarEventos();

		// Evitar que sea null
		if (listadoEventos == null) listadoEventos = Set.of();

		// Constante de paginación (ejemplo)
		final int CANTIDAD_PAGINACION = 12;

		// Calcular la última página (ceil sobre división de enteros)
		int totalUsuarios = listadoEventos.size();
		int ultimaPagina = (int) Math.ceil((double) totalUsuarios / CANTIDAD_PAGINACION);

		// Página actual solicitada (por defecto 1)
		int pagina = 1;
		String paramPagina = request.getParameter("pagina");
		if (paramPagina != null) {
		    try {
		        pagina = Integer.parseInt(paramPagina);
		    } catch (NumberFormatException ignored) {}
		}

		// Asegurarse de que la página esté en rango [1, ultimaPagina]
		if (pagina < 1) pagina = 1;
		if (ultimaPagina > 0 && pagina > ultimaPagina) pagina = ultimaPagina;

		// Calcular índices de inicio y fin
		int desde = (pagina - 1) * CANTIDAD_PAGINACION;
		int hasta = Math.min(desde + CANTIDAD_PAGINACION, totalUsuarios);

		// Sublista de la página actual
		List<DTEvento> paginaEventos = listadoEventos.stream().toList().subList(desde, hasta);

		// Pasar datos al JSP
		request.setAttribute("fin_paginacion", ultimaPagina);
		request.setAttribute("pagina", pagina);
		request.setAttribute("listado", paginaEventos);

		// Redirigir al JSP
		request.getRequestDispatcher("/WEB-INF/eventos/listadoEventos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
