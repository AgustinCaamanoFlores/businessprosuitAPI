# Guía de Configuración Local - BusinessProSuite API

**Versión**: V0.2  
**Audiencia Objetivo**: Desarrolladores, Ingenieros DevOps  
**Tiempo de Configuración Estimado**: 30-45 minutos  

## 🎯 Propósito

Esta guía proporciona **instrucciones paso a paso** para configurar BusinessProSuite API en tu entorno de desarrollo local. Sigue estas instrucciones para tener una API completamente funcional ejecutándose en tu máquina.

---

## 📋 Prerequisitos

### **Software Requerido**
- **Java 17+** (OpenJDK o Oracle JDK)
- **Git** (versión más reciente)
- **IDE** (IntelliJ IDEA, Eclipse, o VS Code)

### **Opcional pero Recomendado**
- **Docker** (para alternativas de base de datos)
- **Postman** (para pruebas de API)
- **MySQL Workbench** (para gestión de base de datos)

---

## 🚀 Configuración Paso a Paso

### **Paso 1: Clonar el Repositorio**

```bash
# Clonar el repositorio
git clone https://github.com/businessprosuite/BusinessProSuiteAPI.git

# Navegar al directorio del proyecto
cd BusinessProSuiteAPI

# Verificar estructura del proyecto
ls -la
```

**Salida esperada:**
```
├── docs/
├── gradle/
├── src/
├── build.gradle
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

### **Paso 2: Verificar Instalación de Java**

```bash
# Verificar versión de Java
java -version

# Verificar variable de entorno JAVA_HOME
echo $JAVA_HOME
```

**Salida esperada:**
```
openjdk version "17.0.x" 2023-xx-xx
OpenJDK Runtime Environment (build 17.0.x+xx)
OpenJDK 64-Bit Server VM (build 17.0.x+xx, mixed mode, sharing)
```

**Si Java 17 no está instalado:**

#### **macOS (usando Homebrew):**
```bash
# Instalar Homebrew si no está instalado
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Instalar Java 17
brew install openjdk@17

# Configurar JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc
```

#### **Ubuntu/Debian:**
```bash
# Actualizar índice de paquetes
sudo apt update

# Instalar Java 17
sudo apt install openjdk-17-jdk

# Configurar JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc
```

#### **Windows:**
1. Descargar Java 17 de [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o [OpenJDK](https://adoptium.net/)
2. Instalar el paquete descargado
3. Configurar variable de entorno JAVA_HOME en Propiedades del Sistema

### **Paso 3: Configurar Propiedades de la Aplicación**

La aplicación usa **base de datos H2 por defecto** para desarrollo, por lo que no se requiere configuración adicional de base de datos.

#### **Revisar Configuración (Opcional)**
```bash
# Ver configuración actual
cat src/main/resources/application.yml
```

**Configuraciones clave:**
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: ""
  
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET:businessprosuite-secret-key-2025}
  expiration: 86400000
```

### **Paso 4: Construir y Ejecutar la Aplicación**

#### **Opción A: Usando Gradle Wrapper (Recomendado)**
```bash
# Hacer gradlew ejecutable (Linux/macOS)
chmod +x gradlew

# Construir la aplicación
./gradlew build

# Ejecutar la aplicación
./gradlew bootRun
```

#### **Opción B: Usando tu Instalación Local de Gradle**
```bash
# Construir la aplicación
gradle build

# Ejecutar la aplicación
gradle bootRun
```

#### **Opción C: Usando Archivo JAR**
```bash
# Construir archivo JAR
./gradlew build

# Ejecutar el JAR
java -jar build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar
```

### **Paso 5: Verificar Inicio de Aplicación**

**Buscar logs de inicio exitoso:**
```
2025-06-03 10:30:00.000  INFO --- [           main] c.b.api.BusinessProSuiteApiApplication   : Started BusinessProSuiteApiApplication in 15.234 seconds
2025-06-03 10:30:00.000  INFO --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
```

**Probar conectividad básica:**
```bash
# Probar endpoint de salud
curl http://localhost:8080/actuator/health

# Respuesta esperada:
# {"status":"UP","components":{"db":{"status":"UP","details":{"database":"H2","validationQuery":"isValid()"}}}}
```

### **Paso 6: Acceder a Herramientas de Desarrollo**

#### **Swagger UI (Documentación de API)**
```
URL: http://localhost:8080/swagger-ui/index.html
```

