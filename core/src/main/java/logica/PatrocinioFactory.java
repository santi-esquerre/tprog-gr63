package logica;

import java.time.LocalDate;

import datatypes.NivelPatrocinio;
import dominio.TipoRegistro;
import infra.Tx;
import dominio.Edicion;
import dominio.Institucion;
import dominio.Otorga;
import dominio.Patrocinio;
import jakarta.persistence.EntityManager;
import repos.EdicionRepository;
import repos.InstitucionRepository;

public class PatrocinioFactory {
	private static final PatrocinioFactory INSTANCE = new PatrocinioFactory();
	private PatrocinioFactory() {}
	public static PatrocinioFactory get() { return INSTANCE; }
	
	public void crearPatrocinio(EntityManager em, LocalDate fecha, String nombreEdicion, String nombreInstitucion, float aporte, TipoRegistro tr, int cantGratuitos, String codigo, NivelPatrocinio nivelPatrocinio) {
		var repoI = InstitucionRepository.get();
		var repoE = EdicionRepository.get();
		var i = repoI.buscarInstitucion(em, nombreInstitucion);
		var e = repoE.buscarEdicion(em, nombreEdicion);
		var p = new Patrocinio(aporte,codigo, nivelPatrocinio, fecha, i,  e);
		i.addPatrocinio(p);
		e.addPatrocinio(p);
		
		em.merge(i);
		em.merge(e);
		
		var o = new Otorga(cantGratuitos, tr);
		p.addOtorga(o);
		
		em.persist(o);
		em.persist(p);
		
	}

}
