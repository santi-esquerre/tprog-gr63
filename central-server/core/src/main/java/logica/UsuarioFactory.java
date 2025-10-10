package logica;

import java.time.LocalDate;

import dominio.Asistente;
import dominio.Institucion;
import dominio.Organizador;
import jakarta.persistence.EntityManager;
import repos.InstitucionRepository;

public final class UsuarioFactory {
	private static final UsuarioFactory INSTANCE = new UsuarioFactory();

	private UsuarioFactory() {
	}

	public static UsuarioFactory get() {
		return INSTANCE;
	}

	private final InstitucionRepository repoI = InstitucionRepository.get();
	
	public void altaAsistente(EntityManager entm, String nickname, String nombre, String correo, String clave, String foto, String apellido, LocalDate fechaNacimiento, Institucion i) {
		var u = new Asistente(nickname, nombre, apellido, clave, foto, correo, fechaNacimiento);
		u.setInstitucion(i);
		i.addAsistente(u);
		entm.persist(u);
		entm.merge(i);
	}

	public void altaAsistente(EntityManager em, String nickname, String nombre, String correo, String clave, String foto, String apellido,
			LocalDate fechaNacimiento) {
		var u = new Asistente(nickname, nombre, correo, clave, foto, apellido, fechaNacimiento);
		em.persist(u);
	}

	public void altaOrganizador(EntityManager em, String nickname, String nombre, String correo, String clave, String foto, String descripcion,
			String linkSitioWeb) {
		var u = new Organizador(nickname, nombre, correo, clave, foto, descripcion, linkSitioWeb);
		em.persist(u);
	}

	public void altaOrganizador(EntityManager em, String nickname, String nombre, String correo, String clave, String foto, String descripcion) {
		var u = new Organizador(nickname, nombre, correo, clave, foto, descripcion);
		em.persist(u);
	}

}
