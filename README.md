# BusinessProSuite API (v0.0.3)

Este proyecto es una API REST construida con Spring Boot. Provee servicios para distintos dominios del sistema (inventario, finanzas, seguridad, usuarios, etc.).

## Requisitos

- Java 17 (Gradle descargará la JDK automáticamente si no está instalada)
- Gradle 8+
- MySQL 8

## Puesta en marcha

1. Clonar el repositorio.
2. Configurar las variables de entorno indicadas abajo o editar `src/main/resources/application.properties`.
3. Ejecutar `./gradlew bootRun` para iniciar la API.

### Ejecución local paso a paso

Si es la primera vez que ejecutas la API de forma local sigue estos pasos:

1. **Crear la base de datos** en MySQL.
   ```sql
   CREATE DATABASE BusinessProSuite;
   ```
2. **Definir la conexión**. Puedes utilizar variables de entorno o modificar los
   siguientes archivos:
   - `src/main/resources/application.properties` (perfil activo y puerto).
   - `src/main/resources/application-dev.properties` (credenciales y URL de la base de datos).
3. Asegúrate de especificar `spring.datasource.url`, `spring.datasource.username`
   y `spring.datasource.password` con los valores de tu entorno.
4. Opcionalmente ajusta `JWT_SECRET` u otras variables que aparecen en la sección
   siguiente.
5. Finalmente ejecuta:
   ```bash
   ./gradlew bootRun
   ```
6. Accede a <http://localhost:8080/swagger-ui.html> para comprobar que la API está
   en funcionamiento.

## Variables de entorno principales

- `SPRING_DATASOURCE_URL` – URL de conexión a la base de datos.
- `SPRING_DATASOURCE_USERNAME` – usuario de la BD.
- `SPRING_DATASOURCE_PASSWORD` – contraseña de la BD.
- `JWT_SECRET` – clave secreta para firmar tokens.
- `JWT_EXPIRATION` – tiempo de expiración en milisegundos.
- `APP_ENVIRONMENT` – entorno de ejecución (dev, prod, etc.),
  usado para la propiedad `app.environment`.
- `APP_VERSION` – versión desplegada (opcional, por defecto `0.0.3`).
- `APP_DESCRIPTION` – descripción de la API (opcional).
- `APP_NAME` – nombre de la aplicación (opcional, por defecto `BusinessProSuite`).

## Migraciones de base de datos

Para controlar el orden de creacion del esquema en produccion se usa Flyway.
Coloca los scripts en `src/main/resources/db/migration` numerados como `V1__`, `V2__`, etc.
El archivo `V1__baseline.sql` crea las tablas basicas (`usr_roles`, `usr_users` y `usr_user_roles`) en ese orden.
En desarrollo Hibernate puede seguir actualizando el esquema con `spring.jpa.hibernate.ddl-auto=update`.


## Estructura de módulos

El código está organizado por dominios dentro de `com.businessprosuite.api`:

- `controller` – Controladores REST.
- `service` / `impl` – Interfaces de servicio y sus implementaciones.
- `repository` – Repositorios JPA.
- `dto` – Objetos de transferencia.
- `model` – Entidades JPA.

Cada subpaquete (finance, inventory, user, security, etc.) representa un dominio del negocio.

Desde 2025 se cuenta además con el endpoint `/api/hr/payrolls` para gestionar la nómina de los trabajadores.
Además se habilitó inicio de sesión mediante OAuth2 para proveedores externos (por ejemplo Google). Configurar las credenciales en `application.properties` usando el prefijo `spring.security.oauth2.client`.
También se incorpora autenticación multifactor (MFA) con TOTP. Usa los endpoints bajo `/api/mfa` para activar y verificar códigos.
El sistema de notificaciones ahora permite enviar correos mediante `/api/notification-queue/dispatch`.
Se añadió el endpoint `/api/version` para consultar la versión desplegada.
También se incorporó `/api/info` que muestra la versión, descripción y entorno de ejecución.

## Pruebas

Las pruebas unitarias utilizan JUnit 5 y Mockito. Para ejecutarlas:

```bash
./gradlew test
```

Algunos tests pueden requerir dependencias adicionales (por ejemplo Testcontainers) que no están incluidas por defecto.

## Compatibilidad

La API se ha ejecutado correctamente en macOS con procesador M4 Max utilizando Java 17 y Gradle 8.
