# Guía Completa de Integración - BusinessProSuite API

**Versión**: V0.2  
**Audiencia Objetivo**: Ingenieros de Software, Desarrolladores Frontend, Especialistas en Integración  
**Tiempo de Lectura Estimado**: 45-60 minutos  

## 🎯 Propósito

Esta guía proporciona **información completa** para que los ingenieros desarrollen su software de capa visual usando la API de BusinessProSuite. Incluye autenticación, todos los endpoints disponibles, ejemplos de código, manejo de errores y mejores prácticas.

---

## 🚀 Inicio Rápido

### **1. URL Base y Ambiente**
```
Desarrollo: http://localhost:8080
Producción: https://api.businessprosuite.com (cuando esté desplegado)
```

### **2. Versionado de API**
```
Ruta Base: /api/v1/
Ejemplo: http://localhost:8080/api/v1/auth/login
```

### **3. Documentación Interactiva**
```
Swagger UI: http://localhost:8080/swagger-ui/index.html
Especificación OpenAPI: http://localhost:8080/v3/api-docs
```

---

## 🔐 Autenticación y Seguridad

### **Autenticación Basada en Token JWT**

#### **Paso 1: Registrar un Nuevo Usuario**
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "juan_perez",
  "email": "juan@empresa.com",
  "password": "ClaveSegura123!",
  "fullName": "Juan Pérez",
  "companyName": "Corporación Acme",
  "companyEmail": "info@acme.com",
  "companyAddress": "123 Calle de Negocios",
  "companyPhone": "+1-555-0123",
  "countryCode": "US"
}
```

**Respuesta (201 Created):**
```json
{
  "message": "Usuario registrado exitosamente",
  "user": {
    "id": 1,
    "username": "juan_perez",
    "email": "juan@empresa.com",
    "fullName": "Juan Pérez"
  },
  "company": {
    "id": 1,
    "name": "Corporación Acme",
    "email": "info@acme.com"
  }
}
```

#### **Paso 2: Login y Obtener Token JWT**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "juan_perez",
  "password": "ClaveSegura123!"
}
```

**Respuesta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqdWFuX3BlcmV6IiwiZXhwIjoxNjg3Mzg0ODAwfQ.ejemplo_signature",
  "type": "Bearer",
  "username": "juan_perez",
  "email": "juan@empresa.com",
  "authorities": ["ROLE_USER"],
  "expiresIn": 86400
}
```

#### **Paso 3: Usar Token en Requests Posteriores**
```http
GET /api/v1/companies
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### **Ejemplos de Código**

#### **JavaScript/Fetch**
```javascript
class BusinessProSuiteAPI {
  constructor(baseUrl = 'http://localhost:8080') {
    this.baseUrl = baseUrl;
    this.token = localStorage.getItem('jwt_token');
  }

  async login(username, password) {
    const response = await fetch(`${this.baseUrl}/api/v1/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    if (response.ok) {
      const data = await response.json();
      this.token = data.token;
      localStorage.setItem('jwt_token', this.token);
      return data;
    } else {
      throw new Error('Login falló');
    }
  }

  async apiCall(endpoint, method = 'GET', body = null) {
    const headers = {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    };

    const config = {
      method,
      headers
    };

    if (body) {
      config.body = JSON.stringify(body);
    }

    const response = await fetch(`${this.baseUrl}/api/v1${endpoint}`, config);
    
    if (!response.ok) {
      throw new Error(`Llamada API falló: ${response.status}`);
    }

    return response.json();
  }
}

// Uso
const api = new BusinessProSuiteAPI();
await api.login('juan_perez', 'ClaveSegura123!');
const companies = await api.apiCall('/companies');
```

#### **Python/Requests**
```python
import requests
import json

