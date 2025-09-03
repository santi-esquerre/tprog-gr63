package logica;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import dominio.Edicion;
import dominio.TipoRegistro;
import exceptions.CantidadCuposDisponiblesException;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.EdicionInexistenteException;
import exceptions.ExistePatrocinioException;
import exceptions.TipoRegistroRepetidoException;
import exceptions.ValidationInputException;
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

  private EdicionController() {
  }

  public static EdicionController get() {
    return INSTANCE;
  }

  /**
   * Da de alta un nuevo tipo de registro en una edición existente.
   * 
   * @param nombreEdicion     El nombre de la edición donde se dará de alta el
   *                          tipo de registro.
   * @param datosTipoRegistro Los datos del tipo de registro a dar de alta.
   * @throws EdicionInexistenteException   Si la edición no existe.
   * @throws TipoRegistroRepetidoException Si ya existe un tipo de registro con el
   *                                       mismo nombre en la edición.
   */
  @Override
  public void altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro)
      throws ValidationInputException, EdicionInexistenteException, TipoRegistroRepetidoException {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Objects.requireNonNull(datosTipoRegistro, "datosTipoRegistro requerido");

    // Truncate input lengths if they exceed database limits
    String nombre = datosTipoRegistro.nombre();
    String descripcion = datosTipoRegistro.descripcion();

    if (nombre.length() > 120) {
      nombre = nombre.substring(0, 120);
    }
    if (descripcion.length() > 300) {
      descripcion = descripcion.substring(0, 300);
    }

    // Create truncated DTTipoRegistro if necessary
    DTTipoRegistro datosTruncados = (!nombre.equals(datosTipoRegistro.nombre())
        || !descripcion.equals(datosTipoRegistro.descripcion()))
            ? new DTTipoRegistro(nombre, descripcion, datosTipoRegistro.costo(), datosTipoRegistro.cupo())
            : datosTipoRegistro;

    // Verificar si la edición existe
    var ed = Tx.inTx(em -> {
      return edicionRepo.buscarEdicion(em, nombreEdicion);
    });

    if (ed == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }
    if (Tx.inTx(em -> {
      // Verificar si ya existe el tipo de registro
      var tr = ed.buscarTipoRegistro(datosTruncados.nombre());
      // Si no existe, dar de alta el tipo de registro
      return tr != null;
    }))
      throw new TipoRegistroRepetidoException(
          "Ya existe el tipo de registro de nombre " + datosTruncados.nombre() + " en la edición " + nombreEdicion);

    Tx.inTx(em -> {
      // Alta del tipo de registro
      TipoRegistroFactory.get().altaTipoRegistro(em, ed, datosTruncados);
      return null;
    });
  }

  /**
   * Consulta un tipo de registro en una edición existente.
   * 
   * @param nombreEdicion      El nombre de la edición donde se encuentra el tipo
   *                           de registro.
   * @param nombreTipoRegistro El nombre del tipo de registro a consultar.
   * @return Los datos del tipo de registro consultado.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws ValidationInputException    Si el tipo de registro no existe en la
   *                                     edición.
   */
  @Override
  public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro)
      throws EdicionInexistenteException, ValidationInputException {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Objects.requireNonNull(nombreTipoRegistro, "nombreTipoRegistro requerido");
    // Verificar si la edición existe
    var ed = Tx.inTx(em -> {
      return edicionRepo.buscarEdicion(em, nombreEdicion);
    });

    if (ed == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }

    var tipoRegistro = Tx.inTx(em -> {
      return ed.buscarTipoRegistro(nombreTipoRegistro);
    });

    if (tipoRegistro == null) {
      throw new ValidationInputException(
          "No existe el tipo de registro de nombre " + nombreTipoRegistro + " en la edición " + nombreEdicion);
    }

    DTTipoRegistro tr = tipoRegistro.obtenerDTTipoRegistro();

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
    if (edicionRecordada == null)
      return Set.of();
    return Tx.inTx(em -> {
      // refresco para evitar lazy issues
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return (ed == null) ? Set.of() : ed.obtenerDTAsistentes();
    });
  }

  @Override
  public boolean cupoDisponible(String nombreTipoRegistro) {
    if (edicionRecordada == null)
      return false;
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return ed != null && ed.cupoDisponible(nombreTipoRegistro);
    });
  }

  @Override
  public boolean asistenteNoRegistrado(String nickname) {
    if (edicionRecordada == null)
      return false;
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      return ed != null && ed.verificarNoRegistro(nickname);
    });
  }

  @Override
  public void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname, Date fecha) {
    if (edicionRecordada == null)
      throw new IllegalStateException("Edición no seleccionada");
    Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      if (ed == null)
        throw new IllegalArgumentException("Edición inexistente");
      var tr = ed.buscarTipoRegistro(nombreTipoRegistro);
      if (tr == null)
        throw new IllegalArgumentException("Tipo de registro inexistente");
      var a = usuarioRepo.obtenerAsistente(em, nickname);
      if (a == null)
        throw new IllegalArgumentException("Asistente inexistente");
      if (!ed.cupoDisponible(nombreTipoRegistro))
        throw new IllegalStateException("Sin cupo");
      if (!ed.verificarNoRegistro(nickname))
        throw new IllegalStateException("Ya registrado");
      registroFactory.altaRegistro(em, ed, a, tr, tr.obtenerDTTipoRegistro().costo(), fecha);
      tr.decrementarCupo();
      em.merge(tr);
      edicionRecordada = null;
      return null;
    });
  }

  @Override
  public void altaRegistroEdicionEvento(String nombreTipoRegistro, String nickname) {
    Date fecha = new Date();
    if (edicionRecordada == null)
      throw new IllegalStateException("Edición no seleccionada");
    Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, edicionRecordada.getNombre());
      if (ed == null)
        throw new IllegalArgumentException("Edición inexistente");
      var tr = ed.buscarTipoRegistro(nombreTipoRegistro);
      if (tr == null)
        throw new IllegalArgumentException("Tipo de registro inexistente");
      var a = usuarioRepo.obtenerAsistente(em, nickname);
      if (a == null)
        throw new IllegalArgumentException("Asistente inexistente");
      if (!ed.cupoDisponible(nombreTipoRegistro))
        throw new IllegalStateException("Sin cupo");
      if (!ed.verificarNoRegistro(nickname))
        throw new IllegalStateException("Ya registrado");
      registroFactory.altaRegistro(em, ed, a, tr, tr.obtenerDTTipoRegistro().costo(), fecha);
      tr.decrementarCupo();
      em.merge(tr);
      edicionRecordada = null;
      return null;
    });
  }

  @Override
  public void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte,
      String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
      throws ExistePatrocinioException, CostoRegistrosGratuitosException, CantidadCuposDisponiblesException {

    if (Tx.inTx(em -> {
      return edicionRepo.existePatrocinio(em, nombreEdicion, nombreInstitucion);
    })) {
      throw new ExistePatrocinioException(nombreInstitucion, nombreEdicion);
    }

    TipoRegistro tr = Tx.inTx(emt -> {
      return tipoRegistroRepo.buscarTipoRegistro(emt, nombreTipoRegistro, nombreEdicion);
    });
    int cupos = tr.getCupo();
    if (cupos < cantGratuitos) {
      throw new CantidadCuposDisponiblesException(cupos, nombreTipoRegistro);
    }

    if (((tr.getCosto() * cantGratuitos) / aporte) > 0.2) {
      throw new CostoRegistrosGratuitosException();
    }

    Tx.inTx(em -> {
      patrocinioFactory.crearPatrocinio(em, fecha, nombreEdicion, nombreInstitucion, aporte, tr, cantGratuitos, codigo,
          nivelPatrocinio);
      return null;
    });
  }

  @Override
  public void cancelarRegistroEdicionEvento() {
    edicionRecordada = null;
  }

  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEvento, String nombreEdicion)
      throws ValidationInputException {
    java.util.Objects.requireNonNull(nombreEvento, "nombreEvento requerido");
    java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    var resultado = Tx.inTx(em -> edicionRepo.obtenerDatosDetalladosEdicion(em, nombreEvento, nombreEdicion));
    if (resultado == null) {
      throw new ValidationInputException(
          "No existe la edición '" + nombreEdicion + "' para el evento '" + nombreEvento + "'");
    }
    return resultado;
  }

  @Override
  public Set<datatypes.DTEdicion> obtenerEdicionesPorOrganizador(String nicknameOrganizador)
      throws ValidationInputException {
    java.util.Objects.requireNonNull(nicknameOrganizador, "nicknameOrganizador requerido");
    Set<DTEdicion> resultado = Set.of(Tx.inTx(em -> edicionRepo.obtenerEdicionesPorOrganizador(em, nicknameOrganizador))
        .stream().map(e -> e.toDTEdicion()).toArray(DTEdicion[]::new));
    return resultado;
  }
}
