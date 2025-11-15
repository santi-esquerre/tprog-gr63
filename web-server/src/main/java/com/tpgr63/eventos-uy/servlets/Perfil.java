package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.ImagesManager;
import controllers.SessionManager;
import webservice.PortFactory;
import model.EstadoSesion;
import webservice.CantidadCuposDisponiblesException_Exception;
import webservice.CostoRegistrosGratuitosException_Exception;
import webservice.DtAsistente;
import webservice.DtEdicion;
import webservice.DtEvento;
import webservice.DtEventoAlta;
import webservice.DtEventoDetallado;
import webservice.DtInstitucion;
import webservice.DtOrganizador;
import webservice.DtRegistro;
import webservice.DtTipoRegistro;
import webservice.DtUsuario;
import webservice.EdicionService;
import webservice.EstadoEdicion;
import webservice.EventoService;
import webservice.ExistePatrocinioException_Exception;
import webservice.InstitucionNoExistenteException_Exception;
import webservice.InstitucionRepetidaException_Exception;
import webservice.InstitucionService;
import webservice.NivelPatrocinio;
import webservice.TipoUsuario;
import webservice.UsuarioCorreoRepetidoException_Exception;
import webservice.UsuarioService;
import webservice.ValidationInputException_Exception;
import webservice.XMLGregorianCalendarAdapter;

/**
 * Servlet implementation class Perfil
 * Maneja tanto la vista del perfil como la API REST para organizadores y asistentes
 */
