// perfil-asistente.js - v1
(() => {
  const ctx = window.appCtx || "";
  const byId = (id) => document.getElementById(id);

  let edicionesDisponibles = [];
  let codigoPatrocinioValido = false;

  // ======== CARGAR EDICIONES DISPONIBLES ========
  async function loadEdicionesDisponibles() {
    try {
      const res = await fetch(`${ctx}/api/asistente/ediciones-disponibles`);
      const data = await res.json();
      edicionesDisponibles = data.data || data || [];
      
      const select = byId("regEdicion");
      if (select) {
        select.innerHTML = '<option value="" disabled selected>Selecciona una edición…</option>';
        edicionesDisponibles.forEach(ed => {
          const option = document.createElement("option");
          option.value = ed.nombre;
          option.textContent = `${ed.nombreEvento} - ${ed.nombre}`;
          option.dataset.evento = ed.nombreEvento;
          select.appendChild(option);
        });
      }
    } catch (error) {
      console.error("Error cargando ediciones:", error);
    }
  }

  // ======== CARGAR TIPOS DE REGISTRO ========
  async function loadTiposRegistro(edicionNombre) {
    try {
      const res = await fetch(`${ctx}/api/asistente/tipos-registro?edicion=${encodeURIComponent(edicionNombre)}`);
      const data = await res.json();
      const tipos = data.data || data || [];
      
      const select = byId("regTipo");
      if (select) {
        select.innerHTML = '<option value="" disabled selected>Selecciona un tipo…</option>';
        tipos.forEach(tipo => {
          const option = document.createElement("option");
          option.value = tipo.nombre;
          option.textContent = `${tipo.nombre} - UYU ${tipo.costo}`;
          option.dataset.costo = tipo.costo;
          option.dataset.cupo = tipo.cupo;
          select.appendChild(option);
        });
        select.disabled = false;
      }
    } catch (error) {
      console.error("Error cargando tipos de registro:", error);
      const select = byId("regTipo");
      if (select) select.disabled = true;
    }
  }

  // ======== VALIDAR CÓDIGO DE PATROCINIO ========
  async function validarCodigoPatrocinio() {
    const codigo = byId("regCodigoPatrocinio")?.value.trim();
    const edicion = byId("regEdicion")?.value;
    
    if (!codigo || !edicion) {
      return;
    }

    try {
      const res = await fetch(`${ctx}/api/asistente/validar-patrocinio`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ codigo, edicion })
      });
      const data = await res.json();
      
      const validoEl = byId("regCodigoValido");
      const invalidoEl = byId("regCodigoInvalido");
      const costoEl = byId("regCosto");
      
      if (data.success && data.valido) {
        codigoPatrocinioValido = true;
        if (validoEl) validoEl.classList.remove("d-none");
        if (invalidoEl) invalidoEl.classList.add("d-none");
        if (costoEl) costoEl.value = "0.00";
      } else {
        codigoPatrocinioValido = false;
        if (validoEl) validoEl.classList.add("d-none");
        if (invalidoEl) invalidoEl.classList.remove("d-none");
      }
    } catch (error) {
      console.error("Error validando código:", error);
      codigoPatrocinioValido = false;
      const invalidoEl = byId("regCodigoInvalido");
      if (invalidoEl) invalidoEl.classList.remove("d-none");
    }
  }

  // ======== INIT ========
document.addEventListener("DOMContentLoaded", () => {
  // Render tabla
  document.addEventListener("DOMContentLoaded", () => {
    // Cargar ediciones al abrir el modal
    const modalEl = byId("addRegistroModal");
    if (modalEl) {
      modalEl.addEventListener("show.bs.modal", () => {
        loadEdicionesDisponibles();
        
        // Reset form
        codigoPatrocinioValido = false;
        const form = byId("formAddRegistro");
        if (form) form.reset();
        byId("regCodigoValido")?.classList.add("d-none");
        byId("regCodigoInvalido")?.classList.add("d-none");
        const tipoSelect = byId("regTipo");
        if (tipoSelect) tipoSelect.disabled = true;
        
        // Set fecha = hoy
        const today = new Date().toISOString().slice(0, 10);
        const fechaEl = byId("regFecha");
        if (fechaEl) fechaEl.value = today;
      });
    }

    // Cambio de edición -> cargar tipos
    byId("regEdicion")?.addEventListener("change", (e) => {
      const edicionNombre = e.target.value;
      loadTiposRegistro(edicionNombre);
      const cupoInfo = byId("regCupoInfo");
      if (cupoInfo) cupoInfo.textContent = "";
      const costoEl = byId("regCosto");
      if (costoEl) costoEl.value = "";
      
      // Reset código de patrocinio
      codigoPatrocinioValido = false;
      byId("regCodigoValido")?.classList.add("d-none");
      byId("regCodigoInvalido")?.classList.add("d-none");
    });

    // Cambio de tipo -> actualizar costo y cupo
    byId("regTipo")?.addEventListener("change", (e) => {
      const selectedOption = e.target.selectedOptions[0];
      const costo = selectedOption?.dataset.costo || "0";
      const cupo = selectedOption?.dataset.cupo || "—";
      
      if (!codigoPatrocinioValido) {
        const costoEl = byId("regCosto");
        if (costoEl) costoEl.value = costo;
      }
      const cupoInfo = byId("regCupoInfo");
      if (cupoInfo) cupoInfo.textContent = `Cupo disponible: ${cupo}`;
    });

    // Botón validar código
    byId("btnValidarCodigo")?.addEventListener("click", validarCodigoPatrocinio);

    // Submit nuevo registro
    byId("formAddRegistro")?.addEventListener("submit", async (e) => {
      e.preventDefault();
      e.stopPropagation();
      
      const form = e.currentTarget;
      if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
      }

      const edicion = byId("regEdicion")?.value;
      const tipo = byId("regTipo")?.value;
      const codigo = byId("regCodigoPatrocinio")?.value.trim();
      
      const payload = {
        edicion,
        tipo,
        codigoPatrocinio: codigo || undefined
      };

      try {
        const res = await fetch(`${ctx}/api/asistente/registro`, {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams(payload)
        });
        const data = await res.json();
        
        if (data.success) {
          alert("Registro creado exitosamente");
          const modal = bootstrap.Modal.getInstance(modalEl);
          if (modal) modal.hide();
          location.reload(); // Recargar para mostrar el nuevo registro
        } else {
          alert(data.message || "Error al crear el registro");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("Error al crear el registro");
      }
      
      form.classList.add("was-validated");
    });

    // Previsualización foto asistente
    byId("asInpImg")?.addEventListener("change", (e) => {
      const file = e.target.files?.[0];
      const preview = byId("asPreviewImg");
      if (file && preview) {
        preview.src = URL.createObjectURL(file);
        preview.classList.remove("d-none");
      } else if (preview) {
        preview.src = "";
        preview.classList.add("d-none");
      }
    });
  });
})();})
