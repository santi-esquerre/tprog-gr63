<%@ page import="webservice.DtEdicion, webservice.DtTipoRegistro, webservice.DtUsuario, webservice.DtAsistente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 

DtEdicion edicion = (DtEdicion) request.getAttribute("edicion");
DtTipoRegistro tipoRegistro = (DtTipoRegistro) request.getAttribute("tipoRegistro");
DtUsuario dtUsuario = (DtUsuario) request.getAttribute("usuario");
DtAsistente dtAsistente = (DtAsistente) dtUsuario;

%>

  
  <div class="modal fade" id="<%=tipoRegistro.getNombre()%>registroModal" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-fullscreen-sm-down modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registroModalLabel">Registro a Edición de Evento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="d-flex justify-content-start mb-3" style="background-image: url('<%= request.getContextPath() %>/image/<%=edicion.getFoto()%>'); height: 75px; background-size: cover; background-position:center;">
                        <h4 class="ms-3 fs-5 text-white fw-bold" style="line-height: 75px;"><%=edicion.getNombre() %></h4>
                    </div>
                    <form action="<%= request.getContextPath()%>/edicion/<%= edicion.getNombre().replace(" ", "-") %>" method="POST">
                        <div class="mb-3">
                            <label for="nickname" class="col-form-label">Nickname:</label>
                            <input type="text" class="form-control" id="nickname" name="nickname" value="<%=dtAsistente.getNickname()%>" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="col-form-label">Nombre:</label>
                            <input type="text" class="form-control" id="nombre" value="<%=dtAsistente.getNombre()%>" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="apellido" class="col-form-label">Apellido:</label>
                            <input type="text" class="form-control" id="apellido" value="<%=dtAsistente.getApellido()%>" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="col-form-label">Correo electrónico:</label>
                            <input type="email" class="form-control" id="correo" value="<%=dtAsistente.getCorreo()%>" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="tipoEntrada" class="col-form-label">Tipo de Entrada:</label>
                            <input type="tipoEntrada" class="form-control" id="tipoEntrada" value="<%=tipoRegistro.getNombre() %> - $<%=tipoRegistro.getCosto()%>" disabled>
                        </div>
                        <input type="hidden" name="nickname" value="<%=dtAsistente.getNickname()%>">
                        <input type="hidden" name="nombreEdicion" value="<%= edicion.getNombre() %>">
                        <input type="hidden" name="nombreTipoRegistro" value="<%= tipoRegistro.getNombre() %>">
                        <div class="mb-3">
                            <label for="codigoPatrocinio" class="col-form-label">Código de Patrocinio:</label>
                            <div class="input-group position-relative">
                                <input type="text" class="form-control" id="codigoPatrocinio" name="codigoPatrocinio" value="<%= (request.getAttribute("abrirModal") != null && request.getAttribute("abrirModal").equals(tipoRegistro.getNombre()) && request.getAttribute("codigoIngresado") != null) ? request.getAttribute("codigoIngresado") : "" %>">
                                <button class="btn btn-outline-primary ms-2" type="submit" name="action" value="validarCodigo" id="validarCodigo">Validar</button>
                                
                                                    <!-- Contenedor el toast -->
					<div class="position-absolute bottom-0 ms-3 mb-4 end-0 p-3" style="z-index: 11">
					    <div class="toast align-items-center text-bg-danger border-0" id="toastError" role="alert">
					        <div class="d-flex">
					            <div class="toast-body">El código está vacío</div>
					            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
					        </div>
					    </div>
					</div>
                                
                                
                            </div>
							<%
							Boolean codigoValido = (Boolean) request.getAttribute("codigoValido");
                            if (request.getAttribute("abrirModal") != null && codigoValido != null && request.getAttribute("abrirModal").equals(tipoRegistro.getNombre())) {
							%>
							    <p class="<%= codigoValido ? "text-success" : "text-danger" %> fs-6 mt-2">
							        <%= codigoValido ? "El código ingresado es correcto" : "El código ingresado es incorrecto" %>
							    </p>
							<% } %>
						</div>
                    <h4 class="mt-4 text-end">Total a Pagar: $<%=tipoRegistro.getCosto()%></h4>
                    <div class="input-group d-flex justify-content-end mt-4 mb-4">
                        <button class="btn btn-primary text-end" type="submit" name="action" value="confirmarRegistro" id="confirmarRegistro">Confirmar Registro</button>
                        <button class="btn btn-outline-secondary ms-2" data-bs-dismiss="modal" type="button" id="cancelarRegistro">Cancelar</button>
                    </div>
                    </form>
                    
                    
                    
                </div>
            </div>
        </div>
    </div>    