class BusinessProSuiteAPI:
    def __init__(self, base_url='http://localhost:8080'):
        self.base_url = base_url
        self.token = None
        self.session = requests.Session()

    def login(self, username, password):
        url = f"{self.base_url}/api/v1/auth/login"
        data = {"username": username, "password": password}
        
        response = self.session.post(url, json=data)
        response.raise_for_status()
        
        result = response.json()
        self.token = result['token']
        self.session.headers.update({
            'Authorization': f"Bearer {self.token}"
        })
        return result

    def api_call(self, endpoint, method='GET', data=None):
        url = f"{self.base_url}/api/v1{endpoint}"
        
        if method == 'GET':
            response = self.session.get(url)
        elif method == 'POST':
            response = self.session.post(url, json=data)
        elif method == 'PUT':
            response = self.session.put(url, json=data)
        elif method == 'DELETE':
            response = self.session.delete(url)
        
        response.raise_for_status()
        return response.json()

# Uso
api = BusinessProSuiteAPI()
api.login('juan_perez', 'ClaveSegura123!')
companies = api.api_call('/companies')
```

#### **Java/Spring Boot**
```java
@Component
public class BusinessProSuiteApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private String token;

    public BusinessProSuiteApiClient(@Value("${api.base-url:http://localhost:8080}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public LoginResponse login(String username, String password) {
        String url = baseUrl + "/api/v1/auth/login";
        LoginRequest request = new LoginRequest(username, password);
        
        LoginResponse response = restTemplate.postForObject(url, request, LoginResponse.class);
        this.token = response.getToken();
        
        return response;
    }

    public <T> T apiCall(String endpoint, HttpMethod method, Object body, Class<T> responseType) {
        String url = baseUrl + "/api/v1" + endpoint;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
        return response.getBody();
    }
}
```

---

## 👥 APIs de Gestión de Usuarios

### **Gestión de Usuarios de Seguridad**

#### **Crear Usuario de Seguridad**
```http
POST /api/v1/security-users
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "empleado1",
  "password": "ClaveEmpleado123!",
  "email": "empleado@empresa.com",
  "available": 1,
  "active": 1,
  "mfaEnabled": 0,
  "roleId": 2,
  "companyId": 1
}
```

#### **Obtener Todos los Usuarios de Seguridad**
```http
GET /api/v1/security-users
Authorization: Bearer {token}
```

#### **Obtener Usuario de Seguridad por ID**
```http
GET /api/v1/security-users/{id}
Authorization: Bearer {token}
```

#### **Actualizar Usuario de Seguridad**
```http
PUT /api/v1/security-users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "empleado_actualizado",
  "email": "actualizado@empresa.com",
  "active": 1,
  "available": 1
}
```

#### **Eliminar Usuario de Seguridad**
```http
DELETE /api/v1/security-users/{id}
Authorization: Bearer {token}
```

---

## 🏢 APIs de Gestión de Empresas

### **Operaciones CRUD de Empresas**

#### **Crear Empresa**
```http
POST /api/v1/companies
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "Nueva Empresa LLC",
  "address": "456 Avenida de Innovación",
  "phone": "+1-555-9876",
  "email": "info@nuevaempresa.com",
  "taxId": "12-3456789",
  "countryCode": "US"
}
```

#### **Obtener Todas las Empresas**
```http
GET /api/v1/companies
Authorization: Bearer {token}
```

#### **Obtener Empresa por ID**
```http
GET /api/v1/companies/{id}
Authorization: Bearer {token}
```

#### **Actualizar Empresa**
```http
PUT /api/v1/companies/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Nombre de Empresa Actualizado",
  "address": "Nueva Dirección 789",
  "phone": "+1-555-5555",
  "email": "nuevo@empresa.com"
}
```

#### **Eliminar Empresa**
```http
DELETE /api/v1/companies/{id}
Authorization: Bearer {token}
```

---

## 🏪 APIs de Gestión de Clientes

### **Operaciones CRUD de Clientes**

#### **Crear Cliente**
```http
POST /api/v1/customers
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "Cliente Premium Inc.",
  "email": "cliente@premium.com",
  "phone": "+1-555-1111",
  "address": "123 Plaza del Cliente",
  "taxId": "98-7654321",
  "companyId": 1,
  "countryCode": "US"
}
```

#### **Obtener Todos los Clientes**
```http
GET /api/v1/customers
Authorization: Bearer {token}
```

#### **Obtener Cliente por ID**
```http
GET /api/v1/customers/{id}
Authorization: Bearer {token}
```

#### **Actualizar Cliente**
```http
PUT /api/v1/customers/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Nombre de Cliente Actualizado",
  "email": "actualizado@cliente.com",
  "phone": "+1-555-2222"
}
```

---

## ⚙️ APIs de Administración del Sistema

### **Health Check**
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

### **Información de la Aplicación**
```http
GET /actuator/info
```

---

## 🚨 Manejo de Errores

### **Formato de Respuesta de Error Estándar**
```json
{
  "timestamp": "2025-06-03T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validación falló",
  "path": "/api/v1/companies",
  "errors": [
    {
      "field": "name",
      "message": "El nombre de la empresa no puede estar en blanco"
    },
    {
      "field": "email",
      "message": "Formato de email inválido"
    }
  ]
}
```

### **Códigos de Estado HTTP**

| Código | Descripción | Uso |
|--------|-------------|-----|
| 200 | OK | Operaciones GET, PUT exitosas |
| 201 | Created | Operaciones POST exitosas |
| 204 | No Content | Operaciones DELETE exitosas |
| 400 | Bad Request | Datos de request inválidos |
| 401 | Unauthorized | Token faltante o inválido |
| 403 | Forbidden | Permisos insuficientes |
| 404 | Not Found | El recurso no existe |
| 409 | Conflict | Recurso duplicado |
| 500 | Internal Server Error | Error del servidor |

### **Manejo de Errores en Código**

#### **Ejemplo JavaScript**
```javascript
async function handleApiCall() {
  try {
    const response = await api.apiCall('/companies');
    return response;
  } catch (error) {
    if (error.message.includes('401')) {
      // Token expirado, redirigir al login
      window.location.href = '/login';
    } else if (error.message.includes('400')) {
      // Error de validación, mostrar errores del formulario
      displayValidationErrors(error.details);
    } else {
      // Manejo genérico de errores
      showErrorMessage('Ocurrió un error inesperado');
    }
  }
}
```

#### **Ejemplo Python**
```python
def handle_api_error(response):
    if response.status_code == 401:
        # Token expirado
        return redirect_to_login()
    elif response.status_code == 400:
        # Errores de validación
        errors = response.json().get('errors', [])
        return display_validation_errors(errors)
    elif response.status_code == 404:
        # Recurso no encontrado
        return show_not_found_message()
    else:
        # Error genérico
        return show_error_message("Ocurrió un error inesperado")
