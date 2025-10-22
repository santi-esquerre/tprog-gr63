package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import datatypes.DTUsuarioItemListado;
import interfaces.Factory;
import interfaces.IUsuarioController;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int CANTIDAD_PAGINACION = 4;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Usuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtener controlador y lista completa de usuarios
		IUsuarioController usuarioController = Factory.get().getIUsuarioController();
		List<DTUsuarioItemListado> listadoUsuarios = usuarioController.obtenerUsuarios();

		// Evitar que sea null
		if (listadoUsuarios == null) listadoUsuarios = List.of();

		// Constante de paginación (ejemplo)
		final int CANTIDAD_PAGINACION = 4;

		// Calcular la última página (ceil sobre división de enteros)
		int totalUsuarios = listadoUsuarios.size();
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
		List<DTUsuarioItemListado> paginaUsuarios = listadoUsuarios.subList(desde, hasta);

		// Pasar datos al JSP
		request.setAttribute("fin_paginacion", ultimaPagina);
		request.setAttribute("pagina", pagina);
		request.setAttribute("listado", paginaUsuarios);

		// Redirigir al JSP
		request.getRequestDispatcher("/WEB-INF/usuarios/listadoUsuarios.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
