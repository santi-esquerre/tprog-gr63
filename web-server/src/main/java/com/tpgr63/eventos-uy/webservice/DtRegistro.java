
package webservice;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtRegistro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtRegistro">
 *   <complexContent>
 *     <extension base="{http://webservice/}record">
 *       <sequence>
 *         <element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="nombreEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombreEdicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nicknameAsistente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fotoAsistente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tipoRegistro" type="{http://webservice/}dtTipoRegistro" minOccurs="0"/>
 *         <element name="usoCodigo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtRegistro", propOrder = {
    "fecha",
    "costo",
    "nombreEvento",
    "nombreEdicion",
    "nicknameAsistente",
    "fotoAsistente",
    "tipoRegistro",
    "usoCodigo"
})
public class DtRegistro
    extends Record
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected float costo;
    protected String nombreEvento;
    protected String nombreEdicion;
    protected String nicknameAsistente;
    protected String fotoAsistente;
    protected DtTipoRegistro tipoRegistro;
    protected Boolean usoCodigo;

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
     * Obtiene el valor de la propiedad nombreEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * Define el valor de la propiedad nombreEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEvento(String value) {
        this.nombreEvento = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEdicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    /**
     * Define el valor de la propiedad nombreEdicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEdicion(String value) {
        this.nombreEdicion = value;
    }

    /**
     * Obtiene el valor de la propiedad nicknameAsistente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNicknameAsistente() {
        return nicknameAsistente;
    }

    /**
     * Define el valor de la propiedad nicknameAsistente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNicknameAsistente(String value) {
        this.nicknameAsistente = value;
    }

    /**
     * Obtiene el valor de la propiedad fotoAsistente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFotoAsistente() {
        return fotoAsistente;
    }

    /**
     * Define el valor de la propiedad fotoAsistente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFotoAsistente(String value) {
        this.fotoAsistente = value;
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
     * Obtiene el valor de la propiedad usoCodigo.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsoCodigo() {
        return usoCodigo;
    }

    /**
     * Define el valor de la propiedad usoCodigo.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsoCodigo(Boolean value) {
        this.usoCodigo = value;
    }

}
