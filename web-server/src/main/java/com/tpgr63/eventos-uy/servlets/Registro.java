package servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


import interfaces.Factory;
import interfaces.IInstitucionController;
import datatypes.DTInstitucion;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;

import java.util.Set;
import model.EstadoSesion;
import controllers.SessionManager;
import controllers.ImagesManager;
import interfaces.IUsuarioController;
import java.time.LocalDate;



/**
 * Servlet implementation class Registro
 */
@WebServlet("/registro")
@MultipartConfig
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		SessionManager.initSesion(request);
		EstadoSesion estado = (EstadoSesion) request.getSession().getAttribute("estado_sesion");
		if (estado == EstadoSesion.LOGIN_CORRECTO) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		Factory factory = Factory.get();
		IInstitucionController institucionController = factory.getIInstitucionController();
		Set<DTInstitucion> instituciones = institucionController.listarInstituciones();
		request.setAttribute("instituciones", instituciones);
		request.getRequestDispatcher("/WEB-INF/registro/registroUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nickname = request.getParameter("nickname");
		String nombre = request.getParameter("nombre");
		String correo = request.getParameter("correo");
		String password = request.getParameter("password");
		String rol = request.getParameter("rol");
		String foto = ImagesManager.getImageName(request);
		
		System.out.println("Primer Correo: " + correo);
		Factory factory = Factory.get();
		IUsuarioController usuarioController = factory.getIUsuarioController();
		
		try {
			
			if (rol.equals("asistente")) {
				System.out.println("Está haciendo un asistente");
				int dia = Integer.parseInt(request.getParameter("dia"));
				int mes = Integer.parseInt(request.getParameter("mes"));
				int anio = Integer.parseInt(request.getParameter("anio"));
				LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
				String apellido = request.getParameter("apellido");
				String institucion = request.getParameter("institucion");
				if (institucion.equals("ninguna")) {
					usuarioController.crearAsistente(nickname, nombre, correo, password, foto, apellido, fechaNacimiento);
					
				} else {
					usuarioController.crearAsistente(nickname, nombre, correo, password, foto, apellido, fechaNacimiento, institucion);
				}
			}
			else {
				String descripcion = request.getParameter("descripcion");
				String linkSitioWeb = request.getParameter("sitioWeb");
				if (linkSitioWeb.isEmpty()) {
					usuarioController.crearOrganizador(nickname, nombre, correo, password, foto, descripcion);
				} else {
					usuarioController.crearOrganizador(nickname, nombre, correo, password, foto, descripcion, linkSitioWeb);
				}
			}
			
		} catch (UsuarioNicknameRepetidoException en) {
			request.setAttribute("error", "El nickname ya está en uso. Por favor, elija otro.");
			doGet(request, response);
			return;
		}
		catch (UsuarioCorreoRepetidoException ec) {
			request.setAttribute("error", "El correo ya está en uso. Por favor, elija otro.");
			doGet(request, response);
			return;
		} catch (Exception e) {
			request.setAttribute("error", "Error al registrar el usuario. Por favor, intente nuevamente.");
			doGet(request, response);
			return;
		}
		ImagesManager.uploadImage(request, response);
		System.out.println("Usuario registrado con éxito.");
		response.sendRedirect(request.getContextPath() + "/sesion");
		
	}

}
