const toastEl = document.getElementById('toastError');
const toast = new bootstrap.Toast(toastEl);


	document.addEventListener("DOMContentLoaded", function() {
	    if (abrirModal) {

	        var modal = new bootstrap.Modal(document.getElementById(abrirModal + 'registroModal'));
			abrirModal = null; // Reset the variable after use
	        modal.show();
	    }
		
		document.querySelectorAll('.input-group button').forEach(boton => {
		    boton.addEventListener('click', (e) => {
		        const input = boton.closest('.input-group').querySelector('input');
		        if (!input.value) {
		            toast.show();
					e.preventDefault();
		        }
		    });
		});

		
	});
