package datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEventoDetallado implements Serializable {
	DTEvento evento;
	ArrayList<String> categorias = new ArrayList<>();
	ArrayList<DTEdicion> ediciones = new ArrayList<>();
	
	public DTEventoDetallado () {}
	
	public DTEventoDetallado(DTEvento evento, ArrayList<String> categorias, ArrayList<DTEdicion> ediciones) {
		this.evento = evento;
		this.categorias = categorias;
		this.ediciones = ediciones;
	}
	
	public DTEvento evento() {
		return evento;
	}
	
	public ArrayList<String> categorias() {
		return categorias;
	}
	
	public ArrayList<DTEdicion> ediciones() {
		return ediciones;
	}
	
}