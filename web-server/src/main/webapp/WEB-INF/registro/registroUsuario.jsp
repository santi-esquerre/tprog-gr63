<%@ page import="java.time.LocalDate, java.util.Set, java.util.Map, datatypes.DTInstitucion" %>
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
</head>
    <body class="d-flex flex-column h-100 bg-primary">
        
    <style>
      :root {
        --brand: #6f42c1;
        --brand-2: #0d6efd;
      }
      .navbar-blur {
        backdrop-filter: blur(8px);
        background: rgba(33, 37, 41, 0.8) !important;
      }
      .hero {
        position: relative;
        min-height: 75vh;
        display: grid;
        place-items: center;
        overflow: clip;
        color: #fff;
      }
      .hero video,
      .hero .hero-img {
        position: absolute;
        inset: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        filter: brightness(0.55);
      }
      .hero::after {
        content: "";
        position: absolute;
        inset: 0;
        background: radial-gradient(
            1200px 600px at 20% 20%,
            rgba(111, 66, 193, 0.35),
            transparent 50%
          ),
          linear-gradient(180deg, rgba(13, 110, 253, 0.25), transparent 60%),
          linear-gradient(0deg, rgba(0, 0, 0, 0.25), rgba(0, 0, 0, 0.25));
        pointer-events: none;
      }
      .hero-content {
        position: relative;
        z-index: 2;
      }
      .chip {
        border: 1px solid rgba(255, 255, 255, 0.4);
        color: #fff;
        background: rgba(255, 255, 255, 0.08);
        padding: 0.4rem 0.75rem;
        border-radius: 999px;
        display: inline-flex;
        gap: 0.4rem;
        align-items: center;
        transition: all 0.2s ease;
      }
      .chip:hover {
        background: rgba(255, 255, 255, 0.18);
        transform: translateY(-1px);
      }
      .search-wrap {
        background: rgba(255, 255, 255, 0.9);
        border-radius: 1rem;
        box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.5) inset,
          0 8px 30px rgba(0, 0, 0, 0.2);
      }
      .stat {
        border-radius: 1rem;
        background: linear-gradient(
          145deg,
          rgba(13, 110, 253, 0.1),
          rgba(111, 66, 193, 0.1)
        );
        border: 1px solid rgba(13, 110, 253, 0.15);
      }
      .card-hover:hover {
        transform: translateY(-4px);
        box-shadow: 0 1rem 2rem rgba(0, 0, 0, 0.12) !important;
      }
      .logo-cloud img {
        opacity: 0.6;
        transition: opacity 0.2s;
        filter: grayscale(1);
      }
      .logo-cloud img:hover {
        opacity: 1;
        filter: none;
      }

      /* Pequeñas animaciones */
      @keyframes float {
        from {
          transform: translateY(0);
        }
        50% {
          transform: translateY(-6px);
        }
        to {
          transform: translateY(0);
        }
      }
      .float {
        animation: float 5s ease-in-out infinite;
      }

      [data-bs-theme="dark"] .search-wrap {
        background: rgba(33, 37, 41, 0.9);
        color: #fff;
      }
      [data-bs-theme="dark"] .chip {
        border-color: rgba(255, 255, 255, 0.25);
      }
      
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
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top navbar-blur"> </nav>

    <div class="container mt-2 py-5">
      <div class="card mx-auto shadow" style="max-width: 600px;">
                <div class="card-header bg-white text-center">
                    <h2 class="fw-bold" style="color: black;">Crea una cuenta</h2>
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/registro" method="POST" enctype="multipart/form-data">
                    
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
                                        var anioActual = LocalDate.now().getYear();
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
                                   	var instituciones = (Set<DTInstitucion>) request.getAttribute("instituciones");
                                    for (var institucion : instituciones) {
                                    	%>
                                    	<option value="<%=institucion.nombre()%>"><%=institucion.nombre()%></option>	
                                    	
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
                        <p class="text-center mt-2"><a href="login.html" class="text-decoration-none">¿Ya tienes una cuenta? </a></p>
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