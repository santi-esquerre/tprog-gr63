
package webservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtAsistenteArray complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtAsistenteArray">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="asistente" type="{http://webservice/}dtAsistente" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtAsistenteArray", propOrder = {
    "asistente"
})
public class DtAsistenteArray {

    protected List<DtAsistente> asistente;

    /**
     * Gets the value of the asistente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the asistente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsistente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtAsistente }
     * 
     * 
     * @return
     *     The value of the asistente property.
     */
    public List<DtAsistente> getAsistente() {
        if (asistente == null) {
            asistente = new ArrayList<>();
        }
        return this.asistente;
    }

}
