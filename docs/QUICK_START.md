# 🚀 Quick Start Guide - BusinessProSuite API V0.2

![Version](https://img.shields.io/badge/Version-V0.2-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)

## ⚡ Start in 5 minutes

Esta guía te permite tener BusinessProSuite API funcionando en **menos de 5 minutos**.

## 📋 Prerequisites

- ☑️ **Java 17** o superior
- ☑️ **Git** instalado
- ☑️ **Puerto 8080** disponible

## 🚀 Step 1: Clone & Run

```bash
# Clone repository
git clone <repository-url>
cd BusinessProSuiteAPI

# Run application
./gradlew bootRun
```

**Windows users:**
```cmd
gradlew.bat bootRun
```

## 🎯 Step 2: Verify Installation

### ✅ Health Check
```bash
curl http://localhost:8080/actuator/health
```
**Expected response:**
```json
{"status":"UP"}
```

### 📚 Access Swagger UI
Open: http://localhost:8080/swagger-ui/index.html

## 🔐 Step 3: Test Authentication

### Register New User
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@empresa.com", 
    "password": "MySecurePass123!",
    "firstName": "Admin",
    "lastName": "User",
    "companyName": "Mi Empresa",
    "companyEmail": "contact@empresa.com"
  }'
```

### Login & Get Token
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "MySecurePass123!"
  }'
```

**Save the token from response for next steps!**

### Test Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/companies \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## 🎉 Success! 

Si todos los pasos funcionaron correctamente, tienes:
- ✅ API ejecutándose en puerto 8080
- ✅ Base de datos H2 funcionando
- ✅ JWT authentication configurado
- ✅ Swagger UI accesible
- ✅ Endpoints de prueba respondiendo

## 🔍 Next Steps

### 📖 Explore API
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa` 
  - Password: (leave empty)

### 📚 Documentation
- **[API Reference](API_DOCUMENTATION.md)** - Complete endpoint documentation
- **[Integration Guide](how-to-use-it/complete-integration-guide_EN.md)** - Code examples
- **[Local Setup](set-up-API/local-setup-guide_EN.md)** - Detailed setup guide

### 🏗️ Development
- **[How to Read Docs](help/how-to-read-documentation_EN.md)** - Navigation guide
- **[Completed Features](completed/features-implemented_EN.md)** - What's implemented
- **[Roadmap](next-changes/general-roadmap_EN.md)** - Future plans

## ❗ Troubleshooting

### Port 8080 in use
```bash
# Find process using port 8080
lsof -ti:8080

# Kill process (replace PID)
kill -9 <PID>
```

### Java version issues
```bash
# Check Java version
java -version

# Should show Java 17+
```

### Database connection issues
- The API uses H2 in-memory database by default
- No additional setup required for quick start
- Data is reset on each restart

### Build issues
```bash
# Clean and rebuild
./gradlew clean build

# Skip tests if needed
./gradlew bootRun --no-daemon
```

## 💡 Need More Help?

1. **Detailed Setup**: [Local Setup Guide](set-up-API/local-setup-guide_EN.md)
2. **API Usage**: [Integration Guide](how-to-use-it/complete-integration-guide_EN.md)
3. **Documentation Help**: [How to Read Docs](help/how-to-read-documentation_EN.md)

---

**Quick Start Complete!** 🎉  
Ready to explore the API → **[Swagger UI](http://localhost:8080/swagger-ui/index.html)** 