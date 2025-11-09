
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

    private final static QName _DTAsistente_QNAME = new QName("http://webservice/", "DTAsistente");
    private final static QName _DTOrganizador_QNAME = new QName("http://webservice/", "DTOrganizador");
    private final static QName _Exception_QNAME = new QName("http://webservice/", "Exception");
    private final static QName _InstitucionNoExistenteException_QNAME = new QName("http://webservice/", "InstitucionNoExistenteException");
    private final static QName _UsuarioCorreoRepetidoException_QNAME = new QName("http://webservice/", "UsuarioCorreoRepetidoException");
    private final static QName _UsuarioInexistenteException_QNAME = new QName("http://webservice/", "UsuarioInexistenteException");
    private final static QName _UsuarioNicknameRepetidoException_QNAME = new QName("http://webservice/", "UsuarioNicknameRepetidoException");
    private final static QName _Asistentes_QNAME = new QName("http://webservice/", "asistentes");
    private final static QName _Registros_QNAME = new QName("http://webservice/", "registros");
    private final static QName _Usuarios_QNAME = new QName("http://webservice/", "usuarios");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DtAsistente }
     * 
     * @return
     *     the new instance of {@link DtAsistente }
     */
    public DtAsistente createDtAsistente() {
        return new DtAsistente();
    }

    /**
     * Create an instance of {@link DtOrganizador }
     * 
     * @return
     *     the new instance of {@link DtOrganizador }
     */
    public DtOrganizador createDtOrganizador() {
        return new DtOrganizador();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     * @return
     *     the new instance of {@link Exception }
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link InstitucionNoExistenteException }
     * 
     * @return
     *     the new instance of {@link InstitucionNoExistenteException }
     */
    public InstitucionNoExistenteException createInstitucionNoExistenteException() {
        return new InstitucionNoExistenteException();
    }

    /**
     * Create an instance of {@link UsuarioCorreoRepetidoException }
     * 
     * @return
     *     the new instance of {@link UsuarioCorreoRepetidoException }
     */
    public UsuarioCorreoRepetidoException createUsuarioCorreoRepetidoException() {
        return new UsuarioCorreoRepetidoException();
    }

    /**
     * Create an instance of {@link UsuarioInexistenteException }
     * 
     * @return
     *     the new instance of {@link UsuarioInexistenteException }
     */
    public UsuarioInexistenteException createUsuarioInexistenteException() {
        return new UsuarioInexistenteException();
    }

    /**
     * Create an instance of {@link UsuarioNicknameRepetidoException }
     * 
     * @return
     *     the new instance of {@link UsuarioNicknameRepetidoException }
     */
    public UsuarioNicknameRepetidoException createUsuarioNicknameRepetidoException() {
        return new UsuarioNicknameRepetidoException();
    }

    /**
     * Create an instance of {@link DtAsistenteArray }
     * 
     * @return
     *     the new instance of {@link DtAsistenteArray }
     */
    public DtAsistenteArray createDtAsistenteArray() {
        return new DtAsistenteArray();
    }

    /**
     * Create an instance of {@link DtRegistroArray }
     * 
     * @return
     *     the new instance of {@link DtRegistroArray }
     */
    public DtRegistroArray createDtRegistroArray() {
        return new DtRegistroArray();
    }

    /**
     * Create an instance of {@link DtUsuarioItemListadoArray }
     * 
     * @return
     *     the new instance of {@link DtUsuarioItemListadoArray }
     */
    public DtUsuarioItemListadoArray createDtUsuarioItemListadoArray() {
        return new DtUsuarioItemListadoArray();
    }

    /**
     * Create an instance of {@link DtUsuarioItemListado }
     * 
     * @return
     *     the new instance of {@link DtUsuarioItemListado }
     */
    public DtUsuarioItemListado createDtUsuarioItemListado() {
        return new DtUsuarioItemListado();
    }

    /**
     * Create an instance of {@link DtRegistroDetallado }
     * 
     * @return
     *     the new instance of {@link DtRegistroDetallado }
     */
    public DtRegistroDetallado createDtRegistroDetallado() {
        return new DtRegistroDetallado();
    }

    /**
     * Create an instance of {@link DtEvento }
     * 
     * @return
     *     the new instance of {@link DtEvento }
     */
    public DtEvento createDtEvento() {
        return new DtEvento();
    }

    /**
     * Create an instance of {@link DtEdicion }
     * 
     * @return
     *     the new instance of {@link DtEdicion }
     */
    public DtEdicion createDtEdicion() {
        return new DtEdicion();
    }

    /**
     * Create an instance of {@link DtUsuario }
     * 
     * @return
     *     the new instance of {@link DtUsuario }
     */
    public DtUsuario createDtUsuario() {
        return new DtUsuario();
    }

    /**
     * Create an instance of {@link DtTipoRegistro }
     * 
     * @return
     *     the new instance of {@link DtTipoRegistro }
     */
    public DtTipoRegistro createDtTipoRegistro() {
        return new DtTipoRegistro();
    }

    /**
     * Create an instance of {@link Optional }
     * 
     * @return
     *     the new instance of {@link Optional }
     */
    public Optional createOptional() {
        return new Optional();
    }

    /**
     * Create an instance of {@link DtRegistro }
     * 
     * @return
     *     the new instance of {@link DtRegistro }
     */
    public DtRegistro createDtRegistro() {
        return new DtRegistro();
    }

    /**
     * Create an instance of {@link LocalDate }
     * 
     * @return
     *     the new instance of {@link LocalDate }
     */
    public LocalDate createLocalDate() {
        return new LocalDate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtAsistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtAsistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "DTAsistente")
    public JAXBElement<DtAsistente> createDTAsistente(DtAsistente value) {
        return new JAXBElement<>(_DTAsistente_QNAME, DtAsistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtOrganizador }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtOrganizador }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "DTOrganizador")
    public JAXBElement<DtOrganizador> createDTOrganizador(DtOrganizador value) {
        return new JAXBElement<>(_DTOrganizador_QNAME, DtOrganizador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InstitucionNoExistenteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InstitucionNoExistenteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "InstitucionNoExistenteException")
    public JAXBElement<InstitucionNoExistenteException> createInstitucionNoExistenteException(InstitucionNoExistenteException value) {
        return new JAXBElement<>(_InstitucionNoExistenteException_QNAME, InstitucionNoExistenteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioCorreoRepetidoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioCorreoRepetidoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "UsuarioCorreoRepetidoException")
    public JAXBElement<UsuarioCorreoRepetidoException> createUsuarioCorreoRepetidoException(UsuarioCorreoRepetidoException value) {
        return new JAXBElement<>(_UsuarioCorreoRepetidoException_QNAME, UsuarioCorreoRepetidoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioInexistenteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioInexistenteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "UsuarioInexistenteException")
    public JAXBElement<UsuarioInexistenteException> createUsuarioInexistenteException(UsuarioInexistenteException value) {
        return new JAXBElement<>(_UsuarioInexistenteException_QNAME, UsuarioInexistenteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioNicknameRepetidoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioNicknameRepetidoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "UsuarioNicknameRepetidoException")
    public JAXBElement<UsuarioNicknameRepetidoException> createUsuarioNicknameRepetidoException(UsuarioNicknameRepetidoException value) {
        return new JAXBElement<>(_UsuarioNicknameRepetidoException_QNAME, UsuarioNicknameRepetidoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtAsistenteArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtAsistenteArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "asistentes")
    public JAXBElement<DtAsistenteArray> createAsistentes(DtAsistenteArray value) {
        return new JAXBElement<>(_Asistentes_QNAME, DtAsistenteArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtRegistroArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtRegistroArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "registros")
    public JAXBElement<DtRegistroArray> createRegistros(DtRegistroArray value) {
        return new JAXBElement<>(_Registros_QNAME, DtRegistroArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtUsuarioItemListadoArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtUsuarioItemListadoArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "usuarios")
    public JAXBElement<DtUsuarioItemListadoArray> createUsuarios(DtUsuarioItemListadoArray value) {
        return new JAXBElement<>(_Usuarios_QNAME, DtUsuarioItemListadoArray.class, null, value);
    }

}
