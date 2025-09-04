package tests;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTEvento;
import datatypes.DTEventoDetallado;
import datatypes.DTInstitucion;
import datatypes.DTOrganizador;
import datatypes.DTPatrocinio;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTRegistrosOtorgados;
import datatypes.DTTipoRegistro;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.NivelPatrocinio;
import datatypes.TipoUsuario;

/**
 * Tests for datatype classes that had no or low coverage in the Jacoco report.
 * Focus on testing DTRegistro, DTRegistroDetallado, and other DTO classes.
 */
class DatatypesTest extends BaseTest {

    @Test
    void testDTRegistro() {
        Date fecha = new Date();
        float costo = 100.0f;
        String nombreEvento = "EventoTest";
        String nombreEdicion = "EdicionTest";
        String nicknameAsistente = "AsistenteTest";
        DTTipoRegistro tipoRegistro = new DTTipoRegistro("Tipo", "Desc", 100.0f, 10);

        DTRegistro registro = new DTRegistro(fecha, costo, nombreEvento, nombreEdicion, nicknameAsistente,
                tipoRegistro);

        assertEquals(fecha, registro.fecha());
        assertEquals(costo, registro.costo());
        assertEquals(nombreEvento, registro.nombreEvento());
        assertEquals(nombreEdicion, registro.nombreEdicion());
        assertEquals(nicknameAsistente, registro.nicknameAsistente());
        assertEquals(tipoRegistro, registro.tipoRegistro());

        // Test toString method
        String str = registro.toString();
        assertNotNull(str);
    }

    @Test
    void testDTRegistroDetallado() {
        Date fecha = new Date();
        float costo = 100.0f;
        DTEvento evento = new DTEvento("EventoTest", "SGLA", "desc", new Date());
        DTEdicion edicion = new DTEdicion("EdicionTest", "SGLA", new Date(), new Date(), new Date(), "Montevideo",
                "Uruguay");
        DTAsistente asistente = new DTAsistente("nick", "nombre", "apellido", "email", new Date());
        DTTipoRegistro tipoRegistro = new DTTipoRegistro("Tipo", "Desc", 100.0f, 10);
        Optional<DTPatrocinio> patrocinio = Optional.empty();

        DTRegistroDetallado detallado = new DTRegistroDetallado(
                fecha, costo, evento, edicion, asistente, tipoRegistro, patrocinio);

        assertEquals(fecha, detallado.fecha());
        assertEquals(costo, detallado.costo());
        assertEquals(evento, detallado.evento());
        assertEquals(edicion, detallado.edicion());
        assertEquals(asistente, detallado.asistente());
        assertEquals(tipoRegistro, detallado.tipoRegistro());
        assertEquals(patrocinio, detallado.patrocinio());

        // Test with patrocinio present
        DTInstitucion institucion = new DTInstitucion("InstTest", "desc", "web");
        DTPatrocinio dtPatrocinio = new DTPatrocinio(new Date(), 1000.0f, 123, NivelPatrocinio.ORO, institucion);
        Optional<DTPatrocinio> optPatrocinio = Optional.of(dtPatrocinio);

        detallado = new DTRegistroDetallado(
                fecha, costo, evento, edicion, asistente, tipoRegistro, optPatrocinio);

        assertTrue(detallado.patrocinio().isPresent());
        assertEquals(dtPatrocinio, detallado.patrocinio().get());
    }

    @Test
    void testDTEdicionDetallada() {
        DTEdicion edicion = new DTEdicion("EdicionTest", "SGLA", new Date(), new Date(), new Date(), "Montevideo",
                "Uruguay");
        DTOrganizador organizador = new DTOrganizador("nick", "nombre", "email", "desc", "web");

        DTEdicionDetallada detallada = new DTEdicionDetallada(
                edicion, organizador, null, null, null);

        assertEquals(edicion, detallada.edicion());
        assertEquals(organizador, detallada.organizador());
    }

    @Test
    void testEnumTypes() {
        // Test NivelPatrocinio enum - has 4 values: PLATINO, ORO, PLATA, BRONCE
        assertEquals(4, NivelPatrocinio.values().length);
        assertEquals("PLATINO", NivelPatrocinio.PLATINO.name());
        assertEquals("ORO", NivelPatrocinio.ORO.name());
        assertEquals("PLATA", NivelPatrocinio.PLATA.name());
        assertEquals("BRONCE", NivelPatrocinio.BRONCE.name());

        // Test TipoUsuario enum
        assertEquals(2, TipoUsuario.values().length);
        assertEquals("ASISTENTE", TipoUsuario.ASISTENTE.name());
        assertEquals("ORGANIZADOR", TipoUsuario.ORGANIZADOR.name());
    }

    @Test
    void testDTUsuarioTypes() {
        // Test DTUsuario
        DTUsuario usuario = new DTUsuario("nickU", "NombreU", "correoU@test.com");
        assertEquals("nickU", usuario.nickname());
        assertEquals("NombreU", usuario.nombre());
        assertEquals("correoU@test.com", usuario.correo());
        assertEquals("nickU", usuario.toString());

        // Test DTUsuarioItemListado
        DTUsuarioItemListado item = new DTUsuarioItemListado("nickU", "correoU@test.com", TipoUsuario.ASISTENTE);
        assertEquals("nickU", item.nickname());
        assertEquals("correoU@test.com", item.correo());
        assertEquals(TipoUsuario.ASISTENTE, item.tipoUsuario());
        assertTrue(item.toString().contains("nickU"));
        assertTrue(item.toString().contains("ASISTENTE"));
    }

