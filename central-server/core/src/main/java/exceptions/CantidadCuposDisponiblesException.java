package exceptions;

public class CantidadCuposDisponiblesException extends ValidationInputException {

	private static final long serialVersionUID = 1L;
	
	public CantidadCuposDisponiblesException (int cuposDisponibles, String nombreTipoRegistro) {
        super(cuposDisponibles > 0
                    ? "No hay suficientes cupos disponibles para el tipo de registro " + nombreTipoRegistro
                    : "No hay cupos disponibles para el tipo de registro " + nombreTipoRegistro
            );
	}
}
