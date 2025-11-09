package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTEvento;
@SuppressWarnings("serial") @XmlRootElement(name = "eventos")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEventoArray implements Serializable {
  
  @XmlElement(name = "evento")
  private List<DTEvento> eventos;

  public DTEventoArray() {}
  public DTEventoArray(List<DTEvento> eventos) {
    this.eventos = eventos;
  }

  public List<DTEvento> getEventos() {
    return eventos;
  }
}
