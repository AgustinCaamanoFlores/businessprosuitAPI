# Futuro desarrollo

Este documento resume mejoras sugeridas para **BusinessProSuite API**.

## Sectores existentes

Actualmente la API abarca los siguientes dominios:

- analytics
- asset
- audit
- auth
- company
- config
- core
- customer
- document
- error
- etl
- finance
- gdpr
- hr
- inventory
- leasing
- notification
- schema
- security
- subs
- user
- workflow

Cada uno cuenta con controladores, servicios y modelos JPA.

## Áreas a potenciar a corto plazo

- **Inventory** – Añadir pronósticos de stock y manejo de ubicaciones con códigos de barras.
- **Finance** – Incorporar cuentas por pagar/cobrar, contabilidad completa y gestión de impuestos multimoneda.
- **HR** – Integrar nómina y seguimiento de desempeño.
- **Security** – Implementar MFA y OAuth2 para autenticación externa.
- **Workflow** – Permitir reglas de negocio avanzadas y notificaciones automáticas.
- **Notification** – Conectar con proveedores de correo y SMS para envíos masivos.
- **Analytics** – Consolidar un data warehouse y paneles de KPI.
- **Subs** – Ampliar la gestión de facturación y renovaciones.

## Mejoras a mediano plazo

- **GDPR y Audit** – Extender reportes y ciclos de retención.
- **Schema & ETL** – Automatizar migraciones y procesos de carga.
- **Document** – Control de versiones avanzado y firmas digitales.

## Otras recomendaciones

- Aumentar la cobertura de pruebas; actualmente hay pocos tests en `src/test/java`.
- Documentar cada módulo y ejemplos de uso vía Swagger u OpenAPI.
- Evaluar arquitectura multicompañía para aislar datos entre empresas.


## Estructura actual del proyecto

El codigo se organiza bajo `com.businessprosuite.api` en paquetes que responden a cada dominio del negocio. Cada dominio posee:

- `controller` – expone las rutas REST.
- `service` / `impl` – logica principal y acceso a datos.
- `repository` – interfaces JPA.
- `dto` y `model` – transporte y persistencia.

La configuracion general y de seguridad esta en `config` y `security`.

## Funcionalidades implementadas hasta la fecha

- Autenticacion basada en JWT con login desde `AuthController`.
- Manejador global de excepciones.
- MapStruct para mapeo entre entidades y DTO (por ejemplo `InvoiceMapper`).
- Cache con Caffeine para consultas intensivas.
- Pruebas unitarias y de integracion con JUnit 5 y Testcontainers.

## Pasos para probar localmente

1. Clonar este repositorio.
2. Configurar las variables de entorno de base de datos y JWT indicadas en el README.
3. Ejecutar `./gradlew bootRun` para iniciar la API.
4. Visitar `http://localhost:8080/swagger-ui.html` para explorar los endpoints.

Se recomienda crear una base vacia en MySQL y aplicar las migraciones con Flyway al primer arranque.

## Ejemplo de flujo de usuario

1. Un usuario se registra o inicia sesion via `/api/auth/login` y obtiene un JWT.
2. Con el token puede crear clientes, gestionar stock de `inventory` y registrar facturas en `finance`.
3. Cada accion queda auditada y puede visualizarse mediante los endpoints de `audit`.

## Proximas ampliaciones por industria

- **Inventory** – seguimiento de lotes y ubicaciones por codigo QR.
- **Finance** – contabilidad general y reportes fiscales.
- **HR** – portal de empleados con autoservicio y nomina.
- **Leasing** – gestion de contratos y activos en arrendamiento.

Estas tareas implican generar nuevas entidades, servicios y controladores en los paquetes mencionados.

## Cambios implementados en 2025

- **Inventory**: se añadió el servicio de pronóstico de stock con el endpoint `/api/inventory/products/{id}/forecast` y se incorporó el campo de código de barras en `InventoryWarehouse`.
- **Finance**: se crearon las entidades `AccountsPayable` y `AccountsReceivable` junto con sus controladores REST.
- **Documentación**: se agregó `API_DOCUMENTATION.md` y se actualizó `README.md` con instrucciones para macOS M4 Max.
- **HR**: se incorporó gestión de nómina mediante la entidad `Payroll` y el endpoint `/api/hr/payrolls`.
- **Security**: se añadió inicio de sesión mediante OAuth2 que genera un JWT automáticamente.
- **Security**: se implementó autenticación multifactor TOTP con nuevos endpoints y servicio `TotpService`.
- **Notification**: la cola de notificaciones ahora puede despachar correos mediante `/api/notification-queue/dispatch`.
- **Versión**: primera versión estable etiquetada como `v0.0.1`.
