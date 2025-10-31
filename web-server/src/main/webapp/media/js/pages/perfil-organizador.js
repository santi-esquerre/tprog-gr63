// /js/pages/perfil-organizador.js
(() => {
  const $ = (id) => document.getElementById(id);
  const ctx = window.appCtx || "";

  // Helpers
  const j = (u, q) => (q ? `${u}?${new URLSearchParams(q)}` : u);

  // Render helpers
  function cardEvento(ev) {
    return `
      <div class="col-12 col-md-6">
        <div class="card shadow-sm h-100">
          <div class="card-body">
            <h5 class="fw-bold mb-1">${ev.evento.nombre}</h5>
            <p class="text-muted mb-2">${ev.evento.descripcion || "—"}</p>
            <div class="d-flex gap-2 flex-wrap">
              ${(ev.categorias || [])
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
    const res = await fetch(`${ctx}/api/organizador/eventos`);
    const data = await res.json(); // devuelve DTEventoDetallado[]
    document.getElementById("eventosGrid").innerHTML = (data || [])
      .map(cardEvento)
      .join("");
  }

  async function loadEdiciones() {
    const res = await fetch(`${ctx}/api/organizador/ediciones`);
    const data = await res.json(); // devuelve DTEdicion[] (o similar)
    document.getElementById("edicionesGrid").innerHTML = (data || [])
      .map(cardEdicion)
      .join("");
  }

  // Alta Evento (usa /api/organizador vía POST — ver tu servlet)
  async function handleAddEvent(ev) {
    ev.preventDefault();
    const form = ev.currentTarget;
    if (!form.checkValidity()) {
      form.classList.add("was-validated");
      return;
    }

    const fd = new FormData();
    fd.set("nombre", document.getElementById("evNombre").value.trim());
    fd.set(
      "descripcion",
      document.getElementById("evDescripcion").value.trim()
    );
    fd.set("sigla", document.getElementById("evSigla").value.trim());
    // categorías: tomar seleccionadas
    const sel = document.getElementById("evCategorias");
    fd.set(
      "categorias",
      JSON.stringify(Array.from(sel.selectedOptions).map((o) => o.value))
    );
    const img = document.getElementById("evImagen").files?.[0];
    if (img) fd.set("imagen", img);

    const res = await fetch(`${ctx}/api/organizador/eventos`, {
      method: "POST",
      body: fd,
    });
    const out = await res.json();
    if (out.success) {
      bootstrap.Modal.getInstance(form.closest(".modal")).hide();
      await loadEventos();
    } else {
      alert(out.message || "Error al crear evento");
    }
  }

  // Alta Edición
  async function handleAddEdition(ev) {
    ev.preventDefault();
    const form = ev.currentTarget;
    if (!form.checkValidity()) {
      form.classList.add("was-validated");
      return;
    }

    const fd = new FormData();
    fd.set("evento", document.getElementById("edEvento").value);
    fd.set("nombre", document.getElementById("edNombre").value.trim());
    fd.set("sigla", document.getElementById("edSigla").value.trim());
    fd.set("ciudad", document.getElementById("edCiudad").value.trim());
    fd.set("pais", document.getElementById("edPais").value.trim());
    fd.set("fechaInicio", document.getElementById("edInicio").value);
    fd.set("fechaFin", document.getElementById("edFin").value);
    const img = document.getElementById("edImagen").files?.[0];
    if (img) fd.set("imagen", img);

    const res = await fetch(`${ctx}/api/organizador/ediciones`, {
      method: "POST",
      body: fd,
    });
    const out = await res.json();
    if (out.success) {
      bootstrap.Modal.getInstance(form.closest(".modal")).hide();
      await loadEdiciones();
    } else {
      alert(out.message || "Error al crear edición");
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

  // Poblar selects auxiliares
  async function loadCategorias() {
    const res = await fetch(`${ctx}/api/organizador/categorias`);
    const cats = await res.json();
    const sel = document.getElementById("evCategorias");
    sel.innerHTML = (cats || [])
      .map((c) => `<option value="${c}">${c}</option>`)
      .join("");
  }

  async function loadEventosSelect() {
    const res = await fetch(`${ctx}/api/organizador/eventos`);
    const dets = await res.json();
    const sel = document.getElementById("edEvento");
    sel.innerHTML = (dets || [])
      .map(
        (d) => `<option value="${d.evento.nombre}">${d.evento.nombre}</option>`
      )
      .join("");
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
