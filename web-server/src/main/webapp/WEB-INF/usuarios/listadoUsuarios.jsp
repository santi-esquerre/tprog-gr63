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
                                                        class="list-group-item list-group-item-action d-flex border-start-0 border-end-0 user-item" 
                                                        href="perfil?tipo=<%=(usu.tipoUsuario() == TipoUsuario.ASISTENTE) ? "asistente" : "organizador" %>&nickname=<%=usu.nickname()%>"
                                                        data-nickname="<%=usu.nickname()%>"
                                                        data-tipo="<%=(usu.tipoUsuario() == TipoUsuario.ASISTENTE) ? "asistente" : "organizador" %>"
                                                        >
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

        <!-- Modal para mostrar detalle rápido de usuario -->
        <div class="modal fade" id="userDetailModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-xl modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Detalle de usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                    </div>
                    <div class="modal-body" id="userDetailModalBody">
                        <div class="text-center py-5 text-muted">Cargando...</div>
                    </div>
                </div>
            </div>
        </div>

        <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.querySelectorAll('.user-item').forEach(function(el) {
                el.addEventListener('click', function(ev) {
                    // allow normal navigation when JavaScript disabled; here we prevent and do AJAX preview
                    ev.preventDefault();
                    const tipo = el.getAttribute('data-tipo');
                    const nickname = el.getAttribute('data-nickname');
                    const url = el.getAttribute('href');

                    const modalBody = document.getElementById('userDetailModalBody');
                    modalBody.innerHTML = '<div class="text-center py-5 text-muted">Cargando...</div>';
                    // Preferir el endpoint JSON publico para obtener solo los datos necesarios
                    const apiUrl = '<%= request.getContextPath() %>/api/usuario/preview?tipo=' + encodeURIComponent(tipo) + '&nickname=' + encodeURIComponent(nickname);

                    fetch(apiUrl)
                        .then(function(resp) { return resp.json(); })
                        .then(function(json) {
                            if (!json || !json.success) {
                                modalBody.innerHTML = '<div class="text-danger py-4">No se pudo obtener el detalle: ' + (json && json.message ? json.message : 'respuesta inv�lida') + '</div>';
                                new bootstrap.Modal(document.getElementById('userDetailModal')).show();
                                return;
                            }

                            const data = json.data;
                            // Construir HTML rico para el modal según tipo
                            let html = '';
                            if (data.tipo === 'asistente') {
                                html += '<div class="d-flex gap-3 align-items-center">';
                                html += '<img src="' + '<%= request.getContextPath() %>' + '/image/' + (data.foto ? data.foto : 'default.png') + '" alt="foto" class="rounded" width="120" height="120" style="object-fit:cover;"/>';
                                html += '<div>';
                                html += '<h4>' + escapeHtml(data.nombre) + (data.apellido ? (' ' + escapeHtml(data.apellido)) : '') + '</h4>';
                                html += '<div class="text-muted">@' + escapeHtml(data.nickname) + '</div>';
                                html += '<div class="mt-2"><i class="bi bi-envelope me-1"></i>' + escapeHtml(data.correo) + '</div>';
                                html += '<div class="mt-1"><i class="bi bi-calendar-event me-1"></i>' + (data.fechaNacimiento ? escapeHtml(data.fechaNacimiento) : '—') + '</div>';
                                html += '</div></div>';
                            } else if (data.tipo === 'organizador') {
                                html += '<div class="d-flex gap-3 align-items-center">';
                                html += '<img src="' + '<%= request.getContextPath() %>' + '/image/' + (data.foto ? data.foto : 'default.png') + '" alt="foto" class="rounded" width="120" height="120" style="object-fit:cover;"/>';
                                html += '<div>';
                                html += '<h4>' + escapeHtml(data.nombre) + '</h4>';
                                html += '<div class="text-muted">@' + escapeHtml(data.nickname) + '</div>';
                                html += '<div class="mt-2"><i class="bi bi-envelope me-1"></i>' + escapeHtml(data.correo) + '</div>';
                                html += '<div class="mt-1"><i class="bi bi-link-45deg me-1"></i>' + (data.linkSitioWeb ? '<a href="' + escapeHtml(data.linkSitioWeb) + '" target="_blank">' + escapeHtml(data.linkSitioWeb) + '</a>' : '—') + '</div>';
                                if (data.descripcion) html += '<p class="mt-2">' + escapeHtml(data.descripcion) + '</p>';
                                html += '</div></div>';
                                
                                if (data.ediciones && Array.isArray(data.ediciones) && data.ediciones.length > 0) {
                                    html += '<hr/><h5>Ediciones</h5>';
                                    // Grid de cartas
                                    html += '<div class="row g-3">';
                                    data.ediciones.forEach(function(r) {
                                        const foto = r.foto || '';
                                        const sigla = r.sigla || '';
                                        const nombreEd = r.nombre || '';
                                        const fechaRaw = r.fechaInicio || r.fecha || '';
                                        let fecha = '';
                                        if (fechaRaw) {
                                            try {
                                                const d = new Date(fechaRaw);
                                                fecha = d.toLocaleDateString();
                                            } catch (e) { fecha = fechaRaw; }
                                        }
                                        const ciudad = r.ciudad || r.ubicacion || '';
                                        const pais = r.pais || '';
                                        const ubicacion = (ciudad ? ciudad : '') + (ciudad && pais ? ', ' : '') + (pais ? pais : '');

                                        // Estado: normalizar posibles valores de enum
                                        const estadoRaw = (r.estado || '').toString();
                                        let estadoDisplay = estadoRaw;
                                        if (estadoRaw.toUpperCase() === 'ACEPTADA' || estadoRaw === 'Aceptada') estadoDisplay = 'Aceptada';
                                        else if (estadoRaw.toUpperCase() === 'RECHAZADA' || estadoRaw === 'Rechazada') estadoDisplay = 'Rechazada';
                                        else if (estadoRaw.toUpperCase() === 'INGRESADA' || estadoRaw === 'Ingresada') estadoDisplay = 'Ingresada';

                                        let badgeClass = 'primary';
                                        if (estadoDisplay === 'Aceptada') badgeClass = 'success';
                                        else if (estadoDisplay === 'Rechazada') badgeClass = 'danger';

                                            // Hacer la tarjeta clickeable hacia la página de la edición
                                            const slug = nombreEd ? nombreEd.trim().replace(/\s+/g, '-') : '';
                                        html += '<div class="col-12 col-md-6 col-lg-4">';
                                            html += '<a class="text-decoration-none edicionLink" href="' + '<%= request.getContextPath() %>' + '/edicion/' + escapeHtml(slug) + '">';
                                            html += '<div class="card bg-body h-100 shadow-sm border-0">';
                                            html += '<img class="card-img-top fotoEdicion" src="' + '<%= request.getContextPath() %>' + '/image/' + escapeHtml(foto) + '" alt="Banner edición" style="aspect-ratio: 600 / 350; object-fit:cover;"/>';
                                            html += '<div class="card-body">';
                                        html += '<div class="d-flex align-items-center justify-content-between flex-wrap">';
                                        html += '<span class="text-muted siglaEdicion">' + escapeHtml(sigla) + '</span>';
                                        html += '<span class="badge bg-' + badgeClass  + ' estadoEdicion">' + escapeHtml(estadoDisplay) + '</span>';
                                        html += '</div>';
                                        html += '<div class="h5 card-title mb-2 nombreEdicion">' + escapeHtml(nombreEd) + '</div>';
                                        html += '<div class="mb-1"><i class="bi bi-calendar-fill"></i>&nbsp;<span class="fechaEdicion">' + escapeHtml(fecha) + '</span></div>';
                                        html += '<div class="fw-semibold"><i class="bi bi-geo-fill"></i>&nbsp;<span class="ubicacionEdicion">' + escapeHtml(ubicacion) + '</span></div>';
                                        html += '</div></div></a></div>';
                                    });
                                    html += '</div>';
                                }
                            } else {
                                html = '<div class="text-muted py-4">Tipo no reconocido</div>';
                            }

                            modalBody.innerHTML = html;
                            new bootstrap.Modal(document.getElementById('userDetailModal')).show();
                        })
                        .catch(function(err) {
                            modalBody.innerHTML = '<div class="text-danger py-4">Error al obtener detalle: ' + err.message + '</div>';
                            new bootstrap.Modal(document.getElementById('userDetailModal')).show();
                        });
                });
            });
        });

        function escapeHtml(str) {
            if (!str && str !== 0) return '';
            return String(str)
                .replace(/&/g, '&amp;')
                .replace(/</g, '&lt;')
                .replace(/>/g, '&gt;')
                .replace(/"/g, '&quot;')
                .replace(/'/g, '&#039;');
        }
        </script>
    </body>
</html>
