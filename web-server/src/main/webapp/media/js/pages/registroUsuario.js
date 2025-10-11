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

const inputFile = document.getElementById('formFile');
const previewImage = document.getElementById('preview');

inputFile.addEventListener('change', () => {
	const file = inputFile.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.classList.remove('d-none');
        };
        reader.readAsDataURL(file);
    } else {
        previewImage.src = '#';
        previewImage.classList.add('d-none');
    }
	
});

const nickname = document.getElementById("nickname");
let nicknameTooltip = tooltip(nickname, "Escribe tu nombre de usuario");
let checkNickname = false;

nickname.addEventListener("focus", function () {
	if (nickname.value.trim() === "" && checkNickname) {
        nicknameTooltip.show();
	}
    nickname.style.borderColor = "";
});

nickname.addEventListener("blur", function () {
    if (nickname.value.trim() === "") {
        nickname.style.borderColor = "red";
		checkNickname = true;
    } else {
        nickname.style.borderColor = "";
		checkNickname = false;
    }
	nicknameTooltip.hide();
});


const nombreInput = document.getElementById("nombre");
let nombreTooltip = tooltip(nombreInput, "Escribe tu nombre");
let checkNombre = false;
nombreInput.addEventListener("focus", function () {
    if (nombreInput.value.trim() === "" && checkNombre) {
        nombreTooltip.show();
    }
    nombreInput.style.borderColor = "";
});
nombreInput.addEventListener("blur", function () {
    if (nombreInput.value.trim() === "") {
        nombreInput.style.borderColor = "red";
		checkNombre = true;
    } else {
        nombreInput.style.borderColor = "";
		checkNombre = false;
    }
    nombreTooltip.hide();
});

const correo = document.getElementById("correo");
let emailTooltip = tooltip(correo, "Escribe un correo electrónico válido");
let checkEmail = false;
correo.addEventListener("focus", function () {
    if (correo.value.trim() === "" && checkEmail) {
        emailTooltip.show();
    }
    correo.style.borderColor = "";
});
correo.addEventListener("blur", function () {
    if (correo.value.trim() === "") {
        correo.style.borderColor = "red";
		checkEmail = true;
    } else {
        correo.style.borderColor = "";
		checkEmail = false;
    }
    emailTooltip.hide();
});


const password = document.getElementById("password");
let passwordTooltip = tooltip(password, "La contraseña es obligatoria");
let checkPassword = false;
password.addEventListener("focus", function () {
    if (password.value.trim() === "" && checkPassword) {
        passwordTooltip.show();
    }
    password.style.borderColor = "";
});
password.addEventListener("blur", function () {
    if (password.value.trim() === "") {
        password.style.borderColor = "red";
		checkPassword = true;
    } else {
        password.style.borderColor = "";
		checkPassword = false;
    }
    passwordTooltip.hide();
});


const confirmPassword = document.getElementById("confirmPassword");
let confirmPasswordTooltip = tooltip(confirmPassword, "Las contraseñas no coinciden");
let checkConfirmPassword = false;
confirmPassword.addEventListener("focus", function () {
    if ((confirmPassword.value.trim() === "" || confirmPassword.value !== password.value) && checkConfirmPassword) {
        confirmPasswordTooltip.show();
    }
    confirmPassword.style.borderColor = "";
});
confirmPassword.addEventListener("blur", function () {
    if (confirmPassword.value.trim() === "" || confirmPassword.value !== password.value) {
        confirmPassword.style.borderColor = "red";
		checkConfirmPassword = true;
    } else {
        confirmPassword.style.borderColor = "";
		checkConfirmPassword = false;
    }
    confirmPasswordTooltip.hide();
});

const apellido = document.getElementById("apellido");
let apellidoTooltip = tooltip(apellido, "Escribe tu apellido");
let checkApellido = false;
apellido.addEventListener("focus", function () {
    if (apellido.value.trim() === "" && checkApellido) {
        apellidoTooltip.show();
    }
    apellido.style.borderColor = "";
});
apellido.addEventListener("blur", function () {
    if (apellido.value.trim() === "") {
        apellido.style.borderColor = "red";
		checkApellido = true;
    } else {
        apellido.style.borderColor = "";
		checkApellido = false;
    }
    apellidoTooltip.hide();
});

