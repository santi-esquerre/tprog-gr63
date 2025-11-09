
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

    private final static QName _InstitucionRepetidaException_QNAME = new QName("http://webservice/", "InstitucionRepetidaException");
    private final static QName _Instituciones_QNAME = new QName("http://webservice/", "instituciones");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InstitucionRepetidaException }
     * 
     * @return
     *     the new instance of {@link InstitucionRepetidaException }
     */
    public InstitucionRepetidaException createInstitucionRepetidaException() {
        return new InstitucionRepetidaException();
    }

    /**
     * Create an instance of {@link DtInstitucionArray }
     * 
     * @return
     *     the new instance of {@link DtInstitucionArray }
     */
    public DtInstitucionArray createDtInstitucionArray() {
        return new DtInstitucionArray();
    }

    /**
     * Create an instance of {@link DtPatrocinio }
     * 
     * @return
     *     the new instance of {@link DtPatrocinio }
     */
    public DtPatrocinio createDtPatrocinio() {
        return new DtPatrocinio();
    }

    /**
     * Create an instance of {@link DtInstitucion }
     * 
     * @return
     *     the new instance of {@link DtInstitucion }
     */
    public DtInstitucion createDtInstitucion() {
        return new DtInstitucion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InstitucionRepetidaException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InstitucionRepetidaException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "InstitucionRepetidaException")
    public JAXBElement<InstitucionRepetidaException> createInstitucionRepetidaException(InstitucionRepetidaException value) {
        return new JAXBElement<>(_InstitucionRepetidaException_QNAME, InstitucionRepetidaException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtInstitucionArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtInstitucionArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "instituciones")
    public JAXBElement<DtInstitucionArray> createInstituciones(DtInstitucionArray value) {
        return new JAXBElement<>(_Instituciones_QNAME, DtInstitucionArray.class, null, value);
    }

}
