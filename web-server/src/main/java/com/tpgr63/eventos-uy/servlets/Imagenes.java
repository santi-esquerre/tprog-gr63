package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import controllers.ImagesManager;


/**
 * Servlet implementation class Imagenes
 */
@WebServlet("/image/*")
public class Imagenes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Imagenes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        // obtiene la parte después de /image/
        String fileName = request.getPathInfo(); // retorna "/imagen.png"
        if(fileName == null || fileName.equals("/")) {
            fileName = "/default.png";
        }
        
        fileName = fileName.substring(1); // elimina el "/"
            
        String imageUrl = ImagesManager.getImageUrl(fileName);
        response.sendRedirect(imageUrl);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
