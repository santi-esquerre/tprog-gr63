<%@page import="webservice.EstadoEdicion"%>
<%@page import="webservice.DtEdicion"%>
<%@page import="webservice.DtEventoDetallado"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="light">
<%
DtEventoDetallado evento = (DtEventoDetallado) request.getAttribute("data_evento");
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
// evento.getEdiciones() is available
%>
<head>
    <title>Consulta Detallada de Edición</title>
    
   <jsp:include page="/WEB-INF/template/head.jsp" />
    
</head>
<body class="d-flex flex-column h-100 bg-body-tertiary">
     <!-- NAV -->
     <jsp:include page="/WEB-INF/template/header.jsp" />
 
    <section class="position-relative" style="min-height: 500px; background-image: url('<%=request.getContextPath()%>/image/<%=evento.getEvento()!=null?evento.getEvento().getFoto():"" %>'); background-size: cover; background-position: center;">
        <div class="position-absolute bottom-0 start-0 w-100" style="height: 40%;background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);">
            
            <div class="container h-100 d-flex flex-column justify-content-end pb-4">

                <h1 class="fw-bold text-white fs-2 fs-md-1" id="nombreEvento"><%=evento.getEvento()!=null?evento.getEvento().getNombre():"" %></h1>
                <p class="fs-5 fs-md-5 text-white mb-0 fw-bold" id="siglaEvento"><%=evento.getEvento()!=null?evento.getEvento().getSigla():"" %></p>
            </div>
        </div>
    </section>
    <main class="flex-shrink-0 mt-5 mb-5">
        <div class="container p-5 rounded-4 border bg-body shadow-sm">
            <section class>
                <h3>Descripción </h3>
                <p class="fs-5 fs-md-5" id="descripcionEvento"><%=evento.getEvento()!=null && evento.getEvento().getDescripcion()!=null ? evento.getEvento().getDescripcion() : "" %></p>
                <%
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
String fechaAltaStr = "";
if (evento.getEvento() != null && evento.getEvento().getFechaAlta() != null) {
    java.util.Date fecha = evento.getEvento().getFechaAlta().toGregorianCalendar().getTime();
    fechaAltaStr = sdf.format(fecha);
}
%>

<p class="text-muted fst-italic mb-0">
    Evento activo desde <%= fechaAltaStr %>
</p>
                <br>
                <div class="d-flex flex-row flex-md-row gap-2">
                    <% for(String cat : evento.getCategorias()) { %>
                    	<a class="badge fs-6 fs-md-5 btn btn-primary" id="categoriaEvento" href="<%=request.getContextPath() + "/eventos?categoria=" + cat %>"><%=cat %></a>
                    <% } %>
                </div>
            </section> 
        </div>

        <div class="container my-5">
            <div class="card shadow-sm">
                <div class="card-body row g-3 p-5 edicionesContainer">
                    <h3 class="mb-4">Ediciones</h3>
                    <% 
if (evento.getEdiciones() == null || evento.getEdiciones().isEmpty()) { 
%>
    <p>Parece que no hay ediciones para este evento.</p>
<% 
} else { 
    for (DtEdicion edicion : evento.getEdiciones()) {
        String estado;
        switch (edicion.getEstado()) {
            case ACEPTADA -> estado = "Aceptada";
            case RECHAZADA -> estado = "Rechazada";
            default -> estado = "Ingresada";
        }

        String fechaInicio = (edicion.getFechaInicio() != null)
            ? formatter.format(edicion.getFechaInicio().toGregorianCalendar().getTime())
            : "";
        String fechaFin = (edicion.getFechaFin() != null)
            ? formatter.format(edicion.getFechaFin().toGregorianCalendar().getTime())
            : "";
        
%>

    <jsp:include page="/WEB-INF/ediciones/edicionCard.jsp">
        <jsp:param name="nombre" value="<%= edicion.getNombre() %>"/>
        <jsp:param name="sigla" value="<%= edicion.getSigla() %>"/>
        <jsp:param name="ciudad" value="<%= edicion.getCiudad() %>"/>
        <jsp:param name="pais" value="<%= edicion.getPais() %>"/>
        <jsp:param name="fecha_inicio" value="<%= fechaInicio %>"/>
        <jsp:param name="fecha_fin" value="<%= fechaFin %>"/>
        <jsp:param name="estado" value="<%= estado %>"/>
        <jsp:param name="foto" value="<%= edicion.getFoto() %>"/>
    </jsp:include>

<% 
    } // fin for
} // fin else
%>

            </div>
        </div>   
    </main>
</body>
</html>