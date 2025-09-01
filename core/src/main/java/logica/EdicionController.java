package logica;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import dominio.Edicion;
import dominio.TipoRegistro;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.ExistePatrocinioException;
import infra.Tx;
import interfaces.IEdicionController;
import repos.EdicionRepository;
import repos.TipoRegistroRepository;
import repos.UsuarioRepository;

public final class EdicionController implements IEdicionController {
  private static final EdicionController INSTANCE = new EdicionController();
  private final EdicionRepository edicionRepo = EdicionRepository.get();
  private final UsuarioRepository usuarioRepo = UsuarioRepository.get();
  private final RegistroFactory registroFactory = RegistroFactory.get();
  private final TipoRegistroRepository tipoRegistroRepo = TipoRegistroRepository.get();
  private final PatrocinioFactory patrocinioFactory = PatrocinioFactory.get();

  // “edicionRecordada” como en el DCD
  private Edicion edicionRecordada;

  private EdicionController(){}
  public static EdicionController get(){ return INSTANCE; }

  @Override
  public Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(em -> {
      edicionRecordada = edicionRepo.buscarEdicion(em, nombreEdicion);
      return (edicionRecordada == null) ? Set.of() : edicionRecordada.listarDTTiposDeRegistro();
    });
  }

  @Override
  public Set<DTAsistente> mostrarAsistentes() {
    if (edicionRecordada == null) return Set.of();
    return Tx.inTx(em -> {
      // refresco para evitar lazy issues
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return (ed == null) ? Set.of() : ed.obtenerDTAsistentes();
    });
  }

  @Override
  public boolean cupoDisponible(String nombreTipoRegistro) {
    if (edicionRecordada == null) return false;
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return ed != null && ed.cupoDisponible(nombreTipoRegistro);
    });
  }

  @Override
  public boolean asistenteNoRegistrado(String nickname) {
    if (edicionRecordada == null) return false;
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return ed != null && ed.verificarNoRegistro(nickname);
    });
  }

  @Override
  public void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname, Date fecha) {
    if (edicionRecordada == null) throw new IllegalStateException("Edición no seleccionada");
    Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      if (ed == null) throw new IllegalArgumentException("Edición inexistente");
      var tr = ed.buscarTipoRegistro(nombreTipoRegistro);
      if (tr == null) throw new IllegalArgumentException("Tipo de registro inexistente");
      var a = usuarioRepo.obtenerAsistente(em, nickname);
      if (a == null) throw new IllegalArgumentException("Asistente inexistente");
      if (!ed.cupoDisponible(nombreTipoRegistro)) throw new IllegalStateException("Sin cupo");
      if (!ed.verificarNoRegistro(nickname)) throw new IllegalStateException("Ya registrado");
      registroFactory.altaRegistro(em, ed, a, tr, tr.obtenerDTTipoRegistro().costo(), fecha);
      tr.decrementarCupo();
      em.merge(tr);
      edicionRecordada = null;
      return null;
    });
  }
  
  @Override
  public void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte, String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
		  	throws ExistePatrocinioException, CostoRegistrosGratuitosException {
	  
	  if ( Tx.inTx(em -> {
		  return edicionRepo.existePatrocinio(em, nombreEdicion, nombreInstitucion);
	  })) {
		  throw new ExistePatrocinioException(nombreInstitucion, nombreEdicion);
	  }
	  
	  TipoRegistro tr = Tx.inTx(emt -> {return tipoRegistroRepo.buscarTipoRegistro(emt, nombreTipoRegistro, nombreEdicion);});
	  
	  if (tr.getCupo() < cantGratuitos) {
		  throw new CostoRegistrosGratuitosException();
	  }
	  
	  if (((tr.getCosto() * cantGratuitos) / aporte) > 0.2) {
		  throw new CostoRegistrosGratuitosException();
	  }
	  
	  Tx.inTx(em -> {
		  patrocinioFactory.crearPatrocinio(em, fecha, nombreEdicion, nombreInstitucion, aporte, tr, cantGratuitos, codigo, nivelPatrocinio);
		  return null;
	  });
  }

  
  @Override
  public void cancelarRegistroEdicionEvento() {
	  edicionRecordada = null;
  }
}
