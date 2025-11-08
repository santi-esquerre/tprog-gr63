
package webservice;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPatrocinio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPatrocinio">
 *   <complexContent>
 *     <extension base="{http://webservice/}record">
 *       <sequence>
 *         <element name="fechaRealizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="monto" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nivel" type="{http://webservice/}nivelPatrocinio" minOccurs="0"/>
 *         <element name="institucion" type="{http://webservice/}dtInstitucion" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPatrocinio", propOrder = {
    "fechaRealizacion",
    "monto",
    "codigo",
    "nivel",
    "institucion"
})
public class DtPatrocinio
    extends Record
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRealizacion;
    protected float monto;
    protected String codigo;
    @XmlSchemaType(name = "string")
    protected NivelPatrocinio nivel;
    protected DtInstitucion institucion;

    /**
     * Obtiene el valor de la propiedad fechaRealizacion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRealizacion() {
        return fechaRealizacion;
    }

    /**
     * Define el valor de la propiedad fechaRealizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRealizacion(XMLGregorianCalendar value) {
        this.fechaRealizacion = value;
    }

    /**
     * Obtiene el valor de la propiedad monto.
     * 
     */
    public float getMonto() {
        return monto;
    }

    /**
     * Define el valor de la propiedad monto.
     * 
     */
    public void setMonto(float value) {
        this.monto = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad nivel.
     * 
     * @return
     *     possible object is
     *     {@link NivelPatrocinio }
     *     
     */
    public NivelPatrocinio getNivel() {
        return nivel;
    }

    /**
     * Define el valor de la propiedad nivel.
     * 
     * @param value
     *     allowed object is
     *     {@link NivelPatrocinio }
     *     
     */
    public void setNivel(NivelPatrocinio value) {
        this.nivel = value;
    }

    /**
     * Obtiene el valor de la propiedad institucion.
     * 
     * @return
     *     possible object is
     *     {@link DtInstitucion }
     *     
     */
    public DtInstitucion getInstitucion() {
        return institucion;
    }

    /**
     * Define el valor de la propiedad institucion.
     * 
     * @param value
     *     allowed object is
     *     {@link DtInstitucion }
     *     
     */
    public void setInstitucion(DtInstitucion value) {
        this.institucion = value;
    }

}
