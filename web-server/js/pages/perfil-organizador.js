/* ===========================
   UTILIDADES GENERALES
=========================== */
const $ = (id) => document.getElementById(id);
const $$ = (sel, ctx = document) => ctx.querySelector(sel);
const $$$ = (sel, ctx = document) => Array.from(ctx.querySelectorAll(sel));
const fmtMoney = (v) => "UYU " + Number(v || 0).toFixed(2);

/* ===========================
   VALIDACIÓN BOOTSTRAP + SUBMITS BÁSICOS
=========================== */
(() => {
  "use strict";
  const forms = document.querySelectorAll(".needs-validation");
  Array.from(forms).forEach((form) => {
    form.addEventListener(
      "submit",
      (event) => {
        // Validación especial de cambio de contraseña (solo si edita organizador)
        if (form.id === "formEditOrganizer") {
          const p1 = $("inpPass").value;
          const p2 = $("inpPass2").value;
          if ((p1 || p2) && p1 !== p2) {
            event.preventDefault();
            event.stopPropagation();
            $("inpPass2").setCustomValidity("mismatch");
          } else {
            $("inpPass2").setCustomValidity("");
          }
        }

        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        } else {
          event.preventDefault();
          // ======= TODO: Reemplazar por fetch() a tu backend =======
          if (form.id === "formEditOrganizer") {
            $("orgNombre").innerText = $("inpNombre").value || "—";
            $("orgDescripcion").innerText = $("inpDescripcion").value || "—";
            const link = $("inpLink").value;
            $("orgLink").textContent = link || "—";
            $("orgLink").href = link || "#";
          }
          if (form.id === "formAddEvent") {
            console.log("Evento creado (demo)");
          }
          if (form.id === "formAddEdition") {
            console.log("Edición creada (demo)");
          }
          // ==========================================================
          const modalEl = form.closest(".modal");
          const modal = bootstrap.Modal.getInstance(modalEl);
          modal && modal.hide();
        }
        form.classList.add("was-validated");
      },
      false
    );
  });
})();

/* ===========================
   EDITAR ORGANIZADOR: PREVIEW IMG
=========================== */
$("inpImg")?.addEventListener("change", (e) => {
  const file = e.target.files?.[0];
  const preview = $("previewImg");
  if (file) {
    preview.src = URL.createObjectURL(file);
    preview.classList.remove("d-none");
  } else {
    preview.src = "";
    preview.classList.add("d-none");
  }
});

/* ===========================
   ALTA INSTITUCIÓN
=========================== */
$("insLogo")?.addEventListener("change", (e) => {
  const f = e.target.files?.[0];
  if (!f) {
    $("insPreview").classList.add("d-none");
    return;
  }
  $("insPreview").src = URL.createObjectURL(f);
  $("insPreview").classList.remove("d-none");
});

$("formAddInstitution")?.addEventListener("submit", async (ev) => {
  ev.preventDefault();
  const form = ev.currentTarget;
  if (!form.checkValidity()) {
    form.classList.add("was-validated");
    return;
  }

  // TODO: validar duplicado por API: /instituciones?nombre=...
  const duplicado = false; // <== reemplazar
  $("insDupWarn").classList.toggle("d-none", !duplicado);
  if (duplicado) return;

  // TODO: POST /instituciones (FormData si envías imagen)
  console.log("Institución creada (demo)");
  bootstrap.Modal.getInstance(form.closest(".modal")).hide();
});

/* ===========================
   ALTA PATROCINIO (SOLO CONTEXTUAL)
   - Se abre desde una card de edición
   - Usa spEdicionId (hidden)
   - Calcula 20% sobre aporte
=========================== */

// Abrir modal desde card (botón "Patrocinio")
function openSponsorshipForEdition(btn) {
  const card = btn.closest(".card");
  const evNombre = card?.dataset.evNombre || "";
  const edNombre = card?.dataset.edNombre || "";
  const edId = card?.dataset.edId || "";

  // Contexto visible y valores
  $("spCtxEv").textContent = evNombre || "—";
  $("spCtxEd").textContent = edNombre || "—";
  $("spEdicionId").value = edId;

  // Reset de warnings
  $("spDup")?.classList.add("d-none");
  // Reset tabla y totales si querés (opcional):
  // $('spRows').innerHTML = '';
  // recalcTotals();
}

