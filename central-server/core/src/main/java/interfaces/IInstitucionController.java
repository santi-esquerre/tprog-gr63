package interfaces;

import datatypes.DTInstitucion;
import datatypes.DTPatrocinio;
import exceptions.InstitucionRepetidaException;
import java.util.Set;

public interface IInstitucionController {
  boolean crearInstitucion(String nombre, String descripcion, String sitioWeb)
      throws InstitucionRepetidaException;

  Set<DTInstitucion> listarInstituciones();

  DTPatrocinio obtenerDTPatrocinio(String nombreEdicionEvento, String nombreInstitucion);

  boolean afiliarAsistenteAInstitucion(String nombreAsistente, String nombreInstitucion);
}
