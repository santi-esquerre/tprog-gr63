package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import infra.JPA;
import jakarta.persistence.EntityManager;

class BasicConnectionTest {

    @Test
    void testDatabaseConnection() {
        JPA.switchToTesting();
        EntityManager em = JPA.em();
        assertNotNull(em);
        em.close();
    }

    @Test
    void testSimpleQuery() {
        JPA.switchToTesting();
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
