const radioBUttons = document.querySelectorAll('input[name="rol"]');
const asistente = document.getElementById('asistente');
const organizador = document.getElementById('organizador');
const params = new URLSearchParams(window.location.search);
const nombre = params.get('nombre');

radioBUttons.forEach(radio => {
    radio.addEventListener('change', () => {
        if (radio.id === 'btnRadioAsistente' && radio.checked) {
            asistente.classList.remove('d-none');
            organizador.classList.add('d-none');
        } else if (radio.id === 'btnRadioOrganizador' && radio.checked) {
            organizador.classList.remove('d-none');
            asistente.classList.add('d-none');
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const dia = document.getElementById('dia');
    for (let i = 1; i <= 31; i++) {
        let option = new Option(i, i); 
        dia.add(option);
    }

    const meses = {Enero:1, Febrero:2, Marzo:3, Abril:4, Mayo:5, Junio:6, Julio:7, Agosto:8, Septiembre:9, Octubre:10, Noviembre:11, Diciembre:12};
    const mes = document.getElementById('mes');
    for (const [key, value] of Object.entries(meses)) {
        let option = new Option(key, value);
        mes.add(option);
    }

    const anio = document.getElementById('anio');
    const anioActual = new Date().getFullYear();
    for (let i = 0; i <= 120; i++) {
        let option = new Option(anioActual - i, anioActual - i);
        anio.add(option);
    }
});


