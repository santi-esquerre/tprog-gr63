package tests;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTEvento;
import datatypes.DTEventoAlta;
import datatypes.DTEventoDetallado;
import datatypes.DTInstitucion;
import datatypes.DTOrganizador;
import datatypes.DTPatrocinio;
import datatypes.DTTipoRegistro;
import datatypes.NivelPatrocinio;
import datatypes.TipoUsuario;

/**
 * Tests for domain model classes that had low coverage in the Jacoco report.
 * Focus on testing Registro, RegistroOtorgado, and Patrocinio classes.
 */
class DominioTest extends BaseTest {

    @Test
    void testRegistroMethods() {
        // Create necessary objects using factories for valid state
        String uniqueId = generateUniqueId();

        // Test basic getters after creation through controller
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asist" + uniqueId, "nombre", "apellido",
                    "asist" + uniqueId + "@test.com", LocalDate.now());

            eventoController.altaDeCategoria("cat" + uniqueId);
            Set<String> cats = new HashSet<>();
            cats.add("cat" + uniqueId);

            eventoController.altaEvento(new DTEventoAlta("evento" + uniqueId, "desc", new Date(), "SIG", cats));

            usuarioController.crearOrganizador("org" + uniqueId, "nombre org", "org" + uniqueId + "@test.com", "desc");

            DTEdicion edicion = new DTEdicion("edicion" + uniqueId, "SGLA",
                    new Date(System.currentTimeMillis() + 100000),
                    new Date(System.currentTimeMillis() + 200000),
                    new Date(), "Ciudad", "Pais");

            eventoController.agregarEdicionAEvento("evento" + uniqueId, "org" + uniqueId, edicion);

            DTTipoRegistro tipoReg = new DTTipoRegistro("tipo" + uniqueId, "desc", 100.0f, 10);
            edicionController.altaTipoRegistro("edicion" + uniqueId, tipoReg);

            // Now register the user
            edicionController.mostrarTiposDeRegistro("edicion" + uniqueId);
            edicionController.altaRegistroEdicionEvento("tipo" + uniqueId, "asist" + uniqueId, new Date());

