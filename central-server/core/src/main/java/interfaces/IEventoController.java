package interfaces;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import datatypes.EstadoEdicion;
import exceptions.ValidationInputException;
import java.util.Set;

public interface IEventoController {
  boolean altaCategoria(String nombre) throws ValidationInputException;

  Set<String> obtenerCategorias();

  boolean altaEvento(DTEventoAlta datosEventoAlta) throws ValidationInputException;

  Set<DTEvento> listarEventos();

  Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException;

  void altaDeCategoria(String nombreCategoria) throws ValidationInputException;

  Set<DTEdicion> mostrarEdiciones(String nombreEvento, EstadoEdicion estado)
      throws ValidationInputException;

  boolean agregarEdicionAEvento(String nombreEvento, String nicknameOrganizador, DTEdicion datos)
      throws ValidationInputException;

  // New detailed data retrieval operation
  DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento)
      throws ValidationInputException;

  DTEventoDetallado obtenerDatosDetalladosEventoAprobadas(String nombreEvento)
      throws ValidationInputException;
}
