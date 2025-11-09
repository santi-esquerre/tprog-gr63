<%@ page import="webservice.DtPatrocinio, webservice.DtRegistro, webservice.NivelPatrocinio, javax.xml.datatype.XMLGregorianCalendar, java.time.ZoneId, java.time.LocalDate" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%


DtPatrocinio patrocinador = (DtPatrocinio) request.getAttribute("patrocinador");
String nivel;
if (patrocinador.getNivel() == NivelPatrocinio.ORO) {
    nivel = "ORO";
}
else if (patrocinador.getNivel() == NivelPatrocinio.PLATINO) {
    nivel = "PLATINO";
}
else if (patrocinador.getNivel() == NivelPatrocinio.PLATA) {
    nivel = "PLATA";
}
else {
    nivel = "BRONCE";
}

XMLGregorianCalendar xgc = patrocinador.getFechaRealizacion();
LocalDate fecha = xgc != null ? xgc.toGregorianCalendar().toZonedDateTime().toLocalDate() : null;
	

%>

<div class="modal fade text-black text-start" id="<%=patrocinador.getInstitucion().getNombre()%>patrocinadorModal" tabindex="-1" aria-labelledby="PatrocinioModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tipoRegistroModalLabel">Información sobre el Patrocinio</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4" id="infoTipoRegistro">
                    <p>Nombre de la Institución:  <%=patrocinador.getInstitucion().getNombre()%></p>
                    <p>Fecha de Realización: <%=fecha %></p>
                    <p>Aporte: $<%=(int) patrocinador.getMonto()%></p>
                    <p>Nivel: <%=nivel%></p>
                </div>
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    
    