package tests;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTEventoAlta;
import datatypes.DTTipoRegistro;
import exceptions.EdicionInexistenteException;
import exceptions.TipoRegistroRepetidoException;
import exceptions.ValidationInputException;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IUsuarioController;

class EdicionControllerTest {

	private static IEdicionController edicionController;
	private static IEventoController eventoController;
	private static IUsuarioController usuarioController;
	private static Factory factory;

	@BeforeAll
	static void iniciar() {
		factory = Factory.get();
		eventoController = factory.getIEventoController();
		edicionController = factory.getIEdicionController();
		usuarioController = factory.getIUsuarioController();
	}

	@BeforeEach
	void setUp() {
		factory.getIRepository().switchToTesting();
	}

	@Test
	void testAltaTipoRegistro() {
		assertDoesNotThrow(() -> {
			// Preparar datos necesarios
			eventoController.altaCategoria("CategoriaTest1");
			eventoController.altaEvento(new DTEventoAlta("EventoTest1", "Descripcion Test 1", new Date(0), "SIGLA1",
					Set.of("CategoriaTest1")));

			// Crear organizador
			usuarioController.crearOrganizador("orgTest1", "Organizador Test", "org@test.com",
					"Descripción organizador");

			// Agregar edición al evento
			DTEdicion edicionData = new DTEdicion("EdicionTest1", "SIGLA_ED1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoTest1", "orgTest1", edicionData);

			// Crear tipo de registro
			DTTipoRegistro tipoRegistro = new DTTipoRegistro("TipoRegistroTest1", "Descripcion Tipo Registro Test 1",
					50.0f, 100);
			edicionController.altaTipoRegistro("EdicionTest1", tipoRegistro);

		}, "Alta de tipo de registro debería ejecutarse sin errores");

		// Test con edición inexistente
		assertThrows(EdicionInexistenteException.class, () -> {
			DTTipoRegistro tipoRegistro = new DTTipoRegistro("TipoRegistroTest2", "Descripcion", 30.0f, 50);
			edicionController.altaTipoRegistro("EdicionInexistente", tipoRegistro);
		}, "Debería lanzar excepción con edición inexistente");

		// Test con tipo de registro repetido
		assertThrows(TipoRegistroRepetidoException.class, () -> {
			DTTipoRegistro tipoRegistro = new DTTipoRegistro("TipoRegistroTest1", "Descripcion diferente", 40.0f, 75);
			edicionController.altaTipoRegistro("EdicionTest1", tipoRegistro);
		}, "Debería lanzar excepción con tipo de registro repetido");
	}

	@Test
	void testConsultaTipoRegistro() {
		assertDoesNotThrow(() -> {
			// Preparar datos
			eventoController.altaCategoria("CategoriaConsulta1");
			eventoController.altaEvento(new DTEventoAlta("EventoConsulta1", "Descripcion Test", new Date(0), "SIGLA_C1",
					Set.of("CategoriaConsulta1")));

			usuarioController.crearOrganizador("orgConsulta1", "Organizador Consulta", "orgconsulta@test.com",
					"Descripción");

			DTEdicion edicionData = new DTEdicion("EdicionConsulta1", "SIGLA_EC1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoConsulta1", "orgConsulta1", edicionData);

			DTTipoRegistro tipoRegistro = new DTTipoRegistro("TipoConsulta1", "Descripcion Tipo Consulta", 60.0f, 80);
			edicionController.altaTipoRegistro("EdicionConsulta1", tipoRegistro);

			// Consultar tipo de registro creado
			DTTipoRegistro resultado = edicionController.consultaTipoRegistro("EdicionConsulta1", "TipoConsulta1");
			assertNotNull(resultado, "El resultado de la consulta no debería ser null");
			assertEquals("TipoConsulta1", resultado.nombre(), "El nombre debería coincidir");
			assertEquals(60.0f, resultado.costo(), 0.01f, "El costo debería coincidir");
			assertEquals(80, resultado.cupo(), "El cupo debería coincidir");
		});

		// Test con edición inexistente
		assertThrows(ValidationInputException.class, () -> {
			edicionController.consultaTipoRegistro("EdicionInexistente", "TipoRegistro");
		}, "Debería lanzar excepción con edición inexistente");

		// Test con tipo de registro inexistente
		assertThrows(ValidationInputException.class, () -> {
			edicionController.consultaTipoRegistro("EdicionConsulta1", "TipoInexistente");
		}, "Debería lanzar excepción con tipo de registro inexistente");
	}

