
package webservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtEdicionDetallada complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtEdicionDetallada">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="edicion" type="{http://webservice/}dtEdicion" minOccurs="0"/>
 *         <element name="organizador" type="{http://webservice/}dtOrganizador" minOccurs="0"/>
 *         <element name="tiposRegistro" type="{http://webservice/}dtTipoRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="patrocinios" type="{http://webservice/}dtPatrocinio" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="registros" type="{http://webservice/}dtRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtEdicionDetallada", propOrder = {
    "edicion",
    "organizador",
    "tiposRegistro",
    "patrocinios",
    "registros"
})
public class DtEdicionDetallada {

    protected DtEdicion edicion;
    protected DtOrganizador organizador;
    @XmlElement(nillable = true)
    protected List<DtTipoRegistro> tiposRegistro;
    @XmlElement(nillable = true)
    protected List<DtPatrocinio> patrocinios;
    @XmlElement(nillable = true)
    protected List<DtRegistro> registros;

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
     * Obtiene el valor de la propiedad organizador.
     * 
     * @return
     *     possible object is
     *     {@link DtOrganizador }
     *     
     */
    public DtOrganizador getOrganizador() {
        return organizador;
    }

    /**
     * Define el valor de la propiedad organizador.
     * 
     * @param value
     *     allowed object is
     *     {@link DtOrganizador }
     *     
     */
    public void setOrganizador(DtOrganizador value) {
        this.organizador = value;
    }

    /**
     * Gets the value of the tiposRegistro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the tiposRegistro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTiposRegistro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtTipoRegistro }
     * 
     * 
     * @return
     *     The value of the tiposRegistro property.
     */
    public List<DtTipoRegistro> getTiposRegistro() {
        if (tiposRegistro == null) {
            tiposRegistro = new ArrayList<>();
        }
        return this.tiposRegistro;
    }

    /**
     * Gets the value of the patrocinios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the patrocinios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatrocinios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtPatrocinio }
     * 
     * 
     * @return
     *     The value of the patrocinios property.
     */
    public List<DtPatrocinio> getPatrocinios() {
        if (patrocinios == null) {
            patrocinios = new ArrayList<>();
        }
        return this.patrocinios;
    }

    /**
     * Gets the value of the registros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the registros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtRegistro }
     * 
     * 
     * @return
     *     The value of the registros property.
     */
    public List<DtRegistro> getRegistros() {
        if (registros == null) {
            registros = new ArrayList<>();
        }
        return this.registros;
    }

}
