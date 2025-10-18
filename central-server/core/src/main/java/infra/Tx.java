// core/src/main/java/infra/Tx.java
package infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Function;

public final class Tx {
  private Tx() {}

  public static <T> T inTx(Function<EntityManager, T> work) {
    EntityManager entityManager = JPA.entityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      T out = work.apply(entityManager);
      transaction.commit();
      return out;
    } catch (RuntimeException e) {
      if (transaction.isActive()) transaction.rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }
}
