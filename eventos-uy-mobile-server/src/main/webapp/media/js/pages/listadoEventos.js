let ultimoIdentifier;
let ultimaFecha;
let final = false;
let cargando = false;

const observarAgregarEventos = new IntersectionObserver((entradas) => {
	    if (entradas[0].isIntersecting && !final && !cargando) {
                cargarMasEventos();
            }
			});
	
observarAgregarEventos.observe(document.getElementById('observador-agregar-eventos'));


async function cargarMasEventos () {
	    cargando = true;
	    try {
        const respuesta = await fetch(`${basePath}/api/eventos?identifier=${ultimoIdentifier ?? ""}&fecha=${ultimaFecha ?? ""}`);
        if (!respuesta.ok) {
            throw new Error('Error al cargar más eventos');
        }
        const datos = await respuesta.json();
        
        const contenedorEventos = document.getElementById('cards');
        
		if (!datos || !Array.isArray(datos.evento) || datos.evento.length === 0) {
			observarAgregarEventos.disconnect(); // detiene el observer
		    final = true;
			
			const mensajeDiv = document.createElement('div');
			mensajeDiv.textContent = "¡Oops! No quedan más eventos para mostrar.";
			mensajeDiv.classList.add('alert', 'alert-info', 'text-center', 'mt-3');
			document.getElementById('cards').appendChild(mensajeDiv);
		    return;
		}
		
        datos.evento.forEach(evt => {
            const eventoTemplate = document.getElementById('card-template');
			const eventoCard = eventoTemplate.content.cloneNode(true);
			
			eventoCard.querySelector('.evento-link').href = `${basePath}/evento/${encodeURIComponent(evt.nombre.replaceAll(' ', '-'))}`;
			eventoCard.querySelector('.card-img-top').src = `${basePath}/image/${evt.foto}`;
			eventoCard.querySelector('.card-name').textContent = evt.nombre;
			eventoCard.querySelector('.card-desc').textContent = evt.descripcion;
			
			contenedorEventos.appendChild(eventoCard);
            
        });
		
		if (datos.evento.length > 0) {
				    ultimoIdentifier = datos.evento[datos.evento.length - 1].identifier;
					console.log(ultimoIdentifier);
				    ultimaFecha = datos.evento[datos.evento.length - 1].fechaAlta;
					console.log(ultimaFecha);
		}
        
    } catch (error) {
        console.error('Error al cargar más eventos:', error);
    } finally {
        cargando = false;
    }
}	