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
		String foto = ImagesManager.uploadImage(request, response);
		
		Factory factory = Factory.get();
		IUsuarioController usuarioController = factory.getIUsuarioController();
		System.out.print("La imagen es " + foto);
		
		/*try {
			
			if (rol.equals("asistente")) {
				int dia = Integer.parseInt(request.getParameter("dia"));
				int mes = Integer.parseInt(request.getParameter("mes"));
				int anio = Integer.parseInt(request.getParameter("anio"));
				LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
				String apellido = request.getParameter("apellido");
			}
			
			
		} catch (UsuarioNicknameRepetidoException en) {
			
		}*/
		
		
	}

}
