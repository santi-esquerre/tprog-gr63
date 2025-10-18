package logica;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Hibernate;

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
      var edicion = edicionRepo.buscarEdicion(em, nombreEdicion);
      return (edicion == null) ? Set.of() : edicion.listarDTTiposDeRegistro();
    });
  }

  @Override
  public Set<DTAsistente> mostrarAsistentes(String nombreEdicion) {
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(em -> {
      // refresco para evitar lazy issues
      var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
      return (ed == null) ? Set.of() : ed.obtenerDTAsistentes();
    });
  }

  @Override
  public boolean cupoDisponible(String nombreEdicion, String nombreTipoRegistro) {
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
      return ed != null && ed.cupoDisponible(nombreTipoRegistro);
    });
  }

  @Override
  public boolean asistenteNoRegistrado(String nombreEdicion, String nickname) {
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    return Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
      return ed != null && ed.verificarNoRegistro(nickname);
    });
  }

  @Override
  public void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha) {
	Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
    Tx.inTx(em -> {
      var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
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
      return null;
    });
  }
  
  @Override
  public void altaRegistroEdicionEvento(String nombreEdicion, String nombreTipoRegistro, String nickname, Date fecha, String codigo) throws ValidationInputException, IllegalArgumentException {
	  if (verificarCodigoPatrocinio(nombreEdicion, nombreTipoRegistro, nickname, codigo)) {
		  Tx.inTx(em -> {
			  var ed = edicionRepo.buscarEdicion(em, nombreEdicion);
			  var tr = ed.buscarTipoRegistro(nombreTipoRegistro);
			  var asistente = usuarioRepo.obtenerAsistente(em, nickname);
			  if (!ed.cupoDisponible(nombreTipoRegistro))
				  throw new IllegalStateException("No hay cupo disponible para el tipo de registro '" + nombreTipoRegistro + "' en la edición '" + nombreEdicion + "'");
			  if (!ed.verificarNoRegistro(nickname))
				  throw new IllegalStateException("El asistente '" + nickname + "' ya está registrado en la edición '" + nombreEdicion + "'");
			  var registro = registroFactory.altaRegistro(em, ed, asistente, tr, 0.0f, fecha);
			  var institucion = asistente.getInstitucion();
			  var patrocinio = edicionRepo.obtenerPatrocinio(em, nombreEdicion, institucion.getNombre());
			  var otorga = patrocinio.getOtorgan();
			  tr.decrementarCupo();
			  otorga.decrementarCantidad();
			  patrocinio.agregarRegistro(registro);
			  em.merge(tr);
			  em.merge(otorga);
			  em.merge(patrocinio);
			  em.merge(registro);
			  return null;
		  });
	  }
	  else {
		  throw new ValidationInputException("Código de patrocinio inválido");
	  }
  }

  @Override
  public void altaPatrocinio(LocalDate fecha, String nombreEdicion, String nombreInstitucion, Float aporte,
      String nombreTipoRegistro, Integer cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio)
      throws ExistePatrocinioException, CostoRegistrosGratuitosException, CantidadCuposDisponiblesException, InstitucionNoExistenteException {
	  
	  //Chequear si existe la institución
	  Objects.requireNonNull(nombreInstitucion, "nombreInstitucion requerido");
	  	if (Tx.inTx(em -> {return InstitucionRepository.get().noExisteInstitucion(em,nombreInstitucion);})) {
	  throw new InstitucionNoExistenteException("No existe la institución '" + nombreInstitucion + "'");
	}

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
    
    System.out.println(((tr.getCosto() * cantGratuitos) / aporte) + "Datos: " + tr.getCosto() + ", " + cantGratuitos + ", " + aporte);

    if (((tr.getCosto() * cantGratuitos) / aporte) > 0.2f) {
      throw new CostoRegistrosGratuitosException();
    }

    Tx.inTx(em -> {
      patrocinioFactory.crearPatrocinio(em, fecha, nombreEdicion, nombreInstitucion, aporte, tr, cantGratuitos, codigo,
          nivelPatrocinio);
      return null;
    });
  }
  
  @Override
  public boolean verificarCodigoPatrocinio(String nombreEdicion, String nombreTipoRegistro, String nickname, String codigo) throws ValidationInputException {
	  System.out.println("Verificando código de patrocinio...");
	  var asistente = Tx.inTx(em -> {  
			  var asistenteARetornar =  usuarioRepo.obtenerAsistente(em, nickname);
			  if (asistenteARetornar != null) {
				  Hibernate.initialize(asistenteARetornar.getInstitucion());
			  }
			  return asistenteARetornar; });
	  if (asistente == null) {
		  throw new ValidationInputException("No existe el asistente '" + nickname + "'");
	  }
	  var institucion = asistente.getInstitucion();
	  if (institucion == null) {
		  throw new ValidationInputException("El asistente '" + nickname + "' no pertenece a ninguna institución");
	  }
	  var patrocinio = Tx.inTx(em -> {  
			  var patrocinioARetornar = edicionRepo.obtenerPatrocinio(em, nombreEdicion, institucion.getNombre()); 
			  if (patrocinioARetornar != null) {
				  Hibernate.initialize(patrocinioARetornar.getOtorgan());
				  Hibernate.initialize(patrocinioARetornar.getOtorgan().getTipoRegistro());
			  }
			  return patrocinioARetornar;
	  });
	  if (patrocinio == null) {
		  throw new ValidationInputException("No existe un patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "'");
	  }
	  var otorga = patrocinio.getOtorgan();
	  if (otorga.getCantidad() <= 0) {
		  throw new ValidationInputException("El patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "' no tiene registros gratuitos disponibles");
	  }
	  var tr = otorga.getTipoRegistro();
	  if (!tr.getNombre().equals(nombreTipoRegistro)) {
		  throw new ValidationInputException("El patrocinio de la institución '" + institucion.getNombre() + "' para la edición '" + nombreEdicion + "' no incluye el tipo de registro '" + nombreTipoRegistro + "'");
	  }
	  return patrocinio.getCodigo().equals(codigo);	  
	  
  }

  @Override
  public void cancelarRegistroEdicionEvento() {
    edicionRecordada = null;
  }
  
  @Override
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(String nombreEdicion) throws ValidationInputException {
	java.util.Objects.requireNonNull(nombreEdicion, "nombreEdicion requerido");
	var resultado = Tx.inTx(em -> edicionRepo.obtenerDatosDetalladosEdicion(em, nombreEdicion));
	if (resultado == null) {
	  throw new EdicionInexistenteException("No existe la edición '" + nombreEdicion + "'");
	}
	return resultado;
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
  
  @Override
  public void decisionSeleccion(String nombreEdicion, EstadoEdicion decision) throws ValidationInputException {
	  boolean existeEd = Tx.inTx(em -> {
		  Edicion ed = edicionRepo.buscarEdicion(em, nombreEdicion);
		  if (ed != null) {
			  ed.setEstado(decision);
			  em.merge(ed);
			  return true;
		  }
		  else {
			  return false;
		  }
	  });
	  
	  if (!existeEd)
	  {
		  throw new ValidationInputException(
	              "No existe la edición '" + nombreEdicion + "'");
	  }
  }
  
}
