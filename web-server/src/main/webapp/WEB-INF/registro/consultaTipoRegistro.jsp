<%@ page import="datatypes.DTTipoRegistro" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

var tipoRegistro = (DTTipoRegistro) request.getAttribute("tipoRegistro");

%>

<div class="modal fade" id="<%=tipoRegistro.nombre()%>consultaModal" tabindex="-1" aria-labelledby="tipoRegistroModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tipoRegistroModalLabel">Información sobre el Tipo de Registro</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4" id="infoTipoRegistro">
                    <p>Nombre:  <%=tipoRegistro.nombre()%></p>
                    <p>Precio: $<%=(int) tipoRegistro.costo()%></p>
                    <p>Descripción: <%=tipoRegistro.descripcion()%></p>
                    <p>Cupos disponibles: <%=tipoRegistro.cupo()%></p>
                </div>
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div>