package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTRegistro;
@SuppressWarnings("serial") @XmlRootElement(name = "registros")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTRegistroArray implements Serializable {
  
  @XmlElement(name = "registro")
  private List<DTRegistro> registros;

  public DTRegistroArray() {}
  public DTRegistroArray(List<DTRegistro> registros) {
    this.registros = registros;
  }

  public List<DTRegistro> getRegistros() {
    return registros;
  }
}
