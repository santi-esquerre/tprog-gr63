const params = new URLSearchParams(window.location.search);
const id = params.get("id");
const tipoUsuario = params.get("tipoUsuario");

const eventos =[
    "Conferencia de Tecnología",
    "Maratón de Montevideo"
]


const organizadores = [
    { nombre: "MisEventos", correo: "contacto@miseventos.com", desc: "Empresa de organización de eventos. ", img: "assets/img/IMG-US04.jpeg" }
];

const registros = [
    { nombre: "Ana Torres", email: "atorres@gmail.com", fecha: "30/07/2024", tipo: "Corredor 21K", img: "assets/img/IMG-US01.jpg" },
    { nombre: "Ana Torres", email: "atorres@gmail.com", fecha: "21/08/2025", tipo: "Estudiante", img: "assets/img/IMG-US01.jpg" },
    { nombre: "Ana Torres", email: "atorres@gmail.com", fecha: "21/05/2022", tipo: "Corredor 42K", img: "assets/img/IMG-US01.jpg" }

];

const patrocinios = [
  { nombre: "Facultad de Ingeniería", img: "assets/img/sponsor.png", nivel: "Oro", aporte: "20000", fecha: "21/08/2025"}
];

const datos = [
    { nombre: "Maratón de Montevideo 2024", sigla: "MARATON24", ciudad: "Montevideo", pais: "Uruguay", fechaInicio: "14/09/2024", fechaFin: "14/09/2024", img: "assets/img/IMG-EDEV03.jpeg", organizador: organizadores[0], registrado: registros[0], tiposRegistro: [0], patrocinios: undefined, evento : eventos[1]},
    { nombre: "Tecnología Punta del Este 2026", sigla: "CONFTECH26", ciudad: "Punta del Este", pais: "Uruguay", fechaInicio: "06/04/2026", fechaFin: "10/04/2026", img: "https://dummyimage.com/140x40/adb5bd/ffffff.png&text=IMM", organizador: organizadores[0], registrado: undefined, tiposRegistro: [1], patrocinios: patrocinios, evento : eventos[0]},
    { nombre: "Web Summit 2026", sigla: "WS26", ciudad: "Lisboa", pais: "Portugal", fechaInicio: "13/01/2026", fechaFin: "01/02/2026", img: "https://dummyimage.com/140x40/adb5bd/ffffff.png&text=IMM", organizador: organizadores[0], registrado: registros[1], tiposRegistro: [2], patrocinios: undefined, evento : eventos[0]},
    { nombre: "Maratón de Montevideo 2022", sigla: "MARATON22", ciudad: "Montevideo", pais: "Uruguay", fechaInicio: "14/09/2022", fechaFin: "14/09/2022", img: "assets/img/IMG-EDEV04.jpeg", organizador: organizadores[0], registrado: registros[2], tiposRegistro: [0], patrocinios: undefined, evento : eventos[1]},
    { nombre: "Mobile World Congress 2025", sigla: "MWC", ciudad: "Barcelona", pais: "España", fechaInicio: "12/12/2025", fechaFin: "15/12/2025", img: "assets/img/IMG-EDEV09.png", organizador: organizadores[0], registrado: undefined, tiposRegistro: [], patrocinios: undefined, evento : eventos[0]}
];

const tiposRegistro = [ [{nombre: "Corredor 42K", desc: "Inscripción a la maratón completa", costo: "1000", cupo: "300"}, {nombre: "Corredor 21K", desc: "Inscripción a la media maratón", costo: "500", cupo: "500"}],
                        [{nombre: "Full", desc: "Acceso ilimitado + Cena de gala", costo: "1800", cupo: "300"}, {nombre: "General", desc: "Acceso general", costo: "1500", cupo: "500"}, {nombre: "Estudiante", desc: "Acceso para estudiantes", costo: "1000", cupo: "50"}],
                        [{nombre: "Full", desc: "Acceso ilimitado + Cena de gala", costo: "900", cupo: "30"}, {nombre: "General", desc: "Acceso general", costo: "650", cupo: "5"}, {nombre: "Estudiante", desc: "Acceso para estudiantes", costo: "300", cupo: "1"}]
];

const ediciones = { "03":datos[0], "08":datos[1], "10":datos[2], "04":datos[3], "09":datos[4] };

const edicionActual = ediciones[id];

const lista = document.getElementById("listaRegistros");

