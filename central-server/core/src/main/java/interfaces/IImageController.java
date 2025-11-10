package interfaces;

public interface IImageController {
	
	public String uploadImage(byte[] imageData, String imageName);
	
	public boolean deleteImage(String imageName);
	
	public String getDefaultDirectory();
	
		
	
}
