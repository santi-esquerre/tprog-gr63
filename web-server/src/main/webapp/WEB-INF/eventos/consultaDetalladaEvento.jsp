<%@page import="datatypes.EstadoEdicion"%>
<%@page import="datatypes.DTEdicion"%>
<%@page import="datatypes.DTEventoDetallado"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="light">
<%
DTEventoDetallado evento = (DTEventoDetallado) request.getAttribute("data_evento");
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
evento.ediciones();
%>
<head>
    <title>Consulta Detallada de Edición</title>
    
   <jsp:include page="/WEB-INF/template/head.jsp" />
    
</head>
<body class="d-flex flex-column h-100 bg-body-tertiary">
     <!-- NAV -->
     <jsp:include page="/WEB-INF/template/header.jsp" />
 
    <section class="position-relative" style="min-height: 500px; background-image: url('image/<%=evento.evento().foto() %>'); background-size: cover; background-position: center;">
        <div class="position-absolute bottom-0 start-0 w-100" style="height: 40%;background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);">
            
            <div class="container h-100 d-flex flex-column justify-content-end pb-4">

                <h1 class="fw-bold text-white fs-2 fs-md-1" id="nombreEvento"><%=evento.evento().nombre() %></h1>
                <p class="fs-5 fs-md-5 text-white mb-0 fw-bold" id="siglaEvento"><%=evento.evento().sigla() %></p>
            </div>
        </div>
    </section>
    <main class="flex-shrink-0 mt-5 mb-5">
        <div class="container p-5 rounded-4 border bg-body shadow-sm">
            <section class>
                <h3>Descripción </h3>
                <p class="fs-5 fs-md-5" id="descripcionEvento"><%=evento.evento().descripcion() %></p>
            </section> 
        </div>

        <div class="container my-5">
            <div class="card shadow-sm">
                <div class="card-body row g-3 p-5 edicionesContainer">
                    <h3 class="mb-4">Ediciones</h3>
		            <%for (DTEdicion edicion : evento.ediciones()) { String estado = edicion.estado() == EstadoEdicion.ACEPTADA ? "Aceptada" : (edicion.estado() == EstadoEdicion.RECHAZADA ? "Rechazada" : "Ingresada"); %>
                    <jsp:include page="/WEB-INF/ediciones/edicionCard.jsp">
                    	<jsp:param name="nombre" value="<%=edicion.nombre() %>"/>
                        <jsp:param name="sigla" value="<%=edicion.sigla() %>"/>
                        <jsp:param name="ciudad" value="<%=edicion.ciudad()%>"/>
                        <jsp:param name="pais" value="<%=edicion.pais()%>"/>
                        <jsp:param name="fecha_inicio" value="<%=formatter.format(edicion.fechaInicio())%>"/>
                        <jsp:param name="fecha_fin" value="<%=formatter.format(edicion.fechaFin())%>"/>
                        <jsp:param name="estado" value="<%=estado%>"/>
                        <jsp:param name="foto" value="<%=edicion.foto() %>"/>
                    </jsp:include>
                    
                    <%}%>
            </div>
        </div>   
    </main>
</body>
</html>