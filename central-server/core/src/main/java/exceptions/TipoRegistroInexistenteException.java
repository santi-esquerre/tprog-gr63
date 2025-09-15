package exceptions;

@SuppressWarnings("serial")
public class TipoRegistroInexistenteException extends ValidationInputException {
	  public TipoRegistroInexistenteException(String message) {
		  super(message);
	  }
}
