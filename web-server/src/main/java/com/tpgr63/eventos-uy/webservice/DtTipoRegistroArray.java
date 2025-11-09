
package webservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtTipoRegistroArray complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtTipoRegistroArray">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="tipoRegistro" type="{http://webservice/}dtTipoRegistro" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtTipoRegistroArray", propOrder = {
    "tipoRegistro"
})
public class DtTipoRegistroArray {

    protected List<DtTipoRegistro> tipoRegistro;

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the tipoRegistro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoRegistro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtTipoRegistro }
     * 
     * 
     * @return
     *     The value of the tipoRegistro property.
     */
    public List<DtTipoRegistro> getTipoRegistro() {
        if (tipoRegistro == null) {
            tipoRegistro = new ArrayList<>();
        }
        return this.tipoRegistro;
    }

}
