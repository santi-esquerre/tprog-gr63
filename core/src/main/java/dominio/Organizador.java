package dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ORGANIZADOR")
public class Organizador extends Usuario {
  protected Organizador() {}
  public Organizador(String nick, String nom, String ape, String mail) { 
    super(nick, nom, ape, mail); 
  }
}
