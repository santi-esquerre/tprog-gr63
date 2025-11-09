package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTTipoRegistro(String nombre, String descripcion, float costo, int cupo) {

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public float getCosto() {
    return costo;
  }

  public int getCupo() {
    return cupo;
  }
}
