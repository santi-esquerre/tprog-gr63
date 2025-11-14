
package webservice;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtRegistroDetallado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtRegistroDetallado">
 *   <complexContent>
 *     <extension base="{http://webservice/}record">
 *       <sequence>
 *         <element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="evento" type="{http://webservice/}dtEvento" minOccurs="0"/>
 *         <element name="edicion" type="{http://webservice/}dtEdicion" minOccurs="0"/>
 *         <element name="asistente" type="{http://webservice/}dtAsistente" minOccurs="0"/>
 *         <element name="tipoRegistro" type="{http://webservice/}dtTipoRegistro" minOccurs="0"/>
 *         <element name="patrocinio" type="{http://webservice/}optional" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtRegistroDetallado", propOrder = {
    "fecha",
    "costo",
    "evento",
    "edicion",
    "asistente",
    "tipoRegistro",
    "patrocinio"
})
public class DtRegistroDetallado
    extends Record
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected float costo;
    protected DtEvento evento;
    protected DtEdicion edicion;
    protected DtAsistente asistente;
    protected DtTipoRegistro tipoRegistro;
    protected Optional patrocinio;

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     */
    public void setCosto(float value) {
        this.costo = value;
    }

    /**
     * Obtiene el valor de la propiedad evento.
     * 
     * @return
     *     possible object is
     *     {@link DtEvento }
     *     
     */
    public DtEvento getEvento() {
        return evento;
    }

    /**
     * Define el valor de la propiedad evento.
     * 
     * @param value
     *     allowed object is
     *     {@link DtEvento }
     *     
     */
    public void setEvento(DtEvento value) {
        this.evento = value;
    }

    /**
     * Obtiene el valor de la propiedad edicion.
     * 
     * @return
     *     possible object is
     *     {@link DtEdicion }
     *     
     */
    public DtEdicion getEdicion() {
        return edicion;
    }

    /**
     * Define el valor de la propiedad edicion.
     * 
     * @param value
     *     allowed object is
     *     {@link DtEdicion }
     *     
     */
    public void setEdicion(DtEdicion value) {
        this.edicion = value;
    }

    /**
     * Obtiene el valor de la propiedad asistente.
     * 
     * @return
     *     possible object is
     *     {@link DtAsistente }
     *     
     */
    public DtAsistente getAsistente() {
        return asistente;
    }

    /**
     * Define el valor de la propiedad asistente.
     * 
     * @param value
     *     allowed object is
     *     {@link DtAsistente }
     *     
     */
    public void setAsistente(DtAsistente value) {
        this.asistente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoRegistro.
     * 
     * @return
     *     possible object is
     *     {@link DtTipoRegistro }
     *     
     */
    public DtTipoRegistro getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Define el valor de la propiedad tipoRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link DtTipoRegistro }
     *     
     */
    public void setTipoRegistro(DtTipoRegistro value) {
        this.tipoRegistro = value;
    }

    /**
     * Obtiene el valor de la propiedad patrocinio.
     * 
     * @return
     *     possible object is
     *     {@link Optional }
     *     
     */
    public Optional getPatrocinio() {
        return patrocinio;
    }

    /**
     * Define el valor de la propiedad patrocinio.
     * 
     * @param value
     *     allowed object is
     *     {@link Optional }
     *     
     */
    public void setPatrocinio(Optional value) {
        this.patrocinio = value;
    }

}
