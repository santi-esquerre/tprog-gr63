// core/src/main/java/infra/JPA.java
package infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPA {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("eventosPU");
  
  private JPA() {}
  
  public static void switchToTesting() {
	
	if (emf != null && emf.isOpen()) emf.close();
	emf = Persistence.createEntityManagerFactory("testPU");
  }
  
  public static EntityManager em() { return emf.createEntityManager(); }
  public static void close() { emf.close(); }
}
