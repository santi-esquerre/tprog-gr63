package webservice;

import interfaces.Factory;
import interfaces.IImageController;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ImageService {
	
	private IImageController imageController = Factory.get().getIImageController();
	
	//Constructor
	public ImageService() {}
	
	@WebMethod
	public String uploadImage(byte[] imageData, String imageName) {
		return imageController.uploadImage(imageData, imageName);
	}
	
	@WebMethod
	public boolean deleteImage(String imageName) {
		return imageController.deleteImage(imageName);
	}
	
	@WebMethod
	public String getImage(String imageName) {
		ServidorImage servidorImage = ServidorImage.get();
		return servidorImage.getURLBase() + imageName;
	}
	
	
	
}
