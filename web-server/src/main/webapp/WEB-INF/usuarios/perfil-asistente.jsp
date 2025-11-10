<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="webservice.DtAsistente, webservice.DtRegistro, java.util.List, java.text.SimpleDateFormat" %>
<%
DtAsistente asistente = (DtAsistente) request.getAttribute("asistente");
List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("registros");
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="es" data-bs-theme="light">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Perfil Asistente • Eventos.uy</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />
    
    <style>
      .profile-card img {
        object-fit: cover;
      }
      .table-wrap {
        overflow-x: auto;
      }
      .cursor-pointer {
        cursor: pointer;
      }
    </style>
</head>
<body class="d-flex flex-column">
    <main class="flex-shrink-0">
        <!-- NAV -->
        <jsp:include page="/WEB-INF/template/header.jsp" />

        <!-- PERFIL ASISTENTE -->
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
                                <img 
                                    src="<%= request.getContextPath() %>/image/<%= asistente.getFoto() != null ? asistente.getFoto() : "default.png" %>" 
                                    class="card-img-top" 
                                    alt="Foto del asistente"
                                />
                                <span class="badge text-bg-primary position-absolute top-0 start-0 m-3 rounded-pill px-3">Asistente</span>
                            </div>
                            <div class="card-body">
                                <div class="d-flex align-items-start justify-content-between flex-wrap">
                                    <div>
                                        <h4 class="card-title mb-1" id="asNombre">
                                            <%= asistente.getNombre() %> <%= asistente.getApellido() %>
                                        </h4>
                                        <a href="#" class="text-muted text-decoration-none">@<%= asistente.getNickname()%></a>
                                    </div>
                                    <button class="btn btn-outline-primary btn-sm mt-2 mt-sm-0" data-bs-toggle="modal" data-bs-target="#editAssistantModal">
                                        <i class="bi bi-pencil-square me-1"></i> Editar
                                    </button>
                                </div>

                                <p class="mt-3 mb-2">
                                    <i class="bi bi-envelope-fill me-1"></i>
                                    <span id="asCorreo"><%= asistente.getCorreo() %></span>
                                </p>

                                <p class="mb-1">
                                    <i class="bi bi-calendar-fill me-1"></i>
                                    Nacimiento: <%= dateFormat.format(asistente.getFechaNacimiento().toGregorianCalendar().getTime()) %>
                                </p>

                                <p class="text-muted fst-italic mb-0">
                                    Miembro de Eventos.uy
                                </p>
                            </div>
                        </div>
                    </aside>

                    <!-- Col derecha: tabla de registros -->
                    <section class="col-12 col-lg-8">
                        <header class="d-flex align-items-center justify-content-between flex-wrap mb-3">
                            <h3 class="fw-bold m-0">Mis Registros</h3>
                            <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addRegistroModal">
                                <i class="bi bi-plus-circle me-1"></i> Nuevo Registro
                            </button>
                        </header>

                        <div class="card shadow-sm">
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0 align-middle" id="tablaRegistros">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Evento</th>
                                                <th>Edición</th>
                                                <th>Tipo</th>
                                                <th>Fecha</th>
                                                <th>Costo</th>
                                                <th class="text-end">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (registros != null && !registros.isEmpty()) { 
                                                for (DtRegistro reg : registros) { %>
                                            <tr>
                                                <td><%= reg.getNombreEvento() %></td>
                                                <td><%= reg.getNombreEdicion() %></td>
                                                <td><%= reg.getTipoRegistro().getNombre() %></td>
                                                <td><%= dateFormat.format(reg.getFecha().toGregorianCalendar().getTime()) %></td>
                                                <td>UYU <%= String.format("%.2f", reg.getCosto()) %></td>
                                                <td class="text-end">
                                                    <button 
                                                        class="btn btn-sm btn-outline-secondary me-1 btn-view-registro"
                                                        data-evento="<%= reg.getNombreEvento() %>"
                                                        data-edicion="<%= reg.getNombreEdicion() %>"
                                                        title="Ver detalles"
                                                    >
                                                        <i class="bi bi-eye"></i>
                                                    </button>
                                                    <button 
                                                        class="btn btn-sm btn-outline-danger btn-del-registro" 
                                                        data-edicion="<%= reg.getNombreEdicion() %>"
                                                        title="Cancelar registro"
                                                    >
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                            <% } 
                                            } else { %>
                                            <tr>
                                                <td colspan="6" class="text-center text-muted py-4">
                                                    No tienes registros todavía
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>
    </main>

    <!-- FOOTER -->
    <footer class="bg-dark py-4 mt-auto">
        <div class="container px-3">
            <div class="row align-items-center justify-content-between flex-column flex-sm-row">
                <div class="col-auto">
                    <small class="text-white">Copyright &copy; Eventos.uy 2024</small>
                </div>
                <div class="col-auto">
                    <a class="link-light small" href="#!">Privacidad</a>
                    <span class="text-white mx-1">&middot;</span>
                    <a class="link-light small" href="#!">Términos</a>
                    <span class="text-white mx-1">&middot;</span>
                    <a class="link-light small" href="#!">Contacto</a>
                </div>
            </div>
        </div>
    </footer>

    <!-- =======================
         MODALS
    ======================== -->

    <!-- Editar Asistente -->
    <div 
        class="modal fade" 
        id="editAssistantModal" 
        tabindex="-1" 
        aria-labelledby="editAssistantLabel" 
        aria-hidden="true"
    >
        <div class="modal-dialog modal-lg modal-dialog-scrollable">
            <form 
                class="modal-content needs-validation" 
                novalidate 
                id="formEditAssistant"
            >
                <div class="modal-header">
                    <h5 class="modal-title" id="editAssistantLabel">
                        <i class="bi bi-pencil me-2"></i>Editar Perfil de Asistente
                    </h5>
                    <button 
                        type="button" 
                        class="btn-close" 
                        data-bs-dismiss="modal" 
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="asInpNombre" class="form-label fw-semibold">Nombre</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            id="asInpNombre" 
                            value="<%= asistente.getNombre() %>" 
                            required 
                        />
                    </div>
                    <div class="mb-3">
                        <label for="asInpApellido" class="form-label fw-semibold">Apellido</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            id="asInpApellido" 
                            value="<%= asistente.getApellido() %>" 
                            required 
                        />
                    </div>
                    <div class="mb-3">
                        <label for="asInpCorreo" class="form-label fw-semibold">Correo</label>
                        <input 
                            type="email" 
                            class="form-control" 
                            id="asInpCorreo" 
                            value="<%= asistente.getCorreo() %>" 
                            required 
                        />
                    </div>
                    <div class="mb-3">
                        <label for="asInpFechaNac" class="form-label fw-semibold">Fecha de Nacimiento</label>
                        <input 
    type="date" 
    class="form-control" 
    id="asInpFechaNac" 
    value="<%= new SimpleDateFormat("yyyy-MM-dd").format(asistente.getFechaNacimiento().toGregorianCalendar().getTime()) %>" 
    required 
/>
                    </div>
                    <hr />
                    <p class="text-muted small">Dejar en blanco si no deseas cambiar la contraseña</p>
                    <div class="mb-3">
                        <label for="asInpPass" class="form-label fw-semibold">Nueva Contraseña</label>
                        <input type="password" class="form-control" id="asInpPass" />
                    </div>
                    <div class="mb-3">
                        <label for="asInpPass2" class="form-label fw-semibold">Confirmar Contraseña</label>
                        <input type="password" class="form-control" id="asInpPass2" />
                    </div>
                    <hr />
                    <div class="mb-3">
                        <label for="asInpImg" class="form-label fw-semibold">Foto de Perfil</label>
                        <input type="file" class="form-control" id="asInpImg" accept="image/*" />
                        <img 
                            src="" 
                            alt="Preview" 
                            class="mt-2 rounded d-none" 
                            width="100" 
                            id="asPreviewImg" 
                        />
                    </div>
                </div>
                <div class="modal-footer">
                    <button 
                        type="button" 
                        class="btn btn-secondary" 
                        data-bs-dismiss="modal"
                    >
                        Cancelar
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-lg me-1"></i>Guardar Cambios
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Nuevo Registro -->
    <div 
        class="modal fade" 
        id="addRegistroModal" 
        tabindex="-1" 
        aria-labelledby="addRegistroLabel" 
        aria-hidden="true"
    >
        <div class="modal-dialog modal-lg modal-dialog-scrollable">
            <form class="modal-content needs-validation" novalidate id="formAddRegistro">
                <div class="modal-header">
                    <h5 class="modal-title" id="addRegistroLabel">
                        <i class="bi bi-plus-lg me-2"></i>Nuevo Registro
                    </h5>
                    <button 
                        type="button" 
                        class="btn-close" 
                        data-bs-dismiss="modal" 
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-12">
                            <label class="form-label">Edición del evento</label>
                            <select id="regEdicion" class="form-select" required>
                                <option value="" disabled selected>Selecciona una edición…</option>
                            </select>
                            <div class="invalid-feedback">Selecciona una edición.</div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Tipo de registro</label>
                            <select id="regTipo" class="form-select" disabled required>
                                <option value="" disabled selected>Selecciona un tipo…</option>
                            </select>
                            <div class="invalid-feedback">Selecciona un tipo de registro.</div>
                            <div class="form-text" id="regCupoInfo"></div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label class="form-label">Costo</label>
                            <div class="input-group">
                                <span class="input-group-text">UYU</span>
                                <input id="regCosto" type="number" class="form-control" readonly />
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label class="form-label">Fecha de registro</label>
                            <input id="regFecha" type="date" class="form-control" readonly required />
                            <div class="invalid-feedback">Se requiere fecha.</div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Código de patrocinio (opcional)</label>
                            <div class="input-group">
                                <input id="regCodigoPatrocinio" type="text" class="form-control" placeholder="Ingresa código si tienes uno" />
                                <button type="button" class="btn btn-outline-secondary" id="btnValidarCodigo">
                                    <i class="bi bi-check-circle me-1"></i>Validar
                                </button>
                            </div>
                            <div class="form-text text-success d-none" id="regCodigoValido">
                                <i class="bi bi-check-circle-fill me-1"></i>Código válido. Registro gratuito aplicado.
                            </div>
                            <div class="form-text text-danger d-none" id="regCodigoInvalido">
                                <i class="bi bi-x-circle-fill me-1"></i>Código no válido o ya utilizado.
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button 
                        type="button" 
                        class="btn btn-secondary" 
                        data-bs-dismiss="modal"
                    >
                        Cancelar
                    </button>
                    <button class="btn btn-primary" type="submit">
                        <i class="bi bi-check2-circle me-1"></i>Confirmar Registro
                    </button>
                </div>
            </form>
        </div>
    </div>
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Confirmación Eliminar -->
    <div 
        class="modal fade" 
        id="confirmDeleteModal" 
        tabindex="-1" 
        aria-hidden="true"
    >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="bi bi-exclamation-triangle text-warning me-2"></i>Confirmar Cancelación
                    </h5>
                    <button 
                        type="button" 
                        class="btn-close" 
                        data-bs-dismiss="modal" 
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body">
                    ¿Estás seguro que deseas cancelar este registro? Esta acción no se puede deshacer.
                </div>
                <div class="modal-footer">
                    <button 
                        type="button" 
                        class="btn btn-secondary" 
                        data-bs-dismiss="modal"
                    >
                        Cancelar
                    </button>
                    <button 
                        type="button" 
                        class="btn btn-danger" 
                        id="btnConfirmDelete"
                    >
                        <i class="bi bi-trash me-1"></i>Eliminar
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript -->
    <script>
    // Helper functions
    const byId = (id) => document.getElementById(id);

    let pendingDeleteEdicion = null;

    // Show alert helper
    function showAlert(message, type = 'info') {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 start-50 translate-middle-x mt-3`;
        alertDiv.style.zIndex = '9999';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        document.body.appendChild(alertDiv);
        
        setTimeout(() => {
            alertDiv.remove();
        }, 5000);
    }

    // Initialize
    document.addEventListener('DOMContentLoaded', () => {
        // Handle view registro button
        document.querySelectorAll('.btn-view-registro').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const evento = e.currentTarget.dataset.evento;
                const edicion = e.currentTarget.dataset.edicion;
                // Redirect to edition details page
                window.location.href = '<%= request.getContextPath() %>/edicion?evento=' + encodeURIComponent(evento) + '&edicion=' + encodeURIComponent(edicion);
            });
        });

        // Handle delete registro button
        document.querySelectorAll('.btn-del-registro').forEach(btn => {
            btn.addEventListener('click', (e) => {
                pendingDeleteEdicion = e.currentTarget.dataset.edicion;
                new bootstrap.Modal('#confirmDeleteModal').show();
            });
        });

        // Image preview
        byId('asInpImg')?.addEventListener('change', (e) => {
            const file = e.target.files?.[0];
            const preview = byId('asPreviewImg');
            if (file) {
                preview.src = URL.createObjectURL(file);
                preview.classList.remove('d-none');
            } else {
                preview.src = '';
                preview.classList.add('d-none');
            }
        });
    });

    // Form validation and submission
    (function () {
        'use strict';
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach((form) => {
            form.addEventListener('submit', function (event) {
                event.preventDefault();
                event.stopPropagation();
                
                // Validate passwords match
                if (form.id === 'formEditAssistant') {
                    const p1 = byId('asInpPass')?.value || '';
                    const p2 = byId('asInpPass2')?.value || '';
                    if ((p1 || p2) && p1 !== p2) {
                        byId('asInpPass2').setCustomValidity('Las contraseñas no coinciden');
                    } else {
                        byId('asInpPass2').setCustomValidity('');
                    }
                }

                if (!form.checkValidity()) {
                    form.classList.add('was-validated');
                    return;
                }

                // Submit the form via AJAX
                if (form.id === 'formEditAssistant') {
                    submitEditProfile(form);
                }
                
                form.classList.add('was-validated');
            }, false);
        });
    })();

    // Submit edit profile
    function submitEditProfile(form) {
        const formData = new FormData(form);
        formData.append('action', 'editProfile');
        formData.append('nombre', byId('asInpNombre').value);
        formData.append('apellido', byId('asInpApellido').value);
        formData.append('correo', byId('asInpCorreo').value);
        formData.append('fechaNacimiento', byId('asInpFechaNac').value);
        
        const password = byId('asInpPass').value;
        if (password) {
            formData.append('password', password);
        }

        // Show loading state
        const submitBtn = form.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Guardando...';

        fetch('<%= request.getContextPath() %>/perfil', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;
            
            if (data.success) {
                showAlert(data.message, 'success');
                bootstrap.Modal.getInstance(byId('editAssistantModal')).hide();
                
                // Update profile display
                byId('asNombre').textContent = byId('asInpNombre').value + ' ' + byId('asInpApellido').value;
                byId('asCorreo').innerHTML = '<i class="bi bi-envelope me-1"></i>' + byId('asInpCorreo').value;
                
                // Reload after a short delay to show updated data
                setTimeout(() => {
                    location.reload();
                }, 1500);
            } else {
                showAlert(data.message, 'danger');
            }
        })
        .catch(error => {
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;
            showAlert('Error al actualizar el perfil: ' + error.message, 'danger');
        });
    }

    // Confirm delete registro
    byId('btnConfirmDelete')?.addEventListener('click', () => {
        if (pendingDeleteEdicion !== null) {
            const btn = byId('btnConfirmDelete');
            const originalText = btn.innerHTML;
            btn.disabled = true;
            btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Eliminando...';

            fetch('<%= request.getContextPath() %>/perfil', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'action=deleteRegistro&edicion=' + encodeURIComponent(pendingDeleteEdicion)
            })
            .then(response => response.json())
            .then(data => {
                btn.disabled = false;
                btn.innerHTML = originalText;
                
                if (data.success) {
                    showAlert(data.message, 'success');
                    bootstrap.Modal.getInstance(byId('confirmDeleteModal')).hide();
                    
                    // Reload to show updated registrations
                    setTimeout(() => {
                        location.reload();
                    }, 1500);
                } else {
                    showAlert(data.message, 'danger');
                }
            })
            .catch(error => {
                btn.disabled = false;
                btn.innerHTML = originalText;
                showAlert('Error al cancelar el registro: ' + error.message, 'danger');
            });
            
            pendingDeleteEdicion = null;
        }
    });
    
    // Auto-hide alerts after 5 seconds
    document.addEventListener('DOMContentLoaded', function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            setTimeout(function() {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }, 5000); // 5 segundos
        });
    });
    </script>
    <script src="${pageContext.request.contextPath}/media/js/pages/perfil-asistente.js?v=1"></script>
</body>
</html>
