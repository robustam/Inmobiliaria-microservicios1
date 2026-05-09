# 🏠 Inmobiliaria Microservicios

Sistema de gestión inmobiliaria desarrollado con arquitectura de microservicios usando Spring Boot 3.3.2, Java 17, MySQL y Spring Cloud.

## 🏗️ Arquitectura

```
                        ┌─────────────────┐
                        │   API Gateway   │
                        │   Puerto 8080   │
                        └────────┬────────┘
                                 │
                        ┌────────▼────────┐
                        │  Eureka Server  │
                        │   Puerto 8761   │
                        └────────┬────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
┌───────▼──────┐        ┌────────▼───────┐       ┌───────▼──────┐
│ auth-service │        │usuario-service │       │prop-service  │
│  Puerto 8092 │        │  Puerto 8082   │       │ Puerto 8081  │
└──────────────┘        └────────────────┘       └──────────────┘
```

## 📦 Microservicios

| Servicio | Puerto | Base de Datos | Descripción |
|---|---|---|---|
| eureka-server | 8761 | - | Registro y descubrimiento de servicios |
| api-gateway | 8080 | - | Punto de entrada único |
| auth-service | 8092 | db_auth | Autenticación y autorización |
| usuario-service | 8082 | db_usuarios | Gestión de usuarios |
| propiedad-service | 8081 | db_propiedad | Catálogo de propiedades |
| visita-service | 8094 | db_visitas | Agendamiento de visitas |
| reservas-service | 8083 | db_reservas | Gestión de reservas |
| contrato-service | 8093 | db_contratos | Contratos de arriendo |
| pagos-service | 8084 | db_pagos | Procesamiento de pagos |
| mantenimiento-service | 8095 | db_mantenimiento | Solicitudes de mantención |
| documento-service | 8097 | db_documentos | Gestión documental |
| notificaciones-service | 8086 | db_notificaciones | Notificaciones a usuarios |

## 🛠️ Tecnologías

- **Java 17** + **Spring Boot 3.3.2**
- **Spring Cloud** (Eureka, OpenFeign, Gateway)
- **MySQL 8.0** vía Docker
- **JPA / Hibernate**
- **Bean Validation** (`@NotBlank`, `@NotNull`, `@Email`, `@Size`)
- **SLF4J / Logback** para logging
- **Lombok**
- **Docker / Docker Compose**

## 🚀 Cómo ejecutar

### Prerequisitos
- Java 17+
- Docker Desktop
- IntelliJ IDEA

### 1. Levantar MySQL con Docker

```bash
docker-compose up -d
```

### 2. Crear bases de datos

```bash
docker exec -it mysql-inmobiliaria mysql -u root -proot -e "
CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_propiedad;
CREATE DATABASE IF NOT EXISTS db_reservas;
CREATE DATABASE IF NOT EXISTS db_pagos;
CREATE DATABASE IF NOT EXISTS db_auth;
CREATE DATABASE IF NOT EXISTS db_contratos;
CREATE DATABASE IF NOT EXISTS db_visitas;
CREATE DATABASE IF NOT EXISTS db_mantenimiento;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
CREATE DATABASE IF NOT EXISTS db_documentos;
"
```

### 3. Compilar todos los servicios

```bash
for svc in eureka-server api-gateway auth-service usuario-service propiedad-service visita-service reservas-service contrato-service pagos-service mantenimiento-service documento-service notificaciones-service; do
  cd $svc && ./mvnw clean package -DskipTests -q && cd ..
done
```

### 4. Iniciar servicios (orden recomendado)

1. `eureka-server` (esperar 15 segundos)
2. `api-gateway`
3. Resto de microservicios

### 5. Verificar Eureka

Abrir http://localhost:8761 — todos los servicios deben aparecer como **UP**.

## 🧪 Pruebas de endpoints

```bash
bash test-endpoints.sh
```

## 📋 Características implementadas

- ✅ **Arquitectura de microservicios** con patrón CSR (Controller-Service-Repository)
- ✅ **Eureka Server** para descubrimiento de servicios
- ✅ **API Gateway** como punto de entrada único
- ✅ **Feign Client** para comunicación entre servicios
- ✅ **Bean Validation** en todos los modelos
- ✅ **@ControllerAdvice** (GlobalExceptionHandler) en todos los servicios
- ✅ **DTOs** para separación de capas
- ✅ **Logs con SLF4J** en controllers y services
- ✅ **MySQL** como base de datos persistente
- ✅ **JPA / Hibernate** con ddl-auto update

## 👤 Autor

Roberto Bustamante — DUOC UC — Ingeniería en Informática