// Tabla dinámica de registros gratuitos
const spRows = $("spRows");
function addRow(tipo = "", costo = "", cant = "") {
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td>
      <select class="form-select sp-tipo" required>
        <option value="" disabled ${
          !tipo ? "selected" : ""
        }>Selecciona tipo…</option>
        <!-- TODO: poblar desde API /tiposRegistro -->
        <option ${tipo === "General" ? "selected" : ""}>General</option>
        <option ${tipo === "Estudiante" ? "selected" : ""}>Estudiante</option>
        <option ${tipo === "VIP" ? "selected" : ""}>VIP</option>
      </select>
    </td>
    <td><input type="number" class="form-control sp-costo" min="0" step="0.01" value="${costo}" required></td>
    <td><input type="number" class="form-control sp-cant"  min="0" step="1"    value="${
      cant || 0
    }" required></td>
    <td class="text-end">
      <button type="button" class="btn btn-outline-danger btn-sm sp-del"><i class="bi bi-trash"></i></button>
    </td>`;
  spRows.appendChild(tr);
  recalcTotals();
}
$("spAddRow")?.addEventListener("click", () => addRow());

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
  $$$("#spRows tr").forEach((tr) => {
    const c = parseFloat($$(".sp-costo", tr)?.value || 0);
    const q = parseInt($$(".sp-cant", tr)?.value || 0, 10);
    total += c * q;
  });
  $("spTotal").textContent = fmtMoney(total);
  const aporte = parseFloat($("spAporte")?.value || 0);
  const pct = aporte > 0 ? (total / aporte) * 100 : 0;
  $("spPct").textContent = pct.toFixed(1) + "%";
  $("spWarn").classList.toggle("d-none", !(pct > 20));
}
$("spAporte")?.addEventListener("input", recalcTotals);

// Fecha de alta = hoy
(function setToday() {
  const d = new Date();
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  $("spFecha") && ($("spFecha").value = `${yyyy}-${mm}-${dd}`);
})();

// Duplicado (institución + edición fija)
async function checkDuplicateSponsorship() {
  const inst = $("spInstitucion")?.value;
  const ed = $("spEdicionId")?.value; // contextual
  if (!inst || !ed) return;
  // TODO: GET /patrocinios?institucion=...&edicionId=...
  const existe = false; // <== reemplazar con respuesta real
  const dup = $("spDup");
  dup.classList.toggle("d-none", !existe);
  $$$(".sp-inst", dup).forEach((n) => (n.textContent = inst));
  return existe;
}
$$$("#spInstitucion").forEach((el) =>
  el?.addEventListener("change", checkDuplicateSponsorship)
);

$("formAddSponsorship")?.addEventListener("submit", async (ev) => {
  ev.preventDefault();
  const form = ev.currentTarget;
  if (!form.checkValidity()) {
    form.classList.add("was-validated");
    return;
  }

  const dup = await checkDuplicateSponsorship();
  if (dup) return; // informar y no enviar si hay duplicado

  const registros = $$$("#spRows tr").map((tr) => ({
    tipo: $$(".sp-tipo", tr).value,
    costo: parseFloat($$(".sp-costo", tr).value || 0),
    cantidad: parseInt($$(".sp-cant", tr).value || 0, 10),
  }));

  const payload = {
    edicionId: $("spEdicionId").value, // contextual SIEMPRE
    institucion: $("spInstitucion").value,
    nivel: $("spNivel").value,
    aporte: parseFloat($("spAporte").value),
    codigo: $("spCodigo").value,
    fechaAlta: $("spFecha").value,
    registros,
  };

  // TODO: POST /patrocinios  (payload JSON)
  console.log("Patrocinio creado (demo)", payload);

  bootstrap.Modal.getInstance(form.closest(".modal")).hide();
});

/* ===========================
   ALTA TIPO DE REGISTRO (CONTEXTUAL)
=========================== */
function openTipoRegForEdition(btn) {
  const card = btn.closest(".card");
  const evNombre = card?.dataset.evNombre || "";
  const edNombre = card?.dataset.edNombre || "";
  const edId = card?.dataset.edId || "";
  $("trCtxEv").textContent = evNombre || "—";
  $("trCtxEd").textContent = edNombre || "—";
  $("trEdicionId").value = edId || "";
  $("trDupWarn").classList.add("d-none");
  $("formAddTipoReg").reset();
}

$("formAddTipoReg")?.addEventListener("submit", async (ev) => {
  ev.preventDefault();
  const form = ev.currentTarget;
  if (!form.checkValidity()) {
    form.classList.add("was-validated");
    return;
  }

  const edicionId = $("trEdicionId").value;
  const nombre = $("trNombre").value.trim();

  // TODO: validar duplicado: GET /tiposRegistro?edicionId=...&nombre=...
  const duplicado = false; // <== reemplazar
  $("trDupWarn").classList.toggle("d-none", !duplicado);
  if (duplicado) return;

  const payload = {
    edicionId,
    nombre,
    descripcion: $("trDescripcion").value,
    costo: parseFloat($("trCosto").value),
    cupo: parseInt($("trCupo").value, 10),
  };

  // TODO: POST /tiposRegistro
  console.log("Tipo de registro creado (demo)", payload);

  bootstrap.Modal.getInstance(form.closest(".modal")).hide();
});

/* ===========================
   CONSULTA DE REGISTRO (contextual por edición)
=========================== */

/* ====== STATE de Consulta de Registro ====== */
const CR_STATE = {
  edicionId: "",
  dataCache: {}, // edicionId -> array de registros
  search: "",
  page: 1,
  pageSize: 5,
};

/* Mock (reemplazar por fetch) */
const registrosDemo = {
  "ed-feria-2024": [
    {
      id: "r1",
      usuario: "Ana Gabriela",
      nick: "@anag",
      correo: "anag.gomez@hotmail.com",
      fecha: "2024-03-20",
      tipo: "General",
      codigo: "SP-IMM-2024",
    },
    {
      id: "r2",
      usuario: "Luis Pérez",
      nick: "@lperez",
      correo: "luisp@gmail.com",
      fecha: "2024-03-22",
      tipo: "Estudiante",
      codigo: "",
    },
    {
      id: "r3",
      usuario: "Sofía Díaz",
      nick: "@sdiaz",
      correo: "sofiadiaz@mail.com",
      fecha: "2024-03-23",
      tipo: "VIP",
      codigo: "SP-ORO-24",
    },
    {
      id: "r4",
      usuario: "Juan Ríos",
      nick: "@jrios",
      correo: "jrios@mail.com",
      fecha: "2024-03-24",
      tipo: "General",
      codigo: "",
    },
    {
      id: "r5",
      usuario: "Paula Rey",
      nick: "@prey",
      correo: "paula@mail.com",
      fecha: "2024-03-25",
      tipo: "General",
      codigo: "",
    },
    {
      id: "r6",
      usuario: "Diego Acuña",
      nick: "@dacu",
      correo: "dacuna@mail.com",
      fecha: "2024-03-26",
      tipo: "Estudiante",
      codigo: "SP-UTE-24",
    },
  ],
  "ed-feria-2025": [
    {
      id: "r7",
      usuario: "María López",
      nick: "@mlopez",
      correo: "maria@gmail.com",
      fecha: "2025-04-05",
      tipo: "General",
      codigo: "SP-UTE-25",
    },
  ],
};

/* ----- Abre el modal desde la card de la edición (FIX sin '#') ----- */
function openConsultaRegForEdition(btn) {
  const card = btn.closest(".card");
  const evNombre = card?.dataset.evNombre || "";
  const edNombre = card?.dataset.edNombre || "";
  const edId = card?.dataset.edId || "";

  // <<< Aca estaba tu error: usa id sin '#'
  $("crCtxEv").textContent = evNombre || "—";
  $("crCtxEd").textContent = edNombre || "—";
  $("crEdicionId").value = edId || "";

  // Inicializar state
  CR_STATE.edicionId = edId;
  CR_STATE.search = "";
  CR_STATE.page = 1;
  CR_STATE.pageSize = parseInt($("crPageSize")?.value || 5, 10);
  $("crSearch").value = "";
  $("crDetail").classList.add("d-none");

  loadRegistrosForEdition(edId);
}

/* ----- Cargar registros (usa cache; reemplazá por fetch) ----- */
async function loadRegistrosForEdition(edId) {
  if (!CR_STATE.dataCache[edId]) {
    // TODO: const data = await fetch(`/registros?edicionId=${encodeURIComponent(edId)}`).then(r=>r.json());
    const data = registrosDemo[edId] || [];
    CR_STATE.dataCache[edId] = data;
  }
  renderRegistrosTable();
}

/* ----- Render con filtro + paginación ----- */
function renderRegistrosTable() {
  const tbody = $("crTbody");
  tbody.innerHTML = "";

  const all = CR_STATE.dataCache[CR_STATE.edicionId] || [];
  const q = (CR_STATE.search || "").toLowerCase().trim();

  const filtered = all.filter((r) =>
    [r.usuario, r.nick, r.correo].some((v) =>
      (v || "").toLowerCase().includes(q)
    )
  );

  // Paginación
  const total = filtered.length;
  const totalPages = Math.max(1, Math.ceil(total / CR_STATE.pageSize));
  if (CR_STATE.page > totalPages) CR_STATE.page = totalPages;
  if (CR_STATE.page < 1) CR_STATE.page = 1;

  const start = (CR_STATE.page - 1) * CR_STATE.pageSize;
  const end = start + CR_STATE.pageSize;
  const pageItems = filtered.slice(start, end);

  // Pintar filas
  pageItems.forEach((r) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td><span class="fw-semibold">${r.usuario}</span></td>
      <td><span class="text-muted">${r.nick}</span></td>
      <td>${r.correo}</td>
      <td class="text-end">
        <button type="button" class="btn btn-sm btn-outline-primary" data-regid="${r.id}">
          <i class="bi bi-eye me-1"></i> Ver detalle
        </button>
      </td>`;
    tbody.appendChild(tr);
  });

  // Contadores e info
  $("crCount").textContent = `${total} resultado${total !== 1 ? "s" : ""}`;
  $("crPageInfo").textContent = `Página ${CR_STATE.page} de ${totalPages}`;
  $("crPrev").disabled = CR_STATE.page <= 1;
  $("crNext").disabled = CR_STATE.page >= totalPages;

  // Eventos "Ver detalle"
  tbody.querySelectorAll("button[data-regid]").forEach((btn) => {
    btn.addEventListener("click", () => {
      const reg = (CR_STATE.dataCache[CR_STATE.edicionId] || []).find(
        (x) => x.id === btn.dataset.regid
      );
      if (reg) showRegistroDetail(reg);
    });
  });
}

