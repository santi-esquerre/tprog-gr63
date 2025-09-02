package interfaces;

import java.util.Date;
import java.time.LocalDate;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTPatrocinio;
import datatypes.DTEdicionDetallada;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import exceptions.CantidadCuposDisponiblesException;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.ExistePatrocinioException;
import exceptions.ValidationInputException;

public interface IEdicionController {
  void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro) throws ValidationInputException;

  DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro) throws ValidationInputException;

  Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion); // fija edicionRecordada
  Set<DTAsistente> mostrarAsistentes();                              // usa edicionRecordada
  boolean cupoDisponible(String nombreTipoRegistro);                 // idem
  boolean asistenteNoRegistrado(String nickname);                    // idem
  void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname, Date fecha);
  void cancelarRegistroEdicionEvento();
  void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte, String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
  	throws ExistePatrocinioException, CostoRegistrosGratuitosException, CantidadCuposDisponiblesException;

  // boolean altaEdicion(String nombreEvento,
  // String nicknameOrganizador,
  // datatypes.DTEdicion datos) throws exceptions.ValidationInputException;

  // Obtener datos detallados de una edición
  DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEvento, String nombreEdicion)
      throws ValidationInputException;
}
