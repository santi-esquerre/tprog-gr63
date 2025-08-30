package logica;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import dominio.Categoria;
import exceptions.EmptyInputException;
import exceptions.ValidationInputException;
import infra.Tx;
import interfaces.IEventoController;
import jakarta.persistence.EntityManager;
import repos.CategoriaRepository;
import repos.EventoRepository;

public final class EventoController implements IEventoController {
  private static final EventoController INSTANCE = new EventoController();
  private final CategoriaRepository catRepo = CategoriaRepository.get();
  private final EventoRepository eventoRepo = EventoRepository.get();
  private final EventoFactory eventoFactory = EventoFactory.get();

  private EventoController() {}
  public static EventoController get(){ return INSTANCE; }

  @Override
  public Set<String> obtenerCategorias() {
    return Tx.inTx(em -> catRepo.obtenerCategorias(em).stream()
        .map(Categoria::getNombre)
        .collect(Collectors.toCollection(LinkedHashSet::new)));
  }

  @Override
  public boolean altaEvento(DTEventoAlta dta) throws ValidationInputException {
    Objects.requireNonNull(dta, "DTEventoAlta requerido");
    if (dta.nombre() == null || dta.nombre().isBlank())
	  throw new EmptyInputException("Nombre");
    if (dta.descripcion() == null || dta.descripcion().isBlank())
	  throw new EmptyInputException("Descripcion");
    if (dta.fechaAlta() == null)
    	throw new EmptyInputException("Fecha de alta");
    if (dta.sigla() == null || dta.sigla().isBlank())
    	throw new EmptyInputException("Sigla");
    return Tx.inTx(em -> {
      var cats = mapCategorias(em, dta.categorias());
      return eventoFactory.crearEvento(em, dta.nombre(), dta.descripcion(), dta.fechaAlta(), dta.sigla(), cats);
    });
    
  }

  @Override
  public Set<DTEvento> listarEventos() {
    return Tx.inTx(eventoRepo::listarEventos);
  }

  @Override
  public Set<DTEdicion> mostrarEdiciones(String nombreEvento) {
    return Tx.inTx(em -> {
      var ev = eventoRepo.buscarEvento(em, nombreEvento);
      return (ev == null) ? Set.of() : ev.listarDTEdiciones();
    });
  }

  private Set<Categoria> mapCategorias(EntityManager em, Set<String> nombres) {
    if (nombres == null || nombres.isEmpty()) return Set.of();
    Set<Categoria> out = new LinkedHashSet<>();
    for (String n : nombres) {
      var c = catRepo.buscarPorNombre(em, n);
      if (c != null) out.add(c);
    }
    return out;
  }
}
