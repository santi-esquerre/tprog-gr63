package interfaces;
import java.util.Set;
import datatypes.DTInstitucion;

import java.util.Set;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import datatypes.DTRegistrosOtorgados;
import exceptions.InstitucionRepetidaException;

public interface IInstitucionController {
    boolean crearInstitucion(String nombre, String descripcion, String sitioWeb) throws InstitucionRepetidaException;
    Set<DTInstitucion> listarInstituciones();
    DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion);
    boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion);
}
