package datatypes;


import java.util.Date;

public record DTEdicion(String nombre, String sigla, Date fechaInicio, Date fechaFin,
                        Date fechaAlta, String ciudad, String pais, EstadoEdicion estado) {
	
	public DTEdicion(String nombre, String sigla, Date fechaInicio, Date fechaFin,
			Date fechaAlta, String ciudad, String pais) {
		this(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, null);
	}
	
}