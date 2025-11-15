package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservice.DtAsistente;
import webservice.DtEdicion;
import webservice.DtOrganizador;
import webservice.DtUsuarioItemListado;
import webservice.EdicionService;
import webservice.EstadoEdicion;
import webservice.PortFactory;
import webservice.UsuarioService;
import webservice.XMLGregorianCalendarAdapter;

import java.util.GregorianCalendar;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Servlet implementation class Usuarios
 */
@WebServlet(urlPatterns = { "/usuarios", "/api/usuario/preview" })
public class Usuarios extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final int CANTIDAD_PAGINACION = 4;
  private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(javax.xml.datatype.XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
      .registerTypeHierarchyAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
      .setDateFormat("yyyy-MM-dd")
      .setPrettyPrinting()
      .create();
       
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
    UsuarioService usuarioPort = PortFactory.get().getUsuarioPort();
    List<DtUsuarioItemListado> listadoUsuarios = usuarioPort.obtenerUsuarios().getUsuario();

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
    List<DtUsuarioItemListado> paginaUsuarios = listadoUsuarios.subList(desde, hasta);

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
      UsuarioService usuarioPort = PortFactory.get().getUsuarioPort();

      if ("asistente".equalsIgnoreCase(tipo)) {
        DtAsistente asistente = usuarioPort.seleccionarAsistente(nickname);
        if (asistente == null) {
          out.print(gson.toJson(Map.of("success", false, "message", "Asistente no encontrado")));
          return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("tipo", "asistente");
        data.put("nickname", asistente.getNickname());
        data.put("nombre", asistente.getNombre());
        data.put("apellido", asistente.getApellido());
        data.put("correo", asistente.getCorreo());
        GregorianCalendar gCalendar = asistente.getFechaNacimiento().toGregorianCalendar();
        if (asistente.getFechaNacimiento() != null) {
          data.put("fechaNacimiento",
              DateTimeFormatter.ISO_LOCAL_DATE.format(gCalendar.toInstant()
                  .atZone(ZoneId.systemDefault()).toLocalDate()));
        } else {
          data.put("fechaNacimiento", null);
        }
        data.put("foto", asistente.getFoto());

        out.print(gson.toJson(Map.of("success", true, "data", data)));
        return;
      } else if ("organizador".equalsIgnoreCase(tipo)) {
        DtOrganizador org = usuarioPort.seleccionarOrganizador(nickname);
        if (org == null) {
          out.print(gson.toJson(Map.of("success", false, "message", "Organizador no encontrado")));
          return;
        }
  EdicionService edicionController = PortFactory.get().getEdicionPort();
  List<DtEdicion> dtEdicionesSet = edicionController.obtenerEdicionesPorOrganizador(nickname).getEdicion();
  // Filtrar solo ediciones aceptadas y recolectar en un Set serializable
  Set<DtEdicion> edicionesAceptadas = dtEdicionesSet == null ? Set.of()
    : dtEdicionesSet.stream()
      .filter(edicion -> edicion != null && edicion.getEstado().equals(EstadoEdicion.ACEPTADA))
      .collect(Collectors.toSet());
        Map<String, Object> data = new HashMap<>();
        data.put("tipo", "organizador");
        data.put("nickname", org.getNickname());
        data.put("nombre", org.getNombre());
        data.put("descripcion", org.getDescripcion());
        data.put("correo", org.getCorreo());
        data.put("linkSitioWeb", org.getLinkSitioWeb());
        data.put("foto", org.getFoto());
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
