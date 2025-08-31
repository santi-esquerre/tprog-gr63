package logica;

import java.util.Objects;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTTipoRegistro;
import dominio.Edicion;
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
