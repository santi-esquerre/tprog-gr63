// Datos de ejemplo (simularían tu backend)
const ediciones = [
  {
    id: "feria2024",
    nombre: "Feria del libro 2024",
    evento: "Feria del libro",
    tipos: [
      { id: "general", nombre: "General", costo: 0, cupo: 100 },
      { id: "vip", nombre: "VIP", costo: 50, cupo: 20 },
    ],
  },
  {
    id: "expointer2025",
    nombre: "Expointer Uruguay 2025",
    evento: "Expointer Uruguay",
    tipos: [
      { id: "general", nombre: "General", costo: 0, cupo: 150 },
      { id: "estudiante", nombre: "Estudiante", costo: 10, cupo: 200 },
    ],
  },
];

let registros = [
  {
    evento: "Feria del libro",
    edicion: "Feria del libro 2024",
    tipo: "General",
    fecha: "2024-03-20",
    costo: 0,
  },
];
let pendingDeleteIndex = null;

// Helpers
const byId = (id) => document.getElementById(id);

function renderRegistros() {
  const tbody = document.querySelector("#tablaRegistros tbody");
  tbody.innerHTML = "";
  registros.forEach((r, idx) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
            <td>${r.evento}</td>
            <td>${r.edicion}</td>
            <td>${r.tipo}</td>
            <td>${r.fecha}</td>
            <td>${Number(r.costo).toLocaleString(undefined, {
              minimumFractionDigits: 0,
              maximumFractionDigits: 2,
            })}</td>
            <td class="text-end">
              <button class="btn btn-sm btn-outline-secondary me-1 btn-edit" data-index="${idx}">
                <i class="bi bi-pencil"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger btn-del" data-index="${idx}">
                <i class="bi bi-trash"></i>
              </button>
            </td>`;
    tbody.appendChild(tr);
  });
}

function fillEdicionesSelect(selectEl, selectedId = "") {
  selectEl.innerHTML =
    '<option value="" disabled selected>Selecciona una edición…</option>';
  ediciones.forEach((e) => {
    const opt = document.createElement("option");
    opt.value = e.id;
    opt.textContent = e.nombre;
    if (e.id === selectedId) opt.selected = true;
    selectEl.appendChild(opt);
  });
}

function fillTiposSelect(selectEl, edicionId, selectedTipoId = "") {
  selectEl.innerHTML =
    '<option value="" disabled selected>Selecciona un tipo…</option>';
  const ed = ediciones.find((e) => e.id === edicionId);
  if (!ed) {
    selectEl.disabled = true;
    return;
  }
  ed.tipos.forEach((t) => {
    const opt = document.createElement("option");
    opt.value = t.id;
    opt.textContent = `${t.nombre} — $${t.costo}`;
    if (t.id === selectedTipoId) opt.selected = true;
    selectEl.appendChild(opt);
  });
  selectEl.disabled = false;
}

// ======== INIT ========
document.addEventListener("DOMContentLoaded", () => {
  // Render tabla
  renderRegistros();

  // Pre-cargar selects de "nuevo"
  fillEdicionesSelect(byId("regEdicion"));

  // Default fecha = hoy
  const today = new Date().toISOString().slice(0, 10);
  byId("regFecha").value = today;

  // Dependencias de selects (nuevo)
  byId("regEdicion").addEventListener("change", (e) => {
    const edId = e.target.value;
    fillTiposSelect(byId("regTipo"), edId);
    byId("regCupoInfo").textContent = "";
    byId("regCosto").value = "";
  });

  byId("regTipo").addEventListener("change", (e) => {
    const ed = ediciones.find((x) => x.id === byId("regEdicion").value);
    const tipo = ed?.tipos.find((t) => t.id === e.target.value);
    if (tipo) {
      byId("regCosto").value = tipo.costo;
      byId("regCupoInfo").textContent = `Cupo: ${tipo.cupo}`;
    }
  });

  // Editar/Eliminar desde la tabla
  document
    .querySelector("#tablaRegistros tbody")
    .addEventListener("click", (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      const idx = Number(btn.dataset.index);
      if (btn.classList.contains("btn-edit")) {
        // Abrir modal editar
        const r = registros[idx];
        byId("editIndex").value = idx;

        fillEdicionesSelect(byId("editRegEdicion"));
        // Hallar edición por nombre mostrado
        const ed = ediciones.find((ed) => ed.nombre === r.edicion);
        byId("editRegEdicion").value = ed?.id || "";
        fillTiposSelect(byId("editRegTipo"), byId("editRegEdicion").value);

        // Hallar tipo por nombre
        const tipoObj = ed?.tipos.find(
          (t) => t.nombre.toLowerCase() === r.tipo.toLowerCase()
        );
        byId("editRegTipo").value = tipoObj?.id || "";

        byId("editRegFecha").value = r.fecha;
        byId("editRegCosto").value = tipoObj?.costo ?? r.costo;
        byId("editRegCupoInfo").textContent = tipoObj
          ? `Cupo: ${tipoObj.cupo}`
          : "";

        new bootstrap.Modal("#editRegistroModal").show();
      }
      if (btn.classList.contains("btn-del")) {
        pendingDeleteIndex = idx;
        new bootstrap.Modal("#confirmDeleteModal").show();
      }
    });

  // Dependencias de selects (editar)
  byId("editRegEdicion").addEventListener("change", (e) => {
    fillTiposSelect(byId("editRegTipo"), e.target.value);
    byId("editRegCupoInfo").textContent = "";
    byId("editRegCosto").value = "";
  });

  byId("editRegTipo").addEventListener("change", (e) => {
    const ed = ediciones.find((x) => x.id === byId("editRegEdicion").value);
    const tipo = ed?.tipos.find((t) => t.id === e.target.value);
    if (tipo) {
      byId("editRegCosto").value = tipo.costo;
      byId("editRegCupoInfo").textContent = `Cupo: ${tipo.cupo}`;
    }
  });
});

// Previsualización foto asistente
byId("asInpImg")?.addEventListener("change", (e) => {
  const file = e.target.files?.[0];
  const preview = byId("asPreviewImg");
  if (file) {
    preview.src = URL.createObjectURL(file);
    preview.classList.remove("d-none");
  } else {
    preview.src = "";
    preview.classList.add("d-none");
  }
});

// ======== VALIDACIÓN Y SUBMITS ========
(function () {
  "use strict";
  const forms = document.querySelectorAll(".needs-validation");
  Array.from(forms).forEach((form) => {
    form.addEventListener(
      "submit",
      function (event) {
        // Validación especial passwords (editar asistente)
        if (form.id === "formEditAssistant") {
          const p1 = byId("asInpPass")?.value || "";
          const p2 = byId("asInpPass2")?.value || "";
          if ((p1 || p2) && p1 !== p2) {
            event.preventDefault();
            event.stopPropagation();
            byId("asInpPass2").setCustomValidity("mismatch");
          } else {
            byId("asInpPass2").setCustomValidity("");
          }
        }

        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        } else {
          event.preventDefault();
          // ======= TODO: Reemplazar por fetch() a tu backend =======
          if (form.id === "formEditAssistant") {
            // Demo: aplicar cambios a la tarjeta
            byId("asNombre").innerText =
              byId("asInpNombre").value || byId("asNombre").innerText;
            byId("asNacimiento").innerText =
              byId("asInpNacimiento").value || byId("asNacimiento").innerText;
          }
          if (form.id === "formAddRegistro") {
            const ed = ediciones.find((e) => e.id === byId("regEdicion").value);
            const tipo = ed?.tipos.find((t) => t.id === byId("regTipo").value);
            registros.push({
              evento: ed?.evento || "—",
              edicion: ed?.nombre || "—",
              tipo: tipo?.nombre || "—",
              fecha: byId("regFecha").value,
              costo: byId("regCosto").value || 0,
            });
            renderRegistros();
          }
          if (form.id === "formEditRegistro") {
            const idx = Number(byId("editIndex").value);
            const ed = ediciones.find(
              (e) => e.id === byId("editRegEdicion").value
            );
            const tipo = ed?.tipos.find(
              (t) => t.id === byId("editRegTipo").value
            );
            registros[idx] = {
              evento: ed?.evento || "—",
              edicion: ed?.nombre || "—",
              tipo: tipo?.nombre || "—",
              fecha: byId("editRegFecha").value,
              costo: byId("editRegCosto").value || 0,
            };
            renderRegistros();
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

// Confirm delete
byId("btnConfirmDelete").addEventListener("click", () => {
  if (pendingDeleteIndex !== null) {
    // TODO: fetch DELETE
    registros.splice(pendingDeleteIndex, 1);
    renderRegistros();
    pendingDeleteIndex = null;
  }
  bootstrap.Modal.getInstance(byId("confirmDeleteModal")).hide();
});
