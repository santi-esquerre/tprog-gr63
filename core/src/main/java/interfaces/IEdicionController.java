package interfaces;

import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTTipoRegistro;

public interface IEdicionController {
  Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion); // fija edicionRecordada
  Set<DTAsistente> mostrarAsistentes();                              // usa edicionRecordada
  boolean cupoDisponible(String nombreTipoRegistro);                 // idem
  boolean asistenteNoRegistrado(String nickname);                    // idem
  void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname);
  void cancelarRegistroEdicionEvento();
}