```

---

## 📊 Modelos de Datos y DTOs

### **Modelos de Usuario**
```typescript
interface LoginRequest {
  username: string;
  password: string;
}

interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  fullName: string;
  companyName: string;
  companyEmail: string;
  companyAddress: string;
  companyPhone: string;
  countryCode: string;
}

interface LoginResponse {
  token: string;
  type: string;
  username: string;
  email: string;
  authorities: string[];
  expiresIn: number;
}
```

### **Modelos de Empresa**
```typescript
interface Company {
  id: number;
  configCompanyId: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  taxId: string;
  countryCode: string;
  createdAt: string;
  updatedAt: string;
}

interface CreateCompanyRequest {
  configCompanyId: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  taxId: string;
  countryCode: string;
}
```

### **Modelos de Cliente**
```typescript
interface Customer {
  id: number;
  configCompanyId: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  taxId: string;
  companyId: number;
  countryCode: string;
  createdAt: string;
  updatedAt: string;
}
```

---

## 🔄 Paginación y Filtrado

### **Implementación Futura (V0.3)**
```http
GET /api/v1/companies?page=0&size=20&sort=name,asc
GET /api/v1/customers?search=premium&status=active
```

---

## 🎭 Probando tu Integración

### **Probar con cURL**
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"juan_perez","password":"ClaveSegura123!"}'

# Guardar token de la respuesta
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Obtener empresas
curl -X GET http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $TOKEN"

# Crear empresa
curl -X POST http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "configCompanyId": 1,
    "name": "Empresa de Prueba",
    "address": "123 Calle de Prueba",
    "phone": "+1-555-0000",
    "email": "prueba@empresa.com",
    "taxId": "00-0000000",
    "countryCode": "US"
  }'
```

