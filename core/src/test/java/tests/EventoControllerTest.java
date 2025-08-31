package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Set;

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
import interfaces.IEventoController;

public class EventoControllerTest {
	
	private static IEventoController eventoController;
		@BeforeAll
		public static void iniciar() {
			JPA.switchToTesting();
			Factory factory = Factory.get();
			eventoController = factory.getIEventoController();
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
		
		
		
		
	

}