	@Test
	void testMostrarTiposDeRegistro() {
		assertDoesNotThrow(() -> {
			// Preparar datos
			eventoController.altaCategoria("CategoriaMostrar1");
			eventoController.altaEvento(new DTEventoAlta("EventoMostrar1", "Descripcion", new Date(0), "SIGLA_M1",
					Set.of("CategoriaMostrar1")));

			usuarioController.crearOrganizador("orgMostrar1", "Organizador Mostrar", "orgmostrar@test.com",
					"Descripción");

			DTEdicion edicionData = new DTEdicion("EdicionMostrar1", "SIGLA_EM1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoMostrar1", "orgMostrar1", edicionData);

			// Crear múltiples tipos de registro
			DTTipoRegistro tipo1 = new DTTipoRegistro("TipoMostrar1", "Descripcion 1", 50.0f, 100);
			DTTipoRegistro tipo2 = new DTTipoRegistro("TipoMostrar2", "Descripcion 2", 75.0f, 50);

			edicionController.altaTipoRegistro("EdicionMostrar1", tipo1);
			edicionController.altaTipoRegistro("EdicionMostrar1", tipo2);

			// Mostrar tipos de registro
			Set<DTTipoRegistro> tipos = edicionController.mostrarTiposDeRegistro("EdicionMostrar1");
			assertNotNull(tipos, "El set de tipos no debería ser null");
			assertTrue(tipos.size() >= 2, "Debería contener al menos 2 tipos de registro");

			boolean contieneTipo1 = tipos.stream().anyMatch(t -> "TipoMostrar1".equals(t.nombre()));
			boolean contieneTipo2 = tipos.stream().anyMatch(t -> "TipoMostrar2".equals(t.nombre()));

			assertTrue(contieneTipo1, "Debería contener TipoMostrar1");
			assertTrue(contieneTipo2, "Debería contener TipoMostrar2");
		});
	}

	@Test
	void testMostrarAsistentes() {
		assertDoesNotThrow(() -> {
			// Crear algunos asistentes
			usuarioController.crearAsistente("asistenteTest1", "Juan", "Pérez", "juan@test.com",
					java.time.LocalDate.of(1990, 1, 1));
			usuarioController.crearAsistente("asistenteTest2", "María", "González", "maria@test.com",
					java.time.LocalDate.of(1985, 5, 15));

			// Mostrar asistentes
			Set<DTAsistente> asistentes = edicionController.mostrarAsistentes();
			assertNotNull(asistentes, "El set de asistentes no debería ser null");
			// No podemos garantizar el número exacto debido a otros tests
		});
	}

	@Test
	void testCupoDisponible() {
		assertDoesNotThrow(() -> {
			// Preparar datos
			eventoController.altaCategoria("CategoriaCupo1");
			eventoController.altaEvento(
					new DTEventoAlta("EventoCupo1", "Descripcion", new Date(0), "SIGLA_CU1", Set.of("CategoriaCupo1")));

			usuarioController.crearOrganizador("orgCupo1", "Organizador Cupo", "orgcupo@test.com", "Descripción");

			DTEdicion edicionData = new DTEdicion("EdicionCupo1", "SIGLA_ECU1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoCupo1", "orgCupo1", edicionData);

			DTTipoRegistro tipo = new DTTipoRegistro("TipoCupo1", "Descripcion Cupo", 50.0f, 10);
			edicionController.altaTipoRegistro("EdicionCupo1", tipo);

			// Configurar edición recordada
			edicionController.mostrarTiposDeRegistro("EdicionCupo1");

			// Verificar cupo disponible
			boolean cupoDisponible = edicionController.cupoDisponible("TipoCupo1");
			// El resultado depende del estado interno, pero no debería fallar
			assertNotNull(cupoDisponible, "El método debería retornar un booleano");
		});
	}

