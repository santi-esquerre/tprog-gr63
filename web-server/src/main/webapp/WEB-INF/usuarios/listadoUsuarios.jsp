<!DOCTYPE html>
<%@page import="datatypes.TipoUsuario"%>
<html lang="en" data-bs-theme="light">
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTUsuarioItemListado" %>
	<%
		List<DTUsuarioItemListado> usuarios = (List<DTUsuarioItemListado>) request.getAttribute("listado");
		int finPaginacion = (int) request.getAttribute("fin_paginacion");
		int pagina = (Integer)request.getAttribute("pagina") ;
	%>
    <head>
    	        <title>Eventos UY - Usuarios</title>
        <jsp:include page="/WEB-INF/template/head.jsp" />
    </head>
    <body class="d-flex flex-column h-100">
        <jsp:include page="/WEB-INF/template/header.jsp" />
        <main class="flex-shrink-0">
            <!-- Main section-->
            <section class="p-5 mt-5" id="main">
                <div class="container">
                    <div class="text-center mb-5">
                        <h1 class="fw-bolder">Listado completo de usuarios</h1>
                    </div>
                <div class="card shadow-sm">
                    <div class="card-body p-0">
                    
                        <div class="list-group rounded-bottom-0">
                        
                    		<% for(DTUsuarioItemListado usu: usuarios) { %>
                            <a 
                            class="list-group-item list-group-item-action d-flex border-start-0 border-end-0" 
                            href="perfil?tipo=<%=(usu.tipoUsuario() == TipoUsuario.ASISTENTE) ? "asistente" : "organizador" %>&nickname=<%=usu.nickname()%>">
                                <img class="rounded-1 align-self-center" src="image/<%=usu.foto()%>" style="width: 40px; height: 40px;">
	                                <div class="ms-3">
	                                    <div class="fw-bold">
	                                        <%=usu.nickname()%>
	                                        <span class="badge text-bg-<%=(usu.tipoUsuario() == TipoUsuario.ASISTENTE) ? "primary" : "danger" %>" rounded-pill align-self-center"><%=(usu.tipoUsuario() == TipoUsuario.ASISTENTE) ? "Asistente" : "Organizador" %></span>
	                                    </div>
	                                    <div class="small"><%=usu.correo()%></div>
	                                </div>
                            </a>
                            <% } %>
                        </div>
                    </div>
                    <div class="card-footer border-top-0">
                        <nav class="d-flex justify-content-center">
                            <ul class="pagination m-0">
                                <li class="page-item <%= pagina == 1 ? "disabled" : "" %>">
                                    <span class="page-link"><i class="bi bi-caret-left-fill"></i></span>
                                </li>
                                <% for(int i = 1; i <= finPaginacion; i++) { %>
	                                <% if(i==pagina) { %>
		                                <li class="page-item active">
		                                    <span class="page-link"><%= i %></span>
		                                </li>
	                                <% } else{ %>
	                                <li class="page-item">
	                                    <a class="page-link" href="usuarios?pagina=<%=i%>"><%= i %></a>
	                                </li>
	                                
                                <% }} %>
                                <li class="page-item <%= pagina == finPaginacion ? "disabled" : "" %>">
                                    <a class="page-link" href="#"><i class="bi bi-caret-right-fill"></i></a>
                                </li>
                                </ul>
                        
 </nav>
                    </div>
                </div>
                </div>
                
            </section>
        </main>
        <!-- Bootstrap core JS-->
        <script src="js/bootstrap.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
