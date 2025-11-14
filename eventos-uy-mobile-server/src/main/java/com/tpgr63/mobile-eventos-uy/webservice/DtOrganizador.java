
package webservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtOrganizador complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtOrganizador">
 *   <complexContent>
 *     <extension base="{http://webservice/}dtUsuario">
 *       <sequence>
 *         <element name="linkSitioWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtOrganizador", propOrder = {
    "linkSitioWeb",
    "descripcion"
})
public class DtOrganizador
    extends DtUsuario
{

    protected String linkSitioWeb;
    protected String descripcion;

    /**
     * Obtiene el valor de la propiedad linkSitioWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkSitioWeb() {
        return linkSitioWeb;
    }

    /**
     * Define el valor de la propiedad linkSitioWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkSitioWeb(String value) {
        this.linkSitioWeb = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

}
