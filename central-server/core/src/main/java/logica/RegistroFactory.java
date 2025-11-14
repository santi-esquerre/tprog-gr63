package logica;

import dominio.Asistente;
import dominio.Edicion;
import dominio.Registro;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;
import java.util.Date;

public final class RegistroFactory {
  private static final RegistroFactory INSTANCE = new RegistroFactory();

  private RegistroFactory() {}

  public static RegistroFactory get() {
    return INSTANCE;
  }

  public Registro altaRegistro(
      EntityManager entityManager, Edicion edicion, Asistente asistente, TipoRegistro tipoRegistro, float costo, Date fecha) {
    var registro = new Registro();
    // Seteo por reflexión para mantener entidades sin setters públicos
    try {
      var fechaRegistro = Registro.class.getDeclaredField("fecha");
      fechaRegistro.setAccessible(true);
      fechaRegistro.set(registro, fecha);
      var costoRegistro = Registro.class.getDeclaredField("costo");
      costoRegistro.setAccessible(true);
      costoRegistro.set(registro, costo);
      var asistenteRegistro = Registro.class.getDeclaredField("asistente");
      asistenteRegistro.setAccessible(true);
      asistenteRegistro.set(registro, asistente);
      var edicionRegistro = Registro.class.getDeclaredField("edicion");
      edicionRegistro.setAccessible(true);
      edicionRegistro.set(registro, edicion);
      var tipoRegistroNuevo = Registro.class.getDeclaredField("tipo");
      tipoRegistroNuevo.setAccessible(true);
      tipoRegistroNuevo.set(registro, tipoRegistro);
      var asistenciaConfirmadaRegistro = Registro.class.getDeclaredField("asistenciaConfirmada");
      asistenciaConfirmadaRegistro.setAccessible(true);
      asistenciaConfirmadaRegistro.set(registro, false);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(e);
    }
    entityManager.persist(registro);
    return registro;
  }
}