#### **Consola de Base de Datos H2**
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Usuario: sa
Contraseña: (dejar vacío)
```

#### **Endpoint de Health Check**
```
URL: http://localhost:8080/actuator/health
```

---

## 🧪 Probando la Configuración

### **Paso 1: Registrar un Usuario de Prueba**
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "usuarioprueba",
    "email": "prueba@ejemplo.com",
    "password": "ClavePrueba123!",
    "fullName": "Usuario de Prueba",
    "companyName": "Empresa de Prueba",
    "companyEmail": "empresa@prueba.com",
    "companyAddress": "123 Calle de Prueba",
    "companyPhone": "+1-555-0000",
    "countryCode": "US"
  }'
```

### **Paso 2: Login y Obtener Token JWT**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "usuarioprueba",
    "password": "ClavePrueba123!"
  }'
```

**Guardar el token de la respuesta:**
```bash
export JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### **Paso 3: Probar Endpoints Protegidos**
```bash
# Obtener empresas
curl -X GET http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $JWT_TOKEN"

# Crear una empresa
curl -X POST http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "configCompanyId": 1,
    "name": "Mi Empresa de Prueba",
    "address": "456 Avenida de Negocios",
    "phone": "+1-555-1234",
    "email": "contacto@miempresaprueba.com",
    "taxId": "12-3456789",
    "countryCode": "US"
  }'
```

---

## 🔧 Configuración de Desarrollo

### **Configuración de IDE**

#### **IntelliJ IDEA**
1. **Importar Proyecto:**
   - Archivo → Abrir → Seleccionar carpeta `BusinessProSuiteAPI`
   - Elegir "Importar proyecto Gradle"
   - Usar Gradle wrapper

2. **Configurar JDK:**
   - Archivo → Estructura del Proyecto → Proyecto → SDK del Proyecto → Seleccionar Java 17

3. **Habilitar Procesamiento de Anotaciones:**
   - Configuraciones → Construcción → Compilador → Procesadores de Anotaciones → Habilitar

#### **Eclipse**
1. **Importar Proyecto:**
   - Archivo → Importar → Proyecto Gradle Existente
   - Seleccionar carpeta `BusinessProSuiteAPI`

2. **Configurar JDK:**
   - Propiedades del Proyecto → Ruta de Construcción Java → Modulepath → Agregar Biblioteca → Biblioteca del Sistema JRE → Java 17

#### **VS Code**
1. **Instalar Extensiones:**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Gradle for Java

2. **Abrir Proyecto:**
   - Archivo → Abrir Carpeta → Seleccionar `BusinessProSuiteAPI`

### **Variables de Entorno (Opcional)**

Crear un archivo `.env` en la raíz del proyecto:
```bash
# Configuración JWT
JWT_SECRET=tu-clave-secreta-personalizada-aquí
JWT_EXPIRATION=86400000

# Configuración de Base de Datos (si usas MySQL)
DB_HOST=localhost
DB_PORT=3306
DB_NAME=businessprosuite
DB_USERNAME=root
DB_PASSWORD=tu-contraseña

# Nivel de Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_BUSINESSPROSUITE=DEBUG
```

**Cargar variables de entorno:**
```bash
# Linux/macOS
export $(cat .env | xargs)

# Windows PowerShell
Get-Content .env | ForEach-Object { $name, $value = $_.Split('='); Set-Item -Path "env:$name" -Value $value }
```

---

## 🗄️ Alternativas de Base de Datos

### **Opción 1: Base de Datos H2 (Por Defecto - No Requiere Configuración)**
- **Ventajas**: Sin configuración, inicio rápido, perfecto para desarrollo
- **Desventajas**: Solo en memoria, datos se pierden al reiniciar
- **Uso**: Ya configurado y funcionando

### **Opción 2: Base de Datos MySQL**

#### **Instalar MySQL usando Docker (Recomendado)**
```bash
# Ejecutar contenedor MySQL
docker run -d \
  --name businessprosuite-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=businessprosuite \
  -e MYSQL_USER=dbuser \
  -e MYSQL_PASSWORD=dbpassword \
  -p 3306:3306 \
  mysql:8.0

# Verificar que el contenedor esté ejecutándose
docker ps
```

#### **Actualizar application.yml para MySQL**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/businessprosuite?useSSL=false&serverTimezone=UTC
    username: dbuser
    password: dbpassword
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

#### **Instalar MySQL Localmente**

**macOS:**
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

**Ubuntu:**
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