            // Verify registration was successful
            var registros = usuarioController.obtenerRegistrosUsuario("asist" + uniqueId);
            assertFalse(registros.isEmpty(), "Should have at least one registration");
        });
    }

    @Test
    void testRegistroOtorgadoThroughPatrocinio() {
        // Test RegistroOtorgado through patrocinio creation
        String uniqueId = generateUniqueId();

        assertDoesNotThrow(() -> {
            // Create institucion
            institucionController.crearInstitucion("inst" + uniqueId, "desc", "web.com");

            // Create complete event structure
            eventoController.altaDeCategoria("cat" + uniqueId);
            Set<String> cats = new HashSet<>();
            cats.add("cat" + uniqueId);

            eventoController.altaEvento(new DTEventoAlta("evento" + uniqueId, "desc", new Date(), "SIG", cats));

            usuarioController.crearOrganizador("org" + uniqueId, "nombre org", "org" + uniqueId + "@test.com", "desc");

            DTEdicion edicion = new DTEdicion("edicion" + uniqueId, "SGLA",
                    new Date(System.currentTimeMillis() + 100000),
                    new Date(System.currentTimeMillis() + 200000),
                    new Date(), "Ciudad", "Pais");

            eventoController.agregarEdicionAEvento("evento" + uniqueId, "org" + uniqueId, edicion);

            DTTipoRegistro tipoReg = new DTTipoRegistro("tipo" + uniqueId, "desc", 100.0f, 10);
            edicionController.altaTipoRegistro("edicion" + uniqueId, tipoReg);

            // Create patrocinio with registroOtorgado
            // Business rule: cost of free registrations cannot exceed 20% of contribution
            // tipoReg costs 100.0f, want 5 free registrations = 500 cost
            // Need aporte >= 500 / 0.2 = 2500f
            edicionController.altaPatrocinio(
                    LocalDate.now(), "edicion" + uniqueId, "inst" + uniqueId,
                    3000.0f, "tipo" + uniqueId, 5, "CODE" + uniqueId, NivelPatrocinio.ORO);

            // Verify patrocinio was created
            DTPatrocinio patrocinio = institucionController.obtenerDTPatrocinio("edicion" + uniqueId,
                    "inst" + uniqueId);
            assertNotNull(patrocinio, "Patrocinio should be created");
            assertEquals(3000.0f, patrocinio.monto());
            assertEquals(NivelPatrocinio.ORO, patrocinio.nivel());
        });
    }

    @Test
    void testAsistenteCreationAndRetrieval() {
        String uniqueId = generateUniqueId();
        String nickname = "asist" + uniqueId;
        String correo = "asist" + uniqueId + "@test.com";

        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente(nickname, "Nombre", "Apellido", correo, LocalDate.of(1990, 5, 15));

            DTAsistente asistente = usuarioController.seleccionarAsistente(nickname);
            assertNotNull(asistente);
            assertEquals(nickname, asistente.nickname());
            assertEquals("Nombre", asistente.nombre());
            assertEquals("Apellido", asistente.apellido());
            assertEquals(correo, asistente.correo());
            assertEquals(TipoUsuario.ASISTENTE, asistente.getTipoUsuario());
        });
    }

    @Test
    void testOrganizadorCreationAndRetrieval() {
        String uniqueId = generateUniqueId();
        String nickname = "org" + uniqueId;
        String correo = "org" + uniqueId + "@test.com";

        assertDoesNotThrow(() -> {
            usuarioController.crearOrganizador(nickname, "NombreOrg", correo, "Descripcion", "http://web.com");

            DTOrganizador organizador = usuarioController.seleccionarOrganizador(nickname);
            assertNotNull(organizador);
            assertEquals(nickname, organizador.nickname());
            assertEquals("NombreOrg", organizador.nombre());
            assertEquals(correo, organizador.correo());
            assertEquals("Descripcion", organizador.descripcion());
            assertEquals("http://web.com", organizador.linkSitioWeb());
            assertEquals(TipoUsuario.ORGANIZADOR, organizador.getTipoUsuario());
        });
    }

    @Test
    void testTipoRegistroThroughController() {
        String uniqueId = generateUniqueId();

        assertDoesNotThrow(() -> {
            // Create complete structure
            eventoController.altaDeCategoria("cat" + uniqueId);
            Set<String> cats = new HashSet<>();
            cats.add("cat" + uniqueId);

            eventoController.altaEvento(new DTEventoAlta("evento" + uniqueId, "desc", new Date(), "SIG", cats));

            usuarioController.crearOrganizador("org" + uniqueId, "nombre org", "org" + uniqueId + "@test.com", "desc");

            DTEdicion edicion = new DTEdicion("edicion" + uniqueId, "SGLA",
                    new Date(System.currentTimeMillis() + 100000),
                    new Date(System.currentTimeMillis() + 200000),
                    new Date(), "Ciudad", "Pais");

            eventoController.agregarEdicionAEvento("evento" + uniqueId, "org" + uniqueId, edicion);

            DTTipoRegistro tipoReg = new DTTipoRegistro("tipo" + uniqueId, "Descripcion tipo", 150.0f, 20);
            edicionController.altaTipoRegistro("edicion" + uniqueId, tipoReg);

            // Test retrieval
            DTTipoRegistro retrieved = edicionController.consultaTipoRegistro("edicion" + uniqueId, "tipo" + uniqueId);
            assertNotNull(retrieved);
            assertEquals("tipo" + uniqueId, retrieved.nombre());
            assertEquals("Descripcion tipo", retrieved.descripcion());
            assertEquals(150.0f, retrieved.costo());
            assertEquals(20, retrieved.cupo());
        });
    }

    @Test
    void testEventoCreationAndRetrieval() {
        String uniqueId = generateUniqueId();
        String nombreEvento = "evento" + uniqueId;

        assertDoesNotThrow(() -> {
            eventoController.altaDeCategoria("cat" + uniqueId);
            Set<String> cats = new HashSet<>();
            cats.add("cat" + uniqueId);

            eventoController.altaEvento(new DTEventoAlta(nombreEvento, "Descripcion evento", new Date(), "EVNT", cats));

            Set<DTEvento> eventos = eventoController.listarEventos();
            assertNotNull(eventos);

            DTEvento evento = eventos.stream()
                    .filter(e -> e.nombre().equals(nombreEvento))
                    .findFirst()
                    .orElse(null);

            assertNotNull(evento, "Event should be found in list");
            assertEquals(nombreEvento, evento.nombre());
            assertEquals("Descripcion evento", evento.descripcion());
            assertEquals("EVNT", evento.sigla());
            assertNotNull(evento.fechaAlta());

            // Test detailed view
            DTEventoDetallado detallado = eventoController.obtenerDatosDetalladosEvento(nombreEvento);
            assertNotNull(detallado);
            assertEquals(nombreEvento, detallado.evento().nombre());
        });
    }

    @Test
    void testInstitucionCreationAndRetrieval() {
        String uniqueId = generateUniqueId();
        String nombreInstitucion = "inst" + uniqueId;

        assertDoesNotThrow(() -> {
            institucionController.crearInstitucion(nombreInstitucion, "Descripcion inst", "http://inst.com");

            Set<DTInstitucion> instituciones = institucionController.listarInstituciones();
            assertNotNull(instituciones);

            DTInstitucion found = instituciones.stream()
                    .filter(i -> i.nombre().equals(nombreInstitucion))
                    .findFirst()
                    .orElse(null);

            assertNotNull(found, "Institution should be found in list");
            assertEquals(nombreInstitucion, found.nombre());
            assertEquals("Descripcion inst", found.descripcion());
            assertEquals("http://inst.com", found.sitioWeb());
        });
    }

    @Test
    void testEdicionThroughController() {
        String uniqueId = generateUniqueId();

        assertDoesNotThrow(() -> {
            // Create complete structure to test edicion
            eventoController.altaDeCategoria("cat" + uniqueId);
            Set<String> cats = new HashSet<>();
            cats.add("cat" + uniqueId);

            eventoController.altaEvento(new DTEventoAlta("evento" + uniqueId, "desc", new Date(), "SIG", cats));

            usuarioController.crearOrganizador("org" + uniqueId, "nombre org", "org" + uniqueId + "@test.com", "desc");

            DTEdicion edicionData = new DTEdicion("edicion" + uniqueId, "SGLA",
                    new Date(System.currentTimeMillis() + 100000),
                    new Date(System.currentTimeMillis() + 200000),
                    new Date(), "Montevideo", "Uruguay");

            eventoController.agregarEdicionAEvento("evento" + uniqueId, "org" + uniqueId, edicionData);

            // Test detailed retrieval
            DTEdicionDetallada detallada = edicionController.obtenerDatosDetalladosEdicion("edicion" + uniqueId);
            assertNotNull(detallada);
            assertEquals("edicion" + uniqueId, detallada.edicion().nombre());
            assertEquals("Montevideo", detallada.edicion().ciudad());
            assertEquals("Uruguay", detallada.edicion().pais());
            assertEquals("org" + uniqueId, detallada.organizador().nickname());
        });
    }
}
