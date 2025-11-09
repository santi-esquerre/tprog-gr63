package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTAsistente;
@SuppressWarnings("serial") @XmlRootElement(name = "asistentes")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTAsistenteArray implements Serializable {
  
  @XmlElement(name = "asistente")
  private List<DTAsistente> asistentes;

  public DTAsistenteArray() {}
  public DTAsistenteArray(List<DTAsistente> asistentes) {
    this.asistentes = asistentes;
  }

  public List<DTAsistente> getAsistentes() {
    return asistentes;
  }
}
