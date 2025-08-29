package interfaces;

import java.util.Set;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;

public interface IEventoController {
  Set<String> obtenerCategorias();
  boolean altaEvento(DTEventoAlta datosEventoAlta);
  Set<DTEvento> listarEventos();
  Set<DTEdicion> mostrarEdiciones(String nombreEvento);
}
