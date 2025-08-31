package logica;

import java.util.Date;

import datatypes.DTTipoRegistro;
import dominio.Asistente;
import dominio.Edicion;
import dominio.Registro;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class TipoRegistroFactory {
		  private static final TipoRegistroFactory INSTANCE = new TipoRegistroFactory();
		  private TipoRegistroFactory() {}
		  public static TipoRegistroFactory get() { return INSTANCE; }

		  public void altaTipoRegistro(EntityManager em, Edicion ed, DTTipoRegistro datosTipoRegistro) {
		    var r = new TipoRegistro(ed.getNombre(), datosTipoRegistro.nombre(), datosTipoRegistro.descripcion(), datosTipoRegistro.costo(), datosTipoRegistro.cupo());
		    // Seteo por reflexión para mantener entidades sin setters públicos
		    em.persist(r);
		  }

}
