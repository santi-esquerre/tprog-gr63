package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTInstitucion;
@SuppressWarnings("serial") @XmlRootElement(name = "instituciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTInstitucionArray implements Serializable {
  
  @XmlElement(name = "institucion")
  private List<DTInstitucion> instituciones;

  public DTInstitucionArray() {}
  public DTInstitucionArray(List<DTInstitucion> instituciones) {
    this.instituciones = instituciones;
  }

  public List<DTInstitucion> getInstituciones() {
    return instituciones;
  }
}
