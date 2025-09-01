package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import datatypes.DTEventoAlta;
import exceptions.CategoriaRepetidaException;
import exceptions.CategoriasInvalidasException;
import exceptions.EventoRepetidoException;
import exceptions.NingunaCategoriaSeleccionadaException;
import exceptions.ValidationInputException;
import infra.JPA;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;

public class EventoControllerTest {
	static Factory factory = Factory.get();
	private static IEventoController eventoController;
		@BeforeAll
		public static void iniciar() {
			JPA.switchToTesting();
			eventoController = factory.getIEventoController();
		}
		
		@AfterEach
		public void clear() {
			JPA.switchToTesting();
		}
		
		@Test
		public void testObtenerCategorias() {
			Set<String> result = eventoController.obtenerCategorias();

		    System.out.println("Categorias existentes: " + result);
		    assertNotNull(result);
		}
		
		@Test
		public void testAltaCategoria() {
			assertThrows(CategoriaRepetidaException.class, ()-> {
				eventoController.altaCategoria("CategoriaTest1");
				eventoController.altaCategoria("CategoriaTest1");
			});
		}
		
		@Test
		public void testAltaEvento() {
			try {
				eventoController.altaCategoria("CategoriaTest1");

				eventoController.altaCategoria("CategoriaTest2");
				
			} catch (ValidationInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Set<String> cats = eventoController.obtenerCategorias();
		    assertNotNull(cats);
		    
		    System.out.println("Categorias existentes: " + cats);
		    
		    assertDoesNotThrow(()->{
		    	eventoController.altaEvento(new DTEventoAlta("EventoTest1", "Lallalal", new Date(0), "SIGLA1", cats));
		    });
		    
		    assertThrows(EventoRepetidoException.class, ()->{
		    	eventoController.altaEvento(new DTEventoAlta("EventoTest1", "", new Date(), "", Set.of("CategoriaTest1")));
		    });
		    
		    assertThrows(NingunaCategoriaSeleccionadaException.class, ()->{
		    	eventoController.altaEvento(new DTEventoAlta("EventoTest2", "", new Date(), "", Set.of()));
		    });
		    
		    assertThrows(CategoriasInvalidasException.class, ()->{
		    	eventoController.altaEvento(new DTEventoAlta("EventoTest2", "", new Date(), "", Set.of("CategoriaInexistente1", "CategoriaTest1")));
		    });
		}
		
		@Test
		public void testListarEventos() {
			assertDoesNotThrow(()->{
				eventoController.altaCategoria("CategoriaTestListar1");
				eventoController.altaEvento(new DTEventoAlta("EventoTestListar1", "Descripcion", new Date(0), "SIGLA3", Set.of("CategoriaTestListar1")));
			});
			var eventos = eventoController.listarEventos();
			assertNotNull(eventos);
		}
		
		@Test
		public void testMostrarEdiciones() {
			assertDoesNotThrow(()->{
				eventoController.altaCategoria("CategoriaTestMostrarEdiciones1");
				eventoController.altaEvento(new DTEventoAlta("EventoTestMostrarEdiciones1", "Descripcion", new Date(0), "SIGLA4", Set.of("CategoriaTestMostrarEdiciones1")));
			});
			
			
			IEdicionController edicionController = factory.getIEdicionController();
			
			//TO DO: cuando se implemente altaEdicion descomentar y probar funcionalidad de mostrarEdiciones
			//edicionController.altaEdicion("EventoTestMap1", "Edicion1", new Date(0), new Date(1), "Pais1", "Ciudad1");
			assertDoesNotThrow(()->{
				var eds = eventoController.mostrarEdiciones("EventoTestMostrarEdiciones1");
				assertNotNull(eds);
			});
		}
}
