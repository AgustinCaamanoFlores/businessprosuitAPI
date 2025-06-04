# 🌐 Guía de Conexión a BusinessProSuite API

## 📋 Tabla de Contenidos

- [Configuración Inicial](#configuración-inicial)
- [Autenticación](#autenticación)
- [Ejemplos por Lenguaje](#ejemplos-por-lenguaje)
- [Herramientas de Testing](#herramientas-de-testing)
- [Librerías y SDKs](#librerías-y-sdks)
- [Manejo de Errores](#manejo-de-errores)
- [Casos de Uso Completos](#casos-de-uso-completos)
- [Mejores Prácticas](#mejores-prácticas)

---

## ⚙️ Configuración Inicial

### URL Base de la API
```
Desarrollo: http://localhost:8080
Producción: https://api.businessprosuite.com
```

### Headers Requeridos
```http
Content-Type: application/json
Accept: application/json
Authorization: Bearer {jwt_token}  # Para endpoints protegidos
```

### Endpoints Públicos (Sin autenticación)
- `POST /auth/register` - Registro de usuario
- `POST /auth/login` - Login
- `GET /auth/health` - Estado del servicio
- `GET /auth/check-username` - Verificar disponibilidad username
- `GET /auth/check-email` - Verificar disponibilidad email

---

## 🔐 Autenticación

### 1. Registro Inicial
```http
POST /auth/register
Content-Type: application/json

{
  "username": "mi_usuario",
  "email": "usuario@empresa.com",
  "password": "MiPassword123!",
  "fullName": "Mi Nombre Completo",
  "companyName": "Mi Empresa S.A.",
  "companyEmail": "contacto@empresa.com",
  "companyAddress": "Dirección de la empresa",
  "companyPhone": "+34123456789",
  "countryCode": "ES"
}
```

### 2. Obtener Token de Acceso
```http
POST /auth/login
Content-Type: application/json

{
  "username": "mi_usuario",
  "password": "MiPassword123!"
}
```

### 3. Usar Token en Requests
```http
GET /api/companies
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 💻 Ejemplos por Lenguaje

### 🟨 JavaScript / Node.js

#### Con Fetch API (Navegador/Node.js)
```javascript
class BusinessProSuiteAPI {
    constructor(baseUrl = 'http://localhost:8080') {
        this.baseUrl = baseUrl;
        this.token = null;
    }

    async register(userData) {
        const response = await fetch(`${this.baseUrl}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        
        if (!response.ok) {
            throw new Error(`Registration failed: ${response.status}`);
        }
        
        return await response.json();
    }

    async login(username, password) {
        const response = await fetch(`${this.baseUrl}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error(`Login failed: ${response.status}`);
        }

        const data = await response.json();
        this.token = data.token;
        return data;
    }

    async getCompanies() {
        const response = await fetch(`${this.baseUrl}/api/companies`, {
            headers: {
                'Authorization': `Bearer ${this.token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch companies: ${response.status}`);
        }

        return await response.json();
    }

    async createCompany(companyData) {
        const response = await fetch(`${this.baseUrl}/api/companies`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${this.token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(companyData)
        });

        if (!response.ok) {
            throw new Error(`Failed to create company: ${response.status}`);
        }

        return await response.json();
    }
}

// Uso
const api = new BusinessProSuiteAPI();

// Registro
await api.register({
    username: "admin",
    email: "admin@empresa.com",
    password: "Password123!",
    fullName: "Administrador",
    companyName: "Mi Empresa",
    companyEmail: "contacto@empresa.com",
    companyAddress: "Calle Principal 123",
    companyPhone: "+34123456789",
    countryCode: "ES"
});

// Login
await api.login("admin", "Password123!");

// Usar API
const companies = await api.getCompanies();
console.log(companies);
```

#### Con Axios
```javascript
const axios = require('axios');

class BusinessProSuiteClient {
    constructor(baseUrl = 'http://localhost:8080') {
        this.client = axios.create({
            baseURL: baseUrl,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        // Interceptor para añadir token automáticamente
        this.client.interceptors.request.use(
            config => {
                if (this.token) {
                    config.headers.Authorization = `Bearer ${this.token}`;
                }
                return config;
            }
        );
    }

    async login(username, password) {
        try {
            const response = await this.client.post('/auth/login', {
                username,
                password
            });
            
            this.token = response.data.token;
            return response.data;
        } catch (error) {
            throw new Error(`Login failed: ${error.response?.data?.message || error.message}`);
        }
    }

    async getCompanies() {
        const response = await this.client.get('/api/companies');
        return response.data;
    }
}
```

### 🐍 Python

#### Con requests
```python
import requests
import json

class BusinessProSuiteAPI:
    def __init__(self, base_url="http://localhost:8080"):
        self.base_url = base_url
        self.token = None
        self.session = requests.Session()
        self.session.headers.update({
            'Content-Type': 'application/json'
        })

    def register(self, user_data):
        """Registrar nuevo usuario y empresa"""
        url = f"{self.base_url}/auth/register"
        response = self.session.post(url, json=user_data)
        response.raise_for_status()
        return response.json()

    def login(self, username, password):
        """Iniciar sesión y obtener token"""
        url = f"{self.base_url}/auth/login"
        data = {
            "username": username,
            "password": password
        }
        response = self.session.post(url, json=data)
        response.raise_for_status()
        
        result = response.json()
        self.token = result['token']
        
        # Actualizar headers con token
        self.session.headers.update({
            'Authorization': f"Bearer {self.token}"
        })
        
        return result

    def get_companies(self):
        """Obtener lista de empresas"""
        url = f"{self.base_url}/api/companies"
        response = self.session.get(url)
        response.raise_for_status()
        return response.json()

    def create_company(self, company_data):
        """Crear nueva empresa"""
        url = f"{self.base_url}/api/companies"
        response = self.session.post(url, json=company_data)
        response.raise_for_status()
        return response.json()

# Ejemplo de uso
api = BusinessProSuiteAPI()

# Registro
user_data = {
    "username": "admin_python",
    "email": "admin@python-empresa.com",
    "password": "PythonPass123!",
    "fullName": "Python Administrator",
    "companyName": "Python Company Ltd.",
    "companyEmail": "contacto@python-empresa.com",
    "companyAddress": "Python Street 123",
    "companyPhone": "+34987654321",
    "countryCode": "ES"
}

try:
    # Registro
    register_result = api.register(user_data)
    print("Registro exitoso:", register_result)
    
    # Login
    login_result = api.login("admin_python", "PythonPass123!")
    print("Login exitoso:", login_result)
    
    # Obtener empresas
    companies = api.get_companies()
    print("Empresas:", companies)
    
except requests.exceptions.RequestException as e:
    print(f"Error de API: {e}")
```

### ☕ Java

#### Con OkHttp y Jackson
```java
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class BusinessProSuiteClient {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private String token;

    public BusinessProSuiteClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode register(Object userData) throws Exception {
        String json = objectMapper.writeValueAsString(userData);
        
        RequestBody body = RequestBody.create(
            json, MediaType.get("application/json")
        );
        
        Request request = new Request.Builder()
            .url(baseUrl + "/auth/register")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Registration failed: " + response.code());
            }
            return objectMapper.readTree(response.body().string());
        }
    }

    public JsonNode login(String username, String password) throws Exception {
        String json = String.format(
            "{\"username\":\"%s\",\"password\":\"%s\"}", 
            username, password
        );
        
        RequestBody body = RequestBody.create(
            json, MediaType.get("application/json")
        );
        
        Request request = new Request.Builder()
            .url(baseUrl + "/auth/login")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Login failed: " + response.code());
            }
            
            JsonNode result = objectMapper.readTree(response.body().string());
            this.token = result.get("token").asText();
            return result;
        }
    }

    public JsonNode getCompanies() throws Exception {
        Request request = new Request.Builder()
            .url(baseUrl + "/api/companies")
            .addHeader("Authorization", "Bearer " + token)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to get companies: " + response.code());
            }
            return objectMapper.readTree(response.body().string());
        }
    }
}

// Uso
BusinessProSuiteClient api = new BusinessProSuiteClient("http://localhost:8080");

// Login
JsonNode loginResult = api.login("admin", "Password123!");
System.out.println("Login exitoso: " + loginResult);

// Obtener empresas
JsonNode companies = api.getCompanies();
System.out.println("Empresas: " + companies);
```

### 🐘 PHP

```php
<?php

class BusinessProSuiteAPI {
    private $baseUrl;
    private $token;

    public function __construct($baseUrl = 'http://localhost:8080') {
        $this->baseUrl = $baseUrl;
    }

    public function register($userData) {
        $url = $this->baseUrl . '/auth/register';
        
        $options = [
            'http' => [
                'header' => "Content-Type: application/json\r\n",
                'method' => 'POST',
                'content' => json_encode($userData)
            ]
        ];

        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        
        if ($result === FALSE) {
            throw new Exception('Registration failed');
        }
        
        return json_decode($result, true);
    }

    public function login($username, $password) {
        $url = $this->baseUrl . '/auth/login';
        
        $data = [
            'username' => $username,
            'password' => $password
        ];

        $options = [
            'http' => [
                'header' => "Content-Type: application/json\r\n",
                'method' => 'POST',
                'content' => json_encode($data)
            ]
        ];

        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        
        if ($result === FALSE) {
            throw new Exception('Login failed');
        }
        
        $response = json_decode($result, true);
        $this->token = $response['token'];
        
        return $response;
    }

    public function getCompanies() {
        $url = $this->baseUrl . '/api/companies';
        
        $options = [
            'http' => [
                'header' => "Content-Type: application/json\r\n" .
                           "Authorization: Bearer " . $this->token . "\r\n",
                'method' => 'GET'
            ]
        ];

        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        
        if ($result === FALSE) {
            throw new Exception('Failed to get companies');
        }
        
        return json_decode($result, true);
    }
}

// Uso
$api = new BusinessProSuiteAPI();

try {
    // Login
    $loginResult = $api->login('admin', 'Password123!');
    echo "Login exitoso: " . json_encode($loginResult) . "\n";
    
    // Obtener empresas
    $companies = $api->getCompanies();
    echo "Empresas: " . json_encode($companies) . "\n";
    
} catch (Exception $e) {
    echo "Error: " . $e->getMessage() . "\n";
}
?>
```

### 💎 Ruby

```ruby
require 'net/http'
require 'json'
require 'uri'

class BusinessProSuiteAPI
  def initialize(base_url = 'http://localhost:8080')
    @base_url = base_url
    @token = nil
  end

  def register(user_data)
    uri = URI("#{@base_url}/auth/register")
    
    http = Net::HTTP.new(uri.host, uri.port)
    request = Net::HTTP::Post.new(uri)
    request['Content-Type'] = 'application/json'
    request.body = user_data.to_json

    response = http.request(request)
    
    unless response.is_a?(Net::HTTPSuccess)
      raise "Registration failed: #{response.code}"
    end
    
    JSON.parse(response.body)
  end

  def login(username, password)
    uri = URI("#{@base_url}/auth/login")
    
    http = Net::HTTP.new(uri.host, uri.port)
    request = Net::HTTP::Post.new(uri)
    request['Content-Type'] = 'application/json'
    request.body = {
      username: username,
      password: password
    }.to_json

    response = http.request(request)
    
    unless response.is_a?(Net::HTTPSuccess)
      raise "Login failed: #{response.code}"
    end
    
    result = JSON.parse(response.body)
    @token = result['token']
    result
  end

  def get_companies
    uri = URI("#{@base_url}/api/companies")
    
    http = Net::HTTP.new(uri.host, uri.port)
    request = Net::HTTP::Get.new(uri)
    request['Authorization'] = "Bearer #{@token}"
    request['Content-Type'] = 'application/json'

    response = http.request(request)
    
    unless response.is_a?(Net::HTTPSuccess)
      raise "Failed to get companies: #{response.code}"
    end
    
    JSON.parse(response.body)
  end
end

# Uso
api = BusinessProSuiteAPI.new

begin
  # Login
  login_result = api.login('admin', 'Password123!')
  puts "Login exitoso: #{login_result}"
  
  # Obtener empresas
  companies = api.get_companies
  puts "Empresas: #{companies}"
  
rescue => e
  puts "Error: #{e.message}"
end
```

---

## 🛠️ Herramientas de Testing

### cURL (Terminal)
```bash
# Registro
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "email": "test@example.com",
    "password": "TestPass123!",
    "fullName": "Test User",
    "companyName": "Test Company",
    "companyEmail": "contact@test.com",
    "companyAddress": "Test Address 123",
    "companyPhone": "+34123456789",
    "countryCode": "ES"
  }'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "password": "TestPass123!"
  }'

# Usar API con token
curl -X GET http://localhost:8080/api/companies \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Postman Collection
```json
{
  "info": {
    "name": "BusinessProSuite API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080"
    },
    {
      "key": "token",
      "value": ""
    }
  ],
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "url": "{{baseUrl}}/auth/register",
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"postman_user\",\n  \"email\": \"postman@example.com\",\n  \"password\": \"PostmanPass123!\",\n  \"fullName\": \"Postman User\",\n  \"companyName\": \"Postman Company\",\n  \"companyEmail\": \"contact@postman.com\",\n  \"companyAddress\": \"Postman Street 123\",\n  \"companyPhone\": \"+34123456789\",\n  \"countryCode\": \"ES\"\n}"
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "url": "{{baseUrl}}/auth/login",
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"postman_user\",\n  \"password\": \"PostmanPass123!\"\n}"
            }
          },
          "event": [
            {
              "listen": "test",
              "script": {
                "exec": [
                  "if (pm.response.code === 200) {",
                  "    const response = pm.response.json();",
                  "    pm.collectionVariables.set('token', response.token);",
                  "}"
                ]
              }
            }
          ]
        }
      ]
    },
    {
      "name": "Companies",
      "item": [
        {
          "name": "Get Companies",
          "request": {
            "method": "GET",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": "{{baseUrl}}/api/companies"
          }
        }
      ]
    }
  ]
}
```

### Insomnia Workspace
```json
{
  "name": "BusinessProSuite API",
  "requests": [
    {
      "name": "Register",
      "method": "POST",
      "url": "{{ base_url }}/auth/register",
      "headers": [
        {
          "name": "Content-Type",
          "value": "application/json"
        }
      ],
      "body": {
        "username": "insomnia_user",
        "email": "insomnia@example.com",
        "password": "InsomniaPass123!",
        "fullName": "Insomnia User",
        "companyName": "Insomnia Company",
        "companyEmail": "contact@insomnia.com",
        "companyAddress": "Insomnia Avenue 123",
        "companyPhone": "+34123456789",
        "countryCode": "ES"
      }
    }
  ],
  "environment": {
    "base_url": "http://localhost:8080",
    "token": ""
  }
}
```

---

## 📚 Librerías y SDKs Recomendados

### JavaScript/TypeScript
```bash
# Para Node.js
npm install axios
npm install node-fetch
npm install @types/node  # Para TypeScript

# Para navegador
# Usar fetch API nativo o axios vía CDN
```

### Python
```bash
pip install requests
pip install httpx  # Alternativa moderna a requests
pip install aiohttp  # Para async/await
```

### Java
```bash
# Maven
mvn dependency:add -DgroupId=com.squareup.okhttp3 -DartifactId=okhttp -Dversion=4.12.0
mvn dependency:add -DgroupId=com.fasterxml.jackson.core -DartifactId=jackson-databind -Dversion=2.16.0

# Gradle
implementation 'com.squareup.okhttp3:okhttp:4.12.0'
implementation 'com.fasterxml.jackson.core:jackson-databind:2.16.0'
```

**Maven dependencies (pom.xml):**
```xml
<!-- Maven dependencies -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.0</version>
</dependency>
```

### PHP
```bash
composer require guzzlehttp/guzzle  # Cliente HTTP avanzado
composer require ext-curl  # cURL extension
```

---

## 🚨 Manejo de Errores

### Códigos de Estado HTTP
```javascript
const handleApiResponse = async (response) => {
    switch (response.status) {
        case 200:
        case 201:
            return await response.json();
        
        case 400:
            throw new Error('Datos inválidos en la solicitud');
        
        case 401:
            throw new Error('Token de acceso inválido o expirado');
        
        case 403:
            throw new Error('Permisos insuficientes');
        
        case 404:
            throw new Error('Recurso no encontrado');
        
        case 409:
            throw new Error('Conflicto - recurso ya existe');
        
        case 500:
            throw new Error('Error interno del servidor');
        
        default:
            throw new Error(`Error inesperado: ${response.status}`);
    }
};
```

### Reintentos y Timeout
```javascript
class APIClient {
    constructor() {
        this.maxRetries = 3;
        this.timeout = 10000; // 10 segundos
    }

    async makeRequest(url, options, retries = 0) {
        try {
            const controller = new AbortController();
            const timeoutId = setTimeout(() => controller.abort(), this.timeout);
            
            const response = await fetch(url, {
                ...options,
                signal: controller.signal
            });
            
            clearTimeout(timeoutId);
            
            if (!response.ok && retries < this.maxRetries) {
                await new Promise(resolve => setTimeout(resolve, 1000 * (retries + 1)));
                return this.makeRequest(url, options, retries + 1);
            }
            
            return response;
        } catch (error) {
            if (retries < this.maxRetries) {
                await new Promise(resolve => setTimeout(resolve, 1000 * (retries + 1)));
                return this.makeRequest(url, options, retries + 1);
            }
            throw error;
        }
    }
}
```

---

## 📖 Casos de Uso Completos

### 1. Registro y Configuración Inicial
```javascript
async function setupNewCompany() {
    const api = new BusinessProSuiteAPI();
    
    try {
        // 1. Registrar empresa y usuario administrador
        const registerData = {
            username: "admin_empresa",
            email: "admin@nuevaempresa.com",
            password: "SecurePass123!",
            fullName: "Administrator",
            companyName: "Nueva Empresa Tech S.L.",
            companyEmail: "contacto@nuevaempresa.com",
            companyAddress: "Calle Tecnología 123",
            companyPhone: "+34123456789",
            countryCode: "ES"
        };
        
        const registerResult = await api.register(registerData);
        console.log('✅ Empresa registrada:', registerResult);
        
        // 2. Login automático
        const loginResult = await api.login(registerData.username, registerData.password);
        console.log('✅ Login exitoso:', loginResult);
        
        // 3. Crear usuarios adicionales
        const employee = await api.createUser({
            name: "empleado1",
            password: "EmpleadoPass123!",
            email: "empleado@nuevaempresa.com",
            available: 1,
            active: 1,
            roleId: 2,
            companyId: registerResult.company.id
        });
        console.log('✅ Empleado creado:', employee);
        
        // 4. Crear primer cliente
        const customer = await api.createCustomer({
            name: "Cliente Premium S.A.",
            email: "cliente@premium.com",
            phone: "+34111222333",
            address: "Plaza del Cliente 12",
            companyId: registerResult.company.id,
            countryCode: "ES"
        });
        console.log('✅ Cliente creado:', customer);
        
        return {
            company: registerResult.company,
            token: loginResult.token,
            employee,
            customer
        };
        
    } catch (error) {
        console.error('❌ Error en setup:', error.message);
        throw error;
    }
}
```

### 2. Operaciones Diarias
```python
def daily_operations(api):
    """Ejemplo de operaciones diarias típicas"""
    
    # Obtener resumen del día
    companies = api.get_companies()
    users = api.get_users()
    customers = api.get_customers()
    
    print(f"📊 Resumen del día:")
    print(f"   Empresas: {len(companies)}")
    print(f"   Usuarios: {len(users)}")
    print(f"   Clientes: {len(customers)}")
    
    # Crear nueva factura
    new_invoice = api.create_invoice({
        "customerId": customers[0]['id'],
        "number": f"FAC-{datetime.now().strftime('%Y%m%d')}-001",
        "date": datetime.now().isoformat(),
        "dueDate": (datetime.now() + timedelta(days=30)).isoformat(),
        "subtotal": 1000.00,
        "taxAmount": 210.00,
        "total": 1210.00,
        "status": "PENDING"
    })
    
    print(f"✅ Factura creada: {new_invoice['number']}")
    
    return new_invoice
```

---

## 🏆 Mejores Prácticas

### 1. Gestión de Tokens
```javascript
class TokenManager {
    constructor() {
        this.token = localStorage.getItem('bps_token');
        this.refreshToken = localStorage.getItem('bps_refresh_token');
    }

    setTokens(token, refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
        localStorage.setItem('bps_token', token);
        localStorage.setItem('bps_refresh_token', refreshToken);
    }

    clearTokens() {
        this.token = null;
        this.refreshToken = null;
        localStorage.removeItem('bps_token');
        localStorage.removeItem('bps_refresh_token');
    }

    isTokenExpired() {
        if (!this.token) return true;
        
        try {
            const payload = JSON.parse(atob(this.token.split('.')[1]));
            return Date.now() >= payload.exp * 1000;
        } catch {
            return true;
        }
    }
}
```

### 2. Rate Limiting y Caching
```javascript
class RateLimitedAPI {
    constructor() {
        this.requestQueue = [];
        this.processing = false;
        this.requestsPerSecond = 10;
        this.cache = new Map();
        this.cacheTimeout = 5 * 60 * 1000; // 5 minutos
    }

    async makeRequest(url, options) {
        // Verificar cache
        const cacheKey = `${url}_${JSON.stringify(options)}`;
        const cached = this.cache.get(cacheKey);
        
        if (cached && Date.now() - cached.timestamp < this.cacheTimeout) {
            return cached.data;
        }

        // Añadir a cola de requests
        return new Promise((resolve, reject) => {
            this.requestQueue.push({ url, options, resolve, reject });
            this.processQueue();
        });
    }

    async processQueue() {
        if (this.processing || this.requestQueue.length === 0) return;
        
        this.processing = true;
        
        while (this.requestQueue.length > 0) {
            const batch = this.requestQueue.splice(0, this.requestsPerSecond);
            
            const promises = batch.map(async ({ url, options, resolve, reject }) => {
                try {
                    const response = await fetch(url, options);
                    const data = await response.json();
                    
                    // Guardar en cache
                    const cacheKey = `${url}_${JSON.stringify(options)}`;
                    this.cache.set(cacheKey, { data, timestamp: Date.now() });
                    
                    resolve(data);
                } catch (error) {
                    reject(error);
                }
            });
            
            await Promise.all(promises);
            
            // Esperar 1 segundo antes del siguiente batch
            if (this.requestQueue.length > 0) {
                await new Promise(resolve => setTimeout(resolve, 1000));
            }
        }
        
        this.processing = false;
    }
}
```

### 3. Configuración por Entorno
```javascript
const config = {
    development: {
        baseUrl: 'http://localhost:8080',
        timeout: 10000,
        retries: 3
    },
    staging: {
        baseUrl: 'https://staging-api.businessprosuite.com',
        timeout: 15000,
        retries: 5
    },
    production: {
        baseUrl: 'https://api.businessprosuite.com',
        timeout: 20000,
        retries: 5
    }
};

const ENV = process.env.NODE_ENV || 'development';
const API_CONFIG = config[ENV];
```

---

## 🔍 Debugging y Monitoring

### Logging de Requests
```javascript
class LoggedAPIClient {
    constructor() {
        this.requestId = 0;
    }

    async makeRequest(url, options) {
        const requestId = ++this.requestId;
        const startTime = Date.now();
        
        console.log(`📤 [${requestId}] ${options.method || 'GET'} ${url}`, {
            headers: options.headers,
            body: options.body
        });

        try {
            const response = await fetch(url, options);
            const duration = Date.now() - startTime;
            
            console.log(`📥 [${requestId}] ${response.status} ${response.statusText} (${duration}ms)`);
            
            return response;
        } catch (error) {
            const duration = Date.now() - startTime;
            console.error(`❌ [${requestId}] Error after ${duration}ms:`, error);
            throw error;
        }
    }
}
```

---

## 📞 Soporte

Para soporte técnico:
- 📖 **Documentación**: [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)
- 🚀 **Inicio Rápido**: [QUICK_START.md](./QUICK_START.md)
- 🧪 **Script de Pruebas**: `./test-auth-api.sh`
- 📧 **Email**: soporte@businessprosuite.com
- 🐛 **Issues**: GitHub Issues del repositorio

---

*Última actualización: Enero 2024* 