document.addEventListener("DOMContentLoaded", () => {
    const nombreEd = document.getElementById("nombreEdicion");
    const siglaEd = document.getElementById("siglaEdicion");
    const fechasEd = document.getElementById("fechasEdicion");
    const ubicacionEd = document.getElementById("ubicacionEdicion");
    const organizadorEd = document.getElementById("organizadorEdicion");
    const descOrganizadorEd = document.getElementById("descOrganizadorEdicion");
    const correoOrganizadorEd = document.getElementById("correoOrganizadorEdicion");
    const imagenEd = document.getElementById("imagenEd");
    const registradoEd = document.getElementById("registroUsuarios");
    const imgOrganizador = document.getElementById("imgOrganizadorEdicion");
    const nombreEvento = document.getElementById("nombreEvento");

    if (edicionActual) {
        nombreEd.textContent = edicionActual.nombre;
        siglaEd.textContent = edicionActual.sigla;
        fechasEd.textContent = `${edicionActual.fechaInicio} - ${edicionActual.fechaFin}`;
        ubicacionEd.textContent = `${edicionActual.ciudad}, ${edicionActual.pais}`;
        organizadorEd.textContent = edicionActual.organizador.nombre;
        descOrganizadorEd.textContent = edicionActual.organizador.desc;
        correoOrganizadorEd.textContent = edicionActual.organizador.correo;
        imagenEd.style.backgroundImage = `url('${edicionActual.img}')`;
        imgOrganizador.src = edicionActual.organizador.img;
        nombreEvento.textContent = edicionActual.evento;
        registradoEd.innerHTML = "";

        if (edicionActual.registrado) {
           const listaRegistros = [edicionActual.registrado];
            registradoEd.innerHTML = listaRegistros.map(u => `
            <li class="list-group-item d-flex align-items-start flex-wrap">
                <div class="row g-3 w-100">
                <div class="d-flex align-items-center col-8">
                    <img class="rounded-1 align-self-center" src="${u.img}" style="width: 40px; height: 40px;">
                    <div class="ms-3 w-100 mb-0 p-0">
                    <div class="d-flex align-items-center gap-2">
                        <p class="fw-bold mb-0">${u.nombre}</p>
                        <span class="badge text-bg-primary rounded-pill align-self-center">${u.tipo}</span>
                    </div>
                    <p class="mt-0 mb-0">${u.email}</p>
                    <p>${u.fecha}</p>
                    </div>
                </div>
                <div class="col-4 d-flex align-items-center justify-content-end">
                    <div class="mb-0 p-0 d-flex gap-2">
                    <p class="fw-bold">Cupo:</p> 
                    <i class="bi bi-x-circle-fill text-primary"></i>
                    </div>
                </div>
                </div>
            </li>
            `).join("");
        }

        const contenedor = document.getElementById("patrocinadores");
        contenedor.innerHTML = "";
        if (edicionActual.patrocinios) {
            contenedor.innerHTML = edicionActual.patrocinios.map(p => `
                <div class="d-flex flex-column align-items-center text-center col-6 col-md-4 col-lg-3 mb-4 h-100">
                <p>${p.nombre}</p>
                <img class="img-fluid" src="${p.img}" style="max-height: 100px" alt="Logo ${p.nombre}">
                </div>`).join("");
        }
    }

    const tipoR = document.getElementById("tipoRegistro");
    tipoR.innerHTML = "";
    if (edicionActual.tiposRegistro) {
        const registroActual = edicionActual.tiposRegistro.map(i => tiposRegistro[i]).flat();
        tipoR.innerHTML = registroActual.map(t => `
        <div class="col-12 col-md-6 col-lg-6 col-xl-4 mb-4">
            <div class="card mb-5 mb-xl-0 h-100 borderh-100">
                <div class="card-body p-5 d-flex flex-column min-vh-50">
                    <button type="button" class="btn p-0 m-0 align-self-start" data-bs-toggle="modal" data-bs-target="#tipoRegistroModal">
                        <i class="bi bi-question-circle-fill fs-4"></i>
                    </button>
                    <div class="small text-uppercase fw-bold text-muted">${t.nombre}</div>
                    <div class="mb-3">
                        <span class="display-4 fw-bold">$${t.costo}</span>
                    </div>
                    <p>${t.desc}</p>
                    <div class="d-grid mt-auto">
                        <a class="btn btn-dark rounded-4 fs-5" data-bs-toggle="modal" data-bs-target="#registroModal" href="#!">Elegir</a>
                    </div>
                </div>
            </div>
        </div>
    `).join("");
    }

});