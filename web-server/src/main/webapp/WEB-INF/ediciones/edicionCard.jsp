<%@page import="datatypes.DTEdicion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String nombre = request.getParameter("nombre");
String sigla = request.getParameter("sigla");
String ubicacion = request.getParameter("ciudad") + ", " + request.getParameter("pais");
String fecha = request.getParameter("fecha_inicio") + " - " + request.getParameter("fecha_fin");
String estado = request.getParameter("estado");  // Aprobada, Rechazada, Ingresada
String foto = request.getParameter("foto"); // URL de la foto
%>
<div class="col-12 col-xxl-3 col-xl-4 col-md-6 col-12 edicionContainer">
                        <a class="text-decoration-none edicionLink" href="<%=request.getContextPath()%>/edicion/<%=nombre.replace(' ','-')%>">
<div class="card bg-body h-100 shadow-sm border-0">
<img
class="card-img-top fotoEdicion"
                            src="<%=request.getContextPath()%>/image/<%=foto %>"
                            alt="Banner edición"
                            style="aspect-ratio: 600 / 350;"
                            />
                            <div class="card-body">
                                
                            <div
                                class="d-flex align-items-center justify-content-between flex-wrap"
                                >

                            <span class="text-muted siglaEdicion"><%=sigla %></span>
                                    <span class="badge bg-<%= estado.equals("Aceptada") ? "success" : (estado.equals("Rechazada") ? "danger" : "primary") %> estadoEdicion"><%=estado %></span>
                            </div>
                            
                                    <div class="h5 card-title mb-2 nombreEdicion">
                                        <%=nombre %>
                                    </div>
                            <div class="mb-1">
                                <i class="bi bi-calendar-fill"></i>&nbsp;<span class="fechaEdicion"><%=fecha %></span> 
                            </div>
                            <div class="fw-semibold">
                                <i class="bi bi-geo-fill"></i><span class="ubicacionEdicion"><%=ubicacion %></span>
                            </div>
                        
                            </div>
</div>
</a>                 
</div>