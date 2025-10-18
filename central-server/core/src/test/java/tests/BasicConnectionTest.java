package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import infra.JPA;
import interfaces.Factory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

class BasicConnectionTest {

  @Test
  void testDatabaseConnection() {
    Factory.get().getIRepository().switchToTesting();
    EntityManager entityManager = JPA.entityManager();
    assertNotNull(entityManager);
    entityManager.close();
  }

  @Test
  void testSimpleQuery() {
    Factory.get().getIRepository().switchToTesting();
    EntityManager entityManager = JPA.entityManager();
    try {
      // Test que la BD existe y podemos hacer queries
      var result = entityManager.createNativeQuery("SELECT 1").getSingleResult();
      assertNotNull(result);
    } finally {
      entityManager.close();
    }
  }
}