const grupoFecha = document.getElementById("grupoBtn");
const dia = document.getElementById("dia");
const mes = document.getElementById("mes");
const anio = document.getElementById("anio");
let checkFecha = false;

let fechaTooltip = tooltip(dia, "Selecciona una fecha válida");
grupoFecha.addEventListener("focusin", function () {
    if ((dia.value === "0" || mes.value === "0" || anio.value === "0") && checkFecha) {
		if(!fechaTooltip._isShown())
		{
			fechaTooltip.show();
		}
    }
    dia.style.borderColor = "";
    mes.style.borderColor = "";
    anio.style.borderColor = "";
});
grupoFecha.addEventListener("focusout", function (e) {
    // Verifica si el nuevo foco está fuera del grupo
    if (!grupoFecha.contains(e.relatedTarget)) {
        if (dia.value === "0" || mes.value === "0" || anio.value === "0") {
            dia.style.borderColor = "red";
            mes.style.borderColor = "red";
            anio.style.borderColor = "red";
            checkFecha = true;
        } else {
            dia.style.borderColor = "";
            mes.style.borderColor = "";
            anio.style.borderColor = "";
			checkFecha = false;
        }
		fechaTooltip.hide();
    }
})


function tooltip(element, message) {
	element.setAttribute("data-bs-toggle", "tooltip");
	element.setAttribute("data-bs-placement", "left");
	element.setAttribute("title", message);
	
	return new bootstrap.Tooltip(element, {
		trigger: "manual", 
        offset: [0, 10],
    });
}


const descripcion = document.getElementById("descripcion");
let descripcionTooltip = tooltip(descripcion, "Escribe una descripción breve");
let checkDescripcion = false;

descripcion.addEventListener("focus", function () {
    if (descripcion.value.trim() === "" && checkDescripcion) {
        descripcionTooltip.show();
    }
    descripcion.style.borderColor = "";
});
descripcion.addEventListener("blur", function () {
    if (descripcion.value.trim() === "") {
        descripcion.style.borderColor = "red";
		checkDescripcion = true;
    } else {
        descripcion.style.borderColor = "";
		checkDescripcion = false;
    }
    descripcionTooltip.hide();
});

document.addEventListener("DOMContentLoaded", function () {
	//nicknameTooltip.hide();
	nombreTooltip.hide();
	emailTooltip.hide();
	passwordTooltip.hide();
	confirmPasswordTooltip.hide();
	apellidoTooltip.hide();
    fechaTooltip.hide();
});

const btnRegistro = document.getElementById("btnRegistro");
btnRegistro.addEventListener("click", function (e) {
    let valid = true;

    if (nickname.value.trim() === "") {
        nickname.style.borderColor = "red";
		checkNickname = true;
        valid = false;
    }

    if (nombreInput.value.trim() === "") {
        nombreInput.style.borderColor = "red";
		checkNombre = true;
        valid = false;
    }

    if (correo.value.trim() === "") {
        correo.style.borderColor = "red";
		checkEmail = true;
        valid = false;
    }

    if (password.value.trim() === "") {
        password.style.borderColor = "red";
		checkPassword = true;
        valid = false;
    }

    if (confirmPassword.value.trim() === "" || confirmPassword.value !== password.value) {
        confirmPassword.style.borderColor = "red";
		checkConfirmPassword = true;
        valid = false;
    }


	const seleccionado = document.querySelector('input[name="rol"]:checked');
	if (seleccionado && seleccionado.id === "btnRadioAsistente") {
		if (apellido.value.trim() === "") {
		    apellido.style.borderColor = "red";
			checkApellido = true;
		    valid = false;
		}

		if (dia.value === "0" || mes.value === "0" || anio.value === "0") {
		    dia.style.borderColor = "red";
		    mes.style.borderColor = "red";
		    anio.style.borderColor = "red";
		    valid = false;
		    checkFecha = true;
		}
	} else if (seleccionado && seleccionado.id === "btnRadioOrganizador") {
		if (descripcion.value.trim() === "") {
            descripcion.style.borderColor = "red";
			checkDescripcion = true;
			valid = false;
        }
	}
	else {
		valid = false;
	}
	
		

    if (!valid) {
        e.preventDefault(); // Evita el envío del formulario si hay errores
    }
});



