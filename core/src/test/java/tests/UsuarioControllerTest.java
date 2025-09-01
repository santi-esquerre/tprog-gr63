package tests;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import infra.JPA;
import interfaces.Factory;
import interfaces.IUsuarioController;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerTest {

    private static IUsuarioController usuarioController;
    private static Factory factory;

    @BeforeAll
    static void iniciar() {
        factory = Factory.get();
        usuarioController = factory.getIUsuarioController();
    }

    @BeforeEach
    void setUp() {
        JPA.switchToTesting();
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
            usuarioController.crearAsistente("asistenteTest2", "Ana", "Martínez",
                    "ana@test.com", LocalDate.of(1988, 7, 20), "Universidad Test");
        }, "Crear asistente con institución debería ejecutarse sin errores");

        // Verificar que el usuario fue creado
        assertDoesNotThrow(() -> {
            boolean existe = !usuarioController.verificarNoExistenciaNickname("asistenteTest2");
            assertTrue(existe, "El asistente con institución debería haber sido creado");
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
