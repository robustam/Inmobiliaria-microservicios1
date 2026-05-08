-- Script de inicialización de bases de datos para microservicios
-- Se ejecuta automáticamente al levantar el contenedor de MySQL

-- Crear bases de datos para cada microservicio
CREATE DATABASE IF NOT EXISTS db_propiedades CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_usuarios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_contratos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_pagos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_documentos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_visitas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_mantenimiento CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_notificaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_opiniones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_promociones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_reservas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos de propiedades
USE db_propiedades;

-- Tabla propiedades (será creada por JPA/Hibernate si no existe)
-- Pero aquí dejamos comentado como referencia
-- CREATE TABLE propiedades (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     direccion VARCHAR(200) NOT NULL,
--     habitaciones INT NOT NULL,
--     precio DECIMAL(10,2) NOT NULL,
--     INDEX idx_habitaciones (habitaciones),
--     INDEX idx_precio (precio)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
