# Sanaesencia Backend - Microservicios & API REST

Backend para la plataforma y aplicación móvil del **Centro Integrativo Sanaesencia** (Los Vilos, Chile), desarrollado en **Spring Boot 3.3.x** con **Java 21**, **Spring Data JPA**, **Spring Security (JWT)** y **PostgreSQL**.

---

## 🚀 Requisitos Previos

- **Java JDK 21** o superior.
- **Maven 3.9+** (o ejecutar con el Maven de IntelliJ / Maven wrapper).
- **PostgreSQL 14+** (opcional para desarrollo local; se puede usar fallback en memoria o contenedor).

---

## 📦 Configuración y Base de Datos

Las propiedades se configuran en `src/main/resources/application.yml` o mediante variables de entorno:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | URL JDBC de conexión | `jdbc:postgresql://localhost:5432/sanaesencia` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de base de datos | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña | `postgres` |
| `SERVER_PORT` | Puerto de escucha | `8080` |

Al iniciar, el sistema ejecuta automáticamente `src/main/resources/data.sql` con los datos semilla oficiales (información institucional, los 8 boxes de Los Vilos, usuarios iniciales con contraseñas BCrypt, catálogo de productos naturales y testimonios).

---

## 🛠️ Compilación y Ejecución

Compilar el proyecto:
```bash
mvn clean package
```

Iniciar la aplicación:
```bash
mvn spring-boot:run
```

---

## 📖 Documentación de la API (Swagger UI)

Una vez iniciado el servidor, accede a la documentación interactiva OpenAPI 3 en:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📱 Integración con Cliente Android (Retrofit)

El backend expone endpoints compatibles exactamente con la aplicación móvil:
- Base URL en emulador Android: `http://10.0.2.2:8080/`
- CORS habilitado para `10.0.2.2:8080` y `localhost:8080`.
- Algoritmo de validación de RUN chileno (Módulo 11) integrado en `/api/users/register`.
- Control de colisiones (Double-Booking) en profesionales y los 8 boxes presenciales en `/api/appointments`.
