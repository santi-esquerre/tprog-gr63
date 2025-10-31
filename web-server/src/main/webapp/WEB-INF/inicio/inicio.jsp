<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="datatypes.DTEvento" %>
<%@ page import="datatypes.DTEventoDetallado" %>
<%@ page import="datatypes.DTUsuario" %>
<%
    @SuppressWarnings("unchecked")
    List<DTEventoDetallado> eventosDestacados = (List<DTEventoDetallado>) request.getAttribute("eventosDestacados");
    Boolean sesionIniciada = (Boolean) request.getAttribute("sesionIniciada");
    DTUsuario usuarioLogueado = (DTUsuario) request.getAttribute("usuarioLogueado");
    Integer totalEventos = (Integer) request.getAttribute("totalEventos");
    Integer totalEdiciones = (Integer) request.getAttribute("totalEdiciones");
    
    if (sesionIniciada == null) sesionIniciada = false;
    if (totalEventos == null) totalEventos = 0;
    if (totalEdiciones == null) totalEdiciones = 0;
%>
<!DOCTYPE html>
<html lang="es" data-bs-theme="light">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>EventosUY - Inicio</title>

        <!-- Favicon -->
        <link
          rel="icon"
          type="image/x-icon"
          href="<%= request.getContextPath() %>/media/images/favicon.ico"
        />

        <!-- Bootstrap & Icons -->

        <jsp:include page="/WEB-INF/template/head.jsp" />
      </head>
      <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/template/header.jsp" />

        <main class="flex-grow-1">
          <!-- HERO -->
          <header class="hero py-5">
            <div class="container hero-content pt-5">
              <div class="row align-items-center">
                <div class="col-12 col-lg-7">
                  <h1 class="display-4 fw-bold mb-3">
                    Descubrí los
                    <span class="text-primary">mejores eventos</span> de Uruguay
                  </h1>
                  <p class="lead mb-4 text-white-50">
                    Ferias, conferencias, música, deporte y más. Organizadores
                    verificados y registros en un clic.
                  </p>

                  <!-- Buscador -->
                  <form
                    action="<%= request.getContextPath() %>/eventos"
                    method="GET"
                    class="p-2 p-sm-3 search-wrap mb-3"
                  >
                    <div class="row g-2">
                      <div class="col-12 col-md">
                        <div class="input-group">
                          <span class="input-group-text bg-transparent border-0"
                            ><i class="bi bi-search"></i
                          ></span>
                          <input
                            id="q"
                            name="q"
                            type="search"
                            class="form-control border-0"
                            placeholder="Buscar por nombre, ciudad o categoría…"
                          />
                        </div>
                      </div>
                      <div class="col-6 col-md-3">
                        <input
                          id="fecha"
                          name="fecha"
                          type="date"
                          class="form-control border-0"
                        />
                      </div>
                      <div class="col-6 col-md-3 d-grid">
                        <button type="submit" class="btn btn-primary">
                          <i class="bi bi-magic me-1"></i> Ver eventos
                        </button>
                      </div>
                    </div>
                  </form>

                  <!-- Chips categorías -->
                  <div class="d-flex flex-wrap gap-2">
                    <a
                      href="<%= request.getContextPath() %>/eventos?categoria=Tecnología"
                      class="chip text-decoration-none"
                      ><i class="bi bi-laptop"></i> Tecnología</a
                    >
                    <a
                      href="<%= request.getContextPath() %>/eventos?categoria=Innovación"
                      class="chip text-decoration-none"
                      ><i class="bi bi-cpu"></i> Innovación</a
                    >
                    <a
                      href="<%= request.getContextPath() %>/eventos?categoria=Salud"
                      class="chip text-decoration-none"
                      ><i class="bi bi-heart"></i> Salud</a
                    >
                    <a
                      href="<%= request.getContextPath() %>/eventos?categoria=Deporte"
                      class="chip text-decoration-none"
                      ><i class="bi bi-bicycle"></i> Deporte</a
                    >
                  </div>

                  <div class="d-flex gap-2 mt-4">
                    <a class="btn btn-outline-light" href="#destacados"
                      ><i class="bi bi-lightning-charge me-1"></i> Ver
                      destacados</a
                    >
                    <% if (!sesionIniciada) { %>
                    <a
                      class="btn btn-outline-light"
                      href="<%= request.getContextPath() %>/registro"
                      ><i class="bi bi-briefcase me-1"></i> Soy organizador</a
                    >
                    <% } %>
                  </div>
                </div>

                <!-- Carrusel / Showcase -->
                <div class="col-12 col-lg-5 mt-5 mt-lg-0">
                  <div
                    id="heroCarousel"
                    class="carousel slide shadow-lg rounded overflow-hidden float"
                  >
                    <div class="carousel-indicators">
                      <button
                        type="button"
                        data-bs-target="#heroCarousel"
                        data-bs-slide-to="0"
                        class="active"
                      ></button>
                      <button
                        type="button"
                        data-bs-target="#heroCarousel"
                        data-bs-slide-to="1"
                      ></button>
                      <button
                        type="button"
                        data-bs-target="#heroCarousel"
                        data-bs-slide-to="2"
                      ></button>
                    </div>
                    <div class="carousel-inner">
                      <div class="carousel-item active">
                        <img
                          src="https://images.unsplash.com/photo-1514525253161-7a46d19cd819?q=80&w=1400&auto=format&fit=crop"
                          class="d-block w-100"
                          alt="Conferencia"
                        />
                        <div class="carousel-caption text-start">
                          <h5>Conf. de Tecnología</h5>
                          <p class="small">Charlas, demos y networking.</p>
                        </div>
                      </div>
                      <div class="carousel-item">
                        <img
                          src="https://images.unsplash.com/photo-1506157786151-b8491531f063?q=80&w=1400&auto=format&fit=crop"
                          class="d-block w-100"
                          alt="Concierto"
                        />
                        <div class="carousel-caption text-start">
                          <h5>Conciertos</h5>
                          <p class="small">Las mejores bandas en vivo.</p>
                        </div>
                      </div>
                      <div class="carousel-item">
                        <img
                          src="https://images.unsplash.com/photo-1485217988980-11786ced9454?q=80&w=1400&auto=format&fit=crop"
                          class="d-block w-100"
                          alt="Feria del libro"
                        />
                        <div class="carousel-caption text-start">
                          <h5>Ferias & Cultura</h5>
                          <p class="small">Libros, arte y gastronomía.</p>
                        </div>
                      </div>
                    </div>
                    <button
                      class="carousel-control-prev"
                      type="button"
                      data-bs-target="#heroCarousel"
                      data-bs-slide="prev"
                    >
                      <span class="carousel-control-prev-icon"></span>
                      <span class="visually-hidden">Anterior</span>
                    </button>
                    <button
                      class="carousel-control-next"
                      type="button"
                      data-bs-target="#heroCarousel"
                      data-bs-slide="next"
                    >
                      <span class="carousel-control-next-icon"></span>
                      <span class="visually-hidden">Siguiente</span>
                    </button>
                  </div>
                </div>
              </div>

              <!-- Estadísticas -->
              <div class="row g-3 mt-5">
                <div class="col-6 col-md-3">
                  <div class="stat p-3 text-center">
                    <div class="h2 mb-0">
                      <span class="counter" data-to="<%= totalEventos %>"
                        >0</span
                      >+
                    </div>
                    <div class="text-muted small">Eventos activos</div>
                  </div>
                </div>
                <div class="col-6 col-md-3">
                  <div class="stat p-3 text-center">
                    <div class="h2 mb-0">
                      <span class="counter" data-to="<%= totalEdiciones %>"
                        >0</span
                      >+
                    </div>
                    <div class="text-muted small">Ediciones gestionadas</div>
                  </div>
                </div>
                <div class="col-6 col-md-3">
                  <div class="stat p-3 text-center">
                    <div class="h2 mb-0">
                      <span class="counter" data-to="95">0</span>%
                    </div>
                    <div class="text-muted small">Satisfacción</div>
                  </div>
                </div>
                <div class="col-6 col-md-3">
                  <div class="stat p-3 text-center">
                    <div class="h2 mb-0">
                      <span class="counter" data-to="20">0</span>+
                    </div>
                    <div class="text-muted small">Años de experiencia</div>
                  </div>
                </div>
              </div>
            </div>
          </header>

          <!-- Destacados -->
          <section id="destacados" class="py-5">
            <div class="container">
              <div class="text-center mb-4">
                <h2 class="fw-bold">Eventos destacados</h2>
                <p class="text-muted mb-0">
                  Seleccionados por el equipo de Eventos.uy
                </p>
              </div>
              <div class="row g-4 justify-content-center">
                <% if (eventosDestacados != null && !eventosDestacados.isEmpty()) { 
                    for (DTEventoDetallado eventoDetallado : eventosDestacados) { 
                        DTEvento evento = eventoDetallado.evento();
                        Set<String> categorias = eventoDetallado.categorias();
                %>
                <!-- Card -->
                <div class="col-12 col-md-6 col-lg-4">
                  <div class="card h-100 shadow border-0 card-hover">
                    <% if (evento.foto() != null && !evento.foto().isEmpty()) { %>
                    <img
                      class="card-img-top"
                      src="<%= request.getContextPath() %>/image/<%= evento.foto() %>"
                      alt="<%= evento.nombre() %>"
                      style="height: 200px; object-fit: cover"
                      onerror="this.src='<%= request.getContextPath() %>/image/default.png'"
                    />
                    <% } else { %>
                    <img
                      class="card-img-top"
                      src="<%= request.getContextPath() %>/image/default.png"
                      alt="<%= evento.nombre() %>"
                      style="height: 200px; object-fit: cover"
                    />
                    <% } %>
                    <div class="card-body">
                      <div class="d-flex justify-content-start gap-2 align-items-center mb-2 flex-wrap">
                        <% if (categorias != null && !categorias.isEmpty()) { 
                            for (String categoria : categorias) { %>
                        <span class="badge bg-primary"><%= categoria %></span>
                        <% } } %>
                      </div>
                      <h5 class="card-title mb-2"><%= evento.nombre() %></h5>
                      <p class="card-text small text-muted">
                        <% if (evento.descripcion() != null) { 
                            String desc = evento.descripcion();
                            if (desc.length() > 80) { %>
                                <%= desc.substring(0, 80) + "..." %>
                            <% } else { %>
                                <%= desc %>
                            <% } 
                        } else { %>
                            Sin descripción disponible.
                        <% } %>
                      </p>
                      <a
                        class="btn btn-outline-primary w-100"
                        href="<%= request.getContextPath() %>/evento/<%= evento.nombre() %>"
                      >
                        Ver más
                      </a>
                    </div>
                  </div>
                </div>
                <% } } else { %>
                <div class="col-12 text-center">
                  <p class="text-muted">
                    No hay eventos destacados en este momento.
                  </p>
                </div>
                <% } %>
              </div>

              <!-- CTA organizadores -->
              <% if (!sesionIniciada) { %>
              <div
                id="organizadores"
                class="mt-5 p-4 p-md-5 bg-gradient rounded-4"
                style="
                  background: linear-gradient(
                    135deg,
                    rgba(13, 110, 253, 0.12),
                    rgba(111, 66, 193, 0.12)
                  );
                  border: 1px solid rgba(13, 110, 253, 0.2);
                "
              >
                <div class="row align-items-center g-4">
                  <div class="col-12 col-md">
                    <h3 class="mb-1">¿Sos organizador?</h3>
                    <p class="text-muted mb-0">
                      Publicá tu evento y manejá
                      <strong>registros, tipos, ediciones y patrocinios</strong>
                      desde un panel único.
                    </p>
                  </div>
                  <div class="col-12 col-md-auto d-grid d-sm-flex gap-2">
                    <a
                      class="btn btn-primary"
                      href="<%= request.getContextPath() %>/registro"
                      ><i class="bi bi-rocket-takeoff me-1"></i> Empezar
                      ahora</a
                    >
                    <a class="btn btn-outline-secondary" href="#faq"
                      ><i class="bi bi-question-circle me-1"></i> Cómo
                      funciona</a
                    >
                  </div>
                </div>
              </div>
              <% } %>
            </div>
          </section>

          <!-- Testimonio + FAQ -->
          <section class="py-5 bg-light">
            <div class="container">
              <div class="row g-5">
                <div class="col-12 col-lg-6">
                  <div class="text-center text-lg-start">
                    <div class="fs-5 mb-3 fst-italic">
                      “Eventos.uy nos simplificó todo. Registro sin filas y
                      edición confirmada en horas.”
                    </div>
                    <div
                      class="d-flex align-items-center justify-content-center justify-content-lg-start"
                    >
                      <img
                        class="rounded-circle me-3"
                        src="https://dummyimage.com/48x48/ced4da/6c757d"
                        alt="avatar"
                      />
                      <div class="fw-semibold">
                        Aitor Tilla <span class="text-primary">/</span> CEO,
                        FIngVinci
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-12 col-lg-6" id="faq">
                  <div class="accordion" id="faqAcc">
                    <div class="accordion-item">
                      <h2 class="accordion-header">
                        <button
                          class="accordion-button"
                          data-bs-toggle="collapse"
                          data-bs-target="#f1"
                        >
                          ¿Cómo publico mi evento?
                        </button>
                      </h2>
                      <div
                        id="f1"
                        class="accordion-collapse collapse show"
                        data-bs-parent="#faqAcc"
                      >
                        <div class="accordion-body">
                          Creá tu perfil de organizador, cargá el evento y su
                          primera edición. Nuestro equipo verifica y listo.
                        </div>
                      </div>
                    </div>
                    <div class="accordion-item">
                      <h2 class="accordion-header">
                        <button
                          class="accordion-button collapsed"
                          data-bs-toggle="collapse"
                          data-bs-target="#f2"
                        >
                          ¿Cobran comisión?
                        </button>
                      </h2>
                      <div
                        id="f2"
                        class="accordion-collapse collapse"
                        data-bs-parent="#faqAcc"
                      >
                        <div class="accordion-body">
                          Tenemos planes flexibles según lo que necesites:
                          difusión, cobro, acreditación, etc.
                        </div>
                      </div>
                    </div>
                    <div class="accordion-item">
                      <h2 class="accordion-header">
                        <button
                          class="accordion-button collapsed"
                          data-bs-toggle="collapse"
                          data-bs-target="#f3"
                        >
                          ¿Cómo me registro como asistente?
                        </button>
                      </h2>
                      <div
                        id="f3"
                        class="accordion-collapse collapse"
                        data-bs-parent="#faqAcc"
                      >
                        <div class="accordion-body">
                          Buscá la edición, elegí el tipo de registro y completá
                          tus datos. Vas a recibir confirmación por mail.
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>

        <!-- FOOTER -->
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

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
          // Contadores animados al entrar en viewport
          const counters = document.querySelectorAll(".counter");
          const io = new IntersectionObserver(
            (entries, obs) => {
              entries.forEach((entry) => {
                if (!entry.isIntersecting) return;
                const el = entry.target;
                const to = +el.dataset.to || 0;
                const dur = 1000;
                const start = performance.now();
                (function tick(now) {
                  const p = Math.min((now - start) / dur, 1);
                  el.textContent = Math.floor(p * to);
                  if (p < 1) requestAnimationFrame(tick);
                })(start);
                obs.unobserve(el);
              });
            },
            { threshold: 0.6 }
          );
          counters.forEach((c) => io.observe(c));
        </script>
      </body>
    </html>
