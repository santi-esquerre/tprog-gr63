<%@ page import="datatypes.DTEdicionDetallada, datatypes.DTEdicion, datatypes.DTOrganizador,
	datatypes.DTTipoRegistro, datatypes.DTPatrocinio, datatypes.DTRegistro" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta Detallada de Edición</title>

<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body class="d-flex flex-column h-100 bg-light">
     <!-- NAV -->
     <jsp:include page="/WEB-INF/template/header.jsp" />
     
     <% var edicionDetallada = (DTEdicionDetallada)request.getAttribute("edicion"); 
        var edicion = edicionDetallada.edicion();
        var organizador = edicionDetallada.organizador();
        var tiposRegistro = edicionDetallada.tiposRegistro();
        var patrocinios = edicionDetallada.patrocinios();
        var registros = edicionDetallada.registros();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     
     %>
     
        <section class="position-relative" id="imagenEd" style="min-height: 500px; background-image: url('/image/<%=edicion.foto()%> %>'); background-size: cover; background-position: center;">
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
                <div class="d-flex justify-content-end">
                    <a class="btn btn-outline-primary mb-3 me-2" data-bs-toggle="modal" data-bs-target="#registrosEdicionModal" href="#!">Ver registros</a>
                </div> 
                <h3>Organizador </h3>
                <div class="row g-3 align-items-center mb-4 p-2">
                    <div class="col-auto">
                        <div class="d-flex justify-content-center align-items-center bg-secondary rounded-circle" style="width: 75px; height: 75px; font-size: 2rem;">
                            <img src="/image/<%=organizador.foto()%>" class="card-img-top" alt="Foto del organizador"/>
                        </div>
                    </div>
                    <div class="col">
                        <h4><a href="perfil-organizador.html" class="text-decoration-none text-body"><%=organizador.nombre() %>></a></h4>
                        <p class="mb-0"><%=organizador.correo()%>></p>
                        <p class="mb-0"><%=organizador.descripcion()%></p>
                        <% if(organizador.linkSitioWeb() != null) { %> <p class="mb-0"><%=organizador.linkSitioWeb()%></p> <%}%>
                    </div>
                </div>
            </section>
        </div>

        <div class="container p-5 mt-4">
                    <div class="row gx-5 justify-content-center align-items-center align-items-stretch" id="tipoRegistro">
                        <!-- Pricing card free-->
                        <div class="col-12 col-md-6 col-lg-6 col-xl-4 mb-4">
                            <div class="card mb-5 mb-xl-0 h-100 borderh-100">
                                <div class="card-body p-5 d-flex flex-column min-vh-50">
                                    <button type="button" class="btn p-0 m-0 align-self-start" data-bs-toggle="modal" data-bs-target="#tipoRegistroModal">
                                        <i class="bi bi-question-circle-fill fs-4"></i>
                                    </button>
                                    <div class="small text-uppercase fw-bold text-muted">Estudiante</div>
                                    <div class="mb-3">
                                        <span class="display-4 fw-bold">$300</span>
                                    </div>
                                    <p>Acceso para estudiantes</p>
                                    <div class="d-grid mt-auto"><a class="btn btn-dark rounded-4 fs-5" data-bs-toggle="modal" data-bs-target="#registroModal" href="#!">Elegir</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
        </div>
     
    </main>
    <footer class="bg-dark py-4 mt-auto">
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
    <!-- Modal -->
    <div class="modal fade" id="registroModal" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-fullscreen-sm-down modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registroModalLabel">Registro a Edición de Evento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="d-flex justify-content-start mb-3" style="background-image: url('https://placehold.co/400'); height: 75px; background-size: cover; background-position:center;">
                        <h4 class="ms-3 fs-5" style="line-height: 75px;">Tecnología Punta del Este 2026</h4>
                    </div>
                    <form>
                        <div class="mb-3">
                            <label for="nombre" class="col-form-label">Nombre:</label>
                            <input type="text" class="form-control" id="nombre" value="Ana" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="apellido" class="col-form-label">Apellido:</label>
                            <input type="text" class="form-control" id="apellido" value="Torres" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="col-form-label">Correo electrónico:</label>
                            <input type="email" class="form-control" id="correo" placeholder="atorres@gmail.com" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="tipoEntrada" class="col-form-label">Tipo de Entrada:</label>
                            <select class="form-select" id="tipoEntrada">
                            <option value="estudiante">Estudiante - $300</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="codigoPatrocinio" class="col-form-label">Código de Patrocinio:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="codigoPatrocinio">
                                <button class="btn btn-outline-primary ms-2" type="button" id="validarCodigo">Validar</button>
                            </div>
                        </div>
                    </form>
                    <h4 class="mt-4 text-end">Total a Pagar: $300</h4>
                    <div class="input-group d-flex justify-content-end mt-4 mb-4">
                        <button class="btn btn-primary text-end" type="button" id="confirmarRegistro">Confirmar Registro</button>
                        <button class="btn btn-outline-secondary ms-2" data-bs-dismiss="modal" type="button" id="cancelarRegistro">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="tipoRegistroModal" tabindex="-1" aria-labelledby="tipoRegistroModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tipoRegistroModalLabel">Información sobre el Tipo de Registro</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4" id="infoTipoRegistro">
                    <p>Nombre:  Estudiante</p>
                    <p>Precio: $300</p>
                    <p>Descripción: Acceso general a todas las actividades.</p>
                    <p>Cupos disponibles: 100</p>
                    <div class = "mt-4">
                        <h4 class="mb-3">Patrocinadores:</h4> 
                        <ol class="list-group" id="patrocinadoresTipoRegistro">
                            <li class="list-group-item d-flex justify-content-between align-items-start">
                                <div class="ms-2 me-auto">
                                    <div class="fw-bold">Facultad de Ingeniería</div>
                                    $20000 - 21/08/2025
                                </div>
                                <span class="badge bg-warning rounded-pill">Oro</span>
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="registrosEdicionModal" tabindex="-1" aria-labelledby="registrosEdicionLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-fullscreen-sm-down">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registrosEdicionLabel">Registros a la Edición</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                   <ol class="list-group" id="registroUsuarios">
                        <li class="list-group-item d-flex align-items-start flex-wrap" >

                            <div class="row g-3 w-100">
                                <div class="d-flex align-items-center col-8">
                                    <img class="rounded-1 align-self-center" src="assets/img/IMG-US01.jpg" style="width: 40px; height: 40px;">
                                
                                    <div class="ms-3 w-100 mb-0 p-0">
                                        <div class="d-flex align-items-center gap-2">
                                            <p class="fw-bold mb-0">Ana Torres</p>
                                            <span class="badge text-bg-primary rounded-pill align-self-center">Estudiante</span>
                                        </div>
                                        <p class="mt-0 mb-0">atorres@gmail.com</p>
                                        <p>21/08/2025</p>
                                    </div>
                                </div>
                                <div class = "col-4 d-flex align-items-center justify-content-end">
                                    <div class="mb-0 p-0 d-flex gap-2">
                                        <p class="fw-bold">Cupo:</p> 
                                        <i class="bi bi-x-circle-fill text-primary"></i>
                                    </div>
                                </div>

                            </div>
                        </li>
                   </ol> 
                </div>
                        
                
                <div class="modal-footer p-4">
                    <button class="btn btn-primary w-100 fs-5 rounded-4" data-bs-dismiss="modal" type="button">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="js/scripts.js"></script>
    <script src="js/pages/consultaDetalladaEdicion.js"></script>
	
</body>
</html>