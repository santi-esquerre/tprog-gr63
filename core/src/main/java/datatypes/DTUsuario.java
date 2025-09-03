package datatypes;

import java.io.Serializable;

public class DTUsuario implements Serializable {
	String nickname;
	String nombre;
	String correo;

	public DTUsuario(
			String nickname,
			String nombre,
			String correo) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
	}

	public String nickname() {
		return nickname;
	}

	public String nombre() {
		return nombre;
	}

	public String correo() {
		return correo;
	}

	@Override
	public String toString() {
		return nickname;
	}
}
