<%@ page import="webservice.DtEdicionDetallada, webservice.DtRegistro, javax.xml.datatype.XMLGregorianCalendar, java.time.LocalDate, java.time.ZoneId" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% DtEdicionDetallada edicionDetallada = (DtEdicionDetallada) request.getAttribute("edicionDetallada");
    java.util.List<DtRegistro> registros = edicionDetallada.getRegistros();	  
%>

<style>

details summary {
  list-style: none;      /* quita la flecha nativa */
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}

details[open] .bi-arrow-right-short {
    transform: rotate(90deg); /* flecha apunta hacia abajo */
    transition: transform 0.2s;
}

</style>

    <div class="modal fade" id="registrosEdicionModal" tabindex="-1" aria-labelledby="registrosEdicionLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registrosEdicionLabel">Registros a la Edición</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">               
                   <ol class="list-group" id="registroUsuarios">
                   <% for(DtRegistro registro : registros) { 
                	   XMLGregorianCalendar xgc = registro.getFecha();
                	   LocalDate fecha = xgc != null ? xgc.toGregorianCalendar().toZonedDateTime().toLocalDate() : null;
                   
                   %>
                   		
                         <li class="list-group-item d-flex align-items-start flex-wrap p-4" >
						 <details>
						 	<summary>
						 	<i class="bi bi-arrow-right-short"></i>
                            <div class="row g-3 w-100">
                                <div class="d-flex align-items-center col-8">
                                    <img class="rounded-1 align-self-center" src="<%=request.getContextPath()%>/image/<%=registro.getFotoAsistente()%>" style="width: 40px; height: 40px;">
                                
                                    <div class="ms-3 w-100 mb-0 p-0">
                                        <div class="d-flex align-items-center gap-2">
                                            <p class="fw-bold mb-0"><%=registro.getNicknameAsistente()%></p>
                                            <span class="badge text-bg-primary rounded-pill align-self-center"><%= registro.getTipoRegistro() != null ? registro.getTipoRegistro().getNombre() : "" %></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </summary>
                            <div class="pt-1 ms-4 text-muted">
                      	        		<div class="mb-0 mt-0 p-0 d-flex gap-2">
                            	        	<p class="fw-bold mb-0 p-0">Tipo de registro: </p>
                                	         <p class="mb-0 p-0"><%=registro.getTipoRegistro() != null ? registro.getTipoRegistro().getNombre() : "" %></p>
                            	        </div>
                            	        <div class="mb-0 mt-0 p-0 d-flex gap-2">
                            	        	<p class="fw-bold mb-0 p-0">Fecha de registro: </p>
                                	         <p class="mb-0 p-0"><%= fecha != null ? fecha : "" %></p>
                            	        </div>
                                        <div class="mb-0 mt-0 p-0 d-flex gap-2">
                                        	<p class="fw-bold">Uso de código:</p>
                                         	<i class="bi text-primary <%= (registro.isUsoCodigo() != null && registro.isUsoCodigo()) ? "bi bi-check-circle-fill" : "bi-x-circle-fill" %>"></i>
                                        </div>
                            	        </div>
                                        <div class="mb-0 mt-0 p-0 d-flex gap-2">
                                        	<p class="fw-bold">Asistencia confirmada:</p>
                                         	<i class="bi text-primary <%= (registro.isAsistenciaConfirmada() != null && registro.isAsistenciaConfirmada()) ? "bi bi-check-circle-fill" : "bi-x-circle-fill" %>"></i>
                                        </div>                                        
                            </div>
                        </details>
                        </li>                  
					
                   
                                   
                   <% } %>
                   </ol>        
                   
           
                  
                </div>  
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div> 
    
       
    