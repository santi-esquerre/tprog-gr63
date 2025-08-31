package logica;

import java.util.Objects;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTTipoRegistro;
import dominio.Edicion;
import exceptions.EdicionInexistenteException;
import exceptions.TipoRegistroInexistenteException;
import exceptions.TipoRegistroRepetidoException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEdicionController;
import repos.EdicionRepository;
import repos.UsuarioRepository;

public final class EdicionController implements IEdicionController {
  private static final EdicionController INSTANCE = new EdicionController();
  private final EdicionRepository edicionRepo = EdicionRepository.get();
  private final UsuarioRepository usuarioRepo = UsuarioRepository.get();
  private final RegistroFactory registroFactory = RegistroFactory.get();

  // “edicionRecordada” como en el DCD
  private Edicion edicionRecordada;

  private EdicionController(){}
  public static EdicionController get(){ return INSTANCE; }
  
  /**
   * Da de alta un nuevo tipo de registro en una edición existente.
   * @param nombreEdicion El nombre de la edición donde se dará de alta el tipo de registro.
   * @param datosTipoRegistro Los datos del tipo de registro a dar de alta.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws TipoRegistroRepetidoException Si ya existe un tipo de registro con el mismo nombre en la edición.
   */
  @Override
  public void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro) throws EdicionInexistenteException, TipoRegistroRepetidoException {
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
	Objects.requireNonNull(datosTipoRegistro, "datosTipoRegistro requerido");
	
	// Verificar si la edición existe
	var ed = Tx.inTx(em -> {
		return edicionRepo.buscarEdicion(em, nombreEdicion);
	});
	
	if(ed == null) {
		// La edición no existe
		throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
	}
	if(Tx.inTx(em -> {
	  // Verificar si ya existe el tipo de registro
	  var tr = ed.buscarTipoRegistro(nombreEdicion);
	  // Si no existe, dar de alta el tipo de registro
	  return tr != null;
	})) throw new TipoRegistroRepetidoException("Ya existe el tipo de registro de nombre " + datosTipoRegistro.nombre() + " en la edición " + nombreEdicion);
	
	Tx.inTx(em -> {
		//Alta del tipo de registro
	   TipoRegistroFactory.get().altaTipoRegistro(em, ed, datosTipoRegistro);
  	   return null;
	});
  }
  
  /**
   * Consulta un tipo de registro en una edición existente.
   * @param nombreEdicion El nombre de la edición donde se encuentra el tipo de registro.
   * @param nombreTipoRegistro El nombre del tipo de registro a consultar.
   * @return Los datos del tipo de registro consultado.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws TipoRegistroInexistenteException Si el tipo de registro no existe en la edición.
   */
  @Override
  public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro) throws EdicionInexistenteException, TipoRegistroInexistenteException{
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
	Objects.requireNonNull(nombreTipoRegistro, "nombreTipoRegistro requerido");
	// Verificar si la edición existe
	var ed = Tx.inTx(em -> {
		return edicionRepo.buscarEdicion(em, nombreEdicion);
	});
		
	if(ed == null) {
		// La edición no existe
		throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
	}
	
	
	DTTipoRegistro tr = Tx.inTx(em -> {
	  return ed.buscarTipoRegistro(nombreTipoRegistro).obtenerDTTipoRegistro();
	});
	
	if(tr == null) throw new TipoRegistroInexistenteException("No existe el tipo de registro de nombre " + nombreTipoRegistro + " en la edición " + nombreEdicion);
	
	return tr;
  }

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
  public void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname) {
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
      registroFactory.altaRegistro(em, ed, a, tr, tr.obtenerDTTipoRegistro().costo());
      return null;
    });
  }

  @Override
  public void cancelarRegistroEdicionEvento() {
    // placeholder según DCD
  }
}
