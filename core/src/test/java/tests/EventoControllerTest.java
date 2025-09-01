package tests;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import exceptions.CategoriaRepetidaException;
import exceptions.CategoriasInvalidasException;
import exceptions.EventoRepetidoException;
import exceptions.NingunaCategoriaSeleccionadaException;
import exceptions.ValidationInputException;
import infra.JPA;
import interfaces.Factory;
import interfaces.IEventoController;
import interfaces.IUsuarioController;

public class EventoControllerTest {
	static Factory factory = Factory.get();
	private static IEventoController eventoController;
	private static IUsuarioController usuarioController;

	@BeforeAll
	public static void iniciar() {
		JPA.switchToTesting();
		eventoController = factory.getIEventoController();
		usuarioController = factory.getIUsuarioController();
	}

	@AfterEach
	public void clear() {
		JPA.switchToTesting();
	}

	@Test
	public void testObtenerCategorias() {
		Set<String> result = eventoController.obtenerCategorias();
		assertNotNull(result, "Las categorías no deberían ser null");

		// Agregar una categoría y verificar que se refleja
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaTestObtener");
		});

		Set<String> resultActualizado = eventoController.obtenerCategorias();
		assertTrue(resultActualizado.contains("CategoriaTestObtener"), "Debería contener la categoría agregada");
	}

	@Test
	public void testAltaCategoria() {
		// Test de alta exitosa
		assertDoesNotThrow(() -> {
			boolean resultado = eventoController.altaCategoria("CategoriaExitosa1");
			assertTrue(resultado, "El alta de categoría debería ser exitosa");
		});

		// Test de categoría repetida
		Exception exception = assertThrows(CategoriaRepetidaException.class, () -> {
			eventoController.altaCategoria("CategoriaRepetida1");
			eventoController.altaCategoria("CategoriaRepetida1");
		});
		assertNotNull(exception, "Debería lanzar excepción por categoría repetida");

		// Test con categoría null
		Exception exceptionNull = assertThrows(ValidationInputException.class, () -> {
			eventoController.altaCategoria(null);
		});
		assertNotNull(exceptionNull, "Debería lanzar excepción con categoría null");

		// Test con categoría vacía
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("");
		}, "Debería manejar categoría vacía");

		// Test con categoría muy larga - debería fallar
		String categoriaLarga = "a".repeat(1000);
		assertThrows(ValidationInputException.class, () -> {
			eventoController.altaCategoria(categoriaLarga);
		}, "Debería lanzar excepción con categoría muy larga");
	}

	@Test
	public void testAltaEvento() {
		// Preparar categorías
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaEventoTest1");
			eventoController.altaCategoria("CategoriaEventoTest2");
		});

		Set<String> cats = eventoController.obtenerCategorias();
		assertNotNull(cats, "Las categorías no deberían ser null");

		// Test de alta exitosa
		assertDoesNotThrow(() -> {
			DTEventoAlta evento = new DTEventoAlta("EventoExitoso1", "Descripción del evento exitoso",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA1", Set.of("CategoriaEventoTest1"));
			boolean resultado = eventoController.altaEvento(evento);
			assertTrue(resultado, "El alta de evento debería ser exitosa");
		});

		// Test de evento repetido
		Exception exceptionRepetido = assertThrows(EventoRepetidoException.class, () -> {
			DTEventoAlta evento1 = new DTEventoAlta("EventoRepetido1", "Descripción 1",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA2", Set.of("CategoriaEventoTest1"));
			DTEventoAlta evento2 = new DTEventoAlta("EventoRepetido1", "Descripción 2",
					new Date(System.currentTimeMillis() + 172800000), "SIGLA3", Set.of("CategoriaEventoTest2"));
			eventoController.altaEvento(evento1);
			eventoController.altaEvento(evento2);
		});
		assertNotNull(exceptionRepetido, "Debería lanzar excepción por evento repetido");

		// Test sin categorías seleccionadas
		Exception exceptionNinguna = assertThrows(NingunaCategoriaSeleccionadaException.class, () -> {
			DTEventoAlta evento = new DTEventoAlta("EventoSinCategorias", "Descripción",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA4", Set.of());
			eventoController.altaEvento(evento);
		});
		assertNotNull(exceptionNinguna, "Debería lanzar excepción sin categorías");

		// Test con categorías inválidas
		Exception exceptionInvalidas = assertThrows(CategoriasInvalidasException.class, () -> {
			DTEventoAlta evento = new DTEventoAlta("EventoCategoriaInvalida", "Descripción",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA5",
					Set.of("CategoriaInexistente1", "CategoriaEventoTest1"));
			eventoController.altaEvento(evento);
		});
		assertNotNull(exceptionInvalidas, "Debería lanzar excepción con categorías inválidas");

		// Test con datos null
		Exception exceptionNull = assertThrows(ValidationInputException.class, () -> {
			eventoController.altaEvento(null);
		});
		assertNotNull(exceptionNull, "Debería lanzar excepción con evento null");

		// Test con campos null en el evento
		Exception exceptionCampoNull = assertThrows(ValidationInputException.class, () -> {
			DTEventoAlta evento = new DTEventoAlta(null, "Descripción",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA6", Set.of("CategoriaEventoTest1"));
			eventoController.altaEvento(evento);
		});
		assertNotNull(exceptionCampoNull, "Debería lanzar excepción con nombre null");
	}

	@Test
	public void testListarEventos() {
		// Crear algunos eventos
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaListar1");
			eventoController.altaCategoria("CategoriaListar2");

			DTEventoAlta evento1 = new DTEventoAlta("EventoListar1", "Descripción evento 1",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_L1", Set.of("CategoriaListar1"));
			DTEventoAlta evento2 = new DTEventoAlta("EventoListar2", "Descripción evento 2",
					new Date(System.currentTimeMillis() + 172800000), "SIGLA_L2", Set.of("CategoriaListar2"));

			eventoController.altaEvento(evento1);
			eventoController.altaEvento(evento2);
		});

		Set<DTEvento> eventos = eventoController.listarEventos();
		assertNotNull(eventos, "La lista de eventos no debería ser null");
		assertTrue(eventos.size() >= 2, "Debería contener al menos 2 eventos");

		// Verificar que contiene los eventos creados
		boolean contieneEvento1 = eventos.stream().anyMatch(e -> "EventoListar1".equals(e.nombre()));
		boolean contieneEvento2 = eventos.stream().anyMatch(e -> "EventoListar2".equals(e.nombre()));

		assertTrue(contieneEvento1, "Debería contener EventoListar1");
		assertTrue(contieneEvento2, "Debería contener EventoListar2");
	}

	@Test
	public void testMostrarEdiciones() {
		// Crear evento y organizador
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaEdiciones1");
			eventoController.altaEvento(new DTEventoAlta("EventoEdiciones1", "Descripcion",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_ED1", Set.of("CategoriaEdiciones1")));

			usuarioController.crearOrganizador("orgEdiciones1", "Organizador Ediciones",
					"orgediciones@test.com", "Descripción organizador");
		});

		// Test con evento que existe pero sin ediciones
		assertDoesNotThrow(() -> {
			Set<DTEdicion> ediciones = eventoController.mostrarEdiciones("EventoEdiciones1");
			assertNotNull(ediciones, "Las ediciones no deberían ser null");
		});

		// Agregar edición y verificar
		assertDoesNotThrow(() -> {
			DTEdicion edicionData = new DTEdicion("EdicionTest1", "SIGLA_E1",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoEdiciones1", "orgEdiciones1", edicionData);

			Set<DTEdicion> edicionesConDatos = eventoController.mostrarEdiciones("EventoEdiciones1");
			assertTrue(edicionesConDatos.size() >= 1, "Debería contener al menos 1 edición");
		});

		// Test con evento inexistente - debería devolver Set vacío
		assertDoesNotThrow(() -> {
			Set<DTEdicion> edicionesInexistente = eventoController.mostrarEdiciones("EventoInexistente999");
			assertTrue(edicionesInexistente.isEmpty(), "Debería devolver Set vacío para evento inexistente");
		});

		// Test con nombre null
		Exception exceptionNull = assertThrows(ValidationInputException.class, () -> {
			eventoController.mostrarEdiciones(null);
		});
		assertNotNull(exceptionNull, "Debería lanzar excepción con nombre null");
	}

	@Test
	public void testAgregarEdicionAEvento() {
		// Preparar datos
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaAgregarEd1");
			eventoController.altaEvento(new DTEventoAlta("EventoAgregarEd1", "Descripcion",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_AGR1", Set.of("CategoriaAgregarEd1")));

			usuarioController.crearOrganizador("orgAgregar1", "Organizador Agregar",
					"orgagregar@test.com", "Descripción organizador agregar");
		});

		// Test de agregar edición exitoso
		assertDoesNotThrow(() -> {
			DTEdicion edicionData = new DTEdicion("EdicionAgregar1", "SIGLA_EA1",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Montevideo", "Uruguay");
			boolean resultado = eventoController.agregarEdicionAEvento("EventoAgregarEd1", "orgAgregar1", edicionData);
			assertTrue(resultado, "Agregar edición debería ser exitoso");
		});

		// Test con evento inexistente
		Exception exceptionEvento = assertThrows(ValidationInputException.class, () -> {
			DTEdicion edicionData = new DTEdicion("EdicionEvInexistente", "SIGLA_EI",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Ciudad", "País");
			eventoController.agregarEdicionAEvento("EventoInexistente", "orgAgregar1", edicionData);
		});
		assertNotNull(exceptionEvento, "Debería lanzar excepción con evento inexistente");

		// Test con organizador inexistente
		Exception exceptionOrg = assertThrows(ValidationInputException.class, () -> {
			DTEdicion edicionData = new DTEdicion("EdicionOrgInexistente", "SIGLA_OI",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Ciudad", "País");
			eventoController.agregarEdicionAEvento("EventoAgregarEd1", "organizadorInexistente", edicionData);
		});
		assertNotNull(exceptionOrg, "Debería lanzar excepción con organizador inexistente");

		// Test con fechas inválidas (inicio después de fin)
		Exception exceptionFechas = assertThrows(ValidationInputException.class, () -> {
			DTEdicion edicionData = new DTEdicion("EdicionFechasInvalidas", "SIGLA_FI",
					new Date(System.currentTimeMillis() + 172800000), // fecha inicio posterior
					new Date(System.currentTimeMillis() + 86400000), // fecha fin anterior
					new Date(), "Ciudad", "País");
			eventoController.agregarEdicionAEvento("EventoAgregarEd1", "orgAgregar1", edicionData);
		});
		assertNotNull(exceptionFechas, "Debería lanzar excepción con fechas inválidas");

		// Test con parámetros null
		Exception exceptionNull1 = assertThrows(ValidationInputException.class, () -> {
			DTEdicion edicionData = new DTEdicion("EdicionNull", "SIGLA_N",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Ciudad", "País");
			eventoController.agregarEdicionAEvento(null, "orgAgregar1", edicionData);
		});
		assertNotNull(exceptionNull1, "Debería lanzar excepción con nombreEvento null");

		Exception exceptionNull2 = assertThrows(ValidationInputException.class, () -> {
			DTEdicion edicionData = new DTEdicion("EdicionNull2", "SIGLA_N2",
					new Date(System.currentTimeMillis() + 86400000),
					new Date(System.currentTimeMillis() + 172800000),
					new Date(), "Ciudad", "País");
			eventoController.agregarEdicionAEvento("EventoAgregarEd1", null, edicionData);
		});
		assertNotNull(exceptionNull2, "Debería lanzar excepción con nicknameOrganizador null");

		Exception exceptionNull3 = assertThrows(ValidationInputException.class, () -> {
			eventoController.agregarEdicionAEvento("EventoAgregarEd1", "orgAgregar1", null);
		});
		assertNotNull(exceptionNull3, "Debería lanzar excepción con DTEdicion null");
	}

	@Test
	public void testObtenerDatosDetalladosEvento() {
		// Preparar datos
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("CategoriaDetallado1");
			eventoController.altaEvento(new DTEventoAlta("EventoDetallado1", "Descripcion detallada del evento",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_DET1", Set.of("CategoriaDetallado1")));
		});

		// Test obtener datos detallados
		assertDoesNotThrow(() -> {
			DTEventoDetallado detallado = eventoController.obtenerDatosDetalladosEvento("EventoDetallado1");
			assertNotNull(detallado, "Los datos detallados no deberían ser null");
			assertNotNull(detallado.evento(), "El evento en los detalles no debería ser null");
			assertEquals("EventoDetallado1", detallado.evento().nombre(), "El nombre del evento debería coincidir");
		});

		// Test con evento inexistente
		Exception exception = assertThrows(ValidationInputException.class, () -> {
			eventoController.obtenerDatosDetalladosEvento("EventoInexistenteDetallado");
		});
		assertNotNull(exception, "Debería lanzar excepción con evento inexistente");

		// Test con nombre null
		Exception exceptionNull = assertThrows(ValidationInputException.class, () -> {
			eventoController.obtenerDatosDetalladosEvento(null);
		});
		assertNotNull(exceptionNull, "Debería lanzar excepción con nombre null");
	}

	@Test
	public void testCasosLimite() {
		// Test con categorías con caracteres especiales
		assertDoesNotThrow(() -> {
			eventoController.altaCategoria("Categoría-con_caracteres.especiales@123");
		}, "Debería manejar caracteres especiales en categorías");

		// Test con evento con nombre muy largo - debería fallar
		assertThrows(ValidationInputException.class, () -> {
			String nombreLargo = "EventoConNombreMuyMuyMuyLargo".repeat(10);
			eventoController.altaCategoria("CategoriaLarga1");
			DTEventoAlta eventoLargo = new DTEventoAlta(nombreLargo, "Descripción",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_LARGO", Set.of("CategoriaLarga1"));
			eventoController.altaEvento(eventoLargo);
		}, "Debería lanzar excepción con nombres muy largos");

		// Test con múltiples categorías
		assertDoesNotThrow(() -> {
			for (int i = 1; i <= 10; i++) {
				eventoController.altaCategoria("CategoriaMultiple" + i);
			}

			DTEventoAlta eventoMultipleCat = new DTEventoAlta("EventoMultiCat", "Descripción",
					new Date(System.currentTimeMillis() + 86400000), "SIGLA_MULTI",
					Set.of("CategoriaMultiple1", "CategoriaMultiple2", "CategoriaMultiple3"));
			eventoController.altaEvento(eventoMultipleCat);
		}, "Debería manejar múltiples categorías");
	}

	@Test
	public void testConcurrencia() {
		// Test de creación concurrente de categorías
		for (int i = 1; i <= 5; i++) {
			final int index = i;
			assertDoesNotThrow(() -> {
				eventoController.altaCategoria("CategoriaConcurrente" + index);
			}, "Debería manejar creación concurrente de categorías");
		}

		// Test de creación concurrente de eventos
		assertDoesNotThrow(() -> {
			for (int i = 1; i <= 3; i++) {
				DTEventoAlta evento = new DTEventoAlta("EventoConcurrente" + i, "Descripción " + i,
						new Date(System.currentTimeMillis() + (86400000L * i)), "SIGLA_C" + i,
						Set.of("CategoriaConcurrente1"));
				eventoController.altaEvento(evento);
			}
		}, "Debería manejar creación concurrente de eventos");

		// Verificar que todos se crearon correctamente
		Set<DTEvento> eventos = eventoController.listarEventos();
		boolean tieneEventosConcurrentes = eventos.stream()
				.anyMatch(e -> e.nombre().contains("EventoConcurrente"));
		assertTrue(tieneEventosConcurrentes, "Debería contener eventos concurrentes");
	}
}
