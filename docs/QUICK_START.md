# 🚀 Guía de Inicio Rápido - BusinessProSuite API

## ⚡ Inicio en 5 minutos

### 1️⃣ Ejecutar la aplicación
```bash
# Desde la raíz del proyecto
./gradlew bootRun
```

### 2️⃣ Verificar que funciona
```bash
curl http://localhost:8080/auth/health
```

### 3️⃣ Registrar tu primera empresa y usuario
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@miempresa.com",
    "password": "MiPassword123!",
    "fullName": "Administrador Principal",
    "companyName": "Mi Empresa S.A.",
    "companyEmail": "contacto@miempresa.com",
    "companyAddress": "Calle Principal 123",
    "companyPhone": "+34123456789",
    "countryCode": "ES"
  }'
```

### 4️⃣ Iniciar sesión
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "MiPassword123!"
  }'
```

### 5️⃣ Usar el token en requests protegidos
```bash
# Reemplaza {TOKEN} con el token del paso anterior
curl -X GET http://localhost:8080/api/companies \
  -H "Authorization: Bearer {TOKEN}"
```

---

## 🧪 Prueba Automática

Ejecuta el script de pruebas completo:
```bash
chmod +x test-auth-api.sh
./test-auth-api.sh
```

---

## 📋 Endpoints Principales

### 🔐 Autenticación (Público)
- `POST /auth/register` - Registrar usuario y empresa
- `POST /auth/login` - Iniciar sesión  
- `GET /auth/verify` - Verificar token
- `GET /auth/health` - Estado del servicio

### 🏢 Empresas (Requiere Token)
- `GET /api/companies` - Listar empresas
- `POST /api/companies` - Crear empresa
- `GET /api/companies/{id}` - Obtener empresa
- `PUT /api/companies/{id}` - Actualizar empresa

### 👥 Usuarios (Requiere Token)
- `GET /api/security-users` - Listar usuarios
- `POST /api/security-users` - Crear usuario
- `GET /api/security-users/{id}` - Obtener usuario

### 🏪 Clientes (Requiere Token)  
- `GET /api/customers` - Listar clientes
- `POST /api/customers` - Crear cliente

---

## 🔑 Formato de Autenticación

Todos los endpoints protegidos requieren el header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## ⚙️ Configuración Base de Datos

1. **Crear base de datos MySQL:**
```sql
CREATE DATABASE BusinessProSuite;
```

2. **Configurar connection en application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/BusinessProSuite
spring.datasource.username=root
spring.datasource.password=tu_password
```

---

## 🛠️ Ejemplo Completo con JavaScript

```javascript
// 1. Registro
const registerResponse = await fetch('http://localhost:8080/auth/register', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: "admin",
    email: "admin@empresa.com", 
    password: "Password123!",
    fullName: "Administrador",
    companyName: "Mi Empresa",
    companyEmail: "contacto@empresa.com",
    companyAddress: "Calle 123",
    companyPhone: "+34123456789",
    countryCode: "ES"
  })
});

// 2. Login  
const loginResponse = await fetch('http://localhost:8080/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: "admin",
    password: "Password123!"
  })
});

const { token } = await loginResponse.json();

// 3. Usar API protegida
const companiesResponse = await fetch('http://localhost:8080/api/companies', {
  headers: { 'Authorization': `Bearer ${token}` }
});
```

---

## ❓ Solución de Problemas

### Error 400 en registro
- Verificar que el password cumple los requisitos
- Verificar que el email tenga formato válido
- Verificar que el país "ES" existe en la BD

### Error 401 en login
- Verificar username y password
- Verificar que el usuario esté activo

### Error 404
- Verificar que la aplicación esté ejecutándose en puerto 8080
- Verificar la URL del endpoint

---

## 📖 Documentación Completa

Para documentación detallada: [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)

---

*¡Listo para empezar! 🎉* 