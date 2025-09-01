package logica;

import java.time.LocalDate;

import dominio.Asistente;
import dominio.Institucion;
import dominio.Organizador;
import jakarta.persistence.EntityManager;
import repos.InstitucionRepository;

public final class UsuarioFactory {
	private static final UsuarioFactory INSTANCE = new UsuarioFactory();
	private UsuarioFactory() {}
	public static UsuarioFactory get() { return INSTANCE; }
	private final InstitucionRepository repoI = InstitucionRepository.get();
	
	public void altaAsistente(EntityManager entm, String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, Institucion i) {
		var u = new Asistente(nickname, nombre, apellido, correo, fechaNacimiento);
		u.setInstitucion(i);
		i.addAsistente(u);
		entm.persist(u);
		entm.merge(i);
	}
	
	public void altaAsistente(EntityManager em, String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) {
		var u = new Asistente(nickname, nombre, apellido, correo, fechaNacimiento);
		em.persist(u);
	}
	
	public void altaOrganizador(EntityManager em, String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
		var u = new Organizador(nickname, nombre, correo, descripcion, linkSitioWeb);
		em.persist(u);
	}
	
	public void altaOrganizador(EntityManager em, String nickname, String nombre, String correo, String descripcion) {
		var u = new Organizador(nickname, nombre, correo, descripcion);
		em.persist(u);
	}
	
	

}
