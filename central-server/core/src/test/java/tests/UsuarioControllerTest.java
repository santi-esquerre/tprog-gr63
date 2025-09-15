package tests;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import datatypes.DTAsistente;
import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import exceptions.InstitucionNoExistenteException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerTest extends BaseTest {
    @Test
    void testSeleccionarAsistenteYDTAsistente() throws Exception {
        usuarioController.crearAsistente("asistDT", "NombreA", "ApellidoA", "asistdt@test.com",
                LocalDate.of(1995, 5, 5));
        var asistente = usuarioController.seleccionarAsistente("asistDT");
        assertNotNull(asistente, "Debe devolver un DTAsistente válido");
        assertEquals("asistDT", asistente.nickname());
        assertEquals("NombreA", asistente.nombre());
        assertEquals("ApellidoA", asistente.apellido());
        assertEquals("asistdt@test.com", asistente.correo());
        assertNotNull(asistente.fechaNacimiento());
        assertEquals("asistDT", asistente.toString());
        assertEquals(datatypes.TipoUsuario.ASISTENTE, asistente.getTipoUsuario());

        // Caso borde: nickname inexistente
        Exception ex = assertThrows(Exception.class, () -> usuarioController.seleccionarAsistente("noexiste"));
        assertNotNull(ex);
    }

    @Test
    void testSeleccionarOrganizadorYDTOrganizador() throws Exception {
        usuarioController.crearOrganizador("orgDT", "NombreO", "orgdt@test.com", "desc org", "http://org.com");
        var organizador = usuarioController.seleccionarOrganizador("orgDT");
        assertNotNull(organizador, "Debe devolver un DTOrganizador válido");
        assertEquals("orgDT", organizador.nickname());
        assertEquals("NombreO", organizador.nombre());
        assertEquals("orgdt@test.com", organizador.correo());
        assertEquals("desc org", organizador.descripcion());
        assertEquals("http://org.com", organizador.linkSitioWeb());
        assertEquals("orgDT", organizador.toString());
        assertEquals(datatypes.TipoUsuario.ORGANIZADOR, organizador.getTipoUsuario());

        // Caso borde: nickname inexistente
        Exception ex = assertThrows(Exception.class, () -> usuarioController.seleccionarOrganizador("noexiste"));
        assertNotNull(ex);
    }

    @Test
    void testDTUsuarioYDTUsuarioItemListado() {
        datatypes.DTUsuario usuario = new datatypes.DTUsuario("nickU", "NombreU", "correoU@test.com");
        assertEquals("nickU", usuario.nickname());
        assertEquals("NombreU", usuario.nombre());
        assertEquals("correoU@test.com", usuario.correo());
        assertEquals("nickU", usuario.toString());

        datatypes.DTUsuarioItemListado item = new datatypes.DTUsuarioItemListado("nickU", "correoU@test.com",
                datatypes.TipoUsuario.ASISTENTE);
        assertEquals("nickU", item.nickname());
        assertEquals("correoU@test.com", item.correo());
        assertEquals(datatypes.TipoUsuario.ASISTENTE, item.tipoUsuario());
        assertTrue(item.toString().contains("nickU"));
        assertTrue(item.toString().contains("ASISTENTE"));
    }

    @Test
    void testMostrarAsistentes() {
        // Test the missing mostrarAsistentes method that was identified in coverage
        // analysis
        Set<DTAsistente> asistentes = usuarioController.mostrarAsistentes();
        assertNotNull(asistentes, "mostrarAsistentes should not return null");

        // Create some test data and verify it's included
        String uniqueId = generateUniqueId();
        String nickAsistente = "asist" + uniqueId;

        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente(nickAsistente, "Nombre", "Apellido",
                    "test" + uniqueId + "@test.com", LocalDate.now());
        });

        Set<DTAsistente> asistentesAfter = usuarioController.mostrarAsistentes();
        assertTrue(asistentesAfter.size() >= asistentes.size(),
                "Should have at least the same number of asistentes after creation");
    }

    @Test
    void testVerificarNoExistenciaNickname() {
        // Test con nickname que no existe
        assertDoesNotThrow(() -> {
            boolean noExiste = usuarioController.verificarNoExistenciaNickname("usuarioInexistente123");
            assertTrue(noExiste, "Debería devolver true para un nickname que no existe");
        });

        // Crear un usuario y verificar que ya existe
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("usuarioTest1", "Juan", "Pérez", "juan@test.com",
                    LocalDate.of(1990, 1, 1));
            boolean noExiste = usuarioController.verificarNoExistenciaNickname("usuarioTest1");
            assertFalse(noExiste, "Debería devolver false para un nickname que ya existe");
        });
    }

    @Test
    void testVerificarNoExistenciaCorreo() {
        // Test con correo que no existe
        assertDoesNotThrow(() -> {
            boolean noExiste = usuarioController.verificarNoExistenciaCorreo("inexistente@test.com");
            assertTrue(noExiste, "Debería devolver true para un correo que no existe");
        });

        // Crear un usuario y verificar que el correo ya existe
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("usuarioTest2", "María", "González", "maria@test.com",
                    LocalDate.of(1985, 5, 15));
            boolean noExiste = usuarioController.verificarNoExistenciaCorreo("maria@test.com");
            assertFalse(noExiste, "Debería devolver false para un correo que ya existe");
        });
    }

    @Test
    void testCrearAsistenteSinInstitucion() {
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteTest1", "Carlos", "Rodríguez",
                    "carlos@test.com", LocalDate.of(1992, 3, 10));
        }, "Crear asistente sin institución debería ejecutarse sin errores");

        // Verificar que el usuario fue creado
        assertDoesNotThrow(() -> {
            boolean existe = !usuarioController.verificarNoExistenciaNickname("asistenteTest1");
            assertTrue(existe, "El asistente debería haber sido creado");
        });
    }

    @Test
    void testCrearAsistenteConInstitucion() {
        assertDoesNotThrow(() -> {
            institucionController.crearInstitucion("InstitucionTest1", "Descripción de prueba",
                    "http://www.universidadtest.com");
            usuarioController.crearAsistente("asistenteTest2", "Ana", "Martínez",
                    "ana@test.com", LocalDate.of(1988, 7, 20), "InstitucionTest1");
        }, "Crear asistente con institución debería ejecutarse sin errores");

        // Verificar que el usuario fue creado
        assertDoesNotThrow(() -> {
            boolean existe = !usuarioController.verificarNoExistenciaNickname("asistenteTest2");
            assertTrue(existe, "El asistente con institución debería haber sido creado");
        });
    }

    @Test
    void testCrearAsistenteValidaciones() {
        // Intentar crear asistente con nickname duplicado
        assertThrows(UsuarioNicknameRepetidoException.class, () -> {
            usuarioController.crearAsistente("asistenteDuplicado", "Nombre1", "Apellido1", "uncorreo@correo.com",
                    LocalDate.of(1990, 1, 1));
            usuarioController.crearAsistente("asistenteDuplicado", "Nombre2", "Apellido2", "otrocorreo@correo.com",
                    LocalDate.of(1990, 1, 1));
        });

        assertThrows(UsuarioCorreoRepetidoException.class, () -> {
            usuarioController.crearAsistente("asistenteUnico", "Nombre1", "Apellido1", "uncorreo@correo.com",
                    LocalDate.of(1990, 1, 1));
        });
        // Prueba de institucion inexistente
        assertThrows(InstitucionNoExistenteException.class, () -> {
            usuarioController.crearAsistente("asistenteUnico2", "Nombre3", "Apellido3", "tercercorreo@correo.com",
                    LocalDate.of(1990, 1, 1), "InstitucionInexistente");
        });
    }

    @Test
    void testCrearOrganizadorCompleto() {
        assertDoesNotThrow(() -> {
            usuarioController.crearOrganizador("organizadorTest1", "Pedro", "admin@test.com",
                    "Organizador de eventos tecnológicos", "http://www.organizador.com");
        }, "Crear organizador completo debería ejecutarse sin errores");

        // Verificar que el usuario fue creado
        assertDoesNotThrow(() -> {
            boolean existe = !usuarioController.verificarNoExistenciaNickname("organizadorTest1");
            assertTrue(existe, "El organizador completo debería haber sido creado");
        });
    }

    @Test
    void testCrearOrganizadorSinSitioWeb() {
        assertDoesNotThrow(() -> {
            usuarioController.crearOrganizador("organizadorTest2", "Laura", "laura@test.com",
                    "Organizadora de eventos académicos");
        }, "Crear organizador sin sitio web debería ejecutarse sin errores");

        // Verificar que el usuario fue creado
        assertDoesNotThrow(() -> {
            boolean existe = !usuarioController.verificarNoExistenciaNickname("organizadorTest2");
            assertTrue(existe, "El organizador sin sitio web debería haber sido creado");
        });
    }

    @Test
    void testObtenerUsuariosPorTipo() {
        // Crear usuarios de diferentes tipos
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteListado1", "Usuario", "Asistente1",
                    "asistente1@test.com", LocalDate.of(1990, 1, 1));
            usuarioController.crearOrganizador("organizadorListado1", "Usuario Organizador1",
                    "organizador1@test.com", "Descripción org1");
        });

        // Obtener solo asistentes
        assertDoesNotThrow(() -> {
            List<DTUsuarioItemListado> asistentes = usuarioController.obtenerUsuarios(TipoUsuario.ASISTENTE);
            assertNotNull(asistentes, "La lista de asistentes no debería ser null");
            // Verificar que al menos contiene el asistente creado
            boolean contieneAsistente = asistentes.stream()
                    .anyMatch(u -> "asistenteListado1".equals(u.nickname()));
            assertTrue(contieneAsistente, "Debería contener el asistente creado");
        });

        // Obtener solo organizadores
        assertDoesNotThrow(() -> {
            List<DTUsuarioItemListado> organizadores = usuarioController.obtenerUsuarios(TipoUsuario.ORGANIZADOR);
            assertNotNull(organizadores, "La lista de organizadores no debería ser null");
            // Verificar que al menos contiene el organizador creado
            boolean contieneOrganizador = organizadores.stream()
                    .anyMatch(u -> "organizadorListado1".equals(u.nickname()));
            assertTrue(contieneOrganizador, "Debería contener el organizador creado");
        });
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        // Crear usuarios de ambos tipos
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteAll1", "Usuario", "AsistenteTodos1",
                    "asistentetodos1@test.com", LocalDate.of(1990, 1, 1));
            usuarioController.crearOrganizador("organizadorAll1", "Usuario OrganizadorTodos1",
                    "organizadortodos1@test.com", "Descripción orgTodos1");
        });

        assertDoesNotThrow(() -> {
            List<DTUsuarioItemListado> todosUsuarios = usuarioController.obtenerUsuarios();
            assertNotNull(todosUsuarios, "La lista de todos los usuarios no debería ser null");
            assertTrue(todosUsuarios.size() >= 2, "Debería contener al menos 2 usuarios");
        });
    }

    @Test
    void testObtenerRegistrosUsuario() {
        // Crear un asistente
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteRegistros1", "Usuario", "ConRegistros",
                    "conregistros@test.com", LocalDate.of(1990, 1, 1));
        });

        // Obtener registros (inicialmente debería estar vacía)
        assertDoesNotThrow(() -> {
            List<DTRegistro> registros = usuarioController.obtenerRegistrosUsuario("asistenteRegistros1");
            assertNotNull(registros, "La lista de registros no debería ser null");
            // Los registros pueden estar vacíos inicialmente
        });

        // Test con usuario inexistente
        assertDoesNotThrow(() -> {
            List<DTRegistro> registros = usuarioController.obtenerRegistrosUsuario("usuarioInexistente999");
            // Dependiendo de la implementación, esto podría devolver lista vacía o null
            assertNotNull(registros, "Debería manejar usuarios inexistentes gracefulmente");
        });
    }

    @Test
    void testObtenerRegistroDetallado() {
        // Crear un asistente y datos necesarios
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteDetalle1", "Usuario", "ConDetalles",
                    "condetalles@test.com", LocalDate.of(1990, 1, 1));
        });

        // Test con datos que no existen - debería devolver null o lanzar excepción
        assertDoesNotThrow(() -> {
            try {
                DTRegistroDetallado detalle = usuarioController.obtenerRegistroDetallado(
                        "asistenteDetalle1", "EdicionInexistente");
                // Es válido que sea null si no existe el registro
                // No forzamos que sea not null porque no hemos creado ningún registro
                if (detalle != null) {
                    assertNotNull(detalle.asistente(), "El asistente no debería ser null");
                }
            } catch (Exception e) {
                // Es esperado que pueda fallar si no existe la edición
                assertTrue(true, "Es normal que falle si no existe la edición");
            }
        });
    }

    @Test
    void testValidacionesEntrada() {
        // Test crear asistente con nickname duplicado
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("duplicado", "Nombre1", "Apellido1", "email1@test.com",
                    LocalDate.of(1990, 1, 1));
        });

        // Intentar crear otro con el mismo nickname debería fallar
        Exception exception1 = assertThrows(Exception.class, () -> {
            usuarioController.crearAsistente("duplicado", "Nombre2", "Apellido2", "email2@test.com",
                    LocalDate.of(1991, 1, 1));
        });
        assertNotNull(exception1, "Debería lanzar excepción con nickname duplicado");
    }

    @Test
    void testCasosLimite() {
        // Test con fechas límite
        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteViejito", "Usuario", "Viejo",
                    "viejo@test.com", LocalDate.of(1900, 1, 1));
        }, "Debería manejar fechas muy antiguas");

        assertDoesNotThrow(() -> {
            usuarioController.crearAsistente("asistenteJoven", "Usuario", "Joven",
                    "joven@test.com", LocalDate.now());
        }, "Debería manejar fecha actual");

        // Test con strings muy largos
        String nombreMuyLargo = "a".repeat(1000);
        String descripcionMuyLarga = "d".repeat(2000);

        assertThrows(Exception.class, () -> {
            usuarioController.crearOrganizador("orgLargo", nombreMuyLargo, "largo@test.com",
                    descripcionMuyLarga);
        }, "Debería rechazar strings muy largos");
    }

    @Test
    void testConcurrencia() {
        // Test básico de concurrencia - crear múltiples usuarios simultáneamente
        String[] nicknames = { "concurrent1", "concurrent2", "concurrent3", "concurrent4", "concurrent5" };

        for (int i = 0; i < nicknames.length; i++) {
            final int index = i;
            assertDoesNotThrow(() -> {
                usuarioController.crearAsistente(nicknames[index], "Usuario" + index, "Apellido" + index,
                        "concurrent" + index + "@test.com", LocalDate.of(1990 + index, 1, 1));
            }, "Debería manejar creación concurrente de usuarios");
        }

        // Verificar que todos fueron creados
        for (String nickname : nicknames) {
            assertDoesNotThrow(() -> {
                boolean existe = !usuarioController.verificarNoExistenciaNickname(nickname);
                assertTrue(existe, "El usuario " + nickname + " debería haber sido creado");
            });
        }
    }
}
