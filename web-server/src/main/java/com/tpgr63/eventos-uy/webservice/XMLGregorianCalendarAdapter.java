package webservice;

import com.google.gson.*;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import java.lang.reflect.Type;

/**
 * Adaptador personalizado de Gson para serializar y deserializar objetos de tipo
 * {@link XMLGregorianCalendar}, que no son manejados por defecto por Gson.
 *
 * Convierte el XMLGregorianCalendar a una cadena ISO-8601 (por ejemplo: "2025-11-06T15:30:00Z")
 * Permite volver a convertir esa cadena a XMLGregorianCalendar al deserializar.
 * Evita los errores de reflexión al usar Gson con Java 17+.
 *
 * Ejemplo de uso:
 *
 * <pre>{@code
 * Gson gson = new GsonBuilder()
 *     .registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter())
 *     .setPrettyPrinting()
 *     .create();
 *
 * // Crear un XMLGregorianCalendar
 * XMLGregorianCalendar fecha = DatatypeFactory.newInstance()
 *     .newXMLGregorianCalendar("2025-11-06T12:30:00Z");
 *
 * // Serializar a JSON
 * String json = gson.toJson(fecha);
 * System.out.println("JSON serializado: " + json);
 *
 * // Deserializar desde JSON
 * XMLGregorianCalendar recuperada = gson.fromJson("\"2025-11-06T12:30:00Z\"", XMLGregorianCalendar.class);
 * System.out.println("Deserializado: " + recuperada.toXMLFormat());
 * }</pre>
 */
public class XMLGregorianCalendarAdapter
        implements JsonSerializer<XMLGregorianCalendar>, JsonDeserializer<XMLGregorianCalendar> {

    @Override
    public JsonElement serialize(XMLGregorianCalendar src, Type typeOfSrc, JsonSerializationContext context) {
        // Serializa como ISO string (por ejemplo, "2025-11-06T14:30:00Z")
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toXMLFormat());
    }

    @Override
    public XMLGregorianCalendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(json.getAsString());
        } catch (java.lang.Exception e) {
            // Aquí usamos el constructor correcto:
            throw new JsonParseException("Error parsing XMLGregorianCalendar", e);
        }
    }
}