### **Usando Swagger UI**
1. Abrir http://localhost:8080/swagger-ui/index.html
2. Hacer clic en el botón "Authorize"
3. Ingresar: `Bearer tu-jwt-token-aquí`
4. Probar cualquier endpoint interactivamente

---

## 🚀 Mejores Prácticas

### **1. Gestión de Tokens**
- Almacenar tokens JWT de forma segura (no en localStorage para apps sensibles)
- Implementar lógica de refresh de tokens
- Manejar expiración de tokens graciosamente
- Limpiar tokens al cerrar sesión

### **2. Manejo de Errores**
- Siempre verificar códigos de estado HTTP
- Parsear cuerpos de respuesta de error para detalles
- Implementar lógica de retry para errores transitorios
- Proporcionar mensajes de error amigables al usuario

### **3. Performance**
- Implementar cache de request/response
- Usar paginación para conjuntos de datos grandes
- Minimizar llamadas API con operaciones batch
- Implementar estados de carga en UI

### **4. Seguridad**
- Nunca loggear datos sensibles (contraseñas, tokens)
- Validar todas las entradas de usuario
- Usar HTTPS en producción
- Implementar limitación de rate en tu cliente

### **5. Versionado de API**
- Siempre usar endpoints versionados (/api/v1/)
- Planificar para evolución de API
- Probar con nuevas versiones antes de actualizar

---

## 📱 Ejemplos Específicos de Framework

### **Integración React/Next.js**
```jsx
import { useState, useEffect } from 'react';

const CompanyList = () => {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCompanies = async () => {
      try {
        const api = new BusinessProSuiteAPI();
        const data = await api.apiCall('/companies');
        setCompanies(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCompanies();
  }, []);

  if (loading) return <div>Cargando...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Empresas</h1>
      {companies.map(company => (
        <div key={company.id}>
          <h3>{company.name}</h3>
          <p>{company.email}</p>
        </div>
      ))}
    </div>
  );
};
```

### **Integración Vue.js**
```vue
<template>
  <div>
    <h1>Empresas</h1>
    <div v-if="loading">Cargando...</div>
    <div v-else-if="error">Error: {{ error }}</div>
    <div v-else>
      <div v-for="company in companies" :key="company.id">
        <h3>{{ company.name }}</h3>
        <p>{{ company.email }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { BusinessProSuiteAPI } from './api';

export default {
  data() {
    return {
      companies: [],
      loading: true,
      error: null
    };
  },
  async mounted() {
    try {
      const api = new BusinessProSuiteAPI();
      this.companies = await api.apiCall('/companies');
    } catch (error) {
      this.error = error.message;
    } finally {
      this.loading = false;
    }
  }
};
</script>
```

---

## 📞 Soporte y Recursos

### **Enlaces de Documentación**
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Especificación OpenAPI**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health

### **Problemas Comunes y Soluciones**
1. **401 Unauthorized**: Verificar formato y expiración del token
2. **Problemas de CORS**: Asegurar configuración CORS apropiada
3. **Errores de Validación**: Verificar formato del cuerpo del request y campos requeridos
4. **Limitación de Rate**: Implementar backoff exponencial

### **Información de Contacto**
- **Email**: contacto@businessprosuite.com
- **Documentación**: Guías completas en carpeta `/docs`
- **Reporte de Problemas**: Incluir endpoint API, request/response y detalles del error

---

**Estado**: ✅ **Guía Completa de Integración Lista para Desarrollo**  
**Última Actualización**: 3 de Junio de 2025  
**Versión de API**: V0.2 