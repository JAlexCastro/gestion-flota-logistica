--- Usuarios 
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

--- Conductores

CREATE TABLE conductores (
    id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(15) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    numero_licencia VARCHAR(50),
    fecha_vencimiento_licencia DATE,
    usuario_id BIGINT UNIQUE,

    CONSTRAINT fk_conductor_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES usuarios(id)
);

--- Vehículos

CREATE TABLE vehiculos (
    id BIGSERIAL PRIMARY KEY,
    patente VARCHAR(10) UNIQUE NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio INTEGER NOT NULL,
    kilometraje_actual INTEGER NOT NULL,
    estado VARCHAR(30),

    conductor_id BIGINT NOT NULL,

    CONSTRAINT fk_vehiculo_conductor
    FOREIGN KEY (conductor_id)
    REFERENCES conductores(id)
);

--- Mantenciones

CREATE TABLE mantenciones (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    kilometraje INTEGER NOT NULL,
    tipo VARCHAR(50),
    descripcion TEXT,
    taller VARCHAR(100),

    CONSTRAINT fk_mantencion_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- Fallas
CREATE TABLE fallas (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    descripcion TEXT NOT NULL,
    prioridad VARCHAR(20),
    estado VARCHAR(20),

    CONSTRAINT fk_falla_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- SOAP
CREATE TABLE soap (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    aseguradora VARCHAR(100),
    numero_poliza VARCHAR(100),
    fecha_emision DATE,
    fecha_vencimiento DATE,

    CONSTRAINT fk_soap_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- PERMISOS CIRCULACIÓN
CREATE TABLE permisos_circulacion (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    fecha_emision DATE,
    fecha_vencimiento DATE,
    estado VARCHAR(20),

    CONSTRAINT fk_permiso_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- REVISIONES TECNICAS
CREATE TABLE revisiones_tecnicas (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    fecha_revision DATE,
    fecha_vencimiento DATE,
    resultado VARCHAR(20),

    CONSTRAINT fk_revision_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- TABLAS EMISIONES DE GASES
CREATE TABLE emisiones_gases (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    fecha_revision DATE,
    fecha_vencimiento DATE,
    resultado VARCHAR(20),

    CONSTRAINT fk_emision_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);

--- ALERTAS
CREATE TABLE alertas (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    tipo VARCHAR(50),
    mensaje TEXT,
    fecha_generacion TIMESTAMP,
    leida BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_alerta_vehiculo
    FOREIGN KEY (vehiculo_id)
    REFERENCES vehiculos(id)
);