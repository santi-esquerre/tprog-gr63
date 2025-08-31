package dominio;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario",
       indexes = @Index(name = "ix_usuario_nickname", columnList = "nickname"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario extends BaseEntity {

  @Column(nullable = false, unique = true, length = 40) private String nickname;
  @Column(nullable = false, length = 120) private String nombre;
  @Column(nullable = false, length = 180) private String correo;

  protected Usuario() {}
  protected Usuario(String nickname, String nombre, String correo) {
    this.nickname = nickname; this.nombre = nombre; this.correo = correo;
  }

  public String getNickname(){ return nickname; }
  public String getNombre(){ return nombre; }
  public String getCorreo(){ return correo; }
}
