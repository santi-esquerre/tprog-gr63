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
		int contador=1;
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
		    
		    if(archivoDestino.exists()) {
		    	String nombreSinExtension = nombreArchivo.contains(".") ? 
		    			nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.')) : nombreArchivo;
		    	String extension = nombreArchivo.contains(".") ? 
		    			nombreArchivo.substring(nombreArchivo.lastIndexOf('.')) : "";
		    	
		    	while(archivoDestino.exists()) {
		    		nombreArchivo = nombreSinExtension + " (" + contador + ")" + extension;
		    		archivoDestino = new File(carpetaDestino, nombreArchivo);
		    		contador++;
		    	}
		    }
		    
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
	
	public static boolean deleteImage(String imageName, HttpServletRequest request) {
		if (imageName.equals("default.png")) {
			// No borramos la imagen por defecto
			return false;
		}
		
		String imagePath = getImagePath(imageName, request);
		try {
			File imageFile = new File(imagePath);
			if (imageFile.exists()) {
				return imageFile.delete();
			} else {
				System.out.println("La imagen no existe: " + imagePath);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
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
	
	public static String getImageName(HttpServletRequest request) {
	    String imageName = "";
	    try {
	        Part foto = request.getPart("imagen");
	        if (foto != null && foto.getSize() > 0) {
	        	imageName = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
	        } else {
	        	imageName = "default.png"; // por defecto si no se subió nada
	        }
	    } catch (IOException | ServletException e) {
	        e.printStackTrace();
	    }
	    
	    return imageName;
	}
}



