package datatypes;

enum TipoUsuario {
	ASISTENTE,
	ORGANIZADOR
}

public record DTUsuarioListado(String nickname, String correo, TipoUsuario tipo) {}