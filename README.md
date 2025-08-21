# TProg 2025 — Tarea 1

**Servidor Central (lógica)** + **Estación de Trabajo (Swing)**

> Bases y convenciones del repositorio para que todo el equipo programe alineado. Criterios, estructura y forma de trabajo.

---

## 1) Objetivo y alcance de Tarea 1

* Construir el **Servidor Central** (lógica de negocio) y una **Estación de Trabajo** de administración en **Swing**.
* Persistencia **en memoria** (colecciones) para esta etapa.
* La GUI orquesta casos de uso; **la lógica resuelve reglas** (unicidades, cupos, costos, etc.).
* Cobertura mínima en capa lógica: **≥ 80%** con pruebas automatizadas.

---

## 2) Estructura del workspace (Eclipse)

* Dos **Java Projects** en el mismo workspace:

  * **tprog-core**: dominio + interfaces + lógica + excepciones + repos en memoria + pruebas.
  * **tprog-admin**: aplicación Swing (ventana principal y pantallas por caso de uso).
* **Dependencia**: `tprog-admin` depende de `tprog-core` mediante *Build Path → Projects*. En *Order and Export* debe estar marcado para ejecución.
* **Configuraciones comunes**: JRE **Java 21**, compilador a **21**, codificación **UTF‑8**.

---

## 3) Paquetes y responsabilidades

### 3.1. tprog-core (Servidor Central)

* **dominio**: entidades del modelo.
* **datatypes**: estructuras ligeras para exponer datos a la GUI (sólo datos, sin lógica de negocio).
* **interfaces**: contratos de controladores por caso de uso (alta, consulta, modificación, etc.).
* **logica**: implementaciones de controladores y una **Fábrica** (singleton) que entrega instancias vía interfaces.
* **repos**: almacenamiento en memoria, búsquedas y validaciones de existencia.
* **excepciones**: excepciones **checked** de negocio con mensajes claros para la UI.
* **tests**: pruebas de la lógica (unitarias/funcionales) que ayudan a alcanzar la cobertura acordada.

**Principios clave en core**

* **Encapsulamiento**: la GUI **no accede** al paquete `logica` ni a `dominio`. Interactúa **sólo** con `interfaces` y `datatypes`.
* **Contratos primero**: definir métodos de interfaces antes de implementar. Mantener nombres alineados a casos de uso.
* **DTOs/datatypes**: inmutables a nivel conceptual; no exponen referencias mutables.
* **Repos**: operaciones mínimas y claras (agregar, obtener, existe, limpiar para tests).
* **Reglas**: toda validación de negocio ocurre en `logica`, nunca en la GUI.

### 3.2. tprog-admin (Estación de Trabajo Swing)

* **main**: arranque de la aplicación (Look\&Feel por defecto) y creación de la ventana principal.
* **gui**: `JFrame` principal con `JMenuBar` + `JDesktopPane`; un `JInternalFrame` por caso de uso. Botones **Aceptar/Cancelar** en cada flujo. Diálogos de error/confirmación con `JOptionPane`.
* **actions**: listeners/adaptadores de UI que invocan *exclusivamente* interfaces de `tprog-core` a través de la Fábrica.
* **util**: helpers de interfaz (validaciones simples, modelos de tabla/lista, formateos).

**Principios clave en admin**

* **Separación estricta**: no crear dependencias hacia `logica` ni `dominio`.
* **Validaciones de entrada**: formato y campos requeridos en la UI; reglas de negocio en core.
* **Mensajes**: claros y consistentes; español rioplatense neutro; sin stack traces a usuarios.
* **Navegación**: menú por dominio (Usuarios, Eventos, Ediciones, Registros, Patrocinios, etc.).
* **Sin GUI** para Alta de Categoría (según letra de Tarea 1).

---

## 4) Flujo de desarrollo por caso de uso (sin código)

1. **Definir contrato** en `interfaces` con nombres alineados al caso de uso.
2. **Definir datatypes** necesarios para entradas/salidas.
3. **Implementar controlador** en `logica`, aplicando reglas de negocio y usando `repos`.
4. **Agregar pruebas** en `tests` que cubran flujo feliz y errores/validaciones.
5. **Exponer en GUI** creando el `JInternalFrame` y conectando acciones a las interfaces via Fábrica.
6. **Revisar cobertura** y mensajes de error.

---

## 5) Convenciones

* **Paquetes**: minúsculas, sin acentos ni caracteres no ASCII.
* **Clases/Interfaces/Enums**: PascalCase. Interfaces con prefijo `I` sólo en controladores.
* **Métodos/Campos**: lowerCamelCase.
* **Datatypes/DTOs**: prefijo `DT` en nombres; sin lógica, sólo datos.
* **Excepciones**: sufijo `Exception`; una por regla relevante; mensajes útiles para el usuario final.
* **Nulabilidad**: preferir no nulos; cuando aplique, documentar explícitamente.
* **Fechas/horas**: tipos de fecha de Java modernos (no deprecados) — sin formateos en core.
* **Idiomas**: constantes/mensajes en español claro; nombres de clases/métodos en inglés o español, pero **consistentes**.

---

## 6) Pruebas y calidad

* **Herramientas**: JUnit 5 para tests; EclEmma para cobertura.
* **Meta**: ≥ 80% de cobertura de sentencias en `tprog-core` (la GUI no se cubre con tests).
* **Tipos de prueba**:

  * Flujo feliz por caso de uso.
  * Validaciones negativas (duplicados, cupos, entradas inválidas).
  * Borde: colecciones vacías, límites de cupo, rangos y filtros.
* **Datos de prueba**: evitar dependencias entre tests; repos con método para limpiar estado.
* **Revisiones**: no se acepta PR que baje cobertura o rompa tests existentes.

---

## 7) Rama, commits y PR

* **Ramas**: `main` estable; ramas de feature `feat/<area>-<caso-uso>`.
* **Commits**: cortos y atómicos, en imperativo. Ej.: "add: alta de usuario (contrato + lógica + tests)".
* **Pull Request** (Checklist):

  * Compila y ejecuta la GUI.
  * Tests verdes y cobertura ≥ 80% en core.
  * La GUI usa **sólo** interfaces/DTs de core.
  * Excepciones y mensajes de negocio propagados y entendibles.
  * Nombres y paquetes respetan las convenciones.

---

## 8) Troubleshooting frecuente

* **La GUI no encuentra clases del core**: revisar *Build Path → Projects* y *Order and Export* en `tprog-admin`.
* **Tests no corren**: confirmar uso de JUnit 5 y carpeta `test` como *Source Folder*.
* **WindowBuilder no abre formularios**: limpiar proyecto y revisar constructores por defecto en formularios.
* **Cobertura insuficiente**: agregar pruebas de ramas negativas y de borde.

---