**Windows:**
1. Descargar instalador MySQL de [mysql.com](https://dev.mysql.com/downloads/installer/)
2. Ejecutar instalador y seguir asistente de configuración
3. Elegir tipo de configuración "Developer Default"

---

## 🐛 Solución de Problemas

### **Problemas Comunes y Soluciones**

#### **Problema 1: Puerto 8080 Ya en Uso**
```bash
# Encontrar proceso usando puerto 8080
lsof -i :8080

# Terminar el proceso
kill -9 <PID>

# O cambiar puerto en application.yml
server:
  port: 8081
```

#### **Problema 2: Desajuste de Versión de Java**
```bash
# Verificar versión actual de Java
java -version

# Verificar versiones disponibles de Java (macOS)
/usr/libexec/java_home -V

# Cambiar versión de Java (macOS)
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

#### **Problema 3: Construcción Gradle Falla**
```bash
# Limpiar construcción
./gradlew clean

# Construir con información de debug
./gradlew build --debug

# Construir sin tests
./gradlew build -x test
```

#### **Problema 4: Consola H2 No Accesible**
**Verificar configuración en application.yml:**
```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
```

#### **Problema 5: Problemas con Token JWT**
```bash
# Generar una nueva clave secreta
export JWT_SECRET=$(openssl rand -base64 32)

# O usar una clave fija para desarrollo
export JWT_SECRET="dev-secret-key-12345678901234567890"
```

---

## 🔄 Hot Reload y Desarrollo

### **Habilitar Spring Boot DevTools**
DevTools ya está incluido en el proyecto. Proporciona:
- Reinicio automático cuando cambia el classpath
- Soporte para extensión LiveReload del navegador
- Experiencia de desarrollo mejorada

### **Configuración de IntelliJ IDEA**
1. **Habilitar Construcción Automática del Proyecto:**
   - Configuraciones → Construcción → Compilador → Construir proyecto automáticamente

2. **Configuraciones de Registro:**
   - Ayuda → Buscar Acción → Registro
   - Habilitar: `compiler.automake.allow.when.app.running`

### **Monitoreo de Archivos**
La aplicación se reiniciará automáticamente cuando:
- Modifiques archivos fuente Java
- Cambies archivos de configuración
- Actualices dependencias

---

## 📊 Monitoreo y Logs

### **Logs de la Aplicación**
```bash
# Ver logs en tiempo real
tail -f app.log

# Ver logs con grep
grep "ERROR" app.log

# Ver últimas 100 líneas
tail -100 app.log
```

### **Endpoints de Spring Boot Actuator**
```bash
# Health check
curl http://localhost:8080/actuator/health

# Información de la aplicación
curl http://localhost:8080/actuator/info

# Propiedades del entorno
curl http://localhost:8080/actuator/env

# Métricas
curl http://localhost:8080/actuator/metrics
```

---

## 🚀 Próximos Pasos

### **Después de la Configuración Exitosa**
1. **Explorar Documentación de API:** http://localhost:8080/swagger-ui/index.html
2. **Probar Endpoints de API:** Usar Postman o comandos curl
3. **Revisar Estructura de Código:** Familiarizarse con el codebase
4. **Leer Guía de Integración:** Revisar `docs/how-to-use-it/` para ejemplos de integración de cliente

### **Flujo de Trabajo de Desarrollo**
1. **Hacer Cambios:** Modificar código en tu IDE
2. **Probar Localmente:** Usar Swagger UI o Postman
3. **Ejecutar Tests:** `./gradlew test`
4. **Construir:** `./gradlew build`
5. **Confirmar:** Git commit con mensaje descriptivo

### **Preparación para Producción**
1. **Configuración de Entorno:** Configurar variables de entorno de producción
2. **Configuración de Base de Datos:** Configurar base de datos de producción
3. **Revisión de Seguridad:** Actualizar secretos JWT y configuraciones de seguridad
4. **Pruebas de Performance:** Probar con carga realista

---

## 📞 Soporte

### **Obtener Ayuda**
- **Documentación:** Revisar carpeta `docs/` para guías comprensivas
- **Problemas:** Revisar pasos de solución de problemas arriba
- **Swagger UI:** Usar documentación interactiva de API
- **Comunidad:** Contactar equipo de desarrollo

### **Referencia de Comandos Útiles**
```bash
# Iniciar aplicación
./gradlew bootRun

# Ejecutar tests
./gradlew test

# Construcción limpia
./gradlew clean build

# Verificar dependencias
./gradlew dependencies

# Ver tareas del proyecto
./gradlew tasks
```

---

**Estado**: ✅ **Guía de Configuración Local Completa**  
**Última Actualización**: 3 de Junio de 2025  
**Probado En**: macOS, Ubuntu, Windows 10+ 