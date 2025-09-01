package logica;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import dominio.Categoria;
import exceptions.CategoriaRepetidaException;
import exceptions.CategoriasInvalidasException;
import exceptions.EventoRepetidoException;
import exceptions.NingunaCategoriaSeleccionadaException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEventoController;
import jakarta.persistence.EntityManager;
import repos.CategoriaRepository;
import repos.EventoRepository;

public final class EventoController implements IEventoController {
  private static final EventoController INSTANCE = new EventoController();

  private EventoController() {
  }

  public static EventoController get() {
    return INSTANCE;
  }

  @Override
  public boolean altaCategoria(String nombre) throws ValidationInputException {
    if (nombre == null)
      throw new ValidationInputException("Nombre de categoria requerido");
    if (nombre.length() > 120)
      throw new ValidationInputException("Nombre de categoria no puede exceder 120 caracteres");
    CategoriaFactory categoriaFactory = CategoriaFactory.get();
    // CategoriaRepository categoriaRepository = CategoriaRepository.get();
    // Validaciones de entrada
    if (this.obtenerCategorias().contains(nombre))
      throw new CategoriaRepetidaException("Ya existe la categoria " + nombre + "");
    // TO DO: Validar entrada vacía o nula
    return Tx.inTx(em -> categoriaFactory.crearCategoria(em, nombre));
  }

  /**
   * Obtiene los nombres de todas las categorías existentes.
   * 
   * @return Set con los nombres de las categorías.
   */
  @Override
  public Set<String> obtenerCategorias() {
    CategoriaRepository catRepo = CategoriaRepository.get();
    return Tx.inTx(em -> catRepo.obtenerCategorias(em).stream()
        .map(Categoria::getNombre)
        .collect(Collectors.toCollection(LinkedHashSet::new)));
  }

  /**
   * Crea un nuevo evento con los datos proporcionados.
   * 
   * @param dta Datos del evento a crear.
   * @return true si el evento fue creado exitosamente, false si ya existe un
   *         evento con el mismo nombre.
   * @throws ValidationInputException si los datos de entrada no son válidos.
   */
  @Override
  public boolean altaEvento(DTEventoAlta dta) throws ValidationInputException {
    EventoFactory eventoFactory = EventoFactory.get();
    // Validaciones de entrada
    if (dta == null)
      throw new ValidationInputException("DTEventoAlta requerido");
    if (dta.nombre() == null)
      throw new ValidationInputException("Nombre del evento requerido");
    if (dta.nombre() != null && dta.nombre().length() > 120)
      throw new ValidationInputException("Nombre del evento no puede exceder 120 caracteres");
    if (dta.categorias() == null || dta.categorias().isEmpty())
      throw new NingunaCategoriaSeleccionadaException("Debe seleccionar al menos una categoria");
    if (this.obtenerCategorias().containsAll(dta.categorias()) == false)
      throw new CategoriasInvalidasException("Alguna de las categorias seleccionadas no es válida");
    // Transacción de alta de evento

    boolean res = Tx.inTx(em -> {
      var cats = mapCategorias(em, dta.categorias());
      return eventoFactory.crearEvento(em, dta.nombre(), dta.descripcion(), dta.fechaAlta(), dta.sigla(), cats);
    });
    if (!res)
      throw new EventoRepetidoException("Ya existe el evento de nombre " + dta.nombre() + "");
    return res;

  }

  @Override
  public Set<DTEvento> listarEventos() {
    EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(eventoRepo::listarEventos);
  }

  @Override
  public Set<DTEdicion> mostrarEdiciones(String nombreEvento) throws ValidationInputException {
    if (nombreEvento == null)
      throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(em -> {
      var ev = eventoRepo.buscarEvento(em, nombreEvento);
      return (ev == null) ? Set.of() : ev.listarDTEdiciones();
    });
  }

  @Override
  public DTEventoDetallado obtenerDatosDetalladosEvento(String nombreEvento) throws ValidationInputException {
    if (nombreEvento == null)
      throw new ValidationInputException("nombreEvento requerido");
    EventoRepository eventoRepo = EventoRepository.get();
    DTEventoDetallado resultado = Tx.inTx(em -> eventoRepo.obtenerDatosDetalladosEvento(em, nombreEvento));
    if (resultado == null)
      throw new ValidationInputException("Evento no encontrado");
    return resultado;
  }

  private Set<Categoria> mapCategorias(EntityManager em, Set<String> nombres) {
    if (nombres == null || nombres.isEmpty())
      return Set.of();
    Set<Categoria> out = new LinkedHashSet<>();
    CategoriaRepository catRepo = CategoriaRepository.get();
    for (String n : nombres) {
      var c = catRepo.buscarPorNombre(em, n);
      if (c != null)
        out.add(c);
    }
    return out;
  }

  @Override
  public boolean agregarEdicionAEvento(String nombreEvento,
      String nicknameOrganizador,
      DTEdicion dte) throws ValidationInputException {
    if (nombreEvento == null)
      throw new ValidationInputException("nombreEvento requerido");
    if (nicknameOrganizador == null)
      throw new ValidationInputException("nicknameOrganizador requerido");
    if (dte == null)
      throw new ValidationInputException("DTEdicion requerido");

    // regla mínima de fechas (coincide con el sentido del CU)
    if (dte.fechaInicio().after(dte.fechaFin()))
      throw new ValidationInputException("fechaInicio no puede ser posterior a fechaFin");

    var edicionFactory = EdicionFactory.get();
    var eventoRepo = repos.EventoRepository.get();
    var usuarioRepo = repos.UsuarioRepository.get();

    try {
      return Tx.inTx(em -> {
        var ev = eventoRepo.buscarEvento(em, nombreEvento);
        if (ev == null)
          throw new RuntimeException("Evento inexistente: " + nombreEvento);

        var org = usuarioRepo.buscarOrganizadorPorNickname(em, nicknameOrganizador);
        if (org == null)
          throw new RuntimeException("Organizador inexistente: " + nicknameOrganizador);

        boolean ok = edicionFactory.crearEdicion(em, ev, org, dte);
        if (!ok)
          throw new RuntimeException(
              "Ya existe la edición '" + dte.nombre() + "' para el evento '" + nombreEvento + "'");

        return true;
      });
    } catch (RuntimeException e) {
      throw new ValidationInputException(e.getMessage());
    }
  }

}
