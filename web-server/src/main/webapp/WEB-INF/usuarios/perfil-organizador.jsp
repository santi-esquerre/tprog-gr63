<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%
  webservice.DtOrganizador organizador = (webservice.DtOrganizador) request.getAttribute("organizador");
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
      
      <!-- Mensajes de éxito o error -->
      <c:if test="${not empty sessionScope.success_message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="bi bi-check-circle-fill me-2"></i>${sessionScope.success_message}
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="success_message" scope="session"/>
      </c:if>
      
      <c:if test="${not empty sessionScope.error_message}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          <i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error_message}
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="error_message" scope="session"/>
      </c:if>

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
                <div>
                  <h4 class="card-title mb-1" id="orgNombre">
                    ${organizador.nombre}
                  </h4>
                  <a href="#" class="text-muted text-decoration-none">@${organizador.nickname}</a>
                </div>
                <button class="btn btn-outline-primary btn-sm mt-2 mt-sm-0" data-bs-toggle="modal" data-bs-target="#editOrganizerModal">
                  <i class="bi bi-pencil-square me-1"></i> Editar
                </button>
              </div>

              <p class="mt-3 mb-2" id="orgDescripcion">
                ${organizador.descripcion != null ? organizador.descripcion : '—'}
              </p>

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
              <button class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#addInstitutionModal">
                <i class="bi bi-building-add me-1"></i> Nueva Institución
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

