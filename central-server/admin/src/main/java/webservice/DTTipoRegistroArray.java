package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTTipoRegistro;
@SuppressWarnings("serial") @XmlRootElement(name = "tiposRegistro")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTTipoRegistroArray implements Serializable {
  
  @XmlElement(name = "tipoRegistro")
  private List<DTTipoRegistro> tiposRegistro;

  public DTTipoRegistroArray() {}
  public DTTipoRegistroArray(List<DTTipoRegistro> tiposRegistro) {
    this.tiposRegistro = tiposRegistro;
  }

  public List<DTTipoRegistro> getTiposRegistro() {
    return tiposRegistro;
  }
}
