package logica;

import datatypes.DTTipoRegistro;
import dominio.Edicion;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class TipoRegistroFactory {
	private static final TipoRegistroFactory INSTANCE = new TipoRegistroFactory();

		  public void altaTipoRegistro(EntityManager em, Edicion ed, DTTipoRegistro datosTipoRegistro) {
		    var r = new TipoRegistro(datosTipoRegistro.nombre(), ed, datosTipoRegistro.descripcion(), datosTipoRegistro.costo(), datosTipoRegistro.cupo());
		    // Seteo por reflexión para mantener entidades sin setters públicos
		    em.persist(r);
		  }

	public static TipoRegistroFactory get() {
		return INSTANCE;
	}

	public void altaTipoRegistro(EntityManager em, Edicion ed, DTTipoRegistro datosTipoRegistro) {
		var r = new TipoRegistro(ed, datosTipoRegistro.nombre(), datosTipoRegistro.descripcion(),
				datosTipoRegistro.costo(), datosTipoRegistro.cupo());
		em.persist(r);
	}

}
