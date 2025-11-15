
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

    private final static QName _AltaEvento_QNAME = new QName("http://webservice/", "AltaEvento");
    private final static QName _AltaEventoResponse_QNAME = new QName("http://webservice/", "AltaEventoResponse");
    private final static QName _ValidationInputException_QNAME = new QName("http://webservice/", "ValidationInputException");
    private final static QName _AgregarEdicionAEvento_QNAME = new QName("http://webservice/", "agregarEdicionAEvento");
    private final static QName _AgregarEdicionAEventoResponse_QNAME = new QName("http://webservice/", "agregarEdicionAEventoResponse");
    private final static QName _Ediciones_QNAME = new QName("http://webservice/", "ediciones");
    private final static QName _Eventos_QNAME = new QName("http://webservice/", "eventos");
    private final static QName _ListarEventos_QNAME = new QName("http://webservice/", "listarEventos");
    private final static QName _ListarEventosResponse_QNAME = new QName("http://webservice/", "listarEventosResponse");
    private final static QName _ListarUltimosEventos_QNAME = new QName("http://webservice/", "listarUltimosEventos");
    private final static QName _ListarUltimosEventosResponse_QNAME = new QName("http://webservice/", "listarUltimosEventosResponse");
    private final static QName _MostrarEdiciones_QNAME = new QName("http://webservice/", "mostrarEdiciones");
    private final static QName _MostrarEdicionesPorEstado_QNAME = new QName("http://webservice/", "mostrarEdicionesPorEstado");
    private final static QName _MostrarEdicionesPorEstadoResponse_QNAME = new QName("http://webservice/", "mostrarEdicionesPorEstadoResponse");
    private final static QName _MostrarEdicionesResponse_QNAME = new QName("http://webservice/", "mostrarEdicionesResponse");
    private final static QName _ObtenerCategorias_QNAME = new QName("http://webservice/", "obtenerCategorias");
    private final static QName _ObtenerCategoriasResponse_QNAME = new QName("http://webservice/", "obtenerCategoriasResponse");
    private final static QName _ObtenerDatosDetalladosEvento_QNAME = new QName("http://webservice/", "obtenerDatosDetalladosEvento");
    private final static QName _ObtenerDatosDetalladosEventoAprobadas_QNAME = new QName("http://webservice/", "obtenerDatosDetalladosEventoAprobadas");
    private final static QName _ObtenerDatosDetalladosEventoAprobadasResponse_QNAME = new QName("http://webservice/", "obtenerDatosDetalladosEventoAprobadasResponse");
    private final static QName _ObtenerDatosDetalladosEventoResponse_QNAME = new QName("http://webservice/", "obtenerDatosDetalladosEventoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AltaEvento }
     * 
     * @return
     *     the new instance of {@link AltaEvento }
     */
    public AltaEvento createAltaEvento() {
        return new AltaEvento();
    }

    /**
     * Create an instance of {@link AltaEventoResponse }
     * 
     * @return
     *     the new instance of {@link AltaEventoResponse }
     */
    public AltaEventoResponse createAltaEventoResponse() {
        return new AltaEventoResponse();
    }

    /**
     * Create an instance of {@link ValidationInputException }
     * 
     * @return
     *     the new instance of {@link ValidationInputException }
     */
    public ValidationInputException createValidationInputException() {
        return new ValidationInputException();
    }

    /**
     * Create an instance of {@link AgregarEdicionAEvento }
     * 
     * @return
     *     the new instance of {@link AgregarEdicionAEvento }
     */
    public AgregarEdicionAEvento createAgregarEdicionAEvento() {
        return new AgregarEdicionAEvento();
    }

    /**
     * Create an instance of {@link AgregarEdicionAEventoResponse }
     * 
     * @return
     *     the new instance of {@link AgregarEdicionAEventoResponse }
     */
    public AgregarEdicionAEventoResponse createAgregarEdicionAEventoResponse() {
        return new AgregarEdicionAEventoResponse();
    }

    /**
     * Create an instance of {@link DtEdicionArray }
     * 
     * @return
     *     the new instance of {@link DtEdicionArray }
     */
    public DtEdicionArray createDtEdicionArray() {
        return new DtEdicionArray();
    }

    /**
     * Create an instance of {@link DtEventoArray }
     * 
     * @return
     *     the new instance of {@link DtEventoArray }
     */
    public DtEventoArray createDtEventoArray() {
        return new DtEventoArray();
    }

    /**
     * Create an instance of {@link ListarEventos }
     * 
     * @return
     *     the new instance of {@link ListarEventos }
     */
    public ListarEventos createListarEventos() {
        return new ListarEventos();
    }

    /**
     * Create an instance of {@link ListarEventosResponse }
     * 
     * @return
     *     the new instance of {@link ListarEventosResponse }
     */
    public ListarEventosResponse createListarEventosResponse() {
        return new ListarEventosResponse();
    }

    /**
     * Create an instance of {@link ListarUltimosEventos }
     * 
     * @return
     *     the new instance of {@link ListarUltimosEventos }
     */
    public ListarUltimosEventos createListarUltimosEventos() {
        return new ListarUltimosEventos();
    }

    /**
     * Create an instance of {@link ListarUltimosEventosResponse }
     * 
     * @return
     *     the new instance of {@link ListarUltimosEventosResponse }
     */
    public ListarUltimosEventosResponse createListarUltimosEventosResponse() {
        return new ListarUltimosEventosResponse();
    }

    /**
     * Create an instance of {@link MostrarEdiciones }
     * 
     * @return
     *     the new instance of {@link MostrarEdiciones }
     */
    public MostrarEdiciones createMostrarEdiciones() {
        return new MostrarEdiciones();
    }

    /**
     * Create an instance of {@link MostrarEdicionesPorEstado }
     * 
     * @return
     *     the new instance of {@link MostrarEdicionesPorEstado }
     */
    public MostrarEdicionesPorEstado createMostrarEdicionesPorEstado() {
        return new MostrarEdicionesPorEstado();
    }

    /**
     * Create an instance of {@link MostrarEdicionesPorEstadoResponse }
     * 
     * @return
     *     the new instance of {@link MostrarEdicionesPorEstadoResponse }
     */
    public MostrarEdicionesPorEstadoResponse createMostrarEdicionesPorEstadoResponse() {
        return new MostrarEdicionesPorEstadoResponse();
    }

    /**
     * Create an instance of {@link MostrarEdicionesResponse }
     * 
     * @return
     *     the new instance of {@link MostrarEdicionesResponse }
     */
    public MostrarEdicionesResponse createMostrarEdicionesResponse() {
        return new MostrarEdicionesResponse();
    }

    /**
     * Create an instance of {@link ObtenerCategorias }
     * 
     * @return
     *     the new instance of {@link ObtenerCategorias }
     */
    public ObtenerCategorias createObtenerCategorias() {
        return new ObtenerCategorias();
    }

    /**
     * Create an instance of {@link ObtenerCategoriasResponse }
     * 
     * @return
     *     the new instance of {@link ObtenerCategoriasResponse }
     */
    public ObtenerCategoriasResponse createObtenerCategoriasResponse() {
        return new ObtenerCategoriasResponse();
    }

    /**
     * Create an instance of {@link ObtenerDatosDetalladosEvento }
     * 
     * @return
     *     the new instance of {@link ObtenerDatosDetalladosEvento }
     */
    public ObtenerDatosDetalladosEvento createObtenerDatosDetalladosEvento() {
        return new ObtenerDatosDetalladosEvento();
    }

    /**
     * Create an instance of {@link ObtenerDatosDetalladosEventoAprobadas }
     * 
     * @return
     *     the new instance of {@link ObtenerDatosDetalladosEventoAprobadas }
     */
    public ObtenerDatosDetalladosEventoAprobadas createObtenerDatosDetalladosEventoAprobadas() {
        return new ObtenerDatosDetalladosEventoAprobadas();
    }

    /**
     * Create an instance of {@link ObtenerDatosDetalladosEventoAprobadasResponse }
     * 
     * @return
     *     the new instance of {@link ObtenerDatosDetalladosEventoAprobadasResponse }
     */
    public ObtenerDatosDetalladosEventoAprobadasResponse createObtenerDatosDetalladosEventoAprobadasResponse() {
        return new ObtenerDatosDetalladosEventoAprobadasResponse();
    }

    /**
     * Create an instance of {@link ObtenerDatosDetalladosEventoResponse }
     * 
     * @return
     *     the new instance of {@link ObtenerDatosDetalladosEventoResponse }
     */
    public ObtenerDatosDetalladosEventoResponse createObtenerDatosDetalladosEventoResponse() {
        return new ObtenerDatosDetalladosEventoResponse();
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
     * Create an instance of {@link DtEvento }
     * 
     * @return
     *     the new instance of {@link DtEvento }
     */
    public DtEvento createDtEvento() {
        return new DtEvento();
    }

    /**
     * Create an instance of {@link DtEventoDetallado }
     * 
     * @return
     *     the new instance of {@link DtEventoDetallado }
     */
    public DtEventoDetallado createDtEventoDetallado() {
        return new DtEventoDetallado();
    }

    /**
     * Create an instance of {@link DtEventoAlta }
     * 
     * @return
     *     the new instance of {@link DtEventoAlta }
     */
    public DtEventoAlta createDtEventoAlta() {
        return new DtEventoAlta();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AltaEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AltaEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "AltaEvento")
    public JAXBElement<AltaEvento> createAltaEvento(AltaEvento value) {
        return new JAXBElement<>(_AltaEvento_QNAME, AltaEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AltaEventoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AltaEventoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "AltaEventoResponse")
    public JAXBElement<AltaEventoResponse> createAltaEventoResponse(AltaEventoResponse value) {
        return new JAXBElement<>(_AltaEventoResponse_QNAME, AltaEventoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidationInputException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidationInputException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "ValidationInputException")
    public JAXBElement<ValidationInputException> createValidationInputException(ValidationInputException value) {
        return new JAXBElement<>(_ValidationInputException_QNAME, ValidationInputException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgregarEdicionAEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AgregarEdicionAEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "agregarEdicionAEvento")
    public JAXBElement<AgregarEdicionAEvento> createAgregarEdicionAEvento(AgregarEdicionAEvento value) {
        return new JAXBElement<>(_AgregarEdicionAEvento_QNAME, AgregarEdicionAEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgregarEdicionAEventoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AgregarEdicionAEventoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "agregarEdicionAEventoResponse")
    public JAXBElement<AgregarEdicionAEventoResponse> createAgregarEdicionAEventoResponse(AgregarEdicionAEventoResponse value) {
        return new JAXBElement<>(_AgregarEdicionAEventoResponse_QNAME, AgregarEdicionAEventoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtEdicionArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtEdicionArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "ediciones")
    public JAXBElement<DtEdicionArray> createEdiciones(DtEdicionArray value) {
        return new JAXBElement<>(_Ediciones_QNAME, DtEdicionArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DtEventoArray }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DtEventoArray }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "eventos")
    public JAXBElement<DtEventoArray> createEventos(DtEventoArray value) {
        return new JAXBElement<>(_Eventos_QNAME, DtEventoArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarEventos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListarEventos }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "listarEventos")
    public JAXBElement<ListarEventos> createListarEventos(ListarEventos value) {
        return new JAXBElement<>(_ListarEventos_QNAME, ListarEventos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarEventosResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListarEventosResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "listarEventosResponse")
    public JAXBElement<ListarEventosResponse> createListarEventosResponse(ListarEventosResponse value) {
        return new JAXBElement<>(_ListarEventosResponse_QNAME, ListarEventosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarUltimosEventos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListarUltimosEventos }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "listarUltimosEventos")
    public JAXBElement<ListarUltimosEventos> createListarUltimosEventos(ListarUltimosEventos value) {
        return new JAXBElement<>(_ListarUltimosEventos_QNAME, ListarUltimosEventos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarUltimosEventosResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListarUltimosEventosResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "listarUltimosEventosResponse")
    public JAXBElement<ListarUltimosEventosResponse> createListarUltimosEventosResponse(ListarUltimosEventosResponse value) {
        return new JAXBElement<>(_ListarUltimosEventosResponse_QNAME, ListarUltimosEventosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MostrarEdiciones }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MostrarEdiciones }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "mostrarEdiciones")
    public JAXBElement<MostrarEdiciones> createMostrarEdiciones(MostrarEdiciones value) {
        return new JAXBElement<>(_MostrarEdiciones_QNAME, MostrarEdiciones.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesPorEstado }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesPorEstado }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "mostrarEdicionesPorEstado")
    public JAXBElement<MostrarEdicionesPorEstado> createMostrarEdicionesPorEstado(MostrarEdicionesPorEstado value) {
        return new JAXBElement<>(_MostrarEdicionesPorEstado_QNAME, MostrarEdicionesPorEstado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesPorEstadoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesPorEstadoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "mostrarEdicionesPorEstadoResponse")
    public JAXBElement<MostrarEdicionesPorEstadoResponse> createMostrarEdicionesPorEstadoResponse(MostrarEdicionesPorEstadoResponse value) {
        return new JAXBElement<>(_MostrarEdicionesPorEstadoResponse_QNAME, MostrarEdicionesPorEstadoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MostrarEdicionesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "mostrarEdicionesResponse")
    public JAXBElement<MostrarEdicionesResponse> createMostrarEdicionesResponse(MostrarEdicionesResponse value) {
        return new JAXBElement<>(_MostrarEdicionesResponse_QNAME, MostrarEdicionesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCategorias }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerCategorias }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerCategorias")
    public JAXBElement<ObtenerCategorias> createObtenerCategorias(ObtenerCategorias value) {
        return new JAXBElement<>(_ObtenerCategorias_QNAME, ObtenerCategorias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCategoriasResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerCategoriasResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerCategoriasResponse")
    public JAXBElement<ObtenerCategoriasResponse> createObtenerCategoriasResponse(ObtenerCategoriasResponse value) {
        return new JAXBElement<>(_ObtenerCategoriasResponse_QNAME, ObtenerCategoriasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerDatosDetalladosEvento")
    public JAXBElement<ObtenerDatosDetalladosEvento> createObtenerDatosDetalladosEvento(ObtenerDatosDetalladosEvento value) {
        return new JAXBElement<>(_ObtenerDatosDetalladosEvento_QNAME, ObtenerDatosDetalladosEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoAprobadas }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoAprobadas }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerDatosDetalladosEventoAprobadas")
    public JAXBElement<ObtenerDatosDetalladosEventoAprobadas> createObtenerDatosDetalladosEventoAprobadas(ObtenerDatosDetalladosEventoAprobadas value) {
        return new JAXBElement<>(_ObtenerDatosDetalladosEventoAprobadas_QNAME, ObtenerDatosDetalladosEventoAprobadas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoAprobadasResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoAprobadasResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerDatosDetalladosEventoAprobadasResponse")
    public JAXBElement<ObtenerDatosDetalladosEventoAprobadasResponse> createObtenerDatosDetalladosEventoAprobadasResponse(ObtenerDatosDetalladosEventoAprobadasResponse value) {
        return new JAXBElement<>(_ObtenerDatosDetalladosEventoAprobadasResponse_QNAME, ObtenerDatosDetalladosEventoAprobadasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerDatosDetalladosEventoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "obtenerDatosDetalladosEventoResponse")
    public JAXBElement<ObtenerDatosDetalladosEventoResponse> createObtenerDatosDetalladosEventoResponse(ObtenerDatosDetalladosEventoResponse value) {
        return new JAXBElement<>(_ObtenerDatosDetalladosEventoResponse_QNAME, ObtenerDatosDetalladosEventoResponse.class, null, value);
    }

}
