package datatypes;

import java.io.Serializable;

public class DTUsuario implements Serializable {
	String nickname;
	String nombre;
	String correo;
	String foto;

	public DTUsuario(
			String nickname,
			String nombre,
			String correo,
			String foto) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.foto = foto;
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
	
	public String foto() {
		return foto;
	}

	@Override
	public String toString() {
		return nickname;
	}
	
	
}
