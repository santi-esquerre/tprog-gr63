/* ===========================
   DATOS DE EJEMPLO (basados en datos de prueba)
=========================== */

// Registros


// Patrocinios
const patrocinios = [
  {
    id: "PAT1",
    edicionId: "EDEV08",
    institucionId: "INS01",
    nivel: "Oro",
    tipoRegistroId: "TR19", 
    aporte: 20000,
    fecha: "21/08/2025",
    cantidad: 4,
    codigo: "TECHUDELAR"
  }
];

// Instituciones
const instituciones = [
  {
    id: "INS01",
    nombre: "Facultad de Ingeniería",
    descripcion: "Facultad de Ingeniería de la Universidad de la República",
    web: "https://www.fing.edu.uy"
  }
];

// Tipos de Registro
const tiposRegistro = [
  {
    id: "TR07",
    edicionId: "EDEV03",
    nombre: "Corredor 21K",
    descripcion: "Inscripción a la media maratón",
    costo: 500,
    cupo: 500
  },
  {
    id: "TR19", 
    edicionId: "EDEV08",
    nombre: "Estudiante",
    descripcion: "Acceso para estudiantes",
    costo: 1000,
    cupo: 50
  },
  {
    id: "TR20",
    edicionId: "EDEV09", 
    nombre: "Full",
    descripcion: "Acceso ilimitado + Cena de gala",
    costo: 750,
    cupo: 550
  },
  {
    id: "TR25",
    edicionId: "EDEV10",
    nombre: "Estudiante", 
    descripcion: "Acceso para estudiantes",
    costo: 300,
    cupo: 1
  }
];

// Ediciones
const ediciones = [
  {
    id: "EDEV03",
    eventoId: "EV04",
    usuarioId: "US06",
    nombre: "Maratón de Montevideo 2024",
    sigla: "MARATON24",
    ciudad: "Montevideo",
    pais: "Uruguay",
    fechaInicio: "14/09/2024",
    fechaFin: "14/09/2024", 
    fechaAlta: "21/04/2024",
    estado: "Aceptada",
    imagen: "IMG-EDEV03.jpeg"
  },
  {
    id: "EDEV04",
    eventoId: "EV04",
    usuarioId: "US06", 
    nombre: "Maratón de Montevideo 2022",
    sigla: "MARATON22",
    ciudad: "Montevideo",
    pais: "Uruguay",
    fechaInicio: "14/09/2022",
    fechaFin: "14/09/2022",
    fechaAlta: "21/05/2022", 
    estado: "Rechazada",
    imagen: "IMG-EDEV04.jpeg"
  },
  {
    id: "EDEV08",
    eventoId: "EV01",
    usuarioId: "US10",
    nombre: "Tecnología Punta del Este 2026", 
    sigla: "CONFTECH26",
    ciudad: "Punta del Este",
    pais: "Uruguay",
    fechaInicio: "06/04/2026",
    fechaFin: "10/04/2026",
    fechaAlta: "01/08/2025",
    estado: "Aceptada",
    imagen: null
  },
  {
    id: "EDEV09",
    eventoId: "EV01",
    usuarioId: "US05",
    nombre: "Mobile World Congress 2025",
    sigla: "MWC", 
    ciudad: "Barcelona",
    pais: "España",
    fechaInicio: "12/12/2025",
    fechaFin: "15/12/2025",
    fechaAlta: "21/08/2025",
    estado: "Ingresada",
    imagen: "IMG-EDEV09.png"
  },
  {
    id: "EDEV10",
    eventoId: "EV01",
    usuarioId: "US05",
    nombre: "Web Summit 2026",
    sigla: "WS26",
    ciudad: "Lisboa", 
    pais: "Portugal",
    fechaInicio: "13/01/2026",
    fechaFin: "01/02/2026",
    fechaAlta: "04/06/2025",
    estado: "Aceptada",
    imagen: null
  }
];

// Eventos
const eventos = [
  {
    id: "EV01",
    nombre: "Conferencia de Tecnología",
    descripcion: "Evento sobre innovación tecnológica", 
    sigla: "CONFTEC",
    fechaAlta: "10/01/2025",
    categorias: ["CA01", "CA02"],
    imagen: null
  },
  {
    id: "EV04",
    nombre: "Maratón de Montevideo",
    descripcion: "Competencia deportiva anual en la capital",
    sigla: "MARATON",
    fechaAlta: "01/01/2022", 
    categorias: ["CA06", "CA07"],
    imagen: "IMG-EV04.png"
  }
];

// Categorías
const categorias = [
  { id: "CA01", nombre: "Tecnología" },
  { id: "CA02", nombre: "Innovación" },
  { id: "CA06", nombre: "Deporte" },
  { id: "CA07", nombre: "Salud" }
];

// Usuario organizador actual
const organizadorActual = {
  id: "US04",
  tipo: "O",
  nickname: "miseventos", 
  nombre: "MisEventos",
  email: "contacto@miseventos.com"
};

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

const registros = [
   {
    id: "RE02",
    usuario: "Ana Torres",
    nick: "@atorres",
    correo: "atorres@mail.com",
    edicionId: "EDEV03",
    tipoRegistroId: "TR07",
    fecha: "30/07/2024",
    costo: 500,
    tipo: "Corredor 2K"
  },
  {
    id: "RE03",
    usuario: "Ana Torres",
    nick: "@atorres",
    correo: "atorres@mail.com",
    edicionId: "EDEV10",
    tipoRegistroId: "TR25",
    fecha: "21/08/2025",
    costo: 300,
    tipo: "Estudiante"
  }
];

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
    const data = registros.filter(r => r.edicionId === edId) || [];
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
