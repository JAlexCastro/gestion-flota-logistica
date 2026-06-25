```markdown
# 🚚 Transcargo - Gestión de Flota Logística

¡Bienvenido al sistema de **Gestión de Flota Logística de Transcargo**! Esta plataforma integral está diseñada para optimizar, monitorear y administrar la flota de vehículos de la empresa de transporte.

El proyecto está estructurado como un monorepositorio que divide claramente la base de datos, el backend y el frontend.

---

## 📂 Estructura del Proyecto

```text
CARPETA/
│
├── DATABASE/              # Scripts de inicialización y esquemas de la Base de Datos.
├── backend-springboot/    # API REST de negocio construida con Java y Spring Boot.
└── frontend-react/        # Interfaz de usuario interactiva construida con React.

```

---

## 🛠️ Tecnologías Utilizadas

* **Frontend:** React, JavaScript , CSS.
* **Backend:** Java, Spring Boot (Spring Boot Starter Web, Spring Data JPA).
* **Base de Datos:** Relacional (PostgreSQL).

---

## 🚀 Instrucciones de Configuración y Despliegue

Sigue estos pasos en orden para levantar todo el ecosistema de Transcargo localmente:

### 1. Base de Datos (`/DATABASE`)

1. Asegúrate de tener tu motor de base de datos corriendo localmente.
2. Crea una base de datos llamada `db_transcargo`.
3. Ejecuta los scripts de inicialización (`.sql`) que se encuentren dentro de esta carpeta para generar las tablas base de camiones, conductores y rutas.

### 2. Backend (`/backend-springboot`)

Requisitos: **JDK 17 o superior** y **Maven** (o tu IDE de preferencia como IntelliJ o VS Code).

1. Abre la carpeta en tu terminal:

```bash
   cd backend-springboot

```

2. Configura tus credenciales locales de base de datos en el archivo `src/main/resources/application.properties` (o variables de entorno).
3. Compila y ejecuta el backend:

```bash
   ./mvnw spring-boot:run

```

> 🌐 El servidor de la API REST se levantará por defecto en: `http://localhost:8080`

### 3. Frontend (`/front-gestion-flota`)

Requisitos: **Node.js (v18 o superior)**.

1. Abre la carpeta en tu terminal:

```bash
   cd frontend-react

```

2. Instala las dependencias del proyecto:

```bash
   npm install

```

3. Inicia el servidor de desarrollo del cliente:

```bash
   npm run dev

```

> 🌐 La interfaz de usuario estará disponible en el puerto indicado por tu terminal (usualmente `http://localhost:5173` o `http://localhost:3000`).

---


🏢 *Desarrollado para el sistema de control y logística interna de **Transcargo**.*

