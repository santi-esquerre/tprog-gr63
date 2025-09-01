// core/src/main/java/infra/Tx.java
package infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Function;

public final class Tx {
  private Tx() {}
  public static <T> T inTx(Function<EntityManager, T> work) {
    EntityManager em = JPA.em();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      T out = work.apply(em);
      tx.commit();
      return out;
    } catch (RuntimeException e) {
      if (tx.isActive()) tx.rollback();
      throw e;
    } finally {
      em.close();
    }
  }
}
