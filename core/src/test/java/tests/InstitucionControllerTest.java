
package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IInstitucionController;
import logica.EdicionController;
import datatypes.*;
import exceptions.*;

import java.time.LocalDate;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InstitucionControllerTest {
	private static IInstitucionController institucionController;
	private static IEdicionController edicionController;
	private static Factory factory;

	@BeforeAll
	static void iniciar() {
		factory = Factory.get();
		institucionController = factory.getIInstitucionController();
		edicionController = factory.getIEdicionController();
	}

	@BeforeEach
	void setUp() {
		factory.getIRepository().switchToTesting();
	}

	@Test
	void testCrearInstitucion() {
		assertDoesNotThrow(() -> {
			boolean creada = institucionController.crearInstitucion("InstitucionTest", "DescripcionTest", "www.test.com");
			assertTrue(creada, "La institución debería crearse correctamente");
		});

		// Intentar crear la misma institución nuevamente
		Exception ex = assertThrows(InstitucionRepetidaException.class, () -> {
			institucionController.crearInstitucion("InstitucionTest", "DescripcionTest", "www.test.com");
		});
		assertNotNull(ex, "Debería lanzar excepción por institución repetida");
	}

	@Test
	void testCrearInstitucionCasosBorde() {
		// Nombre vacío

		// Descripción muy larga
		String descLarga = "d".repeat(5000);
		Exception ex2 = assertThrows(Exception.class, () -> {
			institucionController.crearInstitucion("InstitucionLarga", descLarga, "web.com");
		});
		assertNotNull(ex2, "Debería lanzar excepción por descripción muy larga");

		// Sitio web nulo
		assertThrows(Exception.class, () -> {
			boolean creada = institucionController.crearInstitucion("InstitucionSinWeb", "desc", null);
			assertTrue(creada, "No debería permitir sitio web nulo");
		});
	}

	@Test
	void testListarInstituciones() {
		assertDoesNotThrow(() -> {
			institucionController.crearInstitucion("InstitucionA", "DescA", "webA.com");
			institucionController.crearInstitucion("InstitucionB", "DescB", "webB.com");
		});
		Set<DTInstitucion> instituciones = institucionController.listarInstituciones();
		assertNotNull(instituciones, "La lista de instituciones no debe ser null");
		assertTrue(instituciones.stream().anyMatch(i -> i.nombre().equals("InstitucionA")), "Debe contener InstitucionA");
		assertTrue(instituciones.stream().anyMatch(i -> i.nombre().equals("InstitucionB")), "Debe contener InstitucionB");

		// Listar cuando no hay instituciones
		factory.getIRepository().switchToTesting(); // Limpiar
		Set<DTInstitucion> vacio = institucionController.listarInstituciones();
		assertNotNull(vacio);
		assertTrue(vacio.isEmpty(), "Debe devolver vacío si no hay instituciones");
	}

	@Test
	void testCrearPatrocinioYObtenerDTPatrocinio() {
		// Crear institución y edición asociada
		assertDoesNotThrow(() -> {
			institucionController.crearInstitucion("InstitucionPatrocinio", "DescPatrocinio", "webPatrocinio.com");
			// Crear edición usando el controlador de eventos
			var eventoController = factory.getIEventoController();
			eventoController.altaCategoria("CatPatrocinio");
			eventoController.altaEvento(new DTEventoAlta("EventoPatrocinio", "DescEvento", new java.util.Date(), "SIGLA_PAT", Set.of("CatPatrocinio")));
			var usuarioController = factory.getIUsuarioController();
			usuarioController.crearOrganizador("OrgPatrocinio", "OrgPatrocinio", "org@patrocinio.com", "desc org");
			var edicion = new DTEdicion("EdicionEventoTest", "SIGLA_ED", new java.util.Date(), new java.util.Date(), new java.util.Date(), "Montevideo", "Uruguay");
			eventoController.agregarEdicionAEvento("EventoPatrocinio", "OrgPatrocinio", edicion);
			DTTipoRegistro tipoRegistro = new DTTipoRegistro("TipoRegTest", "DescTipoReg", 1000.0f, 10);
			DTRegistrosOtorgados registros = new DTRegistrosOtorgados(tipoRegistro, 5);
			edicionController.altaTipoRegistro(edicion.nombre(), tipoRegistro);
			edicionController.altaPatrocinio(LocalDate.of(1999, 10,10), edicion.nombre(), "InstitucionPatrocinio", 20000f, "TipoRegTest", 4, "FINGTECH", NivelPatrocinio.ORO);
			
			DTPatrocinio patrocinio = institucionController.obtenerDTPatrocinio("EdicionEventoTest", "InstitucionPatrocinio");
			assertNotNull(patrocinio, "El DTPatrocinio no debe ser null");
			assertEquals("InstitucionPatrocinio", patrocinio.institucion().nombre(), "El nombre de la institución debe coincidir");

			// Patrocinio con institución inexistente
			boolean creadoInexistente = institucionController.crearPatrocinio("EdicionEventoTest", "NoExiste", registros, "9999");
			assertFalse(creadoInexistente, "No debería crear patrocinio para institución inexistente");

			// Obtener patrocinio inexistente
			DTPatrocinio patNull = institucionController.obtenerDTPatrocinio("EdicionEventoTest", "NoExiste");
			assertNull(patNull, "Debe devolver null si la institución no existe");
			
			assertThrows(InstitucionNoExistenteException.class, () -> {
				DTTipoRegistro tipoRegistro2 = new DTTipoRegistro("TipoRegTest2", "DescTipoReg", 1000.00001f, 10);
				edicionController.altaTipoRegistro(edicion.nombre(), tipoRegistro2);
				edicionController.altaPatrocinio(LocalDate.of(1999, 10,10), edicion.nombre(), "InstitucionPatrocinio2", 20000f, "TipoRegTest2", 4, "FINGTECH2", NivelPatrocinio.ORO);

			}, "No debería permitir crear patrocinio con una institución inexistente");
			
		
		});
		

		
	}

	@Test
	void testAfiliarAsistenteAInstitucion() {
		assertDoesNotThrow(() -> {
			institucionController.crearInstitucion("InstitucionAfiliacion", "DescAfiliacion", "webAfiliacion.com");
		});
		boolean afiliado = institucionController.afiliarAsistenteAInstitucion("AsistenteTest", "InstitucionAfiliacion");
		assertTrue(afiliado, "El asistente debería afiliarse correctamente (mock)");

		// Afiliación a institución inexistente
		boolean afiliadoInexistente = institucionController.afiliarAsistenteAInstitucion("AsistenteTest", "NoExiste");
		assertFalse(afiliadoInexistente, "No debería afiliarse a una institución inexistente");

		// Afiliación con nombre de asistente vacío
		boolean afiliadoVacio = institucionController.afiliarAsistenteAInstitucion("", "InstitucionAfiliacion");
		assertTrue(afiliadoVacio, "Mock: debería devolver true aunque el nombre esté vacío");

		// Afiliación con nombre de institución vacío
		boolean afiliadoInstVacio = institucionController.afiliarAsistenteAInstitucion("AsistenteTest", "");
		assertFalse(afiliadoInstVacio, "No debería afiliarse si la institución está vacía");
	}

	@Test
	void testCrearInstitucionNulls() {
		// Nombre nulo
		Exception ex = assertThrows(Exception.class, () -> {
			institucionController.crearInstitucion(null, "desc", "web.com");
		});
		assertNotNull(ex, "Debe lanzar excepción si el nombre es nulo");

	}
}
