package dominio;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import utils.Cryptography;

@Entity
@Table(name = "usuario",
       indexes = @Index(name = "ix_usuario_nickname", columnList = "nickname"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario extends BaseEntity {

  @Column(nullable = false, unique = true, length = 40) protected String nickname;
  @Column(nullable = false, length = 120) protected String nombre;
  @Column(nullable = false, length = 120) protected String clave;
  @Column(nullable = false, length = 255) protected String foto;
  @Column(nullable = false, length = 120)
  protected String correo;

  protected Usuario() {}
  protected Usuario(String nickname, String nombre, String correo, String clave, String foto) {
    this.nickname = nickname; this.nombre = nombre; this.correo = correo; this.clave = Cryptography.getHash(clave); this.foto = foto;
  }

  public String getNickname(){ return nickname; }
  public String getNombre(){ return nombre; }
  public String getCorreo(){ return correo; }
  public String getFoto(){ return foto; }
  public String getClave(){ return clave; }
  public TipoUsuario getTipo(){ 
	  if (this.getClass().getSimpleName().equals("Asistente")) {
		  return TipoUsuario.ASISTENTE;
	  } else {
		  return TipoUsuario.ORGANIZADOR; }
	  }
  
  public Boolean validarClave(String clave){ 
	  return Cryptography.validateHash(clave, this.clave);
   }

  
  // Métodos abstractos para conversión a DTOs
  public abstract DTUsuario toDataType();
  public abstract DTUsuarioItemListado toDTUsuarioItemListado();
  

}
