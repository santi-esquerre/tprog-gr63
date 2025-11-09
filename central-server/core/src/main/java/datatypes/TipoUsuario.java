package datatypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "TipoUsuario")
@XmlEnum
public enum TipoUsuario {
  ASISTENTE,
  ORGANIZADOR
}
