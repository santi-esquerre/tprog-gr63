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
    const foto = evento.foto || evento.imagen || null;
    const categorias = ev.categorias || [];

    // Generar src de imagen: si hay foto usa el servlet, sino placeholder
    const imgSrc = foto
      ? `${ctx}/image/${foto}`
      : "https://dummyimage.com/600x350/ced4da/6c757d";

    // Formatear categorías
    const categoriasTexto =
      categorias.length > 0 ? categorias.join(", ") : "Sin categorías";

    return `
      <div class="col-12 col-sm-6">
        <div class="card h-100 shadow-sm border-0">
          <img
            class="card-img-top"
            src="${imgSrc}"
            alt="Banner ${nombre}"
            style="aspect-ratio: 600 / 350; object-fit: cover;"
          />
          <div class="card-body">
            <a class="text-muted text-decoration-none" href="#">${nombre}</a>
            <p class="mb-0 small text-muted">
              Categorías: ${categoriasTexto}
            </p>
          </div>
        </div>
      </div>`;
  }

  function cardEdicion(ed) {
    console.log("Edición recibida:", ed); // DEBUG
    const f = (d) => (d ? new Date(d).toLocaleDateString() : "—");
    const estadoBadgeClass =
      ed.estado === "Aceptada" ? "bg-success" : "bg-secondary";
    const imagen = ed.imagen || ed.foto || null;
    const eventoNombre = ed.nombreEvento || ed.evento?.nombre || "—";

    // Formatear fechas
    let fechaTexto = "";
    if (ed.fechaInicio && ed.fechaFin) {
      if (ed.fechaInicio === ed.fechaFin) {
        fechaTexto = f(ed.fechaInicio);
      } else {
        fechaTexto = `${f(ed.fechaInicio)} - ${f(ed.fechaFin)}`;
      }
    } else if (ed.fechaInicio) {
      fechaTexto = f(ed.fechaInicio);
    } else {
      fechaTexto = "—";
    }

    // Generar src de imagen: si hay imagen usa el servlet, sino placeholder
    const imgSrc = imagen
      ? `${ctx}/image/${imagen}`
      : "https://dummyimage.com/600x350/cccccc/666666&text=Sin+imagen";

    return `
      <div class="col-12 col-md-6">
        <div class="card h-100 shadow-sm border-0 card-hover" 
             data-ev-nombre="${eventoNombre}" 
             data-ed-nombre="${ed.nombre}">
          <img class="card-img-top" src="${imgSrc}" 
               alt="Banner ${ed.nombre}" 
               style="aspect-ratio: 600 / 350; object-fit: cover;">
          <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-2 flex-wrap">
              <a class="text-muted text-decoration-none" href="#">${eventoNombre}</a>
              <span class="badge ${estadoBadgeClass}">${ed.estado}</span>
            </div>
            <div class="h5 card-title mb-1">${ed.nombre}</div>
            <div class="mb-1">
              <i class="bi bi-calendar-fill"></i> ${fechaTexto}
            </div>
            <div class="fw-semibold">
              <i class="bi bi-geo-fill"></i> ${ed.ciudad}, ${ed.pais}
            </div>
          </div>
          <div class="card-footer bg-transparent border-0 pt-0">
            <div class="d-flex gap-2">
              <button type="button" class="btn btn-outline-primary btn-sm"
                      data-bs-toggle="modal" 
                      data-bs-target="#addSponsorshipModal"
                      onclick="openSponsorshipForEdition(this)">
                <i class="bi bi-patch-plus me-1"></i> Patrocinio
              </button>
              <button type="button" class="btn btn-outline-secondary btn-sm"
                      data-bs-toggle="modal" 
                      data-bs-target="#addTipoRegModal"
                      onclick="openTipoRegForEdition(this)">
                <i class="bi bi-card-checklist me-1"></i> Tipo de registro
              </button>
              <button type="button" class="btn btn-outline-success btn-sm"
                      data-bs-toggle="modal" 
                      data-bs-target="#consultaRegistroModal"
                      onclick="openConsultaRegForEdition(this)">
                <i class="bi bi-list-check me-1"></i> Ver registros
              </button>
            </div>
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

  // ===========================
  // PATROCINIO
  // ===========================
  window.openSponsorshipForEdition = function (btn) {
    const card = btn.closest(".card");
    const evNombre = card?.dataset.evNombre || "";
    const edNombre = card?.dataset.edNombre || "";

    // Contexto visible y valores
    const spCtxEv = document.getElementById("spCtxEv");
    const spCtxEd = document.getElementById("spCtxEd");
    const spEdicionId = document.getElementById("spEdicionId");

    if (spCtxEv) spCtxEv.textContent = evNombre || "—";
    if (spCtxEd) spCtxEd.textContent = edNombre || "—";
    if (spEdicionId) spEdicionId.value = edNombre;

    // Cargar instituciones y tipos de registro
    loadInstituciones();
    loadTiposRegistro(edNombre);

    // Limpiar tabla de registros
    const spRows = document.getElementById("spRows");
    if (spRows) spRows.innerHTML = "";

    // Reset de warnings
    document.getElementById("spDup")?.classList.add("d-none");

    // Setear fecha de hoy
    const d = new Date();
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const dd = String(d.getDate()).padStart(2, "0");
    const spFecha = document.getElementById("spFecha");
    if (spFecha) spFecha.value = `${yyyy}-${mm}-${dd}`;
  };

  async function loadInstituciones() {
    try {
      const res = await fetch(`${ctx}/api/organizador/instituciones`);
      const data = await res.json();
      const instituciones = data.data || data || [];
      const sel = document.getElementById("spInstitucion");
      sel.innerHTML =
        '<option value="" selected disabled>Selecciona una institución…</option>' +
        instituciones
          .map(
            (inst) => `<option value="${inst.nombre}">${inst.nombre}</option>`
          )
          .join("");
    } catch (error) {
      console.error("Error cargando instituciones:", error);
    }
  }

  // Variable global para almacenar tipos de registro de la edición actual
  let tiposRegistroActuales = [];

  async function loadTiposRegistro(edicionNombre) {
    try {
      const res = await fetch(
        `${ctx}/api/organizador/tipos-registro?edicion=${encodeURIComponent(
          edicionNombre
        )}`
      );
      const data = await res.json();
      tiposRegistroActuales = data.data || data || [];
      console.log("Tipos de registro cargados:", tiposRegistroActuales);
    } catch (error) {
      console.error("Error cargando tipos de registro:", error);
      tiposRegistroActuales = [];
    }
  }

  // Tabla dinámica de registros gratuitos
  const spRows = document.getElementById("spRows");
  function addSponsorshipRow(tipo = "", costo = "", cant = "") {
    const tr = document.createElement("tr");

    // Generar opciones del select con los tipos de registro
    const opcionesTipos = tiposRegistroActuales
      .map(
        (t) =>
          `<option value="${t.nombre}" data-costo="${t.costo}" ${
            t.nombre === tipo ? "selected" : ""
          }>${t.nombre} (UYU ${t.costo})</option>`
      )
      .join("");

    tr.innerHTML = `
      <td>
        <select class="form-select sp-tipo" required>
          <option value="" disabled ${
            !tipo ? "selected" : ""
          }>Selecciona tipo…</option>
          ${opcionesTipos}
        </select>
      </td>
      <td><input type="number" class="form-control sp-costo" min="0" step="0.01" value="${costo}" required readonly></td>
      <td><input type="number" class="form-control sp-cant" min="0" step="1" value="${
        cant || 0
      }" required></td>
      <td class="text-end">
        <button type="button" class="btn btn-outline-danger btn-sm sp-del"><i class="bi bi-trash"></i></button>
      </td>`;

    spRows.appendChild(tr);

    // Listener para auto-rellenar el costo cuando se selecciona un tipo
    const selectTipo = tr.querySelector(".sp-tipo");
    const inputCosto = tr.querySelector(".sp-costo");
    selectTipo?.addEventListener("change", (e) => {
      const selectedOption = e.target.selectedOptions[0];
      const costoTipo = selectedOption?.dataset.costo || "0";
      if (inputCosto) inputCosto.value = costoTipo;
      recalcTotals();
    });

    recalcTotals();
  }

  document
    .getElementById("spAddRow")
    ?.addEventListener("click", () => addSponsorshipRow());

  spRows?.addEventListener("input", (e) => {
    if (e.target.matches(".sp-costo,.sp-cant,.sp-tipo")) recalcTotals();
  });

  spRows?.addEventListener("click", (e) => {
    if (e.target.closest(".sp-del")) {
      e.target.closest("tr").remove();
      recalcTotals();
    }
  });

  function recalcTotals() {
    let total = 0;
    document.querySelectorAll("#spRows tr").forEach((tr) => {
      const c = parseFloat(tr.querySelector(".sp-costo")?.value || 0);
      const q = parseInt(tr.querySelector(".sp-cant")?.value || 0, 10);
      total += c * q;
    });
    const totalEl = document.getElementById("spTotal");
    if (totalEl) totalEl.textContent = `UYU ${total.toFixed(2)}`;

    const aporteEl = document.getElementById("spAporte");
    const aporte = parseFloat(aporteEl?.value || 0);
    const pct = aporte > 0 ? (total / aporte) * 100 : 0;

    const pctEl = document.getElementById("spPct");
    if (pctEl) pctEl.textContent = pct.toFixed(1) + "%";

    const warnEl = document.getElementById("spWarn");
    if (warnEl) warnEl.classList.toggle("d-none", !(pct > 20));
  }

  document.getElementById("spAporte")?.addEventListener("input", recalcTotals);

  document
    .getElementById("formAddSponsorship")
    ?.addEventListener("submit", async (ev) => {
      ev.preventDefault();
      const form = ev.currentTarget;
      if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
      }

      const registros = Array.from(document.querySelectorAll("#spRows tr")).map(
        (tr) => ({
          tipo: tr.querySelector(".sp-tipo").value,
          costo: parseFloat(tr.querySelector(".sp-costo").value || 0),
          cantidad: parseInt(tr.querySelector(".sp-cant").value || 0, 10),
        })
      );

      const payload = {
        edicionId: document.getElementById("spEdicionId").value,
        institucion: document.getElementById("spInstitucion").value,
        nivel: document.getElementById("spNivel").value,
        aporte: parseFloat(document.getElementById("spAporte").value),
        codigo: document.getElementById("spCodigo").value,
        fecha: document.getElementById("spFecha").value,
        registros: JSON.stringify(registros),
      };

      try {
        const res = await fetch(`${ctx}/api/organizador/patrocinio`, {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams(payload),
        });
        const out = await res.json();

        if (out.success) {
          const modalEl = document.getElementById("addSponsorshipModal");
          if (typeof jQuery !== "undefined") {
            jQuery(modalEl).modal("hide");
          }
          form.reset();
          alert("Patrocinio creado exitosamente");
        } else {
          alert(out.message || "Error al crear patrocinio");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("Error al crear patrocinio");
      }
    });

  // ===========================
  // TIPO DE REGISTRO
  // ===========================
  window.openTipoRegForEdition = function (btn) {
    const card = btn.closest(".card");
    const evNombre = card?.dataset.evNombre || "";
    const edNombre = card?.dataset.edNombre || "";

    const trCtxEv = document.getElementById("trCtxEv");
    const trCtxEd = document.getElementById("trCtxEd");
    const trEdicionId = document.getElementById("trEdicionId");
    const trDupWarn = document.getElementById("trDupWarn");
    const formAddTipoReg = document.getElementById("formAddTipoReg");

    if (trCtxEv) trCtxEv.textContent = evNombre || "—";
    if (trCtxEd) trCtxEd.textContent = edNombre || "—";
    if (trEdicionId) trEdicionId.value = edNombre || "";
    if (trDupWarn) trDupWarn.classList.add("d-none");
    if (formAddTipoReg) formAddTipoReg.reset();
  };

  document
    .getElementById("formAddTipoReg")
    ?.addEventListener("submit", async (ev) => {
      ev.preventDefault();
      const form = ev.currentTarget;
      if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
      }

      // Buscar elementos dentro del formulario
      const edicionIdEl = form.querySelector("#trEdicionId");
      const nombreEl = form.querySelector("#trNombre");
      const descripcionEl = form.querySelector("#trDescripcion");
      const costoEl = form.querySelector("#trCosto");
      const cupoEl = form.querySelector("#trCupo");

      if (!edicionIdEl || !nombreEl || !descripcionEl || !costoEl || !cupoEl) {
        console.error("Faltan elementos del formulario");
        console.error("edicionIdEl:", edicionIdEl);
        console.error("nombreEl:", nombreEl);
        console.error("descripcionEl:", descripcionEl);
        console.error("costoEl:", costoEl);
        console.error("cupoEl:", cupoEl);
        return;
      }

      const payload = {
        edicionId: edicionIdEl.value,
        nombre: nombreEl.value.trim(),
        descripcion: descripcionEl.value,
        costo: parseFloat(costoEl.value),
        cupo: parseInt(cupoEl.value, 10),
      };

      try {
        const res = await fetch(`${ctx}/api/organizador/tipo-registro`, {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams(payload),
        });
        const out = await res.json();

        if (out.success) {
          const modalEl = document.getElementById("addTipoRegModal");
          if (typeof jQuery !== "undefined") {
            jQuery(modalEl).modal("hide");
          }
          form.reset();
          alert("Tipo de registro creado exitosamente");
        } else {
          if (out.message && out.message.includes("existe")) {
            const dupWarnEl = document.getElementById("trDupWarn");
            if (dupWarnEl) dupWarnEl.classList.remove("d-none");
          }
          alert(out.message || "Error al crear tipo de registro");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("Error al crear tipo de registro");
      }
    });

  // ===========================
  // CONSULTA DE REGISTRO
  // ===========================
  window.openConsultaRegForEdition = function (btn) {
    const card = btn.closest(".card");
    const evNombre = card?.dataset.evNombre || "";
    const edNombre = card?.dataset.edNombre || "";

    const crCtxEv = document.getElementById("crCtxEv");
    const crCtxEd = document.getElementById("crCtxEd");
    const crEdicionId = document.getElementById("crEdicionId");

    if (crCtxEv) crCtxEv.textContent = evNombre || "—";
    if (crCtxEd) crCtxEd.textContent = edNombre || "—";
    if (crEdicionId) crEdicionId.value = edNombre || "";

    // Cargar registros
    loadRegistros(edNombre);
  };

  async function loadRegistros(edicionNombre) {
    try {
      const res = await fetch(
        `${ctx}/api/organizador/registros?edicion=${encodeURIComponent(
          edicionNombre
        )}`
      );
      const data = await res.json();
      const registros = data.data || data || [];

      const tbody = document.getElementById("crTbody");
      if (registros.length === 0) {
        tbody.innerHTML =
          '<tr><td colspan="4" class="text-center text-muted">No hay registros</td></tr>';
        document.getElementById("crCount").textContent = "0 resultados";
        return;
      }

      tbody.innerHTML = registros
        .map(
          (r) => `
        <tr>
          <td>${r.nombre || r.usuario || "—"}</td>
          <td>${r.nickname || r.nick || "—"}</td>
          <td>${r.correo || r.email || "—"}</td>
          <td class="text-end">
            <button class="btn btn-sm btn-outline-info" onclick="showRegistroDetail('${
              r.id || r.nickname
            }')">
              <i class="bi bi-eye"></i>
            </button>
          </td>
        </tr>
      `
        )
        .join("");

      document.getElementById("crCount").textContent = `${
        registros.length
      } resultado${registros.length !== 1 ? "s" : ""}`;
    } catch (error) {
      console.error("Error cargando registros:", error);
      document.getElementById("crTbody").innerHTML =
        '<tr><td colspan="4" class="text-center text-danger">Error al cargar registros</td></tr>';
    }
  }

  window.showRegistroDetail = function (id) {
    // TODO: Implementar detalle de registro
    console.log("Mostrar detalle de registro:", id);
  };

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
