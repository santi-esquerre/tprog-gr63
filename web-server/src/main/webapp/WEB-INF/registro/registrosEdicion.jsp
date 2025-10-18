<%@ page import="datatypes.DTEdicionDetallada, datatypes.DTRegistro, java.time.LocalDate, java.time.ZoneId" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% var edicionDetallada = (DTEdicionDetallada)request.getAttribute("edicionDetallada");
   var registros = edicionDetallada.registros();		  
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
                   <% for(DTRegistro registro : registros) { 
                	   LocalDate fecha = registro.fecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                   
                   %>
                   		
                         <li class="list-group-item d-flex align-items-start flex-wrap p-4" >
						 <details>
						 	<summary>
						 	<i class="bi bi-arrow-right-short"></i>
                            <div class="row g-3 w-100">
                                <div class="d-flex align-items-center col-8">
                                    <img class="rounded-1 align-self-center" src="<%=request.getContextPath()%>/image/<%=registro.fotoAsistente()%>" style="width: 40px; height: 40px;">
                                
                                    <div class="ms-3 w-100 mb-0 p-0">
                                        <div class="d-flex align-items-center gap-2">
                                            <p class="fw-bold mb-0"><%=registro.nicknameAsistente()%></p>
                                            <span class="badge text-bg-primary rounded-pill align-self-center"><%=registro.tipoRegistro().nombre() %></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </summary>
                            <div class="pt-1 ms-4 text-muted">
                      	        		<div class="mb-0 mt-0 p-0 d-flex gap-2">
                            	        	<p class="fw-bold mb-0 p-0">Tipo de registro: </p>
                            	        	<p class="mb-0 p-0"><%=registro.tipoRegistro().nombre() %></p>
                            	        </div>
                            	        <div class="mb-0 mt-0 p-0 d-flex gap-2">
                            	        	<p class="fw-bold mb-0 p-0">Fecha de registro: </p>
                            	        	<p class="mb-0 p-0"><%=fecha %></p>
                            	        </div>
                                        <div class="mb-0 mt-0 p-0 d-flex gap-2">
                                        	<p class="fw-bold">Uso de código:</p>
                                        	<i class="bi text-primary <%=registro.usoCodigo() ? "bi bi-check-circle-fill" : "bi-x-circle-fill"%>"></i>
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
    
       
    