<%@page import="model.EstadoSesion"%>
<%@page import="controllers.SessionManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	SessionManager.initSesion(request);
	EstadoSesion estadoSesion = (EstadoSesion) request.getSession().getAttribute("estado_sesion");
%>
 <!-- NAV -->
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
    </style>
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top navbar-blur">
      <div class="container">
        <a class="navbar-brand fw-semibold" href="index.html">
          <i class="bi bi-stars me-2 text-warning"></i>Eventos<span
            class="text-primary"
            >.uy</span
          >
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#nav"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="nav">
          <ul class="navbar-nav ms-auto align-items-lg-center">
            <li class="nav-item">
              <a class="nav-link" href="<%=request.getContextPath()%>/inicio">Inicio</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="<%=request.getContextPath()%>/eventos">Eventos</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="<%=request.getContextPath()%>/usuarios">Usuarios</a>
            </li>
            <% if(estadoSesion == EstadoSesion.LOGIN_CORRECTO) { %>
            <li class="nav-item">
              <a class="nav-link" href="<%=request.getContextPath()%>/perfil">Mi perfil</a>
            </li>
            
            <li class="nav-item ms-lg-2">
              <a class="btn btn-danger" id="login/logout" href="<%=request.getContextPath()%>/logout"
                ><i class="bi bi-box-arrow-in-right me-1"></i> Cerrar sesión</a>
            </li>
            <%} else {%>
            <li class="nav-item ms-lg-2">
              <a class="btn btn-primary" id="login/logout" href="<%=request.getContextPath()%>/sesion"
                ><i class="bi bi-box-arrow-in-right me-1"></i> Iniciar sesión</a
              >
            </li>
            <% }%>
            
          </ul>
        </div>
      </div>
    </nav>