/* ----- Detalle ----- */
function showRegistroDetail(reg) {
  $("crDetUser").textContent = `${reg.usuario}${
    reg.nick ? ` (${reg.nick})` : ""
  }`;
  $("crDetFecha").textContent = reg.fecha || "—";
  $("crDetTipo").textContent = reg.tipo || "—";
  $("crDetCodigo").textContent = reg.codigo ? reg.codigo : "No utilizó código";
  $("crDetail").classList.remove("d-none");
}

/* ----- Listeners de búsqueda + paginación ----- */
$("crSearch")?.addEventListener("input", () => {
  CR_STATE.search = $("crSearch").value;
  CR_STATE.page = 1; // reset al buscar
  renderRegistrosTable();
});

$("crPageSize")?.addEventListener("change", () => {
  CR_STATE.pageSize = parseInt($("crPageSize").value, 10) || 5;
  CR_STATE.page = 1;
  renderRegistrosTable();
});

$("crPrev")?.addEventListener("click", () => {
  if (CR_STATE.page > 1) {
    CR_STATE.page--;
    renderRegistrosTable();
  }
});

$("crNext")?.addEventListener("click", () => {
  CR_STATE.page++;
  renderRegistrosTable();
});

/* Exponer funciones para botones de las cards */
window.openSponsorshipForEdition = openSponsorshipForEdition;
window.openTipoRegForEdition = openTipoRegForEdition;
window.openConsultaRegForEdition = openConsultaRegForEdition;
