// core/src/main/java/datatypes/DTEdicionDetallada.java
package datatypes;

import java.io.Serializable;
import java.util.Set;

/**
 * Versión detallada de una edición.
 * Nota: En UML hereda de DTEdicion; en Java (records) lo modelamos por composición.
 */
public record DTEdicionDetallada(
        DTEdicion edicion,                    // datos base de la edición
        DTOrganizador organizador,            // organizador
        Set<DTTipoRegistro> tiposRegistro,    // tipos de registro de la edición
        Set<DTPatrocinio> patrocinios,        // patrocinios asociados
        Set<DTRegistro> registros             // registros existentes
) implements Serializable { }
