// /js/pages/perfil-organizador.js
(() => {
  const $ = (id) => document.getElementById(id);
  const ctx = window.appCtx || "";

  // Helpers
  const j = (u, q) => (q ? `${u}?${new URLSearchParams(q)}` : u);

  // Render helpers
  function cardEvento(ev) {
    // Manejar diferentes estructuras de datos
    const evento = ev.evento || ev; // Si viene DTEventoDetallado o DTEvento directo
    const nombre = evento.nombre || "Sin nombre";
    const descripcion = evento.descripcion || "—";
    const categorias = ev.categorias || [];

    return `
      <div class="col-12 col-md-6">
        <div class="card shadow-sm h-100">
          <div class="card-body">
            <h5 class="fw-bold mb-1">${nombre}</h5>
            <p class="text-muted mb-2">${descripcion}</p>
            <div class="d-flex gap-2 flex-wrap">
              ${categorias
                .map((c) => `<span class="chip">${c}</span>`)
                .join("")}
            </div>
          </div>
        </div>
      </div>`;
  }

  function cardEdicion(ed) {
    const f = (d) => (d ? new Date(d).toLocaleDateString() : "—");
    return `
      <div class="col-12 col-md-6">
        <div class="card shadow-sm h-100">
          <div class="card-body">
            <div class="d-flex justify-content-between">
              <h5 class="fw-bold mb-1">${ed.nombre}</h5>
              <span class="badge text-bg-secondary">${ed.estado}</span>
            </div>
            <p class="mb-1">${ed.ciudad}, ${ed.pais}</p>
            <p class="text-muted mb-0">Del ${f(ed.fechaInicio)} al ${f(
      ed.fechaFin
    )}</p>
          </div>
          <div class="card-footer d-flex gap-2">
            <a class="btn btn-sm btn-outline-primary" href="${ctx}/edicion/${encodeURIComponent(
      ed.nombre
    ).replace(/%20/g, "-")}">
              <i class="bi bi-box-arrow-up-right me-1"></i> Ver
            </a>
            <button class="btn btn-sm btn-outline-secondary" data-ed="${
              ed.nombre
            }" data-action="registros">
              <i class="bi bi-list-check me-1"></i> Registros
            </button>
          </div>
        </div>
      </div>`;
  }

  // Carga inicial
  async function loadEventos() {
    try {
      const res = await fetch(`${ctx}/api/organizador/eventos`);
      const data = await res.json(); // devuelve DTEventoDetallado[]
      console.log("Eventos cargados:", data);
      document.getElementById("eventosGrid").innerHTML = (data || [])
        .map(cardEvento)
        .join("");
    } catch (error) {
      console.error("Error cargando eventos:", error);
      document.getElementById("eventosGrid").innerHTML =
        '<p class="text-danger">Error al cargar eventos</p>';
    }
  }

  async function loadEdiciones() {
    try {
      const res = await fetch(`${ctx}/api/organizador/ediciones`);
      const data = await res.json(); // devuelve DTEdicion[] (o similar)
      console.log("Ediciones cargadas:", data);
      // Asegurarse de que data es un array antes de mapear
      const ediciones = Array.isArray(data) ? data : [];
      document.getElementById("edicionesGrid").innerHTML = ediciones
        .map(cardEdicion)
        .join("");
    } catch (error) {
      console.error("Error cargando ediciones:", error);
      document.getElementById("edicionesGrid").innerHTML =
        '<p class="text-danger">Error al cargar ediciones</p>';
    }
  }

  // Alta Evento (usa /api/organizador vía POST — ver tu servlet)
  async function handleAddEvent(ev) {
    ev.preventDefault(); // Prevenir recarga de página
    console.log("=== handleAddEvent iniciado ===");

    const form = ev.currentTarget;

    // Validar categorías antes de validar el formulario
    const sel = document.getElementById("evCategorias");
    const categoriasArray = Array.from(sel.selectedOptions).map((o) => o.value);

    if (categoriasArray.length === 0) {
      alert("Debe seleccionar al menos una categoría");
      return;
    }

    if (!form.checkValidity()) {
      form.classList.add("was-validated");
      return;
    }

    try {
      const fd = new FormData();
      fd.set("nombre", document.getElementById("evNombre").value.trim());
      fd.set(
        "descripcion",
        document.getElementById("evDescripcion").value.trim()
      );
      fd.set("sigla", document.getElementById("evSigla").value.trim());
      fd.set("categorias", JSON.stringify(categoriasArray));

      console.log("Datos a enviar:", {
        nombre: document.getElementById("evNombre").value.trim(),
        descripcion: document.getElementById("evDescripcion").value.trim(),
        sigla: document.getElementById("evSigla").value.trim(),
        categorias: categoriasArray,
      });

      const img = document.getElementById("evImagen").files?.[0];
      if (img) {
        console.log("Imagen adjunta:", img.name);
        fd.set("imagen", img);
      }

      console.log("Enviando fetch a:", `${ctx}/api/organizador/eventos`);
      const res = await fetch(`${ctx}/api/organizador/eventos`, {
        method: "POST",
        body: fd,
      });

      console.log("Respuesta recibida, status:", res.status);
      const out = await res.json();
      console.log("JSON parseado:", out);

      if (out.success) {
        console.log("Evento creado exitosamente");
        // Cerrar modal usando jQuery directamente
        const modalEl = document.getElementById("addEventModal");
        if (typeof jQuery !== "undefined") {
          jQuery(modalEl).modal("hide");
        } else {
          // Fallback: remover manualmente las clases de Bootstrap
          modalEl.classList.remove("show");
          document.body.classList.remove("modal-open");
          const backdrop = document.querySelector(".modal-backdrop");
          if (backdrop) backdrop.remove();
        }

        // Limpiar formulario
        form.reset();
        form.classList.remove("was-validated");

        // Recargar eventos
        await loadEventos();

        // Mostrar mensaje de éxito
        alert("Evento creado exitosamente");
      } else {
        console.error("Error del servidor:", out.message);
        alert(out.message || "Error al crear evento");
      }
    } catch (error) {
      console.error("Error en handleAddEvent:", error);
      alert("Error al crear el evento: " + error.message);
    }
  }

  // Alta Edición
  async function handleAddEdition(ev) {
    ev.preventDefault();
    console.log("=== handleAddEdition iniciado ===");

    const form = ev.currentTarget;
    if (!form.checkValidity()) {
      form.classList.add("was-validated");
      return;
    }

    try {
      const fd = new FormData();
      fd.set("evento", document.getElementById("edEvento").value);
      fd.set("nombre", document.getElementById("edNombre").value.trim());
      fd.set("sigla", document.getElementById("edSigla").value.trim());
      fd.set("ciudad", document.getElementById("edCiudad").value.trim());
      fd.set("pais", document.getElementById("edPais").value.trim());
      fd.set("fechaInicio", document.getElementById("edInicio").value);
      fd.set("fechaFin", document.getElementById("edFin").value);

      console.log("Datos de edición a enviar:", {
        evento: document.getElementById("edEvento").value,
        nombre: document.getElementById("edNombre").value.trim(),
        fechaInicio: document.getElementById("edInicio").value,
        fechaFin: document.getElementById("edFin").value,
      });

      const img = document.getElementById("edImagen").files?.[0];
      if (img) {
        console.log("Imagen adjunta:", img.name);
        fd.set("imagen", img);
      }

      console.log("Enviando fetch a:", `${ctx}/api/organizador/ediciones`);
      const res = await fetch(`${ctx}/api/organizador/ediciones`, {
        method: "POST",
        body: fd,
      });

      console.log("Respuesta recibida, status:", res.status);
      const out = await res.json();
      console.log("JSON parseado:", out);

      if (out.success) {
        console.log("Edición creada exitosamente");
        // Cerrar modal usando jQuery directamente
        const modalEl = document.getElementById("addEditionModal");
        if (typeof jQuery !== "undefined") {
          jQuery(modalEl).modal("hide");
        } else {
          // Fallback: remover manualmente las clases de Bootstrap
          modalEl.classList.remove("show");
          document.body.classList.remove("modal-open");
          const backdrop = document.querySelector(".modal-backdrop");
          if (backdrop) backdrop.remove();
        }

        // Limpiar formulario
        form.reset();
        form.classList.remove("was-validated");

        // Recargar ediciones
        await loadEdiciones();

        // Mostrar mensaje de éxito
        alert("Edición creada exitosamente");
      } else {
        console.error("Error del servidor:", out.message);
        alert(out.message || "Error al crear edición");
      }
    } catch (error) {
      console.error("Error en handleAddEdition:", error);
      alert("Error al crear la edición: " + error.message);
    }
  }

  // Editar perfil (POST /perfil?action=editProfile) — ya implementado en tu servlet
  document
    .getElementById("formEditOrganizer")
    ?.addEventListener("submit", async (ev) => {
      ev.preventDefault();
      const form = ev.currentTarget;
      // validar contraseñas iguales
      const p1 = document.getElementById("inpPass").value;
      const p2 = document.getElementById("inpPass2").value;
      if ((p1 || p2) && p1 !== p2) {
        form.classList.add("was-validated");
        return;
      }

      const fd = new FormData(form);
      const res = await fetch(`${ctx}/perfil`, { method: "POST", body: fd });
      const out = await res.json();
      if (out.success) {
        // reflejar cambios mínimos en UI
        document.getElementById("orgNombre").textContent =
          document.getElementById("inpNombre").value || "—";
        document.getElementById("orgDescripcion").textContent =
          document.getElementById("inpDescripcion").value || "—";
        const link = document.getElementById("inpLink").value;
        const a = document.getElementById("orgLink");
        a.textContent = link || "—";
        a.href = link || "#";
        bootstrap.Modal.getInstance(
          document.getElementById("editOrganizerModal")
        ).hide();
      } else {
        alert(out.message || "Error al actualizar el perfil");
      }
    });

  // Preview imagen en modal de perfil
  document.getElementById("inpImg")?.addEventListener("change", (e) => {
    const f = e.target.files?.[0];
    const img = document.getElementById("previewImg");
    if (f) {
      img.src = URL.createObjectURL(f);
      img.classList.remove("d-none");
    } else {
      img.src = "";
      img.classList.add("d-none");
    }
  });

  // ===========================
  // ALTA INSTITUCIÓN
  // ===========================

  // Preview del logo de la institución
  document.getElementById("insLogo")?.addEventListener("change", (e) => {
    const f = e.target.files?.[0];
    const preview = document.getElementById("insPreview");
    if (!f) {
      preview.classList.add("d-none");
      return;
    }
    preview.src = URL.createObjectURL(f);
    preview.classList.remove("d-none");
  });

  // Submit del formulario de institución
  document
    .getElementById("formAddInstitution")
    ?.addEventListener("submit", async (ev) => {
      ev.preventDefault();
      console.log("=== handleAddInstitution iniciado ===");

      const form = ev.currentTarget;
      if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
      }

      try {
        const fd = new FormData();
        fd.set("nombre", document.getElementById("insNombre").value.trim());
        fd.set(
          "descripcion",
          document.getElementById("insDescripcion").value.trim()
        );

        const web = document.getElementById("insWeb").value.trim();
        if (web) fd.set("sitioWeb", web);

        const logo = document.getElementById("insLogo").files?.[0];
        if (logo) {
          console.log("Logo adjunto:", logo.name);
          fd.set("imagen", logo);
        }

        console.log("Datos de institución a enviar:", {
          nombre: document.getElementById("insNombre").value.trim(),
          descripcion: document.getElementById("insDescripcion").value.trim(),
          sitioWeb: web || "—",
        });

        console.log(
          "Enviando fetch a:",
          `${ctx}/api/organizador/instituciones`
        );
        const res = await fetch(`${ctx}/api/organizador/instituciones`, {
          method: "POST",
          body: fd,
        });

        console.log("Respuesta recibida, status:", res.status);
        const out = await res.json();
        console.log("JSON parseado:", out);

        if (out.success) {
          console.log("Institución creada exitosamente");
          // Cerrar modal
          const modalEl = document.getElementById("addInstitutionModal");
          if (typeof jQuery !== "undefined") {
            jQuery(modalEl).modal("hide");
          } else {
            modalEl.classList.remove("show");
            document.body.classList.remove("modal-open");
            const backdrop = document.querySelector(".modal-backdrop");
            if (backdrop) backdrop.remove();
          }

          // Limpiar formulario
          form.reset();
          form.classList.remove("was-validated");
          document.getElementById("insDupWarn").classList.add("d-none");
          document.getElementById("insPreview").classList.add("d-none");

          // Mostrar mensaje de éxito
          alert("Institución creada exitosamente");
        } else {
          console.error("Error del servidor:", out.message);

          // Si es error de duplicado, mostrar warning específico
          if (out.message && out.message.includes("existe")) {
            document.getElementById("insDupWarn").classList.remove("d-none");
          }

          alert(out.message || "Error al crear institución");
        }
      } catch (error) {
        console.error("Error en handleAddInstitution:", error);
        alert("Error al crear la institución: " + error.message);
      }
    });

  // ===========================
  // Poblar selects auxiliares
  // ===========================
  async function loadCategorias() {
    const res = await fetch(`${ctx}/api/organizador/categorias`);
    const cats = await res.json();
    const sel = document.getElementById("evCategorias");
    sel.innerHTML = (cats || [])
      .map((c) => `<option value="${c}">${c}</option>`)
      .join("");
  }

  async function loadEventosSelect() {
    try {
      const res = await fetch(`${ctx}/api/organizador/eventos`);
      const dets = await res.json();
      console.log("Eventos para select:", dets);
      const sel = document.getElementById("edEvento");
      sel.innerHTML = (dets || [])
        .map((d) => {
          // Manejar diferentes estructuras de datos
          const evento = d.evento || d;
          const nombre = evento.nombre || "Sin nombre";
          return `<option value="${nombre}">${nombre}</option>`;
        })
        .join("");
    } catch (error) {
      console.error("Error cargando eventos select:", error);
    }
  }

  // Init
  window.addEventListener("DOMContentLoaded", async () => {
    await Promise.all([
      loadCategorias(),
      loadEventos(),
      loadEdiciones(),
      loadEventosSelect(),
    ]);
    document
      .getElementById("formAddEvent")
      ?.addEventListener("submit", handleAddEvent);
    document
      .getElementById("formAddEdition")
      ?.addEventListener("submit", handleAddEdition);
  });
})();
