package datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEventoAlta implements Serializable {
	String nombre;
	String descripcion;
	Date fechaAlta;
	String sigla;
	String foto;
	ArrayList<String> categorias = new ArrayList<>();
	
	public DTEventoAlta () {}
	
	public DTEventoAlta(String nombre, String descripcion, Date fechaAlta, String sigla, String foto, ArrayList<String> categorias) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.sigla = sigla;
		this.foto = foto;
		this.categorias = categorias;
	}
	
	public String nombre() {
		return nombre;
	}
	
	public String descripcion() {
		return descripcion;
	}
	
	public Date fechaAlta() {
		return fechaAlta;
	}
	
	public String sigla() {
		return sigla;
	}
	
	public String foto() {
		return foto;
	}
	
	public ArrayList<String> categorias() {
		return categorias;
	}
	
}