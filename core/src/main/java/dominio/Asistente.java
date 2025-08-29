package dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ASISTENTE")
public class Asistente extends Usuario {
  protected Asistente() {}
  public Asistente(String nick, String nom, String ape, String mail) { 
    super(nick, nom, ape, mail); 
  }
}
