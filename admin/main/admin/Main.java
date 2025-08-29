package admin;

import dominio.Usuario;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		//Crear EntityManagerFactory usando persistence.xml (unidad "eventosPU")
        var emf = Persistence.createEntityManagerFactory("eventosPU");
        var em = emf.createEntityManager();

        // Transacción simple
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario p = new Usuario("Bruce Tester", "correo");
            em.persist(p);
            tx.commit();
            System.out.println("Persona insertada con id = " + p.getId());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

	}

}
