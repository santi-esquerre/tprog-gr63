package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTEventoAlta;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IRepository;

class EdicionControllerTest {
	
	private static IEdicionController edicionController;
	private static IEventoController eventoController;
	private static IRepository repository;

	@BeforeAll
	static void iniciar() {
		eventoController = Factory.get().getIEventoController();
		edicionController = Factory.get().getIEdicionController();
		repository = Factory.get().getIRepository();
	}
	
	
	@BeforeEach
	private void setUpBeforeClass()  {
		repository.switchToTesting();
	}
	
	@Test
	void testAltaTipoRegistro() {
		fail("Not yet implemented");
		assertDoesNotThrow(()-> {
			eventoController.altaCategoria("CategoriaTest1");
			eventoController.altaEvento(new DTEventoAlta("EventoTest1", "Descripcion Test 1", new Date(0), "CategoriaTest1", Set.of("CategoriaTest1")));
			// TO DO: cuando se implemente agregarEdicionAEvento del CU Alta Edicion a Evento
			//eventoController.agregarEdicionAEvento("EventoTest1", new datatypes.DTEdicion("EdicionTest1", new Date(), new Date(), 100, "Lugar Test 1"));
			//edicionController.altaTipoRegistro("EdicionTest1", new datatypes.DTTipoRegistro("TipoRegistroTest1", "Descripcion Tipo Registro Test 1", 50.0f));
		});
	}
	
	@Test
	void testConsultaTipoRegistro() {
		fail("Not yet implemented");
		assertDoesNotThrow(()-> {
			eventoController.altaCategoria("CategoriaTest1");
			eventoController.altaEvento(new DTEventoAlta("EventoTest1", "Descripcion Test 1", new Date(0), "CategoriaTest1", Set.of("CategoriaTest1")));
			// TO DO: cuando se implemente agregarEdicionAEvento del CU Alta Edicion a Evento
			//eventoController.agregarEdicionAEvento("EventoTest1", new datatypes.DTEdicion("EdicionTest1", new Date(), new Date(), 100, "Lugar Test 1"));
			//edicionController.altaTipoRegistro("EdicionTest1", new datatypes.DTTipoRegistro("TipoRegistroTest1", "Descripcion Tipo Registro Test 1", 50.0f));
			//var tr = edicionController.consultaTipoRegistro("EdicionTest1", "TipoRegistroTest1");
			//assertNotNull(tr);
		});
	}
	
	/*Incluye mostrarTiposDeRegistro, mostrarAsistentes, cupoDisponible, asistenteNoRegistrado, altaRegistroEdicionEvento
	 * */
	@Test
	void testOperacionesAsistentes() {
		fail("Not yet implemented");
		assertDoesNotThrow(()-> {
			eventoController.altaCategoria("CategoriaTest1");
			eventoController.altaEvento(new DTEventoAlta("EventoTest1", "Descripcion Test 1", new Date(0), "CategoriaTest1", Set.of("CategoriaTest1")));
			// TO DO: cuando se implemente agregarEdicionAEvento del CU Alta Edicion a Evento
			//eventoController.agregarEdicionAEvento("EventoTest1", new datatypes.DTEdicion("EdicionTest1", new Date(), new Date(), 100, "Lugar Test 1"));
			//edicionController.altaTipoRegistro("EdicionTest1", new datatypes.DTTipoRegistro("TipoRegistroTest1", "Descripcion Tipo Registro Test 1", 50.0f));
			//var trs = edicionController.mostrarTiposDeRegistro("EdicionTest1");
			//assertNotNull(trs);
		});
	}

}
