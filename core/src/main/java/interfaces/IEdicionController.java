package interfaces;

import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTEdicionDetallada;
import datatypes.DTTipoRegistro;
import exceptions.ValidationInputException;

public interface IEdicionController {
  void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro) throws ValidationInputException;
  DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro) throws ValidationInputException;
  Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion); // fija edicionRecordada
  Set<DTAsistente> mostrarAsistentes();                              // usa edicionRecordada
  boolean cupoDisponible(String nombreTipoRegistro);                 // idem
  boolean asistenteNoRegistrado(String nickname);                    // idem
  void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname);
  void cancelarRegistroEdicionEvento();
  
  // Obtener datos detallados de una edición
  DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEvento, String nombreEdicion) throws ValidationInputException;
}
