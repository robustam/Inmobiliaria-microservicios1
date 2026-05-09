-- Script de inicialización de bases de datos
-- Inmobiliaria Microservicios

CREATE DATABASE IF NOT EXISTS db_usuarios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_propiedades CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_reservas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_pagos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_contratos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_visitas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_mantenimiento CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_notificaciones CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_documentos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Otorgar permisos al usuario root
GRANT ALL PRIVILEGES ON db_usuarios.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_propiedades.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_reservas.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_pagos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_auth.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_contratos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_visitas.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_mantenimiento.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_notificaciones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_documentos.* TO 'root'@'%';

FLUSH PRIVILEGES;
