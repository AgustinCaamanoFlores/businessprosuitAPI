# Complete Integration Guide - BusinessProSuite API

**Version**: V0.2  
**Target Audience**: Software Engineers, Frontend Developers, Integration Specialists  
**Estimated Reading Time**: 45-60 minutes  

## 🎯 Purpose

This guide provides **complete information** for engineers to develop their visual layer software using the BusinessProSuite API. It includes authentication, all available endpoints, code examples, error handling, and best practices.

---

## 🚀 Quick Start

### **1. Base URL and Environment**
```
Development: http://localhost:8080
Production: https://api.businessprosuite.com (when deployed)
```

### **2. API Versioning**
```
Base Path: /api/v1/
Example: http://localhost:8080/api/v1/auth/login
```

### **3. Interactive Documentation**
```
Swagger UI: http://localhost:8080/swagger-ui/index.html
OpenAPI Spec: http://localhost:8080/v3/api-docs
```

---

## 🔐 Authentication & Security

### **JWT Token-Based Authentication**

#### **Step 1: Register a New User**
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@company.com",
  "password": "SecurePass123!",
  "fullName": "John Doe",
  "companyName": "Acme Corporation",
  "companyEmail": "info@acme.com",
  "companyAddress": "123 Business Street",
  "companyPhone": "+1-555-0123",
  "countryCode": "US"
}
```

**Response (201 Created):**
```json
{
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@company.com",
    "fullName": "John Doe"
  },
  "company": {
    "id": 1,
    "name": "Acme Corporation",
    "email": "info@acme.com"
  }
}
```

#### **Step 2: Login and Get JWT Token**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImV4cCI6MTY4NzM4NDgwMH0.example_signature",
  "type": "Bearer",
  "username": "john_doe",
  "email": "john@company.com",
  "authorities": ["ROLE_USER"],
  "expiresIn": 86400
}
```

#### **Step 3: Use Token in Subsequent Requests**
```http
GET /api/v1/companies
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### **Code Examples**

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
      throw new Error('Login failed');
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
      throw new Error(`API call failed: ${response.status}`);
    }

    return response.json();
  }
}

// Usage
const api = new BusinessProSuiteAPI();
await api.login('john_doe', 'SecurePass123!');
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

# Usage
api = BusinessProSuiteAPI()
api.login('john_doe', 'SecurePass123!')
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

## 👥 User Management APIs

### **Security Users Management**

#### **Create Security User**
```http
POST /api/v1/security-users
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "employee1",
  "password": "EmployeePass123!",
  "email": "employee@company.com",
  "available": 1,
  "active": 1,
  "mfaEnabled": 0,
  "roleId": 2,
  "companyId": 1
}
```

#### **Get All Security Users**
```http
GET /api/v1/security-users
Authorization: Bearer {token}
```

#### **Get Security User by ID**
```http
GET /api/v1/security-users/{id}
Authorization: Bearer {token}
```

#### **Update Security User**
```http
PUT /api/v1/security-users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "updated_employee",
  "email": "updated@company.com",
  "active": 1,
  "available": 1
}
```

#### **Delete Security User**
```http
DELETE /api/v1/security-users/{id}
Authorization: Bearer {token}
```

---

## 🏢 Company Management APIs

### **Company CRUD Operations**

#### **Create Company**
```http
POST /api/v1/companies
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "New Company LLC",
  "address": "456 Innovation Avenue",
  "phone": "+1-555-9876",
  "email": "info@newcompany.com",
  "taxId": "12-3456789",
  "countryCode": "US"
}
```

#### **Get All Companies**
```http
GET /api/v1/companies
Authorization: Bearer {token}
```

#### **Get Company by ID**
```http
GET /api/v1/companies/{id}
Authorization: Bearer {token}
```

#### **Update Company**
```http
PUT /api/v1/companies/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Updated Company Name",
  "address": "New Address 789",
  "phone": "+1-555-5555",
  "email": "new@company.com"
}
```

#### **Delete Company**
```http
DELETE /api/v1/companies/{id}
Authorization: Bearer {token}
```

---

## 🏪 Customer Management APIs

### **Customer CRUD Operations**

#### **Create Customer**
```http
POST /api/v1/customers
Authorization: Bearer {token}
Content-Type: application/json

