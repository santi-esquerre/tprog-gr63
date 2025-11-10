<%@page import="webservice.DtInstitucion, webservice.DtInstitucionArray"%>
<%@ page import="java.time.LocalDate, java.util.Set, java.util.Map, webservice.DtInstitucion" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    
    <link href="media/css/styles.css" rel="stylesheet" />
    <link href="media/css/sb-admin-2.min.css" rel="stylesheet">
    <jsp:include page="/WEB-INF/template/head.jsp" />
</head>
    <body class="d-flex flex-column h-100 bg-primary">
        
    <style>    
.tooltip .tooltip-inner {
    background-color: #be4b49 !important;
    color: white !important;
    padding: 9px;
    font-size: 14px;
}

 .tooltip {
  --bs-tooltip-bg: #be4b49;
}
	

    </style>
     <!-- NAV -->
     <jsp:include page="/WEB-INF/template/header.jsp" />

    <div class="container mt-5 py-5">
      <div class="card mx-auto shadow" style="max-width: 600px;">
                <div class="card-header bg-white text-center">
                    <h2 class="fw-bold" style="color: black;">Crea una cuenta</h2>
                </div>
                <div class="card-body p-4">
                    <form action="<%= request.getContextPath()%>/registro" method="POST" enctype="multipart/form-data">
                    
                		<%
                		String error = (String) request.getAttribute("error");
                		if (error != null) {
                			
                		%>
            			<div class="border border-danger mb-3" style="background-color: rgba(220, 53, 69, 0.3)"><p class="text-center fw-bold" style="color: black;"><%=error%></p> </div>
                		<% } %>
                		
                        <div class="form-floating mb-3 text-muted">
                            <input type="text" class="form-control" name="nickname" id="nickname" placeholder="Nombre de usuario">
                            <label for="nickname">Nombre de usuario</label>
                        </div>
                        <div class="form-floating mb-3 text-muted">
                            <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Nombre">
                            <label for="nombre">Nombre</label>
                        </div>
                        <div class="form-floating mb-3 text-muted">
                            <input type="text" class="form-control" name="correo" id="correo" placeholder="Correo electrónico">
                            <label for="correo">Correo electrónico</label>
                        </div>
                        <div class="form-floating mb-3 text-muted">
                            <input type="password" class="form-control" name="password" id="password" placeholder="Contraseña">
                            <label for="password" class="form-label">Contraseña</label>
                        </div>
                        <div class="form-floating mb-3 text-muted">
                            <input type="password" class="form-control" id="confirmPassword" placeholder="Confirma tu contraseña">
                            <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
                        </div>

                        <p class="mb-3 text-muted">Rol <i class="bi bi-question-circle-fill ms-1"></i></p>

                        <div class="btn-group mb-3 w-100" role="group" aria-label="Basic radio toggle button group">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="rol" value="asistente" id="btnRadioAsistente" autocomplete="off" checked>
                                <label class="form-check-label" for="btnRadioAsistente">Asistente</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="rol" value="organizador" id="btnRadioOrganizador" autocomplete="off">
                                <label class="form-check-label" for="btnRadioOrganizador">Organizador</label>
                            </div>
                        </div>
                        
                        <div class="asistente-campos" id="asistente">
                            <div class="mb-3 form-floating text-muted">
                                <input type="text" class="form-control" name="apellido" id="apellido" placeholder="Apellido">
                                <label for="apellido">Apellido</label>
                            </div>
                            <div class="mb-3">
                                <p>Fecha de nacimiento</p>
                                <div class ="row g-3" id="grupoBtn">
                                    <div class="col-3">
                                    <select class="form-select" aria-label="Día" name="dia" id="dia">
                                        <option value="0" selected>Día</option>
                                        <% 
                                        	for (int i =1; i <=31; i++) {
 
                                        %>
                                        		<option value="<%=i%>"><%=i%></option>
                                        
										<% } %>
                                    </select>                                      
                                </div>
                                <div class="col-5">
                                    <select class="form-select" aria-label="Mes" name="mes" id="mes">
                                        <option value="0" selected>Mes</option>
                                        <%
                                        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                                        for (int i = 0; i < meses.length; i++) {
                                         	 %>
                                         	 <option value="<%=i+1%>"><%=meses[i]%></option> 	
                                        <% } %> 
                                    </select>
                                </div>
                                <div class="col-4">
                                    <select class="form-select" aria-label="Año" name="anio" id="anio">
                                        <option value="0" selected>Año</option>
                                        <%
                                        int anioActual = LocalDate.now().getYear();
                                        for (int i = 0; i <= 120; i++) {
                                        %>
                                         	<option value="<%=anioActual-i%>"><%=anioActual-i%></option>            
                                        <% } %>
                                        
                                    </select>
                                </div>  
                                </div>
                            </div>
                            <div class="mb-3">
                                <p>Institución (opcional)</p>
                                <select class="form-select" name="institucion" aria-label="Institución" id="institucion">
                                    <option value="ninguna" selected>- Elección de institución -</option>
                                    <%
                                    	var instituciones = ((DtInstitucionArray) request.getAttribute("instituciones")).getInstitucion();
                                    for (DtInstitucion institucion : instituciones) {
                                    	%>
                                    	<option value="<%=institucion.getNombre()%>"><%=institucion.getNombre()%></option>	
                                    	
                                    <% } %>
                                   
                                </select>
                            </div>   
                        </div>

                        <div class="organizador-campos d-none" id="organizador">
                            <div class="mb-3 form-floating text-muted">
                                <input type="text" class="form-control" name="descripcion" id="descripcion" placeholder="Descripción">
                                <label for="descripcion">Descripción</label>
                            </div>
                            <div class="mb-3 form-floating text-muted">
                                <input type="text" class="form-control" name="sitioWeb" id="sitioWeb" placeholder="Sitio web (opcional)">
                                <label for="sitioWeb">Sitio web (opcional)</label>
                            </div>
                        </div>

                        <div>
                            <label for="formFile" class="form-label">Imagen (opcional)</label>
                            <br>
                            <img class="d-none mt-2 mb-2" id="preview" src="" alt="Vista Previa" style="max-width:200px; max-height:200px;">
                            <input class="form-control" name="imagen" type="file" id="formFile" accept="image/*">
                        </div>

                        <button type="submit" class="btn btn-primary w-100 mt-5" id="btnRegistro">Registrarse</button>
                        <p class="text-center mt-2"><a href="sesion" class="text-decoration-none">¿Ya tienes una cuenta? </a></p>
                    </form>
                </div>
        </div> 
    </div>
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="media/js/scripts.js"></script>
    <script src="media/js/pages/registroUsuario.js"></script>
</body>
</html>