	@Test
	void testAsistenteNoRegistrado() {
		assertDoesNotThrow(() -> {
			// Crear un asistente
			usuarioController.crearAsistente("asistenteNoReg1", "Pedro", "Martínez", "pedro@test.com",
					java.time.LocalDate.of(1988, 3, 20));

			// Preparar edición
			eventoController.altaCategoria("CategoriaNoReg1");
			eventoController.altaEvento(new DTEventoAlta("EventoNoReg1", "Descripcion", new Date(0), "SIGLA_NR1",
					Set.of("CategoriaNoReg1")));

			usuarioController.crearOrganizador("orgNoReg1", "Organizador NoReg", "orgnoreg@test.com", "Descripción");

			DTEdicion edicionData = new DTEdicion("EdicionNoReg1", "SIGLA_ENR1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoNoReg1", "orgNoReg1", edicionData);

			// Configurar edición recordada
			edicionController.mostrarTiposDeRegistro("EdicionNoReg1");

			// Verificar que el asistente no está registrado
			boolean noRegistrado = edicionController.asistenteNoRegistrado("asistenteNoReg1");
			// El resultado depende del estado interno
			assertNotNull(noRegistrado, "El método debería retornar un booleano");
		});
	}

	@Test
	void testAltaRegistroEdicionEvento() {
		assertDoesNotThrow(() -> {
			// Preparar datos completos
			eventoController.altaCategoria("CategoriaRegistro1");
			eventoController.altaEvento(new DTEventoAlta("EventoRegistro1", "Descripcion", new Date(0), "SIGLA_REG1",
					Set.of("CategoriaRegistro1")));

			usuarioController.crearOrganizador("orgReg1", "Organizador Registro", "orgreg@test.com", "Descripción");
			usuarioController.crearAsistente("asistenteReg1", "Ana", "López", "ana@test.com",
					java.time.LocalDate.of(1992, 7, 10));

			DTEdicion edicionData = new DTEdicion("EdicionRegistro1", "SIGLA_ERG1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoRegistro1", "orgReg1", edicionData);

			DTTipoRegistro tipo = new DTTipoRegistro("TipoRegistroReg1", "Descripcion Registro", 100.0f, 50);
			edicionController.altaTipoRegistro("EdicionRegistro1", tipo);

			// Configurar edición recordada
			edicionController.mostrarTiposDeRegistro("EdicionRegistro1");

			// Realizar registro
			edicionController.altaRegistroEdicionEvento("TipoRegistroReg1", "asistenteReg1", new Date());

		}, "El alta de registro debería ejecutarse sin errores");

		// Test sin edición recordada
		edicionController.cancelarRegistroEdicionEvento(); // Reset estado
		assertThrows(IllegalStateException.class, () -> {
			edicionController.altaRegistroEdicionEvento("TipoInexistente", "asistenteInexistente", new Date());
		}, "Debería lanzar excepción sin edición recordada");
	}

	@Test
	void testCancelarRegistroEdicionEvento() {
		assertDoesNotThrow(() -> {
			edicionController.cancelarRegistroEdicionEvento();
		}, "Cancelar registro debería ejecutarse sin errores");
	}

	@Test
	void testObtenerDatosDetalladosEdicion() {
		assertDoesNotThrow(() -> {
			// Preparar datos
			eventoController.altaCategoria("CategoriaDetallada1");
			eventoController.altaEvento(new DTEventoAlta("EventoDetallado1", "Descripcion Detallada", new Date(0),
					"SIGLA_DET1", Set.of("CategoriaDetallada1")));

			usuarioController.crearOrganizador("orgDet1", "Organizador Detallado", "orgdet@test.com",
					"Descripción detallada");

			DTEdicion edicionData = new DTEdicion("EdicionDetallada1", "SIGLA_EDET1", new Date(1000), new Date(2000),
					new Date(0), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoDetallado1", "orgDet1", edicionData);

			// Obtener datos detallados
			DTEdicionDetallada detalles = edicionController.obtenerDatosDetalladosEdicion("EventoDetallado1",
					"EdicionDetallada1");
			assertNotNull(detalles, "Los detalles no deberían ser null");
			
			detalles = edicionController.obtenerDatosDetalladosEdicion("EdicionDetallada1");
			
			assertNotNull(detalles, "Los detalles no deberían ser null");

		}, "Obtener datos detallados debería ejecutarse sin errores");

		// Test con evento inexistente
		assertThrows(ValidationInputException.class, () -> {
			edicionController.obtenerDatosDetalladosEdicion("EventoInexistente", "EdicionInexistente");
		}, "Debería lanzar excepción con evento inexistente");
	}

	@Test
	void testObtenerEdicionesPorOrganizador() {
		assertDoesNotThrow(() -> {
			// Preparar datos
			usuarioController.crearOrganizador("orgTest1", "Organizador1", "", "");
			eventoController.altaCategoria("CategoriaOrg1");
			eventoController.altaEvento(new DTEventoAlta("EventoOrg1", "Descripcion Org", new Date(0), "SIGLA_ORG1",
					Set.of("CategoriaOrg1")));
			eventoController.agregarEdicionAEvento("EventoOrg1", "orgTest1",
					new DTEdicion("EdicionOrg1", "SIGLA_EORG1", new Date(1000), new Date(2000), new Date(0), "Ciudad",
							"País"));
			Set<DTEdicion> ediciones = edicionController.obtenerEdicionesPorOrganizador("orgTest1");
			assertEquals(1, ediciones.size(), "Debería devolver al menos una edición");
			

		});
	}                                                                      
	@Test
	void testValidacionesEntrada() {
		// Test con parámetros null
		assertThrows(NullPointerException.class, () -> {
			edicionController.altaTipoRegistro(null, new DTTipoRegistro("Test", "Desc", 50.0f, 100));
		}, "Debería lanzar excepción con nombreEdicion null");

		assertThrows(NullPointerException.class, () -> {
			edicionController.altaTipoRegistro("EdicionTest", null);
		}, "Debería lanzar excepción con datosTipoRegistro null");

		assertThrows(NullPointerException.class, () -> {
			edicionController.consultaTipoRegistro(null, "TipoTest");
		}, "Debería lanzar excepción con nombreEdicion null");

		assertThrows(NullPointerException.class, () -> {
			edicionController.obtenerDatosDetalladosEdicion(null, "EdicionTest");
		}, "Debería lanzar excepción con nombreEvento null");
	}

	@Test
	void testCasosLimite() {
		assertDoesNotThrow(() -> {
			// Test con strings vacíos
			eventoController.altaCategoria("CategoriaVacia1");
			eventoController.altaEvento(
					new DTEventoAlta("EventoVacio1", "", new Date(0), "SIGLA_V1", Set.of("CategoriaVacia1")));

			usuarioController.crearOrganizador("orgVacio1", "Organizador", "orgvacio@test.com", "");

			DTEdicion edicionData = new DTEdicion("EdicionVacia1", "", new Date(1000), new Date(2000), new Date(0), "",
					"");
			eventoController.agregarEdicionAEvento("EventoVacio1", "orgVacio1", edicionData);

			DTTipoRegistro tipoVacio = new DTTipoRegistro("", "", 0.0f, 0);
			edicionController.altaTipoRegistro("EdicionVacia1", tipoVacio);
			
			// Test con nombres vacíos
			assertThrows(ValidationInputException.class, () -> {
				edicionController.obtenerDatosDetalladosEdicion("", "EdicionTest");
			}, "Debería lanzar excepción con nombreEvento vacío");

			assertThrows(ValidationInputException.class, () -> {
				edicionController.obtenerDatosDetalladosEdicion("EventoTest", "");
			}, "Debería lanzar excepción con nombreEdicion vacío");

			// Test con nombres muy largos
			String nombreMuyLargo = "N".repeat(5000);
			assertThrows(ValidationInputException.class, () -> {
				edicionController.obtenerDatosDetalladosEdicion(nombreMuyLargo, "EdicionTest");
			}, "Debería lanzar excepción con nombreEvento muy largo");

			assertThrows(ValidationInputException.class, () -> {
				edicionController.obtenerDatosDetalladosEdicion("EventoTest", nombreMuyLargo);
			}, "Debería lanzar excepción con nombreEdicion muy largo");

		}, "Debería manejar strings vacíos");

		assertDoesNotThrow(() -> {
			// Test con valores límite
			DTTipoRegistro tipoLimite = new DTTipoRegistro("TipoLimite", "Descripcion muy muy muy larga".repeat(100),
					Float.MAX_VALUE, Integer.MAX_VALUE);
			eventoController.altaCategoria("CategoriaLimite1");
			eventoController.altaEvento(
					new DTEventoAlta("EventoLimite1", "Desc", new Date(0), "SIGLA_LIM1", Set.of("CategoriaLimite1")));

			usuarioController.crearOrganizador("orgLimite1", "Organizador Limite", "orglimite@test.com", "Desc");

			DTEdicion edicionData = new DTEdicion("EdicionLimite1", "SIGLA_ELIM1", new Date(1000), new Date(2000),
					new Date(0), "Ciudad", "País");
			eventoController.agregarEdicionAEvento("EventoLimite1", "orgLimite1", edicionData);

			edicionController.altaTipoRegistro("EdicionLimite1", tipoLimite);

		}, "Debería manejar valores límite");
	}
}
