package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import infra.JPA;
import jakarta.persistence.EntityManager;
import interfaces.Factory;

class BasicConnectionTest {

    @Test
    void testDatabaseConnection() {
        Factory.get().getIRepository().switchToTesting();
        EntityManager em = JPA.em();
        assertNotNull(em);
        em.close();
    }

    @Test
    void testSimpleQuery() {
        Factory.get().getIRepository().switchToTesting();
        EntityManager em = JPA.em();
        try {
            // Test que la BD existe y podemos hacer queries
            var result = em.createNativeQuery("SELECT 1").getSingleResult();
            assertNotNull(result);
        } finally {
            em.close();
        }
    }
}
