package datatypes;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public record DTRegistro(
        Date fecha,
        float costo,
        String nombreEvento,
        String nombreEdicion,
        String nicknameAsistente,
        String fotoAsistente,
        DTTipoRegistro tipoRegistro,
		Boolean usoCodigo) { 
}
