package datatypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "EstadoEdicion")
@XmlEnum
public enum EstadoEdicion {
  INGRESADA,
  ACEPTADA,
  RECHAZADA
}
