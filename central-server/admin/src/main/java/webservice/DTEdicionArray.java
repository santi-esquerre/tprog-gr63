package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTEdicion;
@SuppressWarnings("serial") @XmlRootElement(name = "ediciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEdicionArray implements Serializable {
  
  @XmlElement(name = "edicion")
  private List<DTEdicion> ediciones;

  public DTEdicionArray() {}
  public DTEdicionArray(List<DTEdicion> ediciones) {
    this.ediciones = ediciones;
  }

  public List<DTEdicion> getEdiciones() {
    return ediciones;
  }
}
