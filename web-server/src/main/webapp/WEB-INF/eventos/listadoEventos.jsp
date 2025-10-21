<!DOCTYPE html>
<%@page import="datatypes.TipoUsuario"%>
<html lang="en" data-bs-theme="light">
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTEvento" %>
	<%
		List<DTEvento> eventos = (List<DTEvento>) request.getAttribute("listado");
		int finPaginacion = (int) request.getAttribute("fin_paginacion");
		int pagina = (Integer)request.getAttribute("pagina") ;
	%>
    <head>
        <title>Eventos UY - Eventos</title>
        <jsp:include page="/WEB-INF/template/head.jsp" />
    </head>
    <body class="d-flex flex-column h-100">
        <main class="flex-shrink-0">
            <!-- NAV -->
    	    <jsp:include page="/WEB-INF/template/header.jsp" />
            <!-- Main section-->
            <section class="p-5" id="main">
                <div class="container mt-5">
                    <div class="text-center mb-5">
                        <h1 class="fw-bolder">Eventos</h1>
                    </div>
                <div class="card shadow-sm">
                    <div class="row g-3 card-body">
                    <% for (DTEvento ev : eventos){ %>
                        <div class="col-xl-3 col-lg-4 col-sm-6 col-12">
                            <a href="<%=request.getContextPath() %>/evento/<%= ev.nombre() %>" class="text-decoration-none">
                                <div class="card h-100 shadow-sm border-0 text-start evento-card position-relative overflow-hidden" style="transition: box-shadow 0.3s;">
                                    <img
                                        class="card-img-top"
                                        src="image/<%= ev.foto()%>"
                                        alt="Banner evento"
                                        style="aspect-ratio: 600/350;"
                                    />
                                    <div class="card-body">
                                        <span class="text-muted text-deco:ration-none"><%=ev.nombre() %></span>
                                    </div>
                                    <div class="evento-descripcion position-absolute top-0 start-0 w-100 h-100 bg-white p-3 d-flex align-items-center justify-content-center text-center" style="opacity:0; pointer-events:none; transition: opacity 0.3s;">
                                        <span class="text-muted"><%= ev.descripcion() %></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <% } %>
                        <script>
                        document.querySelectorAll('.evento-card').forEach(card => {
                            card.addEventListener('mouseenter', () => {
                                card.style.boxShadow = "0 0 20px rgba(0,0,0,0.2)";
                                card.style.transform = "scale(1.05)";
                                card.querySelector('.evento-descripcion').style.opacity = "1";
                                card.querySelector('.evento-descripcion').style.pointerEvents = "auto";
                            });
                            card.addEventListener('mouseleave', () => {
                                card.style.boxShadow = "";
                                card.style.transform = "";
                                card.querySelector('.evento-descripcion').style.opacity = "0";
                                card.querySelector('.evento-descripcion').style.pointerEvents = "none";
                            });
                        });
                        </script>

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
                
            </section>
        </main>
       
        <!-- Bootstrap core JS-->
        <script src="js/bootstrap.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
