package interfaces;

import java.util.Set;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import exceptions.ValidationInputException;

public interface IEventoController {
boolean altaCategoria(String nombre) throws ValidationInputException;
  Set<String> obtenerCategorias();
  boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException;
  Set<DTEvento> listarEventos();
  Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException;
  
  // New detailed data retrieval operation
  DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento) throws ValidationInputException;
}
