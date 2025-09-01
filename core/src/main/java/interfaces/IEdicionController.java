package interfaces;

import java.time.LocalDate;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTPatrocinio;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.ExistePatrocinioException;

public interface IEdicionController {
  Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion); // fija edicionRecordada
  Set<DTAsistente> mostrarAsistentes();                              // usa edicionRecordada
  boolean cupoDisponible(String nombreTipoRegistro);                 // idem
  boolean asistenteNoRegistrado(String nickname);                    // idem
  void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname);
  void cancelarRegistroEdicionEvento();
  void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte, String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
  	throws ExistePatrocinioException, CostoRegistrosGratuitosException;
}
