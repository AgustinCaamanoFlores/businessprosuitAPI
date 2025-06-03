# BusinessProSuite API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)
![Gradle](https://img.shields.io/badge/Gradle-8.13-blue)
![Version](https://img.shields.io/badge/Version-V0.2-brightgreen)

## 🚀 Descripción

BusinessProSuite API es una solución empresarial completa desarrollada con Spring Boot 3.4.4 y Java 17. Proporciona una arquitectura modular robusta para gestionar múltiples aspectos de negocio incluyendo autenticación, gestión de empresas, finanzas, recursos humanos, inventario, análisis y más.

## 📋 Características Principales

- ✅ **Autenticación JWT** - Sistema de seguridad robusto
- ✅ **Documentación OpenAPI/Swagger** - API completamente documentada
- ✅ **Arquitectura Modular** - 20+ módulos de negocio
- ✅ **Cobertura de Tests** - Suite de pruebas completa
- ✅ **Configuración Multi-ambiente** - Dev, Test, Prod
- ✅ **Base de Datos H2** - Para desarrollo y testing

## 🏗️ Módulos Disponibles

| Módulo | Descripción | Estado |
|--------|-------------|--------|
| 🔐 Authentication | Sistema de autenticación y autorización | ✅ Completado |
| 👥 User Management | Gestión de usuarios y perfiles | ✅ Completado |
| 🏢 Company Management | Administración de empresas | ✅ Completado |
| 💰 Finance | Gestión financiera | 🚧 En desarrollo |
| 👨‍💼 HR | Recursos humanos | 🚧 En desarrollo |
| 📦 Inventory | Control de inventario | 🚧 En desarrollo |
| 📊 Analytics | Análisis y reportes | 🚧 En desarrollo |
| ⚙️ Administration | Configuración del sistema | ✅ Completado |

## 🚀 Inicio Rápido

### Prerrequisitos
- Java 17+
- Gradle 8.13+

### Instalación y Ejecución

```bash
# Clonar el repositorio
git clone <repository-url>
cd BusinessProSuiteAPI

# Ejecutar la aplicación
./gradlew bootRun

# Ejecutar tests
./gradlew test

# Generar documentación
./gradlew build
```

### Acceso a la Aplicación

- **Aplicación**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

## 📚 Documentación

Toda la documentación del proyecto está organizada en la carpeta [`docs/`](./docs/):

### 📖 Documentación Principal
- [**Guía de Inicio Rápido**](./docs/QUICK_START.md) - Configuración e instalación
- [**Documentación de API**](./docs/API_DOCUMENTATION.md) - Endpoints y ejemplos
- [**Conexión a la API**](./docs/CONEXION_API.md) - Guía de integración
- [**Ayuda General**](./docs/HELP.md) - Preguntas frecuentes

### 📊 Reportes y Estado
- [**Reporte Final V0.2**](./docs/REPORTE_FINAL_V0.2.md) - Estado actual del proyecto
- [**Análisis Técnico**](./docs/Análisis%20Técnico%20del%20Repositorio%20BusinessProSuiteAPI.pdf) - Análisis completo del código
- [**Cambios Realizados**](./docs/changes) - Log detallado de cambios
- [**Versión Actual**](./docs/currentVersion) - Estado de la versión actual
- [**Próximos Cambios**](./docs/next_changes) - Roadmap de desarrollo

### 🔧 Desarrollo y Arquitectura
- [**Roadmap de Modularización**](./docs/MODULARIZATION_ROADMAP.md) - Plan de arquitectura
- [**README de Modularización**](./docs/README_MODULARIZATION.md) - Guía de módulos
- [**Reportes de Testing**](./docs/) - Reportes de pruebas Swagger

## 🧪 Testing

El proyecto cuenta con una suite completa de tests:

```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests con reporte de cobertura
./gradlew test jacocoTestReport

# Ejecutar tests de integración
./gradlew integrationTest
```

**Estado Actual**: ✅ 43 tests pasando, 0 fallando

## 🔧 Configuración

### Perfiles de Aplicación
- **dev** - Desarrollo (por defecto)
- **test** - Testing
- **prod** - Producción

### Base de Datos
- **H2 Database** (en memoria para desarrollo)
- **Configuración**: `src/main/resources/application.yml`

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 📞 Contacto

**BusinessProSuite Development Team**
- Email: contact@businessprosuite.com
- Website: https://businessprosuite.com

---

**Versión**: V0.2 - Completado con Warnings Resueltos  
**Última Actualización**: Junio 2025  
**Estado**: ✅ Producción Ready 