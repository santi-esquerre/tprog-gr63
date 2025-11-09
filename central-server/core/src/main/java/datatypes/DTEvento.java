package datatypes;

import java.io.Serializable;
import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEvento implements Serializable {
	String nombre;
	String sigla;
	String descripcion;
	Date fechaAlta;
	String foto;
	
	public DTEvento() {}
	
	public DTEvento(String nombre, String sigla, String descripcion, Date fechaAlta, String foto) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.foto = foto;
	}
	
	public String nombre() {
		return nombre;
	}
	
	public String sigla() {
		return sigla;
	}
	
	public String descripcion() {
		return descripcion;
	}
	
	public Date fechaAlta() {
		return fechaAlta;
	}
	
	public String foto() {
		return foto;
	}
}