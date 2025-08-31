package logica;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
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

  private EventoController() {}
  public static EventoController get(){ return INSTANCE; }
  @Override
  public boolean altaCategoria(String nombre) throws ValidationInputException {
	  Objects.requireNonNull(nombre, "Nombre de categoria requerido");
	  CategoriaFactory categoriaFactory = CategoriaFactory.get();
	  // CategoriaRepository categoriaRepository = CategoriaRepository.get();
	  // Validaciones de entrada
	  if (this.obtenerCategorias().contains(nombre))
		  throw new CategoriaRepetidaException("Ya existe la categoria " + nombre + "");
	  //TO DO: Validar entrada vacía o nula
	  return Tx.inTx(em -> categoriaFactory.crearCategoria(em, nombre));
  }
  /**
   * Obtiene los nombres de todas las categorías existentes.
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
   * @param dta Datos del evento a crear.
   * @return true si el evento fue creado exitosamente, false si ya existe un evento con el mismo nombre.
   * @throws ValidationInputException si los datos de entrada no son válidos.
   */
  @Override
  public boolean altaEvento(DTEventoAlta dta) throws ValidationInputException {
	 EventoFactory eventoFactory = EventoFactory.get();
	  // Validaciones de entrada
    Objects.requireNonNull(dta, "DTEventoAlta requerido");
    if (dta.categorias() == null || dta.categorias().isEmpty())
    	throw new NingunaCategoriaSeleccionadaException("Debe seleccionar al menos una categoria");
    if(this.obtenerCategorias().containsAll(dta.categorias()) == false)
        throw new CategoriasInvalidasException("Alguna de las categorias seleccionadas no es válida");
    //Transacción de alta de evento
    
    boolean res = Tx.inTx(em -> {
      var cats = mapCategorias(em, dta.categorias());
      return eventoFactory.crearEvento(em, dta.nombre(), dta.descripcion(), dta.fechaAlta(), dta.sigla(), cats);
    });
    if (!res) throw new EventoRepetidoException("Ya existe el evento de nombre " + dta.nombre() + "");
    return res;
    
  }

  @Override
  public Set<DTEvento> listarEventos() {
	EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(eventoRepo::listarEventos);
  }

  @Override
  public Set<DTEdicion> mostrarEdiciones(String nombreEvento) {
	  EventoRepository eventoRepo = EventoRepository.get();
    return Tx.inTx(em -> {
      var ev = eventoRepo.buscarEvento(em, nombreEvento);
      return (ev == null) ? Set.of() : ev.listarDTEdiciones();
    });
  }

  private Set<Categoria> mapCategorias(EntityManager em, Set<String> nombres) {
    if (nombres == null || nombres.isEmpty()) return Set.of();
    Set<Categoria> out = new LinkedHashSet<>();
    CategoriaRepository catRepo = CategoriaRepository.get();
    for (String n : nombres) {
      var c = catRepo.buscarPorNombre(em, n);
      if (c != null) out.add(c);
    }
    return out;
  }
}
