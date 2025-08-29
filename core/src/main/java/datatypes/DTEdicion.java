package datatypes;


import java.util.Date;

public record DTEdicion(String nombre, String sigla, Date fechaInicio, Date fechaFin,
                        Date fechaAlta, String ciudad, String pais) {}