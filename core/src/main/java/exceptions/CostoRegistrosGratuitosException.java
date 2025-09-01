package exceptions;

public class CostoRegistrosGratuitosException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public CostoRegistrosGratuitosException() {
		super("El costo de los registros gratuitos no puede ser mayor al 20% del aporte hecho.");
	}

}
