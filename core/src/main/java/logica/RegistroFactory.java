package logica;

import java.util.Date;

import dominio.Asistente;
import dominio.Edicion;
import dominio.Registro;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class RegistroFactory {
  private static final RegistroFactory INSTANCE = new RegistroFactory();
  private RegistroFactory() {}
  public static RegistroFactory get() { return INSTANCE; }

  public void altaRegistro(EntityManager em, Edicion ed, Asistente as, TipoRegistro tr, float costo, Date fecha){
    var r = new Registro();
    // Seteo por reflexión para mantener entidades sin setters públicos
    try {
      var f1 = Registro.class.getDeclaredField("fecha"); f1.setAccessible(true); f1.set(r, fecha);
      var f2 = Registro.class.getDeclaredField("costo"); f2.setAccessible(true); f2.set(r, costo);
      var f3 = Registro.class.getDeclaredField("asistente"); f3.setAccessible(true); f3.set(r, as);
      var f4 = Registro.class.getDeclaredField("edicion"); f4.setAccessible(true); f4.set(r, ed);
      var f5 = Registro.class.getDeclaredField("tipo"); f5.setAccessible(true); f5.set(r, tr);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(e);
    }
    em.persist(r);
  }
}