    @Test
    void testDTAsistente() {
        Date fechaNacimiento = new Date();
        // Constructor order: nickname, nombre, correo, apellido, fechaNacimiento
        DTAsistente asistente = new DTAsistente("nick", "nombre", "correo", "apellido", fechaNacimiento);

        assertEquals("nick", asistente.nickname());
        assertEquals("nombre", asistente.nombre());
        assertEquals("correo", asistente.correo());
        assertEquals("apellido", asistente.apellido());
        assertEquals(fechaNacimiento, asistente.fechaNacimiento());
        assertEquals(TipoUsuario.ASISTENTE, asistente.getTipoUsuario());
        assertEquals("nick", asistente.toString());
    }

    @Test
    void testDTOrganizador() {
        // Constructor order: nickname, nombre, correo, linkSitioWeb, descripcion
        DTOrganizador organizador = new DTOrganizador("nick", "nombre", "correo", "web", "desc");

        assertEquals("nick", organizador.nickname());
        assertEquals("nombre", organizador.nombre());
        assertEquals("correo", organizador.correo());
        assertEquals("web", organizador.linkSitioWeb());
        assertEquals("desc", organizador.descripcion());
        assertEquals(TipoUsuario.ORGANIZADOR, organizador.getTipoUsuario());
        assertEquals("nick", organizador.toString());
    }

    @Test
    void testDTTipoRegistro() {
        DTTipoRegistro tipoRegistro = new DTTipoRegistro("nombre", "desc", 100.0f, 20);

        assertEquals("nombre", tipoRegistro.nombre());
        assertEquals("desc", tipoRegistro.descripcion());
        assertEquals(100.0f, tipoRegistro.costo());
        assertEquals(20, tipoRegistro.cupo());

        // Test legacy getter methods
        assertEquals("nombre", tipoRegistro.getNombre());
        assertEquals("desc", tipoRegistro.getDescripcion());
        assertEquals(100.0f, tipoRegistro.getCosto());
        assertEquals(20, tipoRegistro.getCupo());
    }

    @Test
    void testDTEvento() {
        Date fechaAlta = new Date();
        DTEvento evento = new DTEvento("nombre", "SGLA", "desc", fechaAlta);

        assertEquals("nombre", evento.nombre());
        assertEquals("SGLA", evento.sigla());
        assertEquals("desc", evento.descripcion());
        assertEquals(fechaAlta, evento.fechaAlta());
    }

    @Test
    void testDTEdicion() {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(System.currentTimeMillis() + 100000);
        Date fechaAlta = new Date();

        DTEdicion edicion = new DTEdicion("nombre", "SGLA", fechaInicio, fechaFin, fechaAlta, "ciudad", "pais");

        assertEquals("nombre", edicion.nombre());
        assertEquals("SGLA", edicion.sigla());
        assertEquals(fechaInicio, edicion.fechaInicio());
        assertEquals(fechaFin, edicion.fechaFin());
        assertEquals(fechaAlta, edicion.fechaAlta());
        assertEquals("ciudad", edicion.ciudad());
        assertEquals("pais", edicion.pais());
    }

    @Test
    void testDTInstitucion() {
        DTInstitucion institucion = new DTInstitucion("nombre", "desc", "web");

        assertEquals("nombre", institucion.nombre());
        assertEquals("desc", institucion.descripcion());
        assertEquals("web", institucion.sitioWeb());
    }

    @Test
    void testDTPatrocinio() {
        DTInstitucion institucion = new DTInstitucion("inst", "desc", "web");
        Date fecha = new Date();
        DTPatrocinio patrocinio = new DTPatrocinio(fecha, 1000.0f, 123, NivelPatrocinio.ORO, institucion);

        assertEquals(fecha, patrocinio.fechaRealizacion());
        assertEquals(1000.0f, patrocinio.monto());
        assertEquals(123, patrocinio.codigo());
        assertEquals(NivelPatrocinio.ORO, patrocinio.nivel());
        assertEquals(institucion, patrocinio.institucion());
    }

    @Test
    void testDTRegistrosOtorgados() {
        DTTipoRegistro tipoRegistro = new DTTipoRegistro("tipo", "desc", 100.0f, 10);
        DTRegistrosOtorgados registros = new DTRegistrosOtorgados(tipoRegistro, 5);

        assertEquals(tipoRegistro, registros.tipoRegistro());
        assertEquals(5, registros.cupos());
    }

    @Test
    void testDTEventoDetallado() {
        DTEvento evento = new DTEvento("evento", "SGLA", "desc", new Date());
        DTEventoDetallado detallado = new DTEventoDetallado(evento, null, null);

        assertEquals(evento, detallado.evento());
        assertNull(detallado.categorias());
        assertNull(detallado.ediciones());
    }
}
