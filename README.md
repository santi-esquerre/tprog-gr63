# TProg 2025 — Tarea 1

Servidor Central (**core**) + Estación de Trabajo (**admin** / Swing)

Repositorio Maven multi–módulo. El *parent* declara **dos módulos**: `core` y `admin`.
Las versiones base (Hibernate, SLF4J, JUnit) y la configuración del compilador están centralizadas en el *parent*.

---

## 1) Estructura del proyecto

```
eventos-parent/
  pom.xml                      # parent (packaging=pom)
  core/
    pom.xml
    src/main/java/
      dominio/                 # entidades JPA (@Entity)
      interfaces/              # contratos que consume admin
      logica/                  # servicios/reglas de negocio
      repos/                   # repositorios JPA
      infra/                   # utilidades de JPA/Tx
    src/main/resources/
      META-INF/persistence.xml # unidad 'eventosPU'
    src/test/java/             # tests de lógica
  admin/
    pom.xml
    src/main/java/
      gui/ actions/ util/      # GUI Swing
    src/main/resources/        # íconos, i18n, fuentes
```

* `persistence.xml` está en `core/src/main/resources/META-INF/`.
* Entidad de ejemplo: `dominio.Usuario` mapeada a la tabla **persona**.
* El *admin* genera un **JAR ejecutable** con *shade* (Main por defecto `gui.Principal`).

---

## 2) Base de datos (PostgreSQL) — levantar y conectar

### Parámetros (dev)

* **DB**: `tpgr63eventosuy`
* **Usuario**: `tpgr63eventosuyadmin`
* **Password**: `admin`
* **URL JDBC**: `jdbc:postgresql://localhost:5432/tpgr63eventosuy`
  Estos valores están configurados en `persistence.xml` (unidad `eventosPU`).

### Crear base y usuario (local)

```bash
# como superusuario 'postgres'
createuser -P tpgr63eventosuyadmin    # ingresar 'admin'
createdb -O tpgr63eventosuyadmin tpgr63eventosuy
```

> **Dump opcional**: el repo incluye `assets/bd` (cluster dump). Úsalo si querés partir de un estado predefinido.

### Configuración de Hibernate/JPA (ya incluida)

Propiedades claves del `persistence.xml`: dialecto PostgreSQL, `hbm2ddl.auto=update` (dev), HikariCP como pool.

---

## 3) Compilar y ejecutar

### Compilar todo

```bash
mvn clean package
```

El *parent* gestiona `maven-compiler-plugin` (release 17) y dependencias comunes.

### Ejecutar la GUI

```bash
# si empaquetaste con shade
java -jar admin/target/*-shaded.jar
# o desde el IDE: ejecutar gui.Principal
```

(El `pom` de `admin` configura *shade* para establecer `Main-Class: gui.Principal`).

---

## 4) Probar la conexión JPA/Hibernate (smoke test)

Hay un *smoke* simple que usa la `eventosPU`, persiste un `Usuario` y cierra la EMF: `admin/main/admin/Main.java`. Ejecutalo para verificar conexión, DDL y escritura.

Chequeo rápido en la DB:

```sql
-- luego de ejecutar el smoke
\c tpgr63eventosuy
SELECT id, nombre, correo FROM persona;
```

---

## 5) Desarrollo diario (resumen práctico)

* **Dónde codificar reglas**: `core/logica` (usa `repos` y entidades de `dominio`).
* **Qué expone core a la GUI**: interfaces en `core/interfaces` y DTOs en `core/datatypes`.
* **Acceso a datos**: repos con `EntityManager` (Tx helpers en `infra`).
* **GUI**: `admin/gui` con `JDesktopPane` + `JInternalFrame` por caso de uso; menú ya esqueleto.

---

## 6) Tests y cobertura

`core` incluye JUnit 5; apuntar a ≥80 % de cobertura en lógica (la GUI no se cubre). 

Ejecutar:

```bash
mvn -pl core test
```

---

## 7) Troubleshooting

* **Error de conexión**: validar host/puerto y credenciales de `persistence.xml`; la base debe existir.
* **No genera el JAR ejecutable**: revisar sección *shade* en `admin/pom.xml` y recompilar.
* **No aparece la tabla**: corré el *smoke*, y confirmá `hbm2ddl.auto=update` en dev.

---

### Apéndice: referencias rápidas del repo

* **Parent** con módulos y versiones: `eventos-parent/pom.xml`.
* **Dependencias de `core`** (Hibernate, Postgres, Hikari, SLF4J, JUnit): `core/pom.xml`.
* **Unidad de persistencia**: `core/src/main/resources/META-INF/persistence.xml`.
* **Entidad ejemplo**: `core/src/main/java/dominio/Usuario.java`.
* **Main GUI** (`gui.Principal`) y menú base.