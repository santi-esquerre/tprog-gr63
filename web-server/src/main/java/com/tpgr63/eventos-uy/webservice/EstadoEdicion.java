
package webservice;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para EstadoEdicion.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="EstadoEdicion">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="INGRESADA"/>
 *     <enumeration value="ACEPTADA"/>
 *     <enumeration value="RECHAZADA"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "EstadoEdicion")
@XmlEnum
public enum EstadoEdicion {

    INGRESADA,
    ACEPTADA,
    RECHAZADA;

    public String value() {
        return name();
    }

    public static EstadoEdicion fromValue(String v) {
        return valueOf(v);
    }

}
