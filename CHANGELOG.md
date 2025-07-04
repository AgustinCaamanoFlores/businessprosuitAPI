# Cambios de la API
## v0.0.3
- `app.version` y `app.description` pueden configurarse con las variables de entorno `APP_VERSION` y `APP_DESCRIPTION`.
- Gradle puede descargar automáticamente la JDK requerida para las pruebas.
## v0.0.2
- Nuevo endpoint `/api/version` para consultar la versión de la API.
- Valor de la versión configurable en `application.properties` (app.version).
- Se añadió propiedad `app.description` para describir la API.
- Nuevo endpoint `/api/info` que devuelve versión y descripción.
- Nueva propiedad `app.environment` para indicar el entorno y ahora `/api/info` la retorna.
- La propiedad puede fijarse mediante la variable de entorno `APP_ENVIRONMENT`.
- Se añadieron pruebas de `InfoController` para `/api/version` y `/api/info`.

## v0.0.1
- Implementación de cuentas por pagar y cobrar en Finanzas.
- Servicio de pronóstico de inventario con códigos de barras en almacenes.
- Gestión de nómina para RRHH.
- Inicio de sesión mediante OAuth2 con generación de JWT.
- Autenticación multifactor con TOTP y nuevos endpoints de MFA.
- Envío de notificaciones por correo desde la cola.
