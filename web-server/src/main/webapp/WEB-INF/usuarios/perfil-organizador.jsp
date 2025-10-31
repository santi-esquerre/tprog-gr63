<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  datatypes.DTOrganizador organizador = (datatypes.DTOrganizador) request.getAttribute("organizador");
%>
<!DOCTYPE html>
<html lang="es" data-bs-theme="light">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Mi perfil (Organizador) • Eventos.uy</title>
  <jsp:include page="/WEB-INF/template/head.jsp" />

  <style>
    .navbar-blur { backdrop-filter: blur(8px); background: rgba(0,0,0,.65); }
    .profile-card img { object-fit: cover; }
    .chip { border: 1px solid rgba(0,0,0,.15); border-radius: 999px; padding: 2px 10px; font-size: .8rem; }
    .tab-toolbar { gap: .5rem; }
    [data-bs-theme="dark"] .chip { border-color: rgba(255,255,255,.25); }
    [data-bs-theme="dark"] .search-wrap { background: rgba(33,37,41,.9); color:#fff; }
  </style>
</head>
<body class="d-flex flex-column">
<main class="flex-shrink-0">

  <!-- NAV -->
  <jsp:include page="/WEB-INF/template/header.jsp" />

  <!-- Perfil Organizador -->
  <section class="py-5" style="margin-top:80px">
    <div class="container px-2">
      <div class="row g-4">

        <!-- Col izquierda: tarjeta de perfil -->
        <aside class="col-12 col-lg-4">
          <div class="card profile-card shadow-sm">
            <div class="position-relative">
              <!-- La imagen se sirve por el servlet /image/* -->
              <img
                src="${pageContext.request.contextPath}/image/${organizador.foto != null ? organizador.foto : 'default.png'}"
                class="card-img-top" alt="Foto del organizador" />
              <span class="badge text-bg-danger position-absolute top-0 start-0 m-3 rounded-pill px-3">Organizador</span>
            </div>
            <div class="card-body">
              <div class="d-flex align-items-start justify-content-between flex-wrap">
                <div class="flex-grow-1">
                  <h4 class="fw-bold mb-1" id="orgNombre">
                    ${organizador.nombre}
                  </h4>
                  <p class="mb-2" id="orgDescripcion">
                    ${organizador.descripcion != null ? organizador.descripcion : '—'}
                  </p>
                </div>
                <button class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#editOrganizerModal">
                  <i class="bi bi-pencil-square me-1"></i> Editar
                </button>
              </div>

              <p class="mb-2">
                <i class="bi bi-link-45deg me-1"></i>
                <a id="orgLink"
                   href="${organizador.linkSitioWeb != null ? organizador.linkSitioWeb : '#'}"
                   target="_blank" rel="noopener">
                  ${organizador.linkSitioWeb != null ? organizador.linkSitioWeb : '—'}
                </a>
              </p>

              <p class="mb-1">
                <i class="bi bi-envelope-fill me-1"></i>
                <span id="orgEmail">${organizador.correo}</span>
              </p>

              <p class="text-muted fst-italic mb-0">
                Miembro de Eventos.uy
              </p>
            </div>
          </div>
        </aside>

        <!-- Col derecha: pestañas -->
        <section class="col-12 col-lg-8">
          <header class="d-flex align-items-center justify-content-between flex-wrap mb-3">
            <h3 class="fw-bold m-0">Perfil de Organizador</h3>
            <div class="btn-group">
              <button class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#addEventModal">
                <i class="bi bi-plus-lg me-1"></i> Nuevo Evento
              </button>
              <button class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#addEditionModal">
                <i class="bi bi-plus-lg me-1"></i> Nueva Edición
              </button>
            </div>
          </header>

          <ul class="nav nav-tabs" role="tablist">
            <li class="nav-item"><button class="nav-link active" id="events-tab" data-bs-toggle="tab"
              data-bs-target="#events-pane" type="button" role="tab" aria-controls="events-pane"
              aria-selected="true">Eventos</button></li>
            <li class="nav-item"><button class="nav-link" id="editions-tab" data-bs-toggle="tab"
              data-bs-target="#editions-pane" type="button" role="tab" aria-controls="editions-pane"
              aria-selected="false">Ediciones</button></li>
          </ul>

          <div class="tab-content pt-3">
            <!-- TAB EVENTOS -->
            <div class="tab-pane fade show active" id="events-pane" role="tabpanel" aria-labelledby="events-tab">
              <div class="d-flex flex-wrap justify-content-between align-items-center mb-3 tab-toolbar">
                <h5 class="m-0">Eventos relacionados</h5>
                <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addEventModal">
                  <i class="bi bi-plus-circle me-1"></i> Agregar Evento
                </button>
              </div>
              <div id="eventosGrid" class="row gy-3">
                <!-- Rellenado por JS desde /api/organizador/eventos -->
              </div>
            </div>

            <!-- TAB EDICIONES -->
            <div class="tab-pane fade" id="editions-pane" role="tabpanel" aria-labelledby="editions-tab">
              <div class="d-flex flex-wrap justify-content-between align-items-center mb-3 tab-toolbar">
                <h5 class="m-0">Mis ediciones</h5>
                <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addEditionModal">
                  <i class="bi bi-plus-circle me-1"></i> Agregar Edición
                </button>
              </div>
              <div id="edicionesGrid" class="row gy-3">
                <!-- Rellenado por JS desde /api/organizador/ediciones -->
              </div>
            </div>
          </div>
        </section>

      </div>
    </div>
  </section>
</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/template/footer.jsp" />

<!-- ======================= MODALS ======================= -->

<!-- Editar Organizador -->
<div class="modal fade" id="editOrganizerModal" tabindex="-1" aria-labelledby="editOrganizerLabel" aria-hidden="true">
  <div class="modal-dialog">
    <form id="formEditOrganizer" class="modal-content needs-validation" novalidate
          action="${pageContext.request.contextPath}/perfil" method="post" enctype="multipart/form-data">
      <input type="hidden" name="action" value="editProfile"/>

      <div class="modal-header">
        <h5 class="modal-title" id="editOrganizerLabel"><i class="bi bi-person-gear me-2"></i>Editar perfil</h5>
        <button class="btn-close" type="button" data-bs-dismiss="modal"></button>
      </div>

      <div class="modal-body">
        <div class="row g-3">
          <div class="col-12">
            <label class="form-label">Nombre</label>
            <input name="nombre" id="inpNombre" type="text" class="form-control" value="${organizador.nombre}" required/>
            <div class="invalid-feedback">El nombre es obligatorio.</div>
          </div>

          <div class="col-12">
            <label class="form-label">Descripción</label>
            <textarea name="descripcion" id="inpDescripcion" class="form-control" rows="3">${organizador.descripcion}</textarea>
          </div>

          <div class="col-12">
            <label class="form-label">Sitio web</label>
            <input name="sitioWeb" id="inpLink" type="url" class="form-control" value="${organizador.linkSitioWeb}"/>
          </div>

          <div class="col-12">
            <label class="form-label">Correo</label>
            <input name="correo" type="email" class="form-control" value="${organizador.correo}" required/>
            <div class="invalid-feedback">El correo es obligatorio.</div>
          </div>

          <div class="col-12 col-md-6">
            <label class="form-label">Nueva contraseña (opcional)</label>
            <input name="password" id="inpPass" type="password" class="form-control"/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Repetir contraseña</label>
            <input id="inpPass2" type="password" class="form-control"/>
            <div class="invalid-feedback">Las contraseñas no coinciden.</div>
          </div>

          <div class="col-12">
            <label class="form-label">Imagen (opcional)</label>
            <input name="imagen" id="inpImg" type="file" class="form-control" accept="image/*"/>
            <img id="previewImg" class="img-fluid rounded d-none mt-2" alt="preview"/>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit"><i class="bi bi-check2-circle me-1"></i>Guardar</button>
      </div>
    </form>
  </div>
</div>

<!-- Alta Evento -->
<div class="modal fade" id="addEventModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form id="formAddEvent" class="modal-content needs-validation" novalidate>
      <div class="modal-header"><h5 class="modal-title"><i class="bi bi-calendar2-plus me-2"></i>Nuevo Evento</h5>
        <button class="btn-close" type="button" data-bs-dismiss="modal"></button></div>
      <div class="modal-body">
        <div class="row g-3">
          <div class="col-12">
            <label class="form-label">Nombre del evento</label>
            <input id="evNombre" type="text" class="form-control" required/>
            <div class="invalid-feedback">El nombre es obligatorio.</div>
          </div>
          <div class="col-12">
            <label class="form-label">Descripción</label>
            <textarea id="evDescripcion" class="form-control" rows="3"></textarea>
          </div>
          <div class="col-12">
            <label class="form-label">Categorías</label>
            <select id="evCategorias" class="form-select" multiple></select>
          </div>
          <div class="col-12">
            <label class="form-label">Sigla</label>
            <input id="evSigla" type="text" class="form-control"/>
          </div>
          <div class="col-12">
            <label class="form-label">Imagen (opcional)</label>
            <input id="evImagen" type="file" class="form-control" accept="image/*"/>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit"><i class="bi bi-check2-circle me-1"></i>Crear evento</button>
      </div>
    </form>
  </div>
</div>

<!-- Alta Edición -->
<div class="modal fade" id="addEditionModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <form id="formAddEdition" class="modal-content needs-validation" novalidate>
      <div class="modal-header"><h5 class="modal-title"><i class="bi bi-journal-plus me-2"></i>Nueva edición</h5>
        <button class="btn-close" type="button" data-bs-dismiss="modal"></button></div>
      <div class="modal-body">
        <div class="row g-3">

          <div class="col-12">
            <label class="form-label">Evento</label>
            <select id="edEvento" class="form-select" required></select>
            <div class="invalid-feedback">Selecciona un evento.</div>
          </div>

          <div class="col-12">
            <label class="form-label">Nombre (único)</label>
            <input id="edNombre" type="text" class="form-control" required/>
            <div class="invalid-feedback">El nombre es obligatorio.</div>
          </div>

          <div class="col-12 col-md-6">
            <label class="form-label">Sigla</label>
            <input id="edSigla" type="text" class="form-control" required/>
            <div class="invalid-feedback">La sigla es obligatoria.</div>
          </div>

          <div class="col-12 col-md-6">
            <label class="form-label">Ciudad</label>
            <input id="edCiudad" type="text" class="form-control" required/>
            <div class="invalid-feedback">La ciudad es obligatoria.</div>
          </div>

          <div class="col-12 col-md-6">
            <label class="form-label">País</label>
            <input id="edPais" type="text" class="form-control" required/>
            <div class="invalid-feedback">El país es obligatorio.</div>
          </div>

          <div class="col-12 col-md-3">
            <label class="form-label">Fecha inicio</label>
            <input id="edInicio" type="date" class="form-control" required/>
            <div class="invalid-feedback">Ingresa fecha de inicio.</div>
          </div>

          <div class="col-12 col-md-3">
            <label class="form-label">Fecha fin</label>
            <input id="edFin" type="date" class="form-control" required/>
            <div class="invalid-feedback">Ingresa fecha de fin.</div>
          </div>

          <div class="col-12">
            <label class="form-label">Imagen (opcional)</label>
            <input id="edImagen" type="file" class="form-control" accept="image/*"/>
          </div>

          <div class="col-12">
            <div class="form-text">
              La edición se crea en estado <strong>Ingresada</strong> y luego será confirmada/rechazada por admin.
            </div>
          </div>

        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit"><i class="bi bi-check2-circle me-1"></i>Crear edición</button>
      </div>
    </form>
  </div>
</div>

<!-- Bootstrap & page JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>window.appCtx='${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/js/pages/perfil-organizador.js"></script>
</body>
</html>
