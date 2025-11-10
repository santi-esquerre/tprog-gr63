package logica;
import java.io.File;

import interfaces.IImageController;

public class ImageController implements IImageController {
	private static final ImageController INSTANCE = new ImageController();
	String defaultDirectory = File.separator + ".eventos-uy" + File.separator + "upload";
	
	  public static ImageController get() {
		    return INSTANCE;
		  }
	
	@Override
	public String uploadImage(byte[] imageData, String imageName) {
		int contador=1;
		String defaultDir = getDefaultDirectory();
		File carpetaDestino = new File(defaultDir);
		if (!carpetaDestino.exists()) {
			carpetaDestino.mkdirs();
		}
		
	    // Guardar la imagen
	    File archivoDestino = new File(carpetaDestino, imageName);
	    
	    if(archivoDestino.exists()) {
	    	String nombreSinExtension = imageName.contains(".") ? 
	    			imageName.substring(0, imageName.lastIndexOf('.')) : imageName;
	    	String extension = imageName.contains(".") ? 
	    			imageName.substring(imageName.lastIndexOf('.')) : "";
	    	
	    	while(archivoDestino.exists()) {
	    		imageName = nombreSinExtension + " (" + contador + ")" + extension;
	    		archivoDestino = new File(carpetaDestino, imageName);
	    		contador++;
	    	}
	    }
	    
	    if (imageData != null && imageData.length > 0) {
	    	try { 
		        java.nio.file.Files.write(archivoDestino.toPath(), imageData);
		        return imageName;
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
		return imageName;
	}
	
	@Override
	public boolean deleteImage(String imageName) {
		if (imageName.equals("default.png")) {
			// No borramos la imagen por defecto
			return false;
		}
		
		String imagePath = getDefaultDirectory() + File.separator + imageName;
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
	
	@Override
	public String getDefaultDirectory() {
		String rutaCarpeta = System.getProperty("user.home") + defaultDirectory;
		File carpeta = new File(rutaCarpeta);
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
		return carpeta.getAbsolutePath();
	}
	

}
