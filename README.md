# BusinessProSuite API

Este proyecto es una API REST construida con Spring Boot. Provee servicios para distintos dominios del sistema (inventario, finanzas, seguridad, usuarios, etc.).

## Requisitos

- Java 17
- Gradle 8+
- MySQL 8

## Puesta en marcha

1. Clonar el repositorio.
2. Configurar las variables de entorno indicadas abajo o editar `src/main/resources/application.properties`.
3. Ejecutar `./gradlew bootRun` para iniciar la API.

## Variables de entorno principales

- `SPRING_DATASOURCE_URL` – URL de conexión a la base de datos.
- `SPRING_DATASOURCE_USERNAME` – usuario de la BD.
- `SPRING_DATASOURCE_PASSWORD` – contraseña de la BD.
- `JWT_SECRET` – clave secreta para firmar tokens.
- `JWT_EXPIRATION` – tiempo de expiración en milisegundos.

## Estructura de módulos

El código está organizado por dominios dentro de `com.businessprosuite.api`:

- `controller` – Controladores REST.
- `service` / `impl` – Interfaces de servicio y sus implementaciones.
- `repository` – Repositorios JPA.
- `dto` – Objetos de transferencia.
- `model` – Entidades JPA.

Cada subpaquete (finance, inventory, user, security, etc.) representa un dominio del negocio.

## Pruebas

Las pruebas unitarias utilizan JUnit 5 y Mockito. Para ejecutarlas:

```bash
./gradlew test
```

Algunos tests pueden requerir dependencias adicionales (por ejemplo Testcontainers) que no están incluidas por defecto.

## Compatibilidad

La API se ha ejecutado correctamente en macOS con procesador M4 Max utilizando Java 17 y Gradle 8.