<!-- Alta de Institución -->
<div class="modal fade" id="addInstitutionModal" tabindex="-1" aria-labelledby="addInstitutionLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <form id="formAddInstitution" class="modal-content needs-validation" novalidate>
      <div class="modal-header">
        <h5 class="modal-title" id="addInstitutionLabel">
          <i class="bi bi-building-add me-2"></i>Alta de Institución
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      
      <div class="modal-body">
        <div class="row g-3">
          <div class="col-12">
            <label class="form-label">Nombre (único)</label>
            <input id="insNombre" type="text" class="form-control" required />
            <div class="invalid-feedback">El nombre es obligatorio.</div>
            <div id="insDupWarn" class="form-text text-danger d-none">
              Ya existe una institución con ese nombre.
            </div>
          </div>
          
          <div class="col-12">
            <label class="form-label">Descripción</label>
            <textarea id="insDescripcion" class="form-control" rows="3" required></textarea>
            <div class="invalid-feedback">La descripción es obligatoria.</div>
          </div>
          
          <div class="col-12 col-md-8">
            <label class="form-label">Sitio web (opcional)</label>
            <input id="insWeb" type="url" class="form-control" placeholder="https://..." pattern="https?://.+" />
            <div class="invalid-feedback">URL inválida (debe comenzar con http:// o https://)</div>
          </div>
          
          <div class="col-12 col-md-4">
            <label class="form-label">Logo (opcional)</label>
            <input id="insLogo" type="file" class="form-control" accept="image/*" />
          </div>
          
          <div class="col-12">
            <img id="insPreview" class="img-fluid rounded d-none" alt="Vista previa del logo" />
          </div>
        </div>
      </div>
      
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit">
          <i class="bi bi-check2-circle me-1"></i>Crear institución
        </button>
      </div>
    </form>
  </div>
</div>

<!-- Modal: Alta de Patrocinio -->
<div class="modal fade" id="addSponsorshipModal" tabindex="-1" aria-labelledby="addSponsorshipLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl modal-dialog-scrollable">
    <form class="modal-content needs-validation" novalidate id="formAddSponsorship">
      <div class="modal-header">
        <h5 class="modal-title" id="addSponsorshipLabel">
          <i class="bi bi-patch-plus me-2"></i>Alta de Patrocinio
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <!-- CONTEXTO (solo lectura) -->
        <div id="spContext" class="alert alert-light border">
          <div class="d-flex flex-wrap align-items-center gap-3">
            <div>
              <span class="text-muted">Evento:</span>
              <strong id="spCtxEv">—</strong>
            </div>
            <div>
              <span class="text-muted">Edición:</span>
              <strong id="spCtxEd">—</strong>
            </div>
          </div>
          <!-- ID de edición para el backend -->
          <input type="hidden" id="spEdicionId" />
        </div>
        <!-- Formulario -->
        <div class="row g-3">
          <div class="col-12 col-md-6">
            <label class="form-label">Institución</label>
            <select id="spInstitucion" class="form-select" required>
              <option value="" selected disabled>Selecciona una institución…</option>
            </select>
            <div class="invalid-feedback">Selecciona una institución.</div>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Nivel de patrocinio</label>
            <select id="spNivel" class="form-select" required>
              <option value="" selected disabled>Selecciona nivel…</option>
              <option>Platino</option>
              <option>Oro</option>
              <option>Plata</option>
              <option>Bronce</option>
            </select>
            <div class="invalid-feedback">Selecciona un nivel.</div>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Aporte económico</label>
            <div class="input-group">
              <span class="input-group-text">UYU</span>
              <input id="spAporte" type="number" class="form-control" min="0" step="0.01" required />
            </div>
            <div class="invalid-feedback">Ingresa el aporte económico.</div>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Código de patrocinio</label>
            <input id="spCodigo" type="text" class="form-control" required />
            <div class="invalid-feedback">Ingresa un código.</div>
          </div>
          <div class="col-12">
            <label class="form-label d-flex justify-content-between align-items-center">
              Registros gratuitos a otorgar
              <button type="button" class="btn btn-sm btn-outline-primary" id="spAddRow">
                <i class="bi bi-plus-circle me-1"></i>Agregar registro
              </button>
            </label>
            <div class="table-responsive">
              <table class="table table-sm align-middle">
                <thead>
                  <tr>
                    <th style="width: 38%">Tipo de registro</th>
                    <th style="width: 22%">Costo unitario (UYU)</th>
                    <th style="width: 22%">Cantidad</th>
                    <th style="width: 18%"></th>
                  </tr>
                </thead>
                <tbody id="spRows">
                  <!-- filas dinámicas -->
                </tbody>
              </table>
            </div>
            <div class="d-flex flex-wrap gap-3 align-items-center">
              <div>
                <strong>Total costo registros gratuitos:</strong>
                <span id="spTotal" class="badge bg-secondary">UYU 0.00</span>
              </div>
              <div>
                <strong>Porcentaje sobre aporte:</strong>
                <span id="spPct" class="badge bg-secondary">0%</span>
              </div>
              <div id="spWarn" class="alert alert-danger py-1 px-2 m-0 d-none">
                El costo supera el 20% del aporte económico. Podés editar los valores o cancelar.
              </div>
            </div>
          </div>
          <div class="col-12">
            <label class="form-label">Fecha de alta</label>
            <input id="spFecha" type="date" class="form-control" readonly />
          </div>
          <div class="col-12">
            <div id="spDup" class="alert alert-warning d-none">
              Ya existe un patrocinio de <strong class="sp-inst"></strong> para esta edición.
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit">
          <i class="bi bi-check2-circle me-1"></i>Registrar patrocinio
        </button>
      </div>
    </form>
  </div>
</div>

<!-- Modal: Alta de Tipo de Registro -->
<div class="modal fade" id="addTipoRegModal" tabindex="-1" aria-labelledby="addTipoRegLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-scrollable">
    <form class="modal-content needs-validation" novalidate id="formAddTipoReg">
      <div class="modal-header">
        <h5 class="modal-title" id="addTipoRegLabel">
          <i class="bi bi-card-checklist me-2"></i>Alta de Tipo de Registro
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <!-- Contexto edición -->
        <div id="trContext" class="alert alert-light border">
          <div class="d-flex flex-wrap align-items-center gap-3">
            <div>
              <span class="text-muted">Evento:</span>
              <strong id="trCtxEv"></strong>
            </div>
            <div>
              <span class="text-muted">Edición:</span>
              <strong id="trCtxEd"></strong>
            </div>
          </div>
          <input type="hidden" id="trEdicionId" />
        </div>
        <div class="row g-3">
          <div class="col-12">
            <label class="form-label">Nombre (único en la edición)</label>
            <input id="trNombre" type="text" class="form-control" required />
            <div class="invalid-feedback">El nombre es obligatorio.</div>
            <div id="trDupWarn" class="form-text text-danger d-none">
              Ya existe un tipo de registro con ese nombre en esta edición.
            </div>
          </div>
          <div class="col-12">
            <label class="form-label">Descripción</label>
            <textarea id="trDescripcion" class="form-control" rows="3" required></textarea>
            <div class="invalid-feedback">La descripción es obligatoria.</div>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Costo</label>
            <div class="input-group">
              <span class="input-group-text">UYU</span>
              <input id="trCosto" type="number" class="form-control" min="0" step="0.01" required />
            </div>
            <div class="invalid-feedback">Ingresa el costo.</div>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label">Cupo</label>
            <input id="trCupo" type="number" class="form-control" min="0" step="1" required />
            <div class="invalid-feedback">Ingresa el cupo.</div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit">
          <i class="bi bi-check2-circle me-1"></i>Crear tipo
        </button>
      </div>
    </form>
  </div>
</div>

<!-- Modal: Consulta de Registro -->
<div class="modal fade" id="consultaRegistroModal" tabindex="-1" aria-labelledby="consultaRegistroLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="consultaRegistroLabel">
          <i class="bi bi-list-check me-2"></i>Consulta de Registro
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <!-- Contexto de edición -->
        <div class="alert alert-light border mb-3">
          <div class="d-flex flex-wrap align-items-center gap-3">
            <div>
              <span class="text-muted">Evento:</span>
              <strong id="crCtxEv">—</strong>
            </div>
            <div>
              <span class="text-muted">Edición:</span>
              <strong id="crCtxEd">—</strong>
            </div>
          </div>
          <input type="hidden" id="crEdicionId" />
        </div>
        <!-- Buscador -->
        <div class="row g-2 align-items-center mb-2">
          <div class="col-12 col-md-6">
            <div class="input-group">
              <span class="input-group-text"><i class="bi bi-search"></i></span>
              <input id="crSearch" type="text" class="form-control" placeholder="Buscar por nombre, nickname o correo..." />
            </div>
          </div>
          <div class="col-12 col-md-6 text-md-end">
            <span id="crCount" class="text-muted small">0 resultados</span>
          </div>
        </div>
        <!-- Tabla de usuarios registrados -->
        <div class="table-responsive">
          <table class="table table-hover align-middle mb-0" id="crTable">
            <thead class="table-light">
              <tr>
                <th style="width: 28%">Usuario</th>
                <th style="width: 24%">Nickname</th>
                <th style="width: 28%">Correo</th>
                <th style="width: 20%" class="text-end">Acciones</th>
              </tr>
            </thead>
            <tbody id="crTbody">
              <!-- filas dinámicas -->
            </tbody>
          </table>
          <!-- Paginación -->
          <div class="d-flex flex-wrap align-items-center justify-content-between gap-2 mt-2">
            <div class="d-flex align-items-center gap-2">
              <label for="crPageSize" class="form-label m-0 small text-muted">Filas por página</label>
              <select id="crPageSize" class="form-select form-select-sm" style="width: auto">
                <option value="5" selected>5</option>
                <option value="10">10</option>
                <option value="20">20</option>
              </select>
            </div>
            <div class="d-flex align-items-center gap-2">
              <button class="btn btn-sm btn-outline-secondary" id="crPrev">
                <i class="bi bi-caret-left-fill"></i>
              </button>
              <span id="crPageInfo" class="small text-muted">Página 1 de 1</span>
              <button class="btn btn-sm btn-outline-secondary" id="crNext">
                <i class="bi bi-caret-right-fill"></i>
              </button>
            </div>
          </div>
        </div>
        <!-- Detalle del registro seleccionado -->
        <div id="crDetail" class="card mt-3 d-none">
          <div class="card-body">
            <h6 class="card-title mb-3">Detalle de registro</h6>
            <div class="row g-3">
              <div class="col-12 col-md-4">
                <div class="text-muted small">Usuario</div>
                <div id="crDetUser" class="fw-semibold">—</div>
              </div>
              <div class="col-12 col-md-4">
                <div class="text-muted small">Fecha de registro</div>
                <div id="crDetFecha" class="fw-semibold">—</div>
              </div>
              <div class="col-12 col-md-4">
                <div class="text-muted small">Tipo de registro</div>
                <div id="crDetTipo" class="fw-semibold">—</div>
              </div>
              <div class="col-12">
                <div class="text-muted small">Código de patrocinio utilizado</div>
                <div id="crDetCodigo" class="fw-semibold">—</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>

<!-- jQuery primero (requerido por Bootstrap) -->
<script src="${pageContext.request.contextPath}/media/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap & page JS -->
<script src="${pageContext.request.contextPath}/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>window.appCtx='${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/media/js/pages/perfil-organizador.js?v=17"></script>

<!-- Auto-hide alerts after 5 seconds -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    // Solo cerrar alerts que NO estén dentro de modales
    const alerts = document.querySelectorAll('.alert:not(.modal .alert)');
    alerts.forEach(function(alert) {
      setTimeout(function() {
        const bsAlert = new bootstrap.Alert(alert);
        bsAlert.close();
      }, 5000); // 5 segundos
    });
  });
</script>
</body>
</html>
