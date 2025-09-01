package repos;

import java.util.LinkedHashSet;
import java.util.Set;

import dominio.Edicion;
import dominio.TipoRegistro;
import jakarta.persistence.EntityManager;

public final class EdicionRepository {
  private static final EdicionRepository INSTANCE = new EdicionRepository();
  private EdicionRepository() {}
  public static EdicionRepository get() { return INSTANCE; }

  public Edicion buscarEdicion(EntityManager em, String nombreEdicion){
    return em.createQuery("""
      select ed from Edicion ed
      left join fetch ed.tipos
      left join fetch ed.registros r
      left join fetch r.asistente
      where ed.nombre = :n
    """, Edicion.class).setParameter("n", nombreEdicion)
      .getResultStream().findFirst().orElse(null);
  }

  public TipoRegistro obtenerTipoRegistro(EntityManager em, String nombre) {
    // Stub: in real integration would search for TipoRegistro by name in specific edition
    return em.createQuery(
      "SELECT tr FROM TipoRegistro tr WHERE tr.nombre = :nombre", TipoRegistro.class)
      .setParameter("nombre", nombre)
      .getResultStream().findFirst().orElse(null);
  }
  
  /**
   * Obtiene los datos detallados de una edición específica de un evento
   */
  public datatypes.DTEdicionDetallada obtenerDatosDetalladosEdicion(EntityManager em, String nombreEvento, String nombreEdicion) {
    // Query to get the edition with all related data
    var edicion = em.createQuery("""
        select ed from Edicion ed
        left join fetch ed.organizador
        left join fetch ed.tipos
        left join fetch ed.registros r
        left join fetch r.asistente
        left join fetch r.tipo
        where ed.nombre = :nombreEdicion 
        and ed.evento.nombre = :nombreEvento
      """, dominio.Edicion.class)
      .setParameter("nombreEdicion", nombreEdicion)
      .setParameter("nombreEvento", nombreEvento)
      .getResultStream().findFirst().orElse(null);
      
    if (edicion == null) return null;
    
    // Get patrocinios for this edition
    var patrocinios = em.createQuery("""
        select p from Patrocinio p
        left join fetch p.institucion
        where p.edicion = :edicion
      """, dominio.Patrocinio.class)
      .setParameter("edicion", edicion)
      .getResultList();
    
    // Create DTEdicionDetallada manually with patrocinios included
    var dtEdicion = edicion.toDTEdicion();
    var dtOrganizador = edicion.obtenerDTOrganizador();
    
    var dtTiposRegistro = edicion.listarDTTiposDeRegistro();
    
    Set<datatypes.DTPatrocinio> dtPatrocinios = new LinkedHashSet<>();
    for (var p : patrocinios) {
      dtPatrocinios.add(p.toDTPatrocinio(p.getInstitucion().toDTInstitucion()));
    }
    
    var dtRegistros = edicion.obtenerDTRegistros();
    
    return new datatypes.DTEdicionDetallada(
      dtEdicion,
      dtOrganizador,
      dtTiposRegistro,
      dtPatrocinios,
      dtRegistros
    );
  }
}
