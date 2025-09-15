package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import interfaces.Factory;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IInstitucionController;
import interfaces.IUsuarioController;

/**
 * Base class for all test classes in the core module.
 * Provides common setup and utility methods for testing.
 */
public abstract class BaseTest {
    protected static IUsuarioController usuarioController;
    protected static IEventoController eventoController;
    protected static IEdicionController edicionController;
    protected static IInstitucionController institucionController;
    protected static Factory factory;

    @BeforeAll
    static void initBase() {
        factory = Factory.get();
        usuarioController = factory.getIUsuarioController();
        eventoController = factory.getIEventoController();
        edicionController = factory.getIEdicionController();
        institucionController = factory.getIInstitucionController();
    }

    @BeforeEach
    void setupBase() {
        factory.getIRepository().switchToTesting();
    }

    /**
     * Helper method to generate unique identifiers for tests
     */
    protected String generateUniqueId() {
        return String.valueOf(System.currentTimeMillis() + (int) (Math.random() * 1000));
    }

    /**
     * Helper method to generate unique names with prefix
     */
    protected String generateUniqueName(String prefix) {
        return prefix + generateUniqueId();
    }
}
