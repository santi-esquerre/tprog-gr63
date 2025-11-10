package controllers;

import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import webservice.ImageService;
import webservice.PortFactory;


public class ImagesManager {
	
	public static String uploadImage(HttpServletRequest request, HttpServletResponse response) {
		String nombreArchivo = "";
		try {
			Part foto = request.getPart("imagen");
		    // Nombre del archivo original
		    if (foto != null && foto.getSize() > 0) {
		        nombreArchivo = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
		    } else {
		        nombreArchivo = "default.png"; // por defecto si no se subió nada
		    }
		    
		    if (foto != null && foto.getSize() > 0 && !nombreArchivo.equals("default.png")) {
		        PortFactory factory = PortFactory.get();
		        ImageService imagePort = factory.getImagePort();
		        nombreArchivo = imagePort.uploadImage(foto.getInputStream().readAllBytes(), nombreArchivo);
		    }
		    // Carpeta base dentro del deployment actual
		    } catch (IOException | ServletException e) {
		    	e.printStackTrace();
			    try {
			        response.getWriter().println("Error al guardar la imagen: " + e.getMessage());
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }		    	
		}
		
		
		return nombreArchivo;
	}
	
	public static boolean deleteImage(String imageName) {
		if (imageName.equals("default.png")) {
			// No borramos la imagen por defecto
			return false;
		}
		
		try {
			
			PortFactory factory = PortFactory.get();
			ImageService imagePort = factory.getImagePort();
			return imagePort.deleteImage(imageName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
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
	
	public static String getImageUrl(String imageName) {
		PortFactory factory = PortFactory.get();
		ImageService imagePort = factory.getImagePort();
		return imagePort.getImage(imageName);
	}
}



