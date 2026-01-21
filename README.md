# Auth Service – Spring Boot Microservice

**Arquitectura Hexagonal · JWT · Audit Logs**

---

## Descripción general

Este repositorio contiene la implementación de un **microservicio de autenticación y autorización** desarrollado con **Spring Boot** y basado en **Arquitectura Hexagonal (Ports & Adapters)**.

El servicio está diseñado para actuar como **proveedor central de identidad (Identity Provider)** dentro de una arquitectura de microservicios, delegando la autenticación y autorización mediante **tokens JWT**, y manteniendo **auditoría persistente de eventos de seguridad**.

El proyecto fue concebido con un enfoque **backend profesional**, priorizando:

* desacoplamiento,
* claridad arquitectónica,
* seguridad,
* trazabilidad,
* y escalabilidad.

---

## Objetivo del servicio

El objetivo principal del Auth Service es:

* Centralizar la **gestión de identidad y acceso**
* Proveer mecanismos seguros de **autenticación y autorización**
* Emitir y validar **tokens JWT** reutilizables por otros servicios
* Registrar **audit logs** para trazabilidad y análisis de seguridad
* Servir como base reutilizable para otros proyectos backend

Este servicio **no contiene lógica de negocio de otros dominios**, cumpliendo estrictamente el principio de **Single Responsibility**.

---

## Responsabilidades

El Auth Service es responsable de:

### Autenticación

* Registro de usuarios
* Login con email y contraseña
* Logout mediante invalidación de refresh tokens

### Autorización

* Gestión de roles
* Inclusión de roles en tokens JWT
* Soporte para autorización distribuida en otros microservicios

### Gestión de tokens

* Emisión de Access Tokens (JWT de corta duración)
* Emisión y rotación de Refresh Tokens
* Revocación explícita de tokens
* Control de expiración

### Seguridad

* Hashing de contraseñas (BCrypt)
* Verificación de credenciales
* Bloqueo lógico de cuentas
* Validación de tokens firmados

### Auditoría

* Registro persistente de eventos críticos:

  * login exitoso y fallido
  * registro de usuario
  * refresh de token
  * logout
  * acciones de seguridad
* Almacenamiento de metadata (IP, user-agent, actor, timestamp)

---

## Fuera de alcance (decisión intencional)

Este servicio **no**:

* Implementa lógica de negocio de otros dominios
* Almacena información funcional de aplicaciones cliente
* Comparte credenciales con otros servicios
* Maneja sesiones de estado (stateless por diseño)

---

## Stack tecnológico

### Backend

* **Java 21**
* **Spring Boot 3.x**
* **Spring Security 6**
* **Spring Data JPA**

### Seguridad

* JWT (Access Token + Refresh Token)
* BCrypt para hashing de contraseñas

### Persistencia

* PostgreSQL
* Flyway / Liquibase para control de migraciones

### Infraestructura y herramientas

* Docker
* OpenAPI / Swagger
* Validación con Jakarta Validation
* Tests unitarios y de seguridad

---

## Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)**, lo que permite:

* Aislar el dominio del framework
* Desacoplar la lógica de negocio de la infraestructura
* Facilitar el testing unitario
* Permitir cambios tecnológicos sin impactar el core

### Capas principales

#### 1. Dominio (Domain)

Contiene:

* Entidades
* Value Objects
* Reglas de negocio
* Interfaces de puertos (contratos)

El dominio **no depende de Spring ni de ninguna tecnología externa**.

#### 2. Aplicación (Application)

Contiene:

* Casos de uso
* Orquestación del flujo de negocio
* DTOs de entrada y salida
* Manejo de transacciones

Define *qué* se hace, no *cómo* se implementa.

#### 3. Infraestructura (Infrastructure)

Contiene:

* Adaptadores de entrada (REST Controllers)
* Adaptadores de salida (JPA, JWT, BCrypt, DB)
* Configuración de Spring Security
* Persistencia y detalles técnicos

---

## Estructura del proyecto

```
src/main/java/com/example/auth
│
├── domain
│   ├── model
│   ├── port
│   │   ├── in
│   │   └── out
│   └── exception
│
├── application
│   ├── usecase
│   └── dto
│
├── infrastructure
│   ├── web
│   ├── persistence
│   ├── security
│   └── config
│
└── AuthServiceApplication.java
```

---

## Modelo de datos (resumen)

Entidades principales:

* `users`
* `roles`
* `user_roles`
* `refresh_tokens`
* `audit_logs`

El diseño prioriza:

* Identificadores UUID
* Integridad referencial
* Trazabilidad completa de eventos de seguridad

---

## Flujo de autenticación (resumen)

1. El usuario se autentica mediante `/auth/login`
2. El servicio valida credenciales
3. Se genera un **Access Token** y un **Refresh Token**
4. El cliente usa el Access Token para acceder a otros servicios
5. Al expirar, se solicita uno nuevo mediante `/auth/refresh`
6. Todas las acciones son auditadas

---

## Auditoría (Audit Logs)

El servicio implementa auditoría persistente para eventos críticos de seguridad, permitiendo:

* Análisis de accesos
* Detección de patrones sospechosos
* Cumplimiento de trazabilidad
* Soporte para debugging y monitoreo

La auditoría es tratada como una **capacidad de primer nivel**, no como logging secundario.

---

## Principios aplicados

* Single Responsibility Principle
* Dependency Inversion Principle
* Clean Architecture
* Stateless Authentication
* Security by Design
* Separation of Concerns

---

## Estado del proyecto

🚧 En desarrollo
El proyecto se implementa de forma incremental siguiendo una ruta de desarrollo estructurada, comenzando por el dominio y avanzando hacia infraestructura y seguridad.

---

## Posibles mejoras futuras

* Rate limiting
* Redis para blacklist de tokens
* Email verification
* Password reset
* Account lockout automático
* Integración con API Gateway
