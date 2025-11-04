package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datatypes.DTUsuarioItemListado;
import datatypes.EstadoEdicion;
import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTUsuario;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IUsuarioController;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet(urlPatterns = { "/usuarios", "/api/usuario/preview" })
public class Usuarios extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final int CANTIDAD_PAGINACION = 4;
  private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
       
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
    String servletPath = request.getServletPath();

    // Si la petición es al endpoint API para preview de usuario
    if (servletPath != null && servletPath.startsWith("/api/usuario")) {
      handleApiPreview(request, response);
      return;
    }

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
  


  private void handleApiPreview(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    String tipo = request.getParameter("tipo");
    String nickname = request.getParameter("nickname");

    if (tipo == null || nickname == null) {
      out.print(gson.toJson(Map.of("success", false, "message", "Parámetros 'tipo' y 'nickname' requeridos")));
      return;
    }

    try {
      IUsuarioController usuarioController = Factory.get().getIUsuarioController();

      if ("asistente".equalsIgnoreCase(tipo)) {
        DTAsistente asistente = usuarioController.seleccionarAsistente(nickname);
        if (asistente == null) {
          out.print(gson.toJson(Map.of("success", false, "message", "Asistente no encontrado")));
          return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("tipo", "asistente");
        data.put("nickname", asistente.nickname());
        data.put("nombre", asistente.nombre());
        data.put("apellido", asistente.apellido());
        data.put("correo", asistente.correo());
        if (asistente.fechaNacimiento() != null) {
          data.put("fechaNacimiento",
              DateTimeFormatter.ISO_LOCAL_DATE.format(asistente.fechaNacimiento().toInstant()
                  .atZone(ZoneId.systemDefault()).toLocalDate()));
        } else {
          data.put("fechaNacimiento", null);
        }
        data.put("foto", asistente.foto());

        out.print(gson.toJson(Map.of("success", true, "data", data)));
        return;
      } else if ("organizador".equalsIgnoreCase(tipo)) {
        DTOrganizador org = usuarioController.seleccionarOrganizador(nickname);
        if (org == null) {
          out.print(gson.toJson(Map.of("success", false, "message", "Organizador no encontrado")));
          return;
        }
        
  IEdicionController edicionController = Factory.get().getIEdicionController();
  Set<DTEdicion> dtEdicionesSet = edicionController.obtenerEdicionesPorOrganizador(nickname);
  // Filtrar solo ediciones aceptadas y recolectar en un Set serializable
  Set<DTEdicion> edicionesAceptadas = dtEdicionesSet == null ? Set.of()
    : dtEdicionesSet.stream()
      .filter(edicion -> edicion != null && edicion.estado() == EstadoEdicion.ACEPTADA)
      .collect(Collectors.toSet());
        Map<String, Object> data = new HashMap<>();
        data.put("tipo", "organizador");
        data.put("nickname", org.nickname());
        data.put("nombre", org.nombre());
        data.put("descripcion", org.descripcion());
        data.put("correo", org.correo());
        data.put("linkSitioWeb", org.linkSitioWeb());
        data.put("foto", org.foto());
  data.put("ediciones", edicionesAceptadas);

        out.print(gson.toJson(Map.of("success", true, "data", data)));
        return;
      } else {
        out.print(gson.toJson(Map.of("success", false, "message", "Tipo inválido")));
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.print(gson.toJson(Map.of("success", false, "message", "Error: " + e.getMessage())));
      return;
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
