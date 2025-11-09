package webservice;
import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import datatypes.DTUsuarioItemListado;

@SuppressWarnings("serial") @XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuarioItemListadoArray implements Serializable {
  
  @XmlElement(name = "usuario")
  private List<DTUsuarioItemListado> usuarios;

  public DTUsuarioItemListadoArray() {}
  public DTUsuarioItemListadoArray(List<DTUsuarioItemListado> usuarios) {
    this.usuarios = usuarios;
  }

  public List<DTUsuarioItemListado> getUsuarios() {
    return usuarios;
  }
}