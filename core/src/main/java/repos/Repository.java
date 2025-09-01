package repos;

import infra.JPA;
import infra.Tx;

public class Repository implements interfaces.IRepository {
	
	private static final Repository INSTANCE = new Repository();
	private Repository() {}
	public static Repository get() { return INSTANCE; }
	@Override
	public void switchToTesting() {
		JPA.switchPU("testPU");
		
	}
	@Override
	public void switchToDefault() {
		JPA.switchPU("eventosPU");
	}
	@Override
	public void reset() {
		Tx.inTx(em -> {
			em.createNativeQuery(" BEGIN;\r\n"
					+ "    TRUNCATE TABLE registro, edicion, evento_categoria, evento, categoria, tipo_registro, institucion, usuario, patrocinio RESTART IDENTITY CASCADE;\r\n"
					+ "    COMMIT;").executeUpdate();
			return null;
			
		});
	}
}
