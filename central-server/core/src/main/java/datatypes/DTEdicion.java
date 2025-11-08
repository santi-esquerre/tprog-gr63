package datatypes;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTEdicion {

    private String nombre;
    private String sigla;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaAlta;
    private String ciudad;
    private String pais;
    private String foto;
    private EstadoEdicion estado;

    // Constructor completo
    public DTEdicion(String nombre, String sigla, Date fechaInicio, Date fechaFin,
                     Date fechaAlta, String ciudad, String pais, String foto, EstadoEdicion estado) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.ciudad = ciudad;
        this.pais = pais;
        this.foto = foto;
        this.estado = estado;
    }

    // Constructor vacío (recomendado para JAXB)
    public DTEdicion() {}

    // Getters 
	public String nombre() {
		return nombre;
	}
	
	public String sigla() {
		return sigla;
	}
	
	public Date fechaInicio() {
		return fechaInicio;
	}
	
	public Date fechaFin() {
		return fechaFin;
	}
	
	public Date fechaAlta() {
		return fechaAlta;
	}
	
	public String ciudad() {
		return ciudad;
	}
	
	public String pais() {
		return pais;
	}
	
	public String foto() {
		return foto;
	}
	
	public EstadoEdicion estado() {
		return estado;
	}
}

