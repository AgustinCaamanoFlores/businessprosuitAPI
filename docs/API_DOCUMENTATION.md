# 📚 BusinessProSuite API V0.2 - Documentación Completa

![Version](https://img.shields.io/badge/Version-V0.2-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Tests](https://img.shields.io/badge/Tests-43%20passing-brightgreen)

## 🏢 Descripción General

BusinessProSuite API V0.2 es una plataforma empresarial completa que proporciona servicios modulares para gestión de empresas, usuarios, finanzas, recursos humanos, inventario y más. La API está construida con Spring Boot 3.4.4, Java 17 y utiliza autenticación JWT con documentación OpenAPI/Swagger completa.

### 🌟 Características V0.2
- ✅ **Autenticación JWT** completamente funcional
- ✅ **Documentación Swagger/OpenAPI** interactiva
- ✅ **8 Grupos de API** organizados con emojis
- ✅ **43 Tests unitarios** pasando
- ✅ **Base de datos H2** para desarrollo
- ✅ **Configuración multi-ambiente** (dev, test, prod)

---

## 🚀 Acceso a la Documentación Interactiva

### 📖 Swagger UI
```
URL: http://localhost:8080/swagger-ui/index.html
```
La interfaz Swagger UI proporciona documentación interactiva donde puedes:
- 🔍 Explorar todos los endpoints disponibles
- 🧪 Probar las APIs directamente desde el navegador
- 📋 Ver esquemas de request/response
- 🔐 Configurar autenticación JWT

### 📄 OpenAPI JSON
```
URL: http://localhost:8080/v3/api-docs
```

---

## 🔐 Autenticación y Seguridad

### 🎯 Configuración de Autenticación en Swagger

1. **Accede a Swagger UI**: http://localhost:8080/swagger-ui/index.html
2. **Haz clic en "Authorize"** (botón con candado)
3. **Ingresa el token JWT** en formato: `Bearer {tu-token-jwt}`
4. **Haz clic en "Authorize"** para aplicar a todas las requests

### Endpoints de Autenticación

#### **🔑 Registro de Usuario**
```http
POST /auth/register
Content-Type: application/json

{
  "username": "admin",
  "email": "admin@empresa.com",
  "password": "MySecurePass123!",
  "fullName": "Administrador Principal",
  "companyName": "Mi Empresa S.A.",
  "companyEmail": "contacto@empresa.com",
  "companyAddress": "Calle Principal 123",
  "companyPhone": "+34123456789",
  "countryCode": "ES"
}
```

**Respuesta Exitosa:**
```json
{
  "message": "Usuario registrado exitosamente",
  "user": {
    "id": 1,
    "username": "admin",
    "email": "admin@empresa.com",
    "fullName": "Administrador Principal"
  },
  "company": {
    "id": 1,
    "name": "Mi Empresa S.A.",
    "email": "contacto@empresa.com"
  }
}
```

#### **🔓 Login de Usuario**
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "MySecurePass123!"
}
```

**Respuesta Exitosa:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@empresa.com",
  "authorities": ["ROLE_ADMIN"],
  "expiresIn": 86400
}
```

#### **✅ Verificar Token**
```http
GET /auth/verify
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 📊 Grupos de API Disponibles

La API está organizada en 8 grupos principales:

### 🔐 Authentication
- Registro de usuarios
- Login/Logout
- Verificación de tokens
- Gestión de sesiones

### 👥 User Management  
- Gestión de usuarios de seguridad
- Perfiles de usuario
- Roles y permisos

### 🏢 Company Management
- Gestión de empresas
- Configuración empresarial
- Información corporativa

### 💰 Finance
- Gestión financiera básica
- Configuración de finanzas
- (En desarrollo)

### 👨‍💼 HR (Human Resources)
- Gestión de recursos humanos
- Empleados y departamentos
- (En desarrollo)

### 📦 Inventory
- Control de inventario
- Gestión de productos
- (En desarrollo)

### 📊 Analytics
- Análisis y reportes
- Métricas del sistema
- (En desarrollo)

### ⚙️ Administration
- Configuración del sistema
- Salud de la aplicación
- Administración general

---

## 🏢 Módulo de Empresas

### Gestión de Empresas

#### **➕ Crear Empresa**
```http
POST /api/companies
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "Nueva Empresa S.L.",
  "address": "Avenida de la Innovación 456",
  "phone": "+34987654321",
  "email": "info@nuevaempresa.com",
  "taxId": "B98765432",
  "countryCode": "ES"
}
```

#### **📋 Listar Empresas**
```http
GET /api/companies
Authorization: Bearer {token}
```

#### **🔍 Obtener Empresa por ID**
```http
GET /api/companies/{id}
Authorization: Bearer {token}
```

#### **✏️ Actualizar Empresa**
```http
PUT /api/companies/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Empresa Actualizada S.L.",
  "address": "Nueva Dirección 789",
  "phone": "+34555666777",
  "email": "nuevo@empresa.com"
}
```

---

## 👥 Módulo de Usuarios

### Gestión de Usuarios de Seguridad

#### **➕ Crear Usuario de Seguridad**
```http
POST /api/security-users
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "empleado1",
  "password": "EmpleadoPass123!",
  "email": "empleado@empresa.com",
  "available": 1,
  "active": 1,
  "mfaEnabled": 0,
  "roleId": 2,
  "companyId": 1
}
```

#### **📋 Listar Usuarios**
```http
GET /api/security-users
Authorization: Bearer {token}
```

#### **🔍 Obtener Usuario por ID**
```http
GET /api/security-users/{id}
Authorization: Bearer {token}
```

---

## 🏪 Módulo de Clientes

### Gestión de Clientes

#### **➕ Crear Cliente**
```http
POST /api/customers
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "Cliente Premium S.A.",
  "email": "cliente@premium.com",
  "phone": "+34111222333",
  "address": "Plaza del Cliente 12",
  "taxId": "A11223344",
  "companyId": 1,
  "countryCode": "ES"
}
```

#### **📋 Listar Clientes**
```http
GET /api/customers
Authorization: Bearer {token}
```

---

## ⚙️ Módulo de Administración

### Health Check

#### **🏥 Estado de la Aplicación**
```http
GET /actuator/health
```

**Respuesta:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2",
        "validationQuery": "isValid()"
      }
    }
  }
}
```

---

## 🧪 Testing y Calidad

### Estado de Tests V0.2
- ✅ **43 tests unitarios** pasando
- ✅ **0 tests fallando**
- ✅ **Cobertura de código** en módulos críticos
- ✅ **Tests de integración** para controllers principales

### Ejecutar Tests
```bash
# Todos los tests
./gradlew test

# Con reporte de cobertura
./gradlew test jacocoTestReport

# Tests específicos
./gradlew test --tests "*AuthController*"
```

---

## 🔧 Configuración y Desarrollo

### Base de Datos H2
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vacío)
```

### Perfiles de Aplicación
- **dev** - Desarrollo (por defecto)
- **test** - Testing
- **prod** - Producción

### Variables de Entorno
```bash
# JWT Configuration
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# Database (opcional para H2)
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=
```

---

## 📈 Códigos de Estado HTTP

| Código | Descripción | Uso |
|--------|-------------|-----|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado exitosamente |
| 400 | Bad Request | Error en la request |
| 401 | Unauthorized | Token inválido o faltante |
| 403 | Forbidden | Sin permisos suficientes |
| 404 | Not Found | Recurso no encontrado |
| 500 | Internal Server Error | Error interno del servidor |

---

## 🚀 Próximos Pasos

### Módulos en Desarrollo
- 💰 **Finance**: Gestión financiera completa
- 👨‍💼 **HR**: Sistema de recursos humanos
- 📦 **Inventory**: Control de inventario avanzado
- 📊 **Analytics**: Dashboard y reportes

### Mejoras Planificadas
- 🔒 **Autenticación OAuth2**
- 📱 **API Rate Limiting**
- 🌐 **Internacionalización**
- 📊 **Métricas avanzadas**

---

## 📞 Soporte y Contacto

### Documentación Adicional
- [Guía de Inicio Rápido](./QUICK_START.md)
- [Conexión a la API](./CONEXION_API.md)
- [Ayuda y FAQ](./HELP.md)

### Contacto
- **Email**: contact@businessprosuite.com
- **Website**: https://businessprosuite.com
- **Documentación**: http://localhost:8080/swagger-ui/index.html

---

**Versión**: V0.2 - Completado  
**Última Actualización**: Junio 2025  
**Estado**: ✅ Producción Ready 