<%@ page import="datatypes.DTEdicionDetallada, datatypes.DTEdicion, datatypes.DTOrganizador, datatypes.DTUsuario, datatypes.DTAsistente,
	datatypes.DTTipoRegistro, datatypes.DTPatrocinio, datatypes.DTRegistro" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Consulta Detallada de Edición</title>
	
	<jsp:include page="/WEB-INF/template/head.jsp" />
    <link href="<%= request.getContextPath() %>/media/styles/styles.css" rel="stylesheet">
</head>
<body class="d-flex flex-column h-100 bg-light">
     <!-- NAV -->
     <jsp:include page="/WEB-INF/template/header.jsp" />
     
     <% var edicionDetallada = (DTEdicionDetallada)request.getAttribute("edicionDetallada"); 
        var edicion = edicionDetallada.edicion();
        var organizador = edicionDetallada.organizador();
        var tiposRegistro = edicionDetallada.tiposRegistro();
        var patrocinios = edicionDetallada.patrocinios();
        var registros = edicionDetallada.registros();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        var dtUsuario = (DTUsuario)request.getAttribute("usuario");
        boolean asistente = dtUsuario instanceof DTAsistente; 
        boolean organizadorUsuario = dtUsuario instanceof DTOrganizador;
     
     %>
     
        <section class="position-relative" id="imagenEd" style="min-height: 500px; background-image: url('<%= request.getContextPath() %>/image/<%=edicion.foto()%>'); background-size: cover; background-position: center;">
        <div class="position-absolute bottom-0 start-0 w-100" style="height: 40%;background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);">
            
            <div class="container h-100 d-flex flex-column justify-content-end pb-4">

                <h1 class="fw-bold text-white fs-2 fs-md-1"><%=edicion.nombre()%></h1>
                <p class="fs-5 fs-md-5 text-white mb-0 fw-bold"><%=edicion.sigla()%></p>

                <div class="row g-2 g-md-3 mt-2 align-items-center">
                    <div class="col-auto d-flex align-items-center">
                        <i class="bi bi-calendar-event-fill text-white me-2"></i>
                        <p class="fs-5 fs-md-5 text-white mb-0"><%= formatter.format(edicion.fechaInicio()) %> - <%=formatter.format(edicion.fechaFin()) %></p>
                    </div>
                    <div class="col-auto d-flex align-items-center">
                        <i class="bi bi-geo-alt-fill text-white me-2"></i>
                        <p class="fs-5 fs-md-5 text-white mb-0"><%=edicion.ciudad() %>, <%=edicion.pais() %></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <main class="flex-shrink-0 mt-5 mb-5">
        <div class="container p-5 rounded-4 border bg-white shadow-sm">
            <section>
            <% if (organizadorUsuario) { %>
                <div class="d-flex justify-content-end">
                    <a class="btn btn-outline-primary mb-3 me-2" data-bs-toggle="modal" data-bs-target="#registrosEdicionModal" href="#!">Ver registros</a>
                </div> 
                
            <jsp:include page="/WEB-INF/registro/registrosEdicion.jsp"/>    
           <% } %>     
                
                <h3>Organizador </h3>
                <div class="row g-3 align-items-center mb-4 p-2">
                    <div class="col-auto">
                        <div class="d-flex justify-content-center align-items-center bg-secondary rounded-circle" style="width: 75px; height: 75px; font-size: 2rem;">
                            <img src="<%= request.getContextPath() %>/image/<%=organizador.foto()%>" class="card-img-top" alt="Foto del organizador"/>
                        </div>
                    </div>
                    <div class="col">
                        <h4><a href="perfil-organizador.html" class="text-decoration-none text-body"><%=organizador.nombre() %></a></h4>
                        <p class="mb-0"><%=organizador.correo()%></p>
                        <p class="mb-0"><%=organizador.descripcion()%></p>
                        <% if(organizador.linkSitioWeb() != null) { %> 
                        	<p class="mb-0"><%=organizador.linkSitioWeb()%></p> 
                        <% } %>
                    </div>
                </div>
            </section>
        </div>
        

       <div class="container p-5 mt-4">
                    <div class="row gx-5 justify-content-center align-items-center align-items-stretch" id="tipoRegistro">
                        <!-- Pricing card free-->
                      <% for(DTTipoRegistro tipoRegistro : tiposRegistro) {
                    	  request.setAttribute("tipoRegistro", tipoRegistro);
                      %>
                        <div class="col-12 col-md-6 col-lg-6 col-xl-4 mb-4">
                            <div class="card mb-5 mb-xl-0 h-100 borderh-100">
                                <div class="card-body p-5 d-flex flex-column min-vh-50" style="min-height: 50vh;">
                                    <button type="button" class="btn p-0 m-0 align-self-start" data-bs-toggle="modal" data-bs-target="#<%=tipoRegistro.nombre()%>consultaModal">
                                        <i class="bi bi-question-circle-fill fs-4"></i>
                                    </button>
                                    <div class="small text-uppercase fw-bold text-muted"><%=tipoRegistro.nombre() %></div>
                                    <div class="mb-3">
                                        <span class="display-4 fw-bold">$<%=(int) tipoRegistro.costo()%></span>
                                    </div>
                                    <p><%=tipoRegistro.descripcion()%></p>
                                    
                                    <% if(asistente && !((boolean) request.getAttribute("usuarioRegistrado"))) { 
                                    	request.setAttribute("edicion", edicion); %>
                                    	<div class="d-grid mt-auto"><a class="btn btn-dark rounded-4 fs-5" data-bs-toggle="modal" data-bs-target="#<%=tipoRegistro.nombre() + "registroModal" %>" href="#!">Elegir</a></div>
                                		<jsp:include page="/WEB-INF/registro/altaRegistroEdicion.jsp" />
                                	<% } %>
                                	
                                </div>
                            </div>        
                        </div>
                        <!-- Modal para  registro -->
                        <jsp:include page="/WEB-INF/registro/consultaTipoRegistro.jsp" />
                        
                     <% } %>
                    </div>
        </div>
        
        <% String mensajeError = (String) request.getAttribute("errorRegistro"); 
        if (mensajeError != null) { %>
        <div class="container p-5 mt-4">
        	<div class="border border-danger mb-3" style="background-color: rgba(220, 53, 69, 0.3)"><p class="text-center fw-bold" style="color: black;"><%=mensajeError %></p> </div>
        </div>
        <% } %>
        
        <% if(asistente && ((boolean) request.getAttribute("usuarioRegistrado"))) {     	
        	DTRegistro registroUsuario = (DTRegistro) request.getAttribute("datosRegistroUsuario");
        	%>
        <div class="container p-5 mt-4">
        
        	<div class="border border-success p-2 mb-3 d-flex justify-content-center" style="background-color: rgba(40, 167, 69, 0.3)">
        		<div> 	
        		<p class="fw-bold mb-0 p-0">¡Ya estás registrado a esta edición!<p>
        		<div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Nickname del usuario registrado: </p>
                    <p class="mb-0 p-0"><%=registroUsuario.nicknameAsistente() %></p>
                </div>
                <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Nombre del evento: </p>
                    <p class="mb-0 p-0"><%=registroUsuario.nombreEvento() %></p>
                </div>
                <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Nombre de la edición: </p>
                    <p class="mb-0 p-0"><%=registroUsuario.nombreEdicion() %></p>
                </div>
                <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Fecha del registro: </p>
                    <p class="mb-0 p-0"><%=formatter.format(registroUsuario.fecha()) %></p>
                </div>
                <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Nombre del tipo de registro: </p>
                    <p class="mb-0 p-0"><%=registroUsuario.tipoRegistro().nombre() %></p>
                </div>
                <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Costo: </p>
                    <p class="mb-0 p-0">$<%=(int) registroUsuario.costo() %></p>
                </div>
                 <div class="d-flex gap-2 fw-normal">
                    <p class="fw-bold mb-0 p-0">Uso código de patrocinio?: </p>
                    <p class="mb-0 p-0"><%=registroUsuario.usoCodigo() ? "Sí" : "No" %></p>
                </div>               
        		
        		</div>
        		
        	</div>
        
        
        </div>	
        <% } %>
     
    </main>
    <footer class="bg-dark py-4 mt-auto">
    	<div class="container-fluid bg-dark text-white py-4">
            <div class="container text-center">
                <h5 class="mb-0">PATROCINADORES DE LA EDICIÓN</h5>
                <div class ="d-flex flex-wrap justify-content-center align-items-end mt-4">
                <% for(var patrocinio : patrocinios) { %>
                    <a href="#!"class="d-flex flex-column align-items-center text-center col-6 col-md-4 col-lg-3 mb-4 h-100 text-decoration-none text-white"  data-bs-toggle="modal" data-bs-target="#<%=patrocinio.institucion().nombre()%>patrocinadorModal">
                        <p><%=patrocinio.institucion().nombre()%></p>
                        <img class="img-fluid rounded-pill" src="<%=request.getContextPath() %>/image/<%=patrocinio.institucion().foto().equals("default.png") ? "patrocinadorDefault.png" : patrocinio.institucion().foto() %>" style="width: 100px; height: 100px;" alt="Logo <%=patrocinio.institucion().nombre()%>">
                    </a>
                 <% request.setAttribute("patrocinador", patrocinio); %>
                <jsp:include page="/WEB-INF/ediciones/consultaPatrocinadores.jsp" />
                <% } %>
                </div>
           </div>
    		</div>	
      <div class="container px-3">
        <div
          class="row align-items-center justify-content-between flex-column flex-sm-row"
        >
          <div class="col-auto">
            <div class="small m-0 text-white">
              Copyright &copy; Eventos.uy 2025
            </div>
          </div>
          <div class="col-auto">
            <a class="link-light small" href="#!">Privacy</a>
            <span class="text-white mx-1">&middot;</span>
            <a class="link-light small" href="#!">Terms</a>
            <span class="text-white mx-1">&middot;</span>
            <a class="link-light small" href="#!">Contact</a>
          </div>
        </div>
      </div>
    </footer>
    

    
    
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="<%= request.getContextPath() %>/media/js/scripts.js"></script>
    
    <!-- Guardamos el atributo en una variable JS -->
    <% String abrirModalTR = (String) request.getAttribute("abrirModal") != null ? (String) request.getAttribute("abrirModal") : ""; 
       request.setAttribute("abrirModal", null);
    %>
    
    <script>
    var abrirModal = '<%= abrirModalTR != null ? abrirModalTR : ""%>';
	</script>
	<% abrirModalTR = null; %>

	<script src="script.js"></script>
	<script src="<%= request.getContextPath() %>/media/js/pages/consultaDetalladaEdicion.js"></script>
</body>
</html>