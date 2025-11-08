// core/src/main/java/datatypes/DTEdicionDetallada.java
package datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Versión detallada de una edición. Nota: En UML hereda de DTEdicion; en Java (records) lo
 * modelamos por composición.
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEdicionDetallada implements Serializable {
	DTEdicion edicion;
	DTOrganizador organizador;
	ArrayList<DTTipoRegistro> tiposRegistro = new ArrayList<>();
	ArrayList<DTPatrocinio> patrocinios = new ArrayList<>();
	ArrayList<DTRegistro> registros = new ArrayList<>();
	
	public DTEdicionDetallada () {}
	
	public DTEdicionDetallada(DTEdicion edicion, DTOrganizador organizador, ArrayList<DTTipoRegistro> tiposRegistro, ArrayList<DTPatrocinio> patrocinios, ArrayList<DTRegistro> registros) {
		this.edicion = edicion;
		this.organizador = organizador;
		this.tiposRegistro = tiposRegistro;
		this.patrocinios = patrocinios;
		this.registros = registros;
	}
	
	public DTEdicion edicion() {
		return edicion;
	}
	
	public DTOrganizador organizador() {
		return organizador;
	}
	
	public ArrayList<DTTipoRegistro> tiposRegistro() {
		return tiposRegistro;
	}
	
	public ArrayList<DTPatrocinio> patrocinios() {
		return patrocinios;
	}
	
	public ArrayList<DTRegistro> registros() {
		return registros;
	}
}