{
  "configCompanyId": 1,
  "name": "Premium Customer Inc.",
  "email": "customer@premium.com",
  "phone": "+1-555-1111",
  "address": "123 Customer Plaza",
  "taxId": "98-7654321",
  "companyId": 1,
  "countryCode": "US"
}
```

#### **Get All Customers**
```http
GET /api/v1/customers
Authorization: Bearer {token}
```

#### **Get Customer by ID**
```http
GET /api/v1/customers/{id}
Authorization: Bearer {token}
```

#### **Update Customer**
```http
PUT /api/v1/customers/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Updated Customer Name",
  "email": "updated@customer.com",
  "phone": "+1-555-2222"
}
```

---

## ⚙️ System Administration APIs

### **Health Check**
```http
GET /actuator/health
```

**Response:**
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

### **Application Info**
```http
GET /actuator/info
```

---

## 🚨 Error Handling

### **Standard Error Response Format**
```json
{
  "timestamp": "2025-06-03T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/companies",
  "errors": [
    {
      "field": "name",
      "message": "Company name cannot be blank"
    },
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ]
}
```

### **HTTP Status Codes**

| Code | Description | Usage |
|------|-------------|-------|
| 200 | OK | Successful GET, PUT operations |
| 201 | Created | Successful POST operations |
| 204 | No Content | Successful DELETE operations |
| 400 | Bad Request | Invalid request data |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Duplicate resource |
| 500 | Internal Server Error | Server error |

### **Error Handling in Code**

#### **JavaScript Example**
```javascript
async function handleApiCall() {
  try {
    const response = await api.apiCall('/companies');
    return response;
  } catch (error) {
    if (error.message.includes('401')) {
      // Token expired, redirect to login
      window.location.href = '/login';
    } else if (error.message.includes('400')) {
      // Validation error, show form errors
      displayValidationErrors(error.details);
    } else {
      // Generic error handling
      showErrorMessage('An unexpected error occurred');
    }
  }
}
```

#### **Python Example**
```python
def handle_api_error(response):
    if response.status_code == 401:
        # Token expired
        return redirect_to_login()
    elif response.status_code == 400:
        # Validation errors
        errors = response.json().get('errors', [])
        return display_validation_errors(errors)
    elif response.status_code == 404:
        # Resource not found
        return show_not_found_message()
    else:
        # Generic error
        return show_error_message("Unexpected error occurred")
```

---

## 📊 Data Models and DTOs

### **User Models**
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

### **Company Models**
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

### **Customer Models**
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

## 🔄 Pagination and Filtering

### **Future Implementation (V0.3)**
```http
GET /api/v1/companies?page=0&size=20&sort=name,asc
GET /api/v1/customers?search=premium&status=active
```

---

## 🎭 Testing Your Integration

### **Test with cURL**
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"SecurePass123!"}'

# Save token from response
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Get companies
curl -X GET http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $TOKEN"

# Create company
curl -X POST http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "configCompanyId": 1,
    "name": "Test Company",
    "address": "123 Test St",
    "phone": "+1-555-0000",
    "email": "test@company.com",
    "taxId": "00-0000000",
    "countryCode": "US"
  }'
```

### **Using Swagger UI**
1. Open http://localhost:8080/swagger-ui/index.html
2. Click "Authorize" button
3. Enter: `Bearer your-jwt-token-here`
4. Test any endpoint interactively

---

## 🚀 Best Practices

### **1. Token Management**
- Store JWT tokens securely (not in localStorage for sensitive apps)
- Implement token refresh logic
- Handle token expiration gracefully
- Clear tokens on logout

### **2. Error Handling**
- Always check HTTP status codes
- Parse error response bodies for details
- Implement retry logic for transient errors
- Provide user-friendly error messages

### **3. Performance**
- Implement request/response caching
- Use pagination for large data sets
- Minimize API calls with batch operations
- Implement loading states in UI

### **4. Security**
- Never log sensitive data (passwords, tokens)
- Validate all user inputs
- Use HTTPS in production
- Implement rate limiting in your client

### **5. API Versioning**
- Always use versioned endpoints (/api/v1/)
- Plan for API evolution
- Test with new versions before upgrading

---

## 📱 Framework-Specific Examples

### **React/Next.js Integration**
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

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Companies</h1>
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

### **Vue.js Integration**
```vue
<template>
  <div>
    <h1>Companies</h1>
    <div v-if="loading">Loading...</div>
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

## 📞 Support and Resources

### **Documentation Links**
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health

### **Common Issues and Solutions**
1. **401 Unauthorized**: Check token format and expiration
2. **CORS Issues**: Ensure proper CORS configuration
3. **Validation Errors**: Check request body format and required fields
4. **Rate Limiting**: Implement exponential backoff

### **Contact Information**
- **Email**: contact@businessprosuite.com
- **Documentation**: Complete guides in `/docs` folder
- **Issue Reporting**: Include API endpoint, request/response, and error details

---

**Status**: ✅ **Complete Integration Guide Ready for Development**  
**Last Updated**: June 3, 2025  
**API Version**: V0.2 