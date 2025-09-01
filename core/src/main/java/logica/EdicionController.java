package logica;

import java.util.Objects;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

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
	
	TipoRegistroFactory trFactory = TipoRegistroFactory.get();
	
	int res = Tx.inTx((em) -> {
		var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
		if (ed == null) return 1; 
		var tr = ed.buscarTipoRegistro(datosTipoRegistro.nombre());
		if (tr != null) return 2;
		trFactory.altaTipoRegistro(em, ed, datosTipoRegistro);
		return 0;
	});
	
	switch (res) {
		case 1 -> throw new EdicionInexistenteException("La edición " + nombreEdicion + " no existe");
		case 2 -> throw new TipoRegistroRepetidoException("El tipo de registro " + datosTipoRegistro.nombre() + " ya existe en la edición " + nombreEdicion);
	}
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
	//TO DO: terminar esto	
	  
	  Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
	Objects.requireNonNull(nombreTipoRegistro, "nombreTipoRegistro requerido");
	
	try {
		return Tx.inTx(em -> {
			var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
			return ed.buscarTipoRegistro(nombreTipoRegistro).obtenerDTTipoRegistro();
		});
	} catch (Exception e) {
		System.out.println(e.getMessage());
		return null;
	}
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
