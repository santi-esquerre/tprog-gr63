
package webservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtEdicionArray complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtEdicionArray">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="edicion" type="{http://webservice/}dtEdicion" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtEdicionArray", propOrder = {
    "edicion"
})
public class DtEdicionArray {

    protected List<DtEdicion> edicion;

    /**
     * Gets the value of the edicion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the edicion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEdicion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtEdicion }
     * 
     * 
     * @return
     *     The value of the edicion property.
     */
    public List<DtEdicion> getEdicion() {
        if (edicion == null) {
            edicion = new ArrayList<>();
        }
        return this.edicion;
    }

}
