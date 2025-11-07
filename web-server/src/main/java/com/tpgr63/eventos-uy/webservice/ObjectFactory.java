
package webservice;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HolaMundo_QNAME = new QName("http://webservice/", "holaMundo");
    private final static QName _HolaMundoResponse_QNAME = new QName("http://webservice/", "holaMundoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HolaMundo }
     * 
     */
    public HolaMundo createHolaMundo() {
        return new HolaMundo();
    }

    /**
     * Create an instance of {@link HolaMundoResponse }
     * 
     */
    public HolaMundoResponse createHolaMundoResponse() {
        return new HolaMundoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HolaMundo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HolaMundo }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "holaMundo")
    public JAXBElement<HolaMundo> createHolaMundo(HolaMundo value) {
        return new JAXBElement<HolaMundo>(_HolaMundo_QNAME, HolaMundo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HolaMundoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HolaMundoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "holaMundoResponse")
    public JAXBElement<HolaMundoResponse> createHolaMundoResponse(HolaMundoResponse value) {
        return new JAXBElement<HolaMundoResponse>(_HolaMundoResponse_QNAME, HolaMundoResponse.class, null, value);
    }

}
