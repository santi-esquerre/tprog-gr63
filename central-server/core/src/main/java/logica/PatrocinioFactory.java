package logica;

import datatypes.NivelPatrocinio;
import dominio.Otorga;
import dominio.Patrocinio;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import repos.EdicionRepository;
import repos.InstitucionRepository;

public class PatrocinioFactory {
  private static final PatrocinioFactory INSTANCE = new PatrocinioFactory();

  private PatrocinioFactory() {}

  public static PatrocinioFactory get() {
    return INSTANCE;
  }

  public void crearPatrocinio(
      EntityManager entityManager,
      LocalDate fecha,
      String nombreEdicion,
      String nombreInstitucion,
      float aporte,
      TipoRegistro tipoRegistro,
      int cantGratuitos,
      String codigo,
      NivelPatrocinio nivelPatrocinio) {
    var repoI = InstitucionRepository.get();
    var repoE = EdicionRepository.get();
    var institucion = repoI.buscarInstitucion(entityManager, nombreInstitucion);
    var edicion = repoE.buscarEdicion(entityManager, nombreEdicion);
    var patrocinio = new Patrocinio(aporte, codigo, nivelPatrocinio, fecha, institucion, edicion);
    institucion.addPatrocinio(patrocinio);
    edicion.addPatrocinio(patrocinio);

    entityManager.merge(institucion);
    entityManager.merge(edicion);

    var otorga = new Otorga(cantGratuitos, tipoRegistro);
    patrocinio.addOtorga(otorga);

    entityManager.persist(otorga);
    entityManager.persist(patrocinio);
  }
}
