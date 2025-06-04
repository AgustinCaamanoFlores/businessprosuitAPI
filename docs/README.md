# 📚 BusinessProSuite API Documentation

![Version](https://img.shields.io/badge/Version-V0.2-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Documentation](https://img.shields.io/badge/Documentation-100%25-brightgreen)

## 🎯 Overview

Documentación completa para **BusinessProSuite API V0.2** - Sistema integral de gestión empresarial con arquitectura modular y estándares enterprise.

## 📖 Quick Navigation

### 🚀 Getting Started
- **[Quick Start Guide](QUICK_START.md)** - Configuración rápida en 5 minutos
- **[API Documentation](API_DOCUMENTATION.md)** - Referencia completa de endpoints
- **[Connection Guide](CONEXION_API.md)** - Ejemplos de integración por lenguaje

### ⚙️ Setup & Deployment
- **[Local Setup](set-up-API/local-setup-guide_EN.md)** - Configuración local paso a paso
- **[AWS Deployment](set-up-API/aws-deployment-guide_EN.md)** - Despliegue en la nube
- **[Modularization](MODULARIZATION_ROADMAP.md)** - Arquitectura modular

### 📋 User Guides
- **[Integration Guide](how-to-use-it/complete-integration-guide_EN.md)** - Guía completa de integración
- **[How to Read Docs](help/how-to-read-documentation_EN.md)** - Cómo navegar la documentación

### 📈 Project Status
- **[Completed Features](completed/features-implemented_EN.md)** - Funcionalidades implementadas
- **[Change History](last-changes/v0.2/README.md)** - Historial de cambios V0.2
- **[Roadmap](next-changes/general-roadmap_EN.md)** - Próximas funcionalidades

## 🌟 Key Features V0.2

### ✅ Implemented
- **JWT Authentication** - Sistema completo de autenticación
- **Swagger UI** - Documentación interactiva con 8 grupos de API
- **Multi-environment** - Configuración para dev, test, prod
- **Global Exception Handling** - Manejo estandarizado de errores
- **Comprehensive Testing** - 43 tests unitarios pasando
- **Professional Documentation** - Documentación bilingüe completa

### 🔧 Technical Stack
- **Backend**: Spring Boot 3.4.4 + Java 17
- **Security**: JWT + Spring Security
- **Database**: MySQL 8.0 + HikariCP
- **Cache**: Caffeine
- **Documentation**: OpenAPI 3.0 + Swagger UI
- **Testing**: JUnit 5 + Mockito

## 📊 Project Metrics

| Metric | Value |
|--------|-------|
| **API Endpoints** | 20+ endpoints organizados |
| **Test Coverage** | 43 tests passing |
| **Documentation Files** | 15+ archivos |
| **Languages** | Bilingüe (EN/ES) |
| **Onboarding Time** | 5-10 minutos |

## 🚀 Quick Start

1. **Clone & Setup**
   ```bash
   git clone <repository>
   cd BusinessProSuiteAPI
   ./gradlew bootRun
   ```

2. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

3. **Test Authentication**
   ```bash
   curl -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"password"}'
   ```

## 📚 Documentation Structure

```
docs/
├── README.md                    # Este archivo (inicio)
├── API_DOCUMENTATION.md         # Referencia completa de API
├── QUICK_START.md              # Inicio rápido
├── CONEXION_API.md             # Guías de conexión
├── MODULARIZATION_ROADMAP.md   # Arquitectura modular
├── completed/                   # Funcionalidades completadas
├── help/                       # Guías de ayuda
├── how-to-use-it/              # Guías de uso
├── last-changes/               # Historial de cambios
├── next-changes/               # Roadmap futuro
└── set-up-API/                 # Guías de configuración
```

## 🌐 Language Support

La documentación está disponible en:
- **🇺🇸 English** - Archivos terminados en `_EN.md`
- **🇪🇸 Español** - Archivos terminados en `_ES.md` o sin sufijo

## 💡 Need Help?

1. **Documentation Guide**: [how-to-read-documentation_EN.md](help/how-to-read-documentation_EN.md)
2. **Integration Help**: [complete-integration-guide_EN.md](how-to-use-it/complete-integration-guide_EN.md)
3. **API Reference**: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
4. **Quick Setup**: [QUICK_START.md](QUICK_START.md)

## 📞 Support

- **Technical Documentation**: Este directorio docs/
- **Interactive API**: http://localhost:8080/swagger-ui/index.html
- **Health Check**: http://localhost:8080/actuator/health

---

**Version**: V0.2 Enterprise Ready  
**Last Updated**: $(date +%Y-%m-%d)  
**Status**: ✅ Production Ready