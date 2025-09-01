package interfaces;

import java.util.Set;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import exceptions.ValidationInputException;

public interface IEventoController {
  Set<String> obtenerCategorias();
  boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException;
  Set<DTEvento> listarEventos();
  Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException;
  void altaDeCategoria(String nombreCategoria) throws ValidationInputException;
}
