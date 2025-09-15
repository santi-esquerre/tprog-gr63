package repos;

import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public class TipoRegistroRepository {
	private static final TipoRegistroRepository INSTANCE = new TipoRegistroRepository();
	private TipoRegistroRepository() {}
	public static TipoRegistroRepository get() { return INSTANCE; }
	
	public TipoRegistro buscarTipoRegistro(EntityManager em, String nombreTipoRegistro, String nombreEdicion) {
		return em.createQuery("select tr from TipoRegistro tr where tr.nombre = :nt and tr.edicion.nombre = :ne", TipoRegistro.class)
				.setParameter("nt", nombreTipoRegistro)
				.setParameter("ne", nombreEdicion)
				.getResultStream().findFirst().orElse(null);
	}
}
