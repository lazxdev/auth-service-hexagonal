# Auth Service (Hexagonal)

Microservicio de autenticacion y autorizacion con Spring Boot y arquitectura hexagonal.

Despliegue: https://auth-service-hexagonal.onrender.com

---

## Stack

- Java 21
- Spring Boot 4.x
- Spring WebMVC
- Spring Data JPA
- PostgreSQL
- JWT (jjwt)
- OpenAPI (springdoc)

---

## Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL

---

## Configuracion

Variables de entorno requeridas (ver `src/main/resources/application.yaml`):

- `DB_URL` (ej: `jdbc:postgresql://localhost:5432/auth_service`)
- `DB_USERNAME`
- `DB_PASSWORD`

Configuracion JWT (se define en `src/main/resources/application.yaml`). Para produccion, reemplaza los secretos por valores propios.

---

## Ejecutar en local

```bash
export DB_URL="jdbc:postgresql://localhost:5432/auth_service"
export DB_USERNAME="postgres"
export DB_PASSWORD="postgres"

mvn spring-boot:run
```

El servicio queda disponible en:

- http://localhost:8080

---

## Docker

```bash
docker build -t auth-service-hexagonal .
docker run -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/auth_service" \
  -e DB_USERNAME="postgres" \
  -e DB_PASSWORD="postgres" \
  auth-service-hexagonal
```

---

## Documentacion API (OpenAPI / Swagger)

- Local: http://localhost:8080/swagger-ui/index.html
- Produccion: https://auth-service-hexagonal.onrender.com/swagger-ui/index.html
- Especificacion JSON: `/v3/api-docs`

---

## Estructura (Hexagonal)

```
src/main/java
└── dev/lazxdev/auth
```

---

## Estado del proyecto

En desarrollo. La base del servicio y la configuracion estan definidas; los endpoints se documentaran en Swagger a medida que se implementen.
