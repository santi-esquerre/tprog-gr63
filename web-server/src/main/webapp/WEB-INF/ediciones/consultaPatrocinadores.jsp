<%@ page import="datatypes.DTPatrocinio, datatypes.DTRegistro, datatypes.NivelPatrocinio, java.time.LocalDate, java.time.ZoneId" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

var patrocinador = (DTPatrocinio) request.getAttribute("patrocinador");
String nivel;
if (patrocinador.nivel() == NivelPatrocinio.ORO) {
	nivel = "ORO";
}
else if (patrocinador.nivel() == NivelPatrocinio.PLATINO) {
	nivel = "PLATINO";
}
else if (patrocinador.nivel() == NivelPatrocinio.PLATA) {
	nivel = "PLATA";
}
else {
	nivel = "BRONCE";
}

LocalDate fecha = patrocinador.fechaRealizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	

%>

<div class="modal fade text-black text-start" id="<%=patrocinador.institucion().nombre()%>patrocinadorModal" tabindex="-1" aria-labelledby="PatrocinioModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tipoRegistroModalLabel">Información sobre el Patrocinio</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4" id="infoTipoRegistro">
                    <p>Nombre de la Institución:  <%=patrocinador.institucion().nombre()%></p>
                    <p>Fecha de Realización: <%=fecha %></p>
                    <p>Aporte: $<%=(int) patrocinador.monto()%></p>
                    <p>Nivel: <%=nivel%></p>
                </div>
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    
    