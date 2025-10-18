package logica;

import datatypes.DTTipoRegistro;
import dominio.Edicion;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class TipoRegistroFactory {
  private static final TipoRegistroFactory INSTANCE = new TipoRegistroFactory();

  public static TipoRegistroFactory get() {
    return INSTANCE;
  }

  public void altaTipoRegistro(EntityManager entityManager, Edicion edicion, DTTipoRegistro datosTipoRegistro) {
    var registro =
        new TipoRegistro(
            edicion,
            datosTipoRegistro.nombre(),
            datosTipoRegistro.descripcion(),
            datosTipoRegistro.costo(),
            datosTipoRegistro.cupo());
    entityManager.persist(registro);
  }
}
