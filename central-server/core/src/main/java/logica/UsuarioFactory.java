package logica;

import dominio.Asistente;
import dominio.Institucion;
import dominio.Organizador;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public final class UsuarioFactory {
  private static final UsuarioFactory INSTANCE = new UsuarioFactory();

  private UsuarioFactory() {}

  public static UsuarioFactory get() {
    return INSTANCE;
  }

  public void altaAsistente(
      EntityManager entm,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento,
      Institucion institucion) {
    var asistente = new Asistente(nickname, nombre, apellido, clave, foto, correo, fechaNacimiento);
    asistente.setInstitucion(institucion);
    institucion.addAsistente(asistente);
    entm.persist(asistente);
    entm.merge(institucion);
  }

  public void altaAsistente(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String apellido,
      LocalDate fechaNacimiento) {
    var asistente = new Asistente(nickname, nombre, correo, clave, foto, apellido, fechaNacimiento);
    entityManager.persist(asistente);
  }

  public void altaOrganizador(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion,
      String linkSitioWeb) {
    var organizador = new Organizador(nickname, nombre, correo, clave, foto, descripcion, linkSitioWeb);
    entityManager.persist(organizador);
  }

  public void altaOrganizador(
      EntityManager entityManager,
      String nickname,
      String nombre,
      String correo,
      String clave,
      String foto,
      String descripcion) {
    var usuario = new Organizador(nickname, nombre, correo, clave, foto, descripcion);
    entityManager.persist(usuario);
  }
}
