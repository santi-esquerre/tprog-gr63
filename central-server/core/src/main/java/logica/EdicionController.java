package logica;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTTipoRegistro;
import datatypes.EstadoEdicion;
import datatypes.NivelPatrocinio;
import dominio.Edicion;
import dominio.TipoRegistro;
import exceptions.CantidadCuposDisponiblesException;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.EdicionInexistenteException;
import exceptions.ExistePatrocinioException;
import exceptions.InstitucionNoExistenteException;
import exceptions.TipoRegistroRepetidoException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEdicionController;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import repos.EdicionRepository;
import repos.InstitucionRepository;
import repos.TipoRegistroRepository;
import repos.UsuarioRepository;

public final class EdicionController implements IEdicionController {
  private static final EdicionController INSTANCE = new EdicionController();
  private final EdicionRepository edicionRepo = EdicionRepository.get();
  private final UsuarioRepository usuarioRepo = UsuarioRepository.get();
  private final RegistroFactory registroFactory = RegistroFactory.get();
  private final TipoRegistroRepository tipoRegistroRepo = TipoRegistroRepository.get();
  private final PatrocinioFactory patrocinioFactory = PatrocinioFactory.get();

  private EdicionController() {}

  public static EdicionController get() {
    return INSTANCE;
  }

  /**
   * Da de alta un nuevo tipo de registro en una edición existente.
   *
   * @param nombreEdicion El nombre de la edición donde se dará de alta el tipo de registro.
   * @param datosTipoRegistro Los datos del tipo de registro a dar de alta.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws TipoRegistroRepetidoException Si ya existe un tipo de registro con el mismo nombre en
   *     la edición.
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
    DTTipoRegistro datosTruncados =
        (!nombre.equals(datosTipoRegistro.nombre())
                || !descripcion.equals(datosTipoRegistro.descripcion()))
            ? new DTTipoRegistro(
                nombre, descripcion, datosTipoRegistro.costo(), datosTipoRegistro.cupo())
            : datosTipoRegistro;

    // Verificar si la edición existe
    var edicion =
        Tx.inTx(
            entityManager -> {
              return edicionRepo.buscarEdicion(entityManager, nombreEdicion);
            });

    if (edicion == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }
    if (Tx.inTx(
        entityManager -> {
          // Verificar si ya existe el tipo de registro
          var tipoRegistro = edicion.buscarTipoRegistro(datosTruncados.nombre());
          // Si no existe, dar de alta el tipo de registro
          return tipoRegistro != null;
        }))
      throw new TipoRegistroRepetidoException(
          "Ya existe el tipo de registro de nombre "
              + datosTruncados.nombre()
              + " en la edición "
              + nombreEdicion);

    Tx.inTx(
        entityManager -> {
          // Alta del tipo de registro
          TipoRegistroFactory.get().altaTipoRegistro(entityManager, edicion, datosTruncados);
          return null;
        });
  }

  /**
   * Consulta un tipo de registro en una edición existente.
   *
   * @param nombreEdicion El nombre de la edición donde se encuentra el tipo de registro.
   * @param nombreTipoRegistro El nombre del tipo de registro a consultar.
   * @return Los datos del tipo de registro consultado.
   * @throws EdicionInexistenteException Si la edición no existe.
   * @throws ValidationInputException Si el tipo de registro no existe en la edición.
   */
  @Override
  public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro)
      throws EdicionInexistenteException, ValidationInputException {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Objects.requireNonNull(nombreTipoRegistro, "nombreTipoRegistro requerido");
    // Verificar si la edición existe
    var edicion =
        Tx.inTx(
            entityManager -> {
              return edicionRepo.buscarEdicion(entityManager, nombreEdicion);
            });

    if (edicion == null) {
      // La edición no existe
      throw new EdicionInexistenteException("No existe la edición de nombre " + nombreEdicion);
    }

    var tipoRegistro =
        Tx.inTx(
            entityManager -> {
              return edicion.buscarTipoRegistro(nombreTipoRegistro);
            });

    if (tipoRegistro == null) {
      throw new ValidationInputException(
          "No existe el tipo de registro de nombre "
              + nombreTipoRegistro
              + " en la edición "
              + nombreEdicion);
    }

    DTTipoRegistro dtTipoRegistro = tipoRegistro.obtenerDTTipoRegistro();

    return dtTipoRegistro;
  }

  @Override
  public Set<DTTipoRegistro> mostrarTiposDeRegistro(String nombreEdicion) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return (edicion == null) ? Set.of() : edicion.listarDTTiposDeRegistro();
        });
  }

  @Override
  public Set<DTAsistente> mostrarAsistentes(String nombreEdicion) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          // refresco para evitar lazy issues
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return (edicion == null) ? Set.of() : edicion.obtenerDTAsistentes();
        });
  }

  @Override
  public boolean cupoDisponible(String nombreEdicion, String nombreTipoRegistro) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return edicion != null && edicion.cupoDisponible(nombreTipoRegistro);
        });
  }

  @Override
  public boolean asistenteNoRegistrado(String nombreEdicion, String nickname) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          return edicion != null && edicion.verificarNoRegistro(nickname);
        });
  }

  @Override
  public void altaRegistroEdicionEvento(
      String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha) {
    Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Tx.inTx(
        entityManager -> {
          var edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
          if (edicion == null) throw new IllegalArgumentException("Edición inexistente");
          var tipoRegistro = edicion.buscarTipoRegistro(nombreTipoRegistro);
          if (tipoRegistro == null) throw new IllegalArgumentException("Tipo de registro inexistente");
          var asistente = usuarioRepo.obtenerAsistente(entityManager, nickname);
          if (asistente == null) throw new IllegalArgumentException("Asistente inexistente");
          if (!edicion.cupoDisponible(nombreTipoRegistro)) throw new IllegalStateException("Sin cupo");
          if (!edicion.verificarNoRegistro(nickname)) throw new IllegalStateException("Ya registrado");
          registroFactory.altaRegistro(entityManager, edicion, asistente, tipoRegistro, tipoRegistro.obtenerDTTipoRegistro().costo(), fecha);
          tipoRegistro.decrementarCupo();
          entityManager.merge(tipoRegistro);
          return null;
        });
  }

  @Override
  public void altaPatrocinio(
      LocalDate fecha,
      String nombreEdicion,
      String nombreInstitucion,
      Float aporte,
      String nombreTipoRegistro,
      Integer cantGratuitos,
      String codigo,
      NivelPatrocinio nivelPatrocinio)
      throws ExistePatrocinioException,
          CostoRegistrosGratuitosException,
          CantidadCuposDisponiblesException,
          InstitucionNoExistenteException {

    // Chequear si existe la institución
    Objects.requireNonNull(nombreInstitucion, "nombreInstitucion requerido");
    if (Tx.inTx(
        entityManager -> {
          return InstitucionRepository.get().noExisteInstitucion(entityManager, nombreInstitucion);
        })) {
      throw new InstitucionNoExistenteException(
          "No existe la institución '" + nombreInstitucion + "'");
    }

    if (Tx.inTx(
        entityManager -> {
          return edicionRepo.existePatrocinio(entityManager, nombreEdicion, nombreInstitucion);
        })) {
      throw new ExistePatrocinioException(nombreInstitucion, nombreEdicion);
    }

    TipoRegistro tipoRegistro =
        Tx.inTx(
            emt -> {
              return tipoRegistroRepo.buscarTipoRegistro(emt, nombreTipoRegistro, nombreEdicion);
            });
    int cupos = tipoRegistro.getCupo();
    if (cupos < cantGratuitos) {
      throw new CantidadCuposDisponiblesException(cupos, nombreTipoRegistro);
    }

    System.out.println(
        ((tipoRegistro.getCosto() * cantGratuitos) / aporte)
            + "Datos: "
            + tipoRegistro.getCosto()
            + ", "
            + cantGratuitos
            + ", "
            + aporte);

    if (((tipoRegistro.getCosto() * cantGratuitos) / aporte) > 0.2f) {
      throw new CostoRegistrosGratuitosException();
    }

    Tx.inTx(
        entityManager -> {
          patrocinioFactory.crearPatrocinio(
              entityManager,
              fecha,
              nombreEdicion,
              nombreInstitucion,
              aporte,
              tipoRegistro,
              cantGratuitos,
              codigo,
              nivelPatrocinio);
          return null;
        });
  }

  @Override
  public void cancelarRegistroEdicionEvento() {
  }

  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEdicion)
      throws ValidationInputException {
    java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    var resultado = Tx.inTx(entityManager -> edicionRepo.obtenerDatosDetalladosEdicion(entityManager, nombreEdicion));
    if (resultado == null) {
      throw new EdicionInexistenteException("No existe la edición '" + nombreEdicion + "'");
    }
    return resultado;
  }

  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(
      String nombreEvento, String nombreEdicion) throws ValidationInputException {
    java.util.Objects.requireNonNull(nombreEvento, "nombreEvento requerido");
    java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    var resultado =
        Tx.inTx(entityManager -> edicionRepo.obtenerDatosDetalladosEdicion(entityManager, nombreEvento, nombreEdicion));
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
    Set<DTEdicion> resultado =
        Set.of(
            Tx.inTx(entityManager -> edicionRepo.obtenerEdicionesPorOrganizador(entityManager, nicknameOrganizador))
                .stream()
                .map(e -> e.toDTEdicion())
                .toArray(DTEdicion[]::new));
    return resultado;
  }

  @Override
  public void decisionSeleccion(String nombreEdicion, EstadoEdicion decision)
      throws ValidationInputException {
    boolean existeEd =
        Tx.inTx(
            entityManager -> {
              Edicion edicion = edicionRepo.buscarEdicion(entityManager, nombreEdicion);
              if (edicion != null) {
                edicion.setEstado(decision);
                entityManager.merge(edicion);
                return true;
              } else {
                return false;
              }
            });

    if (!existeEd) {
      throw new ValidationInputException("No existe la edición '" + nombreEdicion + "'");
    }
  }
}
