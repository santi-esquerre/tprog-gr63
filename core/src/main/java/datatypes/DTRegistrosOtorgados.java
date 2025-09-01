package datatypes;

import java.io.Serializable;

public record DTRegistrosOtorgados(
        DTTipoRegistro tipoRegistro,
        int cupos
) implements Serializable { }
