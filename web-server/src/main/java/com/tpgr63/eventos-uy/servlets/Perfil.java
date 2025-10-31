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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.ImagesManager;
import controllers.SessionManager;
import datatypes.*;
import interfaces.Factory;
import interfaces.IUsuarioController;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IInstitucionController;
import model.EstadoSesion;
import exceptions.*;

/**
 * Servlet implementation class Perfil
 * Maneja tanto la vista del perfil como la API REST para organizadores
 */
@WebServlet(urlPatterns = { "/perfil", "/api/organizador/*" })
@MultipartConfig
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd")
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

		// Vista de perfil
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.sendRedirect(request.getContextPath() + "/sesion");
			return;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DTUsuario usuarioLogueado = null;

		if (usuarioObj instanceof DTUsuario) {
			usuarioLogueado = (DTUsuario) usuarioObj;
		}

		if (usuarioLogueado == null) {
			response.sendRedirect(request.getContextPath() + "/sesion");
			return;
		}
		Factory factory = Factory.get();
		IUsuarioController usuarioController = factory.getIUsuarioController();

		try {
			// Determine user type and load appropriate data
			if (usuarioLogueado.getTipo() == TipoUsuario.ASISTENTE) {
				DTAsistente asistente = usuarioController.seleccionarAsistente(usuarioLogueado.nickname());
				List<DTRegistro> registros = usuarioController.obtenerRegistrosUsuario(usuarioLogueado.nickname());

				request.setAttribute("asistente", asistente);
				request.setAttribute("registros", registros);

				request.getRequestDispatcher("/WEB-INF/usuarios/perfil-asistente.jsp").forward(request, response);
			} else {
				DTOrganizador organizador = usuarioController.seleccionarOrganizador(usuarioLogueado.nickname());
				request.setAttribute("organizador", organizador);

				request.getRequestDispatcher("/WEB-INF/usuarios/perfil-organizador.jsp").forward(request, response);
			}
		} catch (Exception e) {
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

		// Acciones de perfil
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DTUsuario usuarioLogueado = null;

		if (usuarioObj instanceof DTUsuario) {
			usuarioLogueado = (DTUsuario) usuarioObj;
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

	private void handleEditProfile(HttpServletRequest request, HttpServletResponse response, DTUsuario usuario)
			throws IOException {
		try {
			Factory factory = Factory.get();
			IUsuarioController usuarioController = factory.getIUsuarioController();

			// Get form data
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String correo = request.getParameter("correo");
			String fechaNacStr = request.getParameter("fechaNacimiento");
			String password = request.getParameter("password");

			// Parse date
			LocalDate fechaNacimiento = null;
			if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
				fechaNacimiento = LocalDate.parse(fechaNacStr);
			}

			// Update based on user type
			if (usuario.getTipo() == TipoUsuario.ASISTENTE) {
				DTAsistente asistente = usuarioController.seleccionarAsistente(usuario.nickname());

				// Handle image upload if present, otherwise keep existing
				String foto = asistente.foto();
				String newFoto = ImagesManager.getImageName(request);
				// Solo actualiza la foto si hay una nueva imagen (no default.png)
				if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
					foto = newFoto;
					ImagesManager.uploadImage(request, response);
				}

				// Use provided values or keep existing ones
				String finalNombre = (nombre != null && !nombre.isEmpty()) ? nombre : asistente.nombre();
				String finalApellido = (apellido != null && !apellido.isEmpty()) ? apellido : asistente.apellido();
				String finalCorreo = (correo != null && !correo.isEmpty()) ? correo : asistente.correo();
				LocalDate finalFecha = (fechaNacimiento != null) ? fechaNacimiento
						: asistente.fechaNacimiento().toInstant().atZone(java.time.ZoneId.systemDefault())
								.toLocalDate();

				// Update using the correct method signature
				// actualizarAsistente(nickname, nombre, apellido, correo, fechaNacimiento,
				// foto, clave)
				usuarioController.actualizarAsistente(
						usuario.nickname(),
						finalNombre,
						finalApellido,
						finalCorreo,
						finalFecha,
						foto,
						password);

				// Reload updated data for session
				DTAsistente updatedAsistente = usuarioController.seleccionarAsistente(usuario.nickname());
				request.getSession().setAttribute("usuario_logueado", updatedAsistente);

			} else if (usuario.getTipo() == TipoUsuario.ORGANIZADOR) {
				DTOrganizador organizador = usuarioController.seleccionarOrganizador(usuario.nickname());

				// Handle image upload if present, otherwise keep existing
				String foto = organizador.foto();
				String newFoto = ImagesManager.getImageName(request);
				// Solo actualiza la foto si hay una nueva imagen (no default.png)
				if (newFoto != null && !newFoto.isEmpty() && !newFoto.equals("default.png")) {
					foto = newFoto;
					ImagesManager.uploadImage(request, response);
				}

				// Use provided values or keep existing ones
				String finalNombre = (nombre != null && !nombre.isEmpty()) ? nombre : organizador.nombre();
				String finalCorreo = (correo != null && !correo.isEmpty()) ? correo : organizador.correo();
				String finalDescripcion = request.getParameter("descripcion");
				if (finalDescripcion == null || finalDescripcion.isEmpty()) {
					finalDescripcion = organizador.descripcion();
				}
				String finalSitioWeb = request.getParameter("sitioWeb");
				if (finalSitioWeb == null || finalSitioWeb.isEmpty()) {
					finalSitioWeb = organizador.linkSitioWeb();
				}

				// Update using the correct method signature
				// actualizarOrganizador(nickname, nombre, correo, descripcion, linkSitioWeb,
				// foto, clave)
				usuarioController.actualizarOrganizador(
						usuario.nickname(),
						finalNombre,
						finalCorreo,
						finalDescripcion,
						finalSitioWeb,
						foto,
						password);

				// Reload updated data for session
				DTOrganizador updatedOrganizador = usuarioController.seleccionarOrganizador(usuario.nickname());
				request.getSession().setAttribute("usuario_logueado", updatedOrganizador);
			}

			// Redirect back to profile page with success message
			request.getSession().setAttribute("success_message", "Perfil actualizado correctamente");
			response.sendRedirect(request.getContextPath() + "/perfil");

		} catch (UsuarioCorreoRepetidoException e) {
			request.getSession().setAttribute("error_message", "El correo ya está en uso");
			response.sendRedirect(request.getContextPath() + "/perfil");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error_message", "Error al actualizar el perfil: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}

	private void handleDeleteRegistro(HttpServletRequest request, HttpServletResponse response, DTUsuario usuario)
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

			Factory factory = Factory.get();
			IEdicionController edicionController = factory.getIEdicionController();

			// IMPORTANT NOTE: cancelarRegistroEdicionEvento() exists in the interface but:
			// 1. It has no parameters (needs edicion and nickname)
			// 2. The implementation is empty (see EdicionController line 358)
			//
			// TODO: You need to either:
			// A) Modify IEdicionController to add: void
			// cancelarRegistroEdicionEvento(String nombreEdicion, String nickname)
			// B) Or implement the existing method to use stored state (edicionRecordada,
			// etc.)
			//
			// For now, calling the method will do nothing, so we return an error message

			// edicionController.cancelarRegistroEdicionEvento();

			out.print(
					"{\"success\": false, \"message\": \"La funcionalidad de cancelación de registros requiere modificaciones en el backend. Por favor, contacta al administrador del sistema.\"}");

		} catch (Exception e) {
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al procesar solicitud: " + e.getMessage());
		}
	}

	// ==================== GET ENDPOINTS ====================

	private void getCategorias(HttpServletResponse response) throws IOException {
		try {
			Factory factory = Factory.get();
			IEventoController eventoController = factory.getIEventoController();
			Set<String> categorias = eventoController.obtenerCategorias();
			sendApiSuccess(response, categorias);
		} catch (Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener categorías: " + e.getMessage());
		}
	}

	private void getEventos(HttpServletResponse response) throws IOException {
		try {
			Factory factory = Factory.get();
			IEventoController eventoController = factory.getIEventoController();

			// Obtener eventos simples primero
			Set<DTEvento> eventos = eventoController.listarEventos();

			// Convertir a DTEventoDetallado para incluir categorías
			List<DTEventoDetallado> eventosDetallados = new ArrayList<>();
			for (DTEvento evento : eventos) {
				try {
					DTEventoDetallado detallado = eventoController.obtenerDatosDetalladosEvento(evento.nombre());
					eventosDetallados.add(detallado);
				} catch (Exception e) {
					// Si no se puede obtener detalle, continuar con siguiente
					System.err.println("Error obteniendo detalle de evento " + evento.nombre() + ": " + e.getMessage());
				}
			}

			sendApiSuccess(response, eventosDetallados);
		} catch (Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener eventos: " + e.getMessage());
		}
	}

	private void getEdiciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// Si se especifica un evento, obtener ediciones de ese evento
			String nombreEvento = request.getParameter("evento");

			Factory factory = Factory.get();

			if (nombreEvento != null && !nombreEvento.isEmpty()) {
				// Obtener ediciones de un evento específico
				IEventoController eventoController = factory.getIEventoController();
				Set<DTEdicion> ediciones = eventoController.mostrarEdiciones(nombreEvento);
				sendApiSuccess(response, ediciones);
			} else {
				// Obtener TODAS las ediciones del organizador logueado
				Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
				if (!(usuarioObj instanceof DTUsuario)) {
					sendApiError(response, "Usuario no logueado");
					return;
				}

				DTUsuario usuario = (DTUsuario) usuarioObj;
				IEdicionController edicionController = factory.getIEdicionController();
				Set<DTEdicion> ediciones = edicionController.obtenerEdicionesPorOrganizador(usuario.nickname());

				sendApiSuccess(response, ediciones);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al obtener ediciones: " + e.getMessage());
		}
	}

	private void getInstituciones(HttpServletResponse response) throws IOException {
		try {
			Factory factory = Factory.get();
			IInstitucionController institucionController = factory.getIInstitucionController();
			Set<DTInstitucion> instituciones = institucionController.listarInstituciones();
			sendApiSuccess(response, instituciones);
		} catch (Exception e) {
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

			Factory factory = Factory.get();
			IEdicionController edicionController = factory.getIEdicionController();
			Set<DTTipoRegistro> tiposRegistro = edicionController.mostrarTiposDeRegistro(nombreEdicion);
			sendApiSuccess(response, tiposRegistro);
		} catch (Exception e) {
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

			Factory factory = Factory.get();
			IEdicionController edicionController = factory.getIEdicionController();
			Set<DTAsistente> asistentes = edicionController.mostrarAsistentes(nombreEdicion);
			sendApiSuccess(response, asistentes);
		} catch (Exception e) {
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
				} catch (Exception e) {
					e.printStackTrace();
					sendApiError(response, "Error al procesar las categorías");
					return;
				}
			}

			// DTEventoAlta expects: (nombre, descripcion, fechaAlta, sigla, foto,
			// categorias)
			DTEventoAlta eventoAlta = new DTEventoAlta(nombre, descripcion, new java.util.Date(), sigla, foto,
					categoriasSet);

			System.out.println("DTEventoAlta creado: " + eventoAlta);

			Factory factory = Factory.get();
			IEventoController eventoController = factory.getIEventoController();

			System.out.println("Llamando a altaEvento...");
			boolean creado = eventoController.altaEvento(eventoAlta);
			System.out.println("Resultado altaEvento: " + creado);

			if (creado) {
				sendApiSuccess(response, "Evento creado exitosamente");
			} else {
				sendApiError(response, "No se pudo crear el evento");
			}
		} catch (ValidationInputException e) {
			System.out.println("ValidationInputException: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception general: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error al crear evento: " + e.getMessage());
		}
	}

	private void createEdicion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
			DTUsuario usuario = null;

			if (usuarioObj instanceof DTUsuario) {
				usuario = (DTUsuario) usuarioObj;
			}

			if (usuario == null) {
				sendApiError(response, "Usuario no logueado");
				return;
			}

			System.out.println("=== DEBUG createEdicion ===");
			System.out.println("Usuario logueado: " + usuario.nickname());

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

			// Convert LocalDate to Date for DTEdicion
			// DTEdicion expects: (nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad,
			// pais, foto, estado)
			java.util.Date fechaInicioDate = java.sql.Date.valueOf(fechaInicio);
			java.util.Date fechaFinDate = java.sql.Date.valueOf(fechaFin);
			java.util.Date fechaAltaDate = new java.util.Date();

			DTEdicion edicion = new DTEdicion(nombre, sigla, fechaInicioDate, fechaFinDate, fechaAltaDate,
					ciudad, pais, foto, EstadoEdicion.INGRESADA);

			System.out.println("DTEdicion creado: " + edicion);

			Factory factory = Factory.get();
			IEventoController eventoController = factory.getIEventoController();

			System.out.println("Llamando a agregarEdicionAEvento con:");
			System.out.println("  - nombreEvento: " + nombreEvento);
			System.out.println("  - nickname: " + usuario.nickname());
			System.out.println("  - edicion: " + edicion.nombre());

			boolean creada = eventoController.agregarEdicionAEvento(nombreEvento, usuario.nickname(), edicion);

			System.out.println("Resultado agregarEdicionAEvento: " + creada);

			if (creada) {
				sendApiSuccess(response, "Edición creada exitosamente");
			} else {
				sendApiError(response, "No se pudo crear la edición");
			}
		} catch (ValidationInputException e) {
			System.out.println("ValidationInputException: " + e.getMessage());
			e.printStackTrace();
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (Exception e) {
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

			Factory factory = Factory.get();
			IInstitucionController institucionController = factory.getIInstitucionController();
			boolean creada = institucionController.crearInstitucion(nombre, descripcion, sitioWeb, foto);

			if (creada) {
				sendApiSuccess(response, "Institución creada exitosamente");
			} else {
				sendApiError(response, "No se pudo crear la institución");
			}
		} catch (InstitucionRepetidaException e) {
			sendApiError(response, "Ya existe una institución con ese nombre");
		} catch (Exception e) {
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

			DTTipoRegistro tipoRegistro = new DTTipoRegistro(nombre, descripcion, costo, cupo);

			Factory factory = Factory.get();
			IEdicionController edicionController = factory.getIEdicionController();
			edicionController.altaTipoRegistro(nombreEdicion, tipoRegistro);

			sendApiSuccess(response, "Tipo de registro creado exitosamente");
		} catch (ValidationInputException e) {
			sendApiError(response, "Error de validación: " + e.getMessage());
		} catch (Exception e) {
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
			LocalDate fechaAlta = LocalDate.parse(fechaAltaStr, formatter);

			// Parsear registros JSON
			String registrosJson = request.getParameter("registros");
			if (registrosJson != null && !registrosJson.trim().isEmpty()) {
				com.google.gson.JsonArray registrosArray = gson.fromJson(registrosJson,
						com.google.gson.JsonArray.class);

				if (registrosArray.size() > 0) {
					com.google.gson.JsonObject primerRegistro = registrosArray.get(0).getAsJsonObject();
					String nombreTipoRegistro = primerRegistro.get("tipo").getAsString();
					int cantGratuitos = primerRegistro.get("cantidad").getAsInt();

					Factory factory = Factory.get();
					IEdicionController edicionController = factory.getIEdicionController();
					edicionController.altaPatrocinio(fechaAlta, nombreEdicion, nombreInstitucion, aporte,
							nombreTipoRegistro, cantGratuitos, codigo, nivel);

					sendApiSuccess(response, "Patrocinio creado exitosamente");
				} else {
					sendApiError(response, "Debe agregar al menos un registro gratuito");
				}
			} else {
				sendApiError(response, "Debe agregar al menos un registro gratuito");
			}
		} catch (ExistePatrocinioException e) {
			sendApiError(response, "Ya existe un patrocinio con ese código");
		} catch (CostoRegistrosGratuitosException e) {
			sendApiError(response, "El costo de los registros gratuitos supera el 20% del aporte");
		} catch (CantidadCuposDisponiblesException e) {
			sendApiError(response, "No hay suficientes cupos disponibles");
		} catch (InstitucionNoExistenteException e) {
			sendApiError(response, "La institución no existe");
		} catch (Exception e) {
			e.printStackTrace();
			sendApiError(response, "Error al crear patrocinio: " + e.getMessage());
		}
	}

	// ==================== UTILIDADES API ====================

	private boolean isApiAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");

		if (estado != EstadoSesion.LOGIN_CORRECTO) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			sendApiError(response, "No autenticado");
			return false;
		}

		Object usuarioObj = request.getSession().getAttribute("usuario_logueado");
		DTUsuario usuario = null;

		if (usuarioObj instanceof DTUsuario) {
			usuario = (DTUsuario) usuarioObj;
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