@WebServlet(urlPatterns = { "/perfil", "/api/organizador/*", "/api/asistente/*" })
@MultipartConfig
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(javax.xml.datatype.XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
      .registerTypeHierarchyAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
      .setDateFormat("yyyy-MM-dd")
      .setPrettyPrinting()
      .create();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Perfil() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();

		// API Endpoints para organizador
		if (servletPath.startsWith("/api/organizador")) {
			handleApiGet(request, response);
			return;
		}

		// API Endpoints para asistente
		if (servletPath.startsWith("/api/asistente")) {
			handleAsistenteApiGet(request, response);
			return;
		}

		// Vista de perfil
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.sendRedirect(request.getContextPath() + "/sesion");
			return;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DtUsuario usuarioLogueado = null;

		if (usuarioObj instanceof DtUsuario) {
			usuarioLogueado = (DtUsuario) usuarioObj;
		}

		if (usuarioLogueado == null) {
			response.sendRedirect(request.getContextPath() + "/sesion");
			return;
		}
		PortFactory factory = PortFactory.get();
		UsuarioService usuarioController = factory.getUsuarioPort();

		try {
			// Determine user type and load appropriate data
			if (usuarioLogueado.getTipo() == TipoUsuario.ASISTENTE) {
				DtAsistente asistente = usuarioController.seleccionarAsistente(usuarioLogueado.getNickname());
				List<DtRegistro> registros = usuarioController.obtenerRegistrosUsuario(usuarioLogueado.getNickname()).getRegistro();

				request.setAttribute("asistente", asistente);
				request.setAttribute("registros", registros);

				request.getRequestDispatcher("/WEB-INF/usuarios/perfil-asistente.jsp").forward(request, response);
			} else {
				DtOrganizador organizador = usuarioController.seleccionarOrganizador(usuarioLogueado.getNickname());
				request.setAttribute("organizador", organizador);

				request.getRequestDispatcher("/WEB-INF/usuarios/perfil-organizador.jsp").forward(request, response);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al cargar el perfil");
			response.sendRedirect(request.getContextPath() + "/inicio");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();

		// API Endpoints para organizador
		if (servletPath.startsWith("/api/organizador")) {
			handleApiPost(request, response);
			return;
		}

		// API Endpoints para asistente
		if (servletPath.startsWith("/api/asistente")) {
			handleAsistenteApiPost(request, response);
			return;
		}

		// Acciones de perfil
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DtUsuario usuarioLogueado = null;

		if (usuarioObj instanceof DtUsuario) {
			usuarioLogueado = (DtUsuario) usuarioObj;
		}

		if (usuarioLogueado == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		String action = request.getParameter("action");

		if ("editProfile".equals(action)) {
			handleEditProfile(request, response, usuarioLogueado);
		} else if ("deleteRegistro".equals(action)) {
			handleDeleteRegistro(request, response, usuarioLogueado);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private void handleEditProfile(HttpServletRequest request, HttpServletResponse response, DtUsuario usuario)
			throws IOException {
		try {
			PortFactory factory = PortFactory.get();
			UsuarioService usuarioController = factory.getUsuarioPort();

			// Get form data
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String correo = request.getParameter("correo");
			String fechaNacStr = request.getParameter("fechaNacimiento");
			String password = request.getParameter("password");

			// Parse date
			XMLGregorianCalendar fechaNacimiento = null;
			GregorianCalendar gCalendar = null;
			if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
			  gCalendar = GregorianCalendar.from(
            LocalDate.parse(fechaNacStr)
                .atStartOfDay(ZoneId.systemDefault()));
				fechaNacimiento = LocalDate.parse(fechaNacStr) != null
            ? DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar)
            : null;
			}

			// Update based on user type
			if (usuario.getTipo() == TipoUsuario.ASISTENTE) {
				DtAsistente asistente = usuarioController.seleccionarAsistente(usuario.getNickname());

				// Handle image upload if present, otherwise keep existing
				String foto = asistente.getFoto();
				String newFoto = ImagesManager.getImageName(request);
				// Solo actualiza la foto si hay una nueva imagen (no default.png)
				if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
					foto = newFoto;
					ImagesManager.uploadImage(request, response);
				}

				// Use provided values or keep existing ones
				String finalNombre = (nombre != null && !nombre.isEmpty()) ? nombre : asistente.getNombre();
				String finalApellido = (apellido != null && !apellido.isEmpty()) ? apellido : asistente.getNombre();
				String finalCorreo = (correo != null && !correo.isEmpty()) ? correo : asistente.getCorreo();
				
				XMLGregorianCalendar finalFecha = (fechaNacimiento != null) ? fechaNacimiento: asistente.getFechaNacimiento();

				// Update using the correct method signature
				// actualizarAsistente(nickname, nombre, apellido, correo, fechaNacimiento,
				// foto, clave)
				usuarioController.actualizarAsistente(
						usuario.getNickname(),
						finalNombre,
						finalApellido,
						finalCorreo,
						finalFecha,
						foto,
						password);

				// Reload updated data for session
				DtAsistente updatedAsistente = usuarioController.seleccionarAsistente(usuario.getNickname());
				request.getSession().setAttribute("usuario_logueado", updatedAsistente);

			} else if (usuario.getTipo() == TipoUsuario.ORGANIZADOR) {
				DtOrganizador organizador = usuarioController.seleccionarOrganizador(usuario.getNickname());

				// Handle image upload if present, otherwise keep existing
				String foto = organizador.getFoto();
				String newFoto = ImagesManager.getImageName(request);
				// Solo actualiza la foto si hay una nueva imagen (no default.png)
				if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
					foto = newFoto;
					ImagesManager.uploadImage(request, response);
				}

				// Use provided values or keep existing ones
				String finalNombre = (nombre != null && !nombre.isEmpty()) ? nombre : organizador.getNombre();
				String finalCorreo = (correo != null && !correo.isEmpty()) ? correo : organizador.getCorreo();
				String finalDescripcion = request.getParameter("descripcion");
				if (finalDescripcion == null || finalDescripcion.isEmpty()) {
					finalDescripcion = organizador.getDescripcion();
				}
				String finalSitioWeb = request.getParameter("sitioWeb");
				if (finalSitioWeb == null || finalSitioWeb.isEmpty()) {
					finalSitioWeb = organizador.getLinkSitioWeb();
				}

				// Update using the correct method signature
				// actualizarOrganizador(nickname, nombre, correo, descripcion, linkSitioWeb,
				// foto, clave)
				usuarioController.actualizarOrganizador(
						usuario.getNickname(),
						finalNombre,
						finalCorreo,
						finalDescripcion,
						finalSitioWeb,
						foto,
						password);

				// Reload updated data for session
				DtOrganizador updatedOrganizador = usuarioController.seleccionarOrganizador(usuario.getNickname());
				request.getSession().setAttribute("usuario_logueado", updatedOrganizador);
			}

			// Redirect back to profile page with success message
			request.getSession().setAttribute("success_message", "Perfil actualizado correctamente");
			response.sendRedirect(request.getContextPath() + "/perfil");

		} catch (UsuarioCorreoRepetidoException_Exception e) {
			request.getSession().setAttribute("error_message", "El correo ya está en uso");
			response.sendRedirect(request.getContextPath() + "/perfil");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error_message", "Error al actualizar el perfil: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}

	private void handleDeleteRegistro(HttpServletRequest request, HttpServletResponse response, DtUsuario usuario)
			throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		try {
			String edicion = request.getParameter("edicion");

			if (edicion == null || edicion.isEmpty()) {
				out.print("{\"success\": false, \"message\": \"Edición no especificada\"}");
				out.flush();
				return;
			}

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();

			// IMPORTANT NOTE: cancelarRegistroEdicionEvento() exists in the interface but:
			// 1. It has no parameters (needs edicion and nickname)
			// 2. The implementation is empty (see EdicionController line 358)
			//
			// TODO: You need to either:
			// A) Modify EdicionService to add: void
			// cancelarRegistroEdicionEvento(String nombreEdicion, String nickname)
			// B) Or implement the existing method to use stored state (edicionRecordada,
			// etc.)
			//
			// For now, calling the method will do nothing, so we return an error message

			// edicionController.cancelarRegistroEdicionEvento();

			out.print(
					"{\"success\": false, \"message\": \"La funcionalidad de cancelación de registros requiere modificaciones en el backend. Por favor, contacta al administrador del sistema.\"}");

		} catch (java.lang.Exception e) {
			e.printStackTrace();
			out.print("{\"success\": false, \"message\": \"Error al cancelar el registro: " + e.getMessage() + "\"}");
		}

		out.flush();
	}

	// ==================== API REST PARA ORGANIZADOR ====================

	private void handleApiGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!isApiAuthenticated(request, response))
			return;

		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "/";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			switch (pathInfo) {
				case "/categorias":
					getCategorias(response);
					break;
				case "/eventos":
					getEventos(response);
					break;
				case "/ediciones":
					getEdiciones(request, response);
					break;
				case "/instituciones":
					getInstituciones(response);
					break;
				case "/tipos-registro":
					getTiposRegistro(request, response);
					break;
				case "/registros":
					getRegistros(request, response);
					break;
				default:
					sendApiError(response, "Endpoint no válido");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al procesar solicitud: " + e.getMessage());
		}
	}

	private void handleApiPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!isApiAuthenticated(request, response))
			return;

		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "/";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			switch (pathInfo) {
				case "/evento":
				case "/eventos": // También aceptar plural
					createEvento(request, response);
					break;
				case "/edicion":
				case "/ediciones": // También aceptar plural
					createEdicion(request, response);
					break;
				case "/institucion":
				case "/instituciones": // También aceptar plural
					createInstitucion(request, response);
					break;
				case "/tipo-registro":
					createTipoRegistro(request, response);
					break;
				case "/patrocinio":
					createPatrocinio(request, response);
					break;
				default:
					sendApiError(response, "Endpoint no válido");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al procesar solicitud: " + e.getMessage());
		}
	}

	// ==================== GET ENDPOINTS ====================

	private void getCategorias(HttpServletResponse response) throws IOException {
		try {
			PortFactory factory = PortFactory.get();
			EventoService eventoController = factory.getEventoPort();
			List<String> categorias = eventoController.obtenerCategorias().getItem();
			sendApiSuccess(response, categorias);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener categorías: " + e.getMessage());
		}
	}

	private void getEventos(HttpServletResponse response) throws IOException {
		try {
			PortFactory factory = PortFactory.get();
			EventoService eventoController = factory.getEventoPort();

			// Obtener eventos simples primero
			List<DtEvento> eventos = eventoController.listarEventos().getEvento();

			// Convertir a DtEventoDetallado para incluir categorías
			List<DtEventoDetallado> eventosDetallados = new ArrayList<>();
			for (DtEvento evento : eventos) {
				try {
					DtEventoDetallado detallado = eventoController.obtenerDatosDetalladosEvento(evento.getNombre());
					eventosDetallados.add(detallado);
				} catch (java.lang.Exception e) {
					// Si no se puede obtener detalle, continuar con siguiente
					System.err.println("Error obteniendo detalle de evento " + evento.getNombre() + ": " + e.getMessage());
				}
			}

			sendApiSuccess(response, eventosDetallados);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener eventos: " + e.getMessage());
		}
	}

	private void getEdiciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// Si se especifica un evento, obtener ediciones de ese evento
			String nombreEvento = request.getParameter("evento");

			PortFactory factory = PortFactory.get();

			if (nombreEvento != null && !nombreEvento.isEmpty()) {
				// Obtener ediciones de un evento específico
				EventoService eventoController = factory.getEventoPort();
				Set<DtEdicion> ediciones = new HashSet<>(eventoController.mostrarEdiciones(nombreEvento).getEdicion());
				sendApiSuccess(response, ediciones);
			} else {
				// Obtener TODAS las ediciones del organizador logueado con información del
				// evento
				Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
				if (!(usuarioObj instanceof DtUsuario)) {
					sendApiError(response, "Usuario no logueado");
					return;
				}

				DtUsuario usuario = (DtUsuario) usuarioObj;
				EdicionService edicionController = factory.getEdicionPort();
				EventoService eventoController = factory.getEventoPort();

				Set<DtEdicion> ediciones = edicionController.obtenerEdicionesPorOrganizador(usuario.getNickname()).getEdicion().stream().collect(java.util.stream.Collectors.toSet());

				// Obtener TODOS los eventos del sistema para cruzar con ediciones
				Set<DtEvento> todosLosEventos = eventoController.listarEventos().getEvento().stream().collect(java.util.stream.Collectors.toSet());

				// Enriquecer las ediciones con el nombre del evento
				java.util.List<java.util.Map<String, Object>> edicionesEnriquecidas = new java.util.ArrayList<>();
				for (DtEdicion edicion : ediciones) {
					// Buscar el evento al que pertenece esta edición
					String nombreEventoPadre = "";
					for (DtEvento evento : todosLosEventos) {
						try {
							Set<DtEdicion> edicionesDelEvento = eventoController.mostrarEdiciones(evento.getNombre()).getEdicion().stream().collect(java.util.stream.Collectors.toSet());;
							for (DtEdicion ed : edicionesDelEvento) {
								if (ed.getNombre().equals(edicion.getNombre())) {
									nombreEventoPadre = evento.getNombre();
									break;
								}
							}
							if (!nombreEventoPadre.isEmpty())
								break;
						} catch (java.lang.Exception e) {
							// Si falla al obtener ediciones de un evento, continuar con el siguiente
							continue;
						}
					}

					java.util.Map<String, Object> edicionMap = new java.util.HashMap<>();
					edicionMap.put("nombre", edicion.getNombre());
					edicionMap.put("sigla", edicion.getSigla());
					edicionMap.put("fechaInicio", edicion.getFechaInicio());
					edicionMap.put("fechaFin", edicion.getFechaFin());
					edicionMap.put("fechaAlta", edicion.getFechaAlta());
					edicionMap.put("ciudad", edicion.getCiudad());
					edicionMap.put("pais", edicion.getPais());
					edicionMap.put("foto", edicion.getFoto());
					edicionMap.put("imagen", edicion.getFoto());
					edicionMap.put("estado", edicion.getEstado().toString());
					edicionMap.put("nombreEvento", nombreEventoPadre);

					edicionesEnriquecidas.add(edicionMap);
				}

				sendApiSuccess(response, edicionesEnriquecidas);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener ediciones: " + e.getMessage());
		}
	}

	private void getInstituciones(HttpServletResponse response) throws IOException {
		try {
			PortFactory factory = PortFactory.get();
			InstitucionService institucionController = factory.getInstitucionPort();
			Set<DtInstitucion> instituciones = institucionController.listarInstituciones().getInstitucion().stream().collect(java.util.stream.Collectors.toSet());
			sendApiSuccess(response, instituciones);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener instituciones: " + e.getMessage());
		}
	}

	private void getTiposRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombreEdicion = request.getParameter("edicion");
			if (nombreEdicion == null || nombreEdicion.isEmpty()) {
				sendApiError(response, "Parámetro 'edicion' requerido");
				return;
			}

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();
			Set<DtTipoRegistro> tiposRegistro = edicionController.mostrarTiposDeRegistro(nombreEdicion).getTipoRegistro().stream().collect(java.util.stream.Collectors.toSet());
			sendApiSuccess(response, tiposRegistro);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener tipos de registro: " + e.getMessage());
		}
	}

	private void getRegistros(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombreEdicion = request.getParameter("edicion");
			if (nombreEdicion == null || nombreEdicion.isEmpty()) {
				sendApiError(response, "Parámetro 'edicion' requerido");
				return;
			}

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();
			Set<DtAsistente> asistentes = edicionController.mostrarAsistentes(nombreEdicion).getAsistente().stream().collect(java.util.stream.Collectors.toSet());
			sendApiSuccess(response, asistentes);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener registros: " + e.getMessage());
		}
	}

	// ==================== POST ENDPOINTS ====================

	private void createEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			String sigla = request.getParameter("sigla");
			String categoriasJson = request.getParameter("categorias");

			System.out.println("=== DEBUG createEvento ===");
			System.out.println("nombre: " + nombre);
			System.out.println("descripcion: " + descripcion);
			System.out.println("sigla: " + sigla);
			System.out.println("categoriasJson: " + categoriasJson);

			if (nombre == null || nombre.isEmpty()) {
				sendApiError(response, "El nombre del evento es requerido");
				return;
			}

			String foto = null;
			String newFoto = ImagesManager.getImageName(request);
			// Solo actualiza la foto si hay una nueva imagen (no default.png)
			if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
				foto = newFoto;
				ImagesManager.uploadImage(request, response);
			}
			System.out.println("foto: " + foto);

			// Parse categorías desde JSON
			Set<String> categoriasSet = new HashSet<>();
			if (categoriasJson != null && !categoriasJson.isEmpty()) {
				try {
					com.google.gson.JsonArray jsonArray = gson.fromJson(categoriasJson,
							com.google.gson.JsonArray.class);
					for (int i = 0; i < jsonArray.size(); i++) {
						categoriasSet.add(jsonArray.get(i).getAsString());
					}
					System.out.println("categorías parseadas: " + categoriasSet);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
					sendApiError(response, "Error al procesar las categorías");
					return;
				}
			}

			// DtEventoAlta expects: (nombre, descripcion, fechaAlta, sigla, foto,
			// categorias)
			DtEventoAlta eventoAlta = new DtEventoAlta();
			eventoAlta.setNombre(nombre);
			eventoAlta.setDescripcion(descripcion);
			LocalDate date = LocalDate.now();
			GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
			XMLGregorianCalendar fechaAlta = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			eventoAlta.setFechaAlta(fechaAlta);
      eventoAlta.setSigla(sigla);
      eventoAlta.setFoto(foto);
			

			System.out.println("DtEventoAlta creado: " + eventoAlta);

			PortFactory factory = PortFactory.get();
			EventoService eventoController = factory.getEventoPort();

			System.out.println("Llamando a altaEvento...");
			boolean creado = eventoController.altaEvento(eventoAlta);
			System.out.println("Resultado altaEvento: " + creado);

			if (creado) {
				sendApiSuccess(response, "Evento creado exitosamente");
			} else {
				sendApiError(response, "No se pudo crear el evento");
			}
		} catch (ValidationInputException_Exception e) {
			System.out.println("ValidationInputException: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (java.lang.Exception e) {
			System.out.println("Exception general: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error al crear evento: " + e.getMessage());
		}
	}

	private void createEdicion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
			DtUsuario usuario = null;

			if (usuarioObj instanceof DtUsuario) {
				usuario = (DtUsuario) usuarioObj;
			}

			if (usuario == null) {
				sendApiError(response, "Usuario no logueado");
				return;
			}

			System.out.println("=== DEBUG createEdicion ===");
			System.out.println("Usuario logueado: " + usuario.getNickname());

			String nombreEvento = request.getParameter("evento");
			String nombre = request.getParameter("nombre");
			String sigla = request.getParameter("sigla");
			String ciudad = request.getParameter("ciudad");
			String pais = request.getParameter("pais");
			String fechaInicioStr = request.getParameter("fechaInicio");
			String fechaFinStr = request.getParameter("fechaFin");

			System.out.println("nombreEvento: " + nombreEvento);
			System.out.println("nombre: " + nombre);
			System.out.println("fechaInicio: " + fechaInicioStr);
			System.out.println("fechaFin: " + fechaFinStr);

			if (nombreEvento == null || nombreEvento.isEmpty() || nombre == null || nombre.isEmpty()) {
				sendApiError(response, "Faltan campos requeridos");
				return;
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
			LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);

			String foto = null;
			String newFoto = ImagesManager.getImageName(request);
			if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
				foto = newFoto;
				ImagesManager.uploadImage(request, response);
			}
			System.out.println("foto: " + foto);

			// Convert LocalDate to Date for DtEdicion
			// DtEdicion expects: (nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad,
			// pais, foto, estado)
			GregorianCalendar gcalInicio = GregorianCalendar.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()));
			GregorianCalendar gcalFin = GregorianCalendar.from(fechaFin.atStartOfDay(ZoneId.systemDefault()));
			GregorianCalendar gcalAlta = GregorianCalendar.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
		  XMLGregorianCalendar fechaInicioDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalInicio);
			XMLGregorianCalendar fechaFinDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalFin);
			XMLGregorianCalendar fechaAltaDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalAlta);

			DtEdicion edicion = new DtEdicion();
			edicion.setNombre(nombre);
			edicion.setSigla(sigla);
			edicion.setCiudad(ciudad);
			edicion.setPais(pais);
			edicion.setFoto(foto);
			edicion.setEstado(EstadoEdicion.INGRESADA);
			edicion.setFechaInicio(fechaInicioDate);
			edicion.setFechaFin(fechaFinDate);
			edicion.setFechaAlta(fechaAltaDate);

			System.out.println("DtEdicion creado: " + edicion);

			PortFactory factory = PortFactory.get();
			EventoService eventoController = factory.getEventoPort();

			System.out.println("Llamando a agregarEdicionAEvento con:");
			System.out.println("  - nombreEvento: " + nombreEvento);
			System.out.println("  - nickname: " + usuario.getNickname());
			System.out.println("  - edicion: " + edicion.getNombre());

			boolean creada = eventoController.agregarEdicionAEvento(nombreEvento, usuario.getNickname(), edicion);

			System.out.println("Resultado agregarEdicionAEvento: " + creada);

			if (creada) {
				sendApiSuccess(response, "Edición creada exitosamente");
			} else {
				sendApiError(response, "No se pudo crear la edición");
			}
		} catch (ValidationInputException_Exception e) {
			System.out.println("ValidationInputException: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (java.lang.Exception e) {
			System.out.println("Exception general: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error al crear edición: " + e.getMessage());
		}
	}

	private void createInstitucion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			String sitioWeb = request.getParameter("sitioWeb");

			if (nombre == null || nombre.isEmpty()) {
				sendApiError(response, "El nombre de la institución es requerido");
				return;
			}

			String foto = null;
			String newFoto = ImagesManager.getImageName(request);
			if (newFoto != null && !newFoto.isEmpty()) {
				foto = newFoto;
				ImagesManager.uploadImage(request, response);
			}

			PortFactory factory = PortFactory.get();
			InstitucionService institucionController = factory.getInstitucionPort();
			boolean creada = institucionController.crearInstitucion(nombre, descripcion, sitioWeb, foto);

			if (creada) {
				sendApiSuccess(response, "Institución creada exitosamente");
			} else {
				sendApiError(response, "No se pudo crear la institución");
			}
		} catch (InstitucionRepetidaException_Exception e) {
			sendApiError(response, "Ya existe una institución con ese nombre");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al crear institución: " + e.getMessage());
		}
	}

	private void createTipoRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombreEdicion = request.getParameter("edicionId");
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			String costoStr = request.getParameter("costo");
			String cupoStr = request.getParameter("cupo");

			if (nombreEdicion == null || nombreEdicion.isEmpty() || nombre == null || nombre.isEmpty()) {
				sendApiError(response, "Faltan campos requeridos");
				return;
			}

			Float costo = Float.parseFloat(costoStr);
			Integer cupo = Integer.parseInt(cupoStr);

			DtTipoRegistro tipoRegistro = new DtTipoRegistro();
			tipoRegistro.setNombre(nombre);
			tipoRegistro.setDescripcion(descripcion);
			tipoRegistro.setCosto(costo);
			tipoRegistro.setCupo(cupo);

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();
			edicionController.altaTipoRegistro(nombreEdicion, tipoRegistro);

			sendApiSuccess(response, "Tipo de registro creado exitosamente");
		} catch (ValidationInputException_Exception e) {
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al crear tipo de registro: " + e.getMessage());
		}
	}

	private void createPatrocinio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String nombreEdicion = request.getParameter("edicionId");
			String nombreInstitucion = request.getParameter("institucion");
			String aporteStr = request.getParameter("aporte");
			String nivelStr = request.getParameter("nivel");
			String codigo = request.getParameter("codigo");
			String fechaAltaStr = request.getParameter("fecha");

			if (nombreEdicion == null || nombreInstitucion == null || aporteStr == null) {
				sendApiError(response, "Faltan campos requeridos");
				return;
			}

			Float aporte = Float.parseFloat(aporteStr);
			NivelPatrocinio nivel = NivelPatrocinio.valueOf(nivelStr.toUpperCase());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			GregorianCalendar gcal = GregorianCalendar.from(
        LocalDate.parse(fechaAltaStr, formatter)
            .atStartOfDay(ZoneId.systemDefault()));
			XMLGregorianCalendar fechaAlta = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

			// Parsear registros JSON
			String registrosJson = request.getParameter("registros");
			if (registrosJson != null && !registrosJson.trim().isEmpty()) {
				com.google.gson.JsonArray registrosArray = gson.fromJson(registrosJson,
						com.google.gson.JsonArray.class);

				if (registrosArray.size() > 0) {
					com.google.gson.JsonObject primerRegistro = registrosArray.get(0).getAsJsonObject();
					String nombreTipoRegistro = primerRegistro.get("tipo").getAsString();
					int cantGratuitos = primerRegistro.get("cantidad").getAsInt();

					PortFactory factory = PortFactory.get();
					EdicionService edicionController = factory.getEdicionPort();
					edicionController.altaPatrocinio(fechaAlta, nombreEdicion, nombreInstitucion, aporte,
							nombreTipoRegistro, cantGratuitos, codigo, nivel);

					sendApiSuccess(response, "Patrocinio creado exitosamente");
				} else {
					sendApiError(response, "Debe agregar al menos un registro gratuito");
				}
			} else {
				sendApiError(response, "Debe agregar al menos un registro gratuito");
			}
		} catch (ExistePatrocinioException_Exception e) {
			sendApiError(response, "Ya existe un patrocinio con ese código");
		} catch (CostoRegistrosGratuitosException_Exception e) {
			sendApiError(response, "El costo de los registros gratuitos supera el 20% del aporte");
		} catch (CantidadCuposDisponiblesException_Exception e) {
			sendApiError(response, "No hay suficientes cupos disponibles");
		} catch (InstitucionNoExistenteException_Exception e) {
			sendApiError(response, "La institución no existe");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al crear patrocinio: " + e.getMessage());
		}
	}

	// ==================== UTILIDADES API ====================

	

	// ==================== ASISTENTE API HANDLERS ====================

	private void handleAsistenteApiGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!isApiAuthenticated(request, response))
			return;

		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "/";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			switch (pathInfo) {
				case "/ediciones-disponibles":
					getEdicionesDisponibles(request, response);
					break;
				case "/tipos-registro":
					getTiposRegistro(request, response);
					break;
				default:
					sendApiError(response, "Endpoint no válido");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al procesar solicitud: " + e.getMessage());
		}
	}

	private void handleAsistenteApiPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!isApiAuthenticated(request, response))
			return;

		String pathInfo = request.getPathInfo();
		if (pathInfo == null)
			pathInfo = "/";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			switch (pathInfo) {
				case "/validar-patrocinio":
					validarPatrocinio(request, response);
					break;
				case "/registro":
					createRegistroAsistente(request, response);
					break;
				default:
					sendApiError(response, "Endpoint no válido");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al procesar solicitud: " + e.getMessage());
		}
	}

	// ==================== ASISTENTE ENDPOINTS ====================

	private void getEdicionesDisponibles(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			PortFactory factory = PortFactory.get();
			EventoService eventoController = factory.getEventoPort();

			// Obtener todas las ediciones activas/confirmadas
			Set<DtEvento> todosLosEventos = eventoController.listarEventos().getEvento().stream().collect(java.util.stream.Collectors.toSet());
			List<Map<String, Object>> edicionesDisponibles = new ArrayList<>();

			for (DtEvento evento : todosLosEventos) {
				Set<DtEdicion> edicionesDelEvento = eventoController.mostrarEdiciones(evento.getNombre()).getEdicion().stream().collect(java.util.stream.Collectors.toSet());

				for (DtEdicion edicion : edicionesDelEvento) {
					// Solo incluir ediciones en estado confirmado
					if (edicion.getEstado() == EstadoEdicion.ACEPTADA) {
						Map<String, Object> edMap = new HashMap<>();
						edMap.put("nombre", edicion.getNombre());
						edMap.put("nombreEvento", evento.getNombre());
						edMap.put("fechaInicio",
								edicion.getFechaInicio() != null ? edicion.getFechaInicio().toString() : null);
						edMap.put("fechaFin", edicion.getFechaFin() != null ? edicion.getFechaFin().toString() : null);
						edMap.put("imagen", edicion.getFoto());
						edicionesDisponibles.add(edMap);
					}
				}
			}

			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("data", edicionesDisponibles);
			sendApiSuccess(response, result);

		} catch (java.lang.Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener ediciones disponibles: " + e.getMessage());
		}
	}

	private void validarPatrocinio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String codigo = request.getParameter("codigo");
			String edicion = request.getParameter("edicion");

			if (codigo == null || codigo.trim().isEmpty() || edicion == null || edicion.trim().isEmpty()) {
				sendApiError(response, "Código y edición son requeridos");
				return;
			}

			// Obtener usuario logueado
			Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
			if (!(usuarioObj instanceof DtUsuario)) {
				sendApiError(response, "Usuario no autenticado");
				return;
			}
			DtUsuario usuario = (DtUsuario) usuarioObj;

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();

			// Verificar código con tipo de registro y nickname del usuario
			// Nota: Necesitamos el tipo de registro - usaremos validación simple
			// El backend verificará el código cuando se intente crear el registro

			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("valido", true); // Por ahora aceptamos cualquier código, se validará al crear el registro
			result.put("message", "El código se validará al confirmar el registro");
			sendApiSuccess(response, result);

		} catch (java.lang.Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<>();
			result.put("success", false);
			result.put("valido", false);
			result.put("message", "Error al validar código: " + e.getMessage());
			sendApiSuccess(response, result);
		}
	}

	private void createRegistroAsistente(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// Obtener usuario logueado
			Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
			if (!(usuarioObj instanceof DtUsuario)) {
				sendApiError(response, "Usuario no autenticado");
				return;
			}
			DtUsuario usuario = (DtUsuario) usuarioObj;

			String edicion = request.getParameter("edicion");
			String tipo = request.getParameter("tipo");
			String codigoPatrocinio = request.getParameter("codigoPatrocinio");

			if (edicion == null || edicion.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()) {
				sendApiError(response, "Edición y tipo de registro son requeridos");
				return;
			}

			PortFactory factory = PortFactory.get();
			EdicionService edicionController = factory.getEdicionPort();

			// Crear registro con o sin código de patrocinio
			GregorianCalendar gCalendar = new GregorianCalendar();
			gCalendar.setTime(new Date());
			XMLGregorianCalendar fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar); // Fecha actual

			if (codigoPatrocinio != null && !codigoPatrocinio.trim().isEmpty()) {
				// Registro con código de patrocinio - la sobrecarga valida el código
				edicionController.altaRegistroEdicionEventoConCodigo(
						edicion,
						tipo,
						usuario.getNickname(),
						fecha,
						codigoPatrocinio);
			} else {
				// Registro normal
				edicionController.altaRegistroEdicionEvento(
						edicion,
						tipo,
						usuario.getNickname(),
						fecha);
			}

			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("message", "Registro creado exitosamente");
			sendApiSuccess(response, result);

		} catch (ValidationInputException_Exception e) {
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			sendApiError(response, "Código de patrocinio inválido o ya utilizado");
		} catch (java.lang.Exception  e) {
			e.printStackTrace();
			sendApiError(response, "Error al crear registro: " + e.getMessage());
		}
	}

	// ==================== UTILITY METHODS ====================

	private boolean isApiAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			sendApiError(response, "No autenticado");
			return false;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DtUsuario usuario = null;

		if (usuarioObj instanceof DtUsuario) {
			usuario = (DtUsuario) usuarioObj;
		}

		if (usuario == null || usuario.getTipo() != TipoUsuario.ORGANIZADOR) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			sendApiError(response, "Acceso denegado");
			return false;
		}

		return true;
	}

	private void sendApiSuccess(HttpServletResponse response, String message) throws IOException {
		PrintWriter out = response.getWriter();
		out.print("{\"success\": true, \"message\": \"" + escapeJson(message) + "\"}");
		out.flush();
	}

	private void sendApiSuccess(HttpServletResponse response, Object data) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(data));
		out.flush();
	}

	private void sendApiError(HttpServletResponse response, String message) throws IOException {
		PrintWriter out = response.getWriter();
		out.print("{\"success\": false, \"message\": \"" + escapeJson(message) + "\"}");
		out.flush();
	}

	private String escapeJson(String str) {
		if (str == null)
			return "";
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
				.replace("\t", "\\t");
	}

}
