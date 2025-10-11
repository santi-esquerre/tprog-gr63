package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


public class ImagesManager {
	
	public static String uploadImage(HttpServletRequest request, HttpServletResponse response) {
		String nombreArchivo = "";
		try {
		    // Obtenemos la imagen subida desde el formulario
		    Part foto = request.getPart("imagen");		
		    
		    // Nombre del archivo original
		    if (foto != null && foto.getSize() > 0) {
		        nombreArchivo = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
		    } else {
		        nombreArchivo = "default.png"; // por defecto si no se subió nada
		    }

		    // Carpeta base dentro del deployment actual
		    String deploymentPath = request.getServletContext().getRealPath("/");
		    
		    // Convertimos a File para poder manipularla
		    File webappDir = new File(deploymentPath);
		    
		    // Subimos un nivel (parent)
		    File parentDir = webappDir.getParentFile();
		    
		    // Bajamos a la carpeta "upload"
		    File carpetaDestino = new File(parentDir, "upload");

		    // Crear carpeta si no existe
		    if (!carpetaDestino.exists()) {
		        carpetaDestino.mkdirs();
		    }

		    // Guardar la imagen
		    File archivoDestino = new File(carpetaDestino, nombreArchivo);
		    if (foto != null && foto.getSize() > 0) {
		        foto.write(archivoDestino.getAbsolutePath());
		    }


		} catch (IOException | ServletException e) {
		    // Captura errores de lectura/escritura o de manejo de servlet
		    e.printStackTrace();
		    try {
		        response.getWriter().println("Error al guardar la imagen: " + e.getMessage());
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		}
		return nombreArchivo;
	}
	
	public static String getImagePath(String imageName, HttpServletRequest request) {

		// Carpeta base dentro del deployment actual
	    String deploymentPath = request.getServletContext().getRealPath("/");
	    
	    // Convertimos a File para poder manipularla
	    File webappDir = new File(deploymentPath);
	    
	    // Subimos un nivel (parent)
	    File parentDir = webappDir.getParentFile();
	    
	    // Bajamos a la carpeta "upload"
	    File carpetaDestino = new File(parentDir, "upload");
	    
	    // Ruta completa de la imagen
	    File archivoImagen = new File(carpetaDestino, imageName);
	    
	    return archivoImagen.getAbsolutePath();
	}
	
	public static File getDefaultDirectory(HttpServletRequest request) {
		// Carpeta base dentro del deployment actual
	    String deploymentPath = request.getServletContext().getRealPath("/");
	    
	    // Convertimos a File para poder manipularla
	    File webappDir = new File(deploymentPath);
	    
	    // Subimos un nivel (parent)
	    File parentDir = webappDir.getParentFile();
	    
	    // Bajamos a la carpeta "upload"
	    File carpetaDestino = new File(parentDir, "upload");
	    
	    
	    return carpetaDestino;
	}
}



