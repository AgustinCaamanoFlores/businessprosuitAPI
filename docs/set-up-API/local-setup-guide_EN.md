# Local Setup Guide - BusinessProSuite API

**Version**: V0.2  
**Target Audience**: Developers, DevOps Engineers  
**Estimated Setup Time**: 30-45 minutes  

## 🎯 Purpose

This guide provides **step-by-step instructions** to set up BusinessProSuite API in your local development environment. Follow these instructions to have a fully functional API running on your machine.

---

## 📋 Prerequisites

### **Required Software**
- **Java 17+** (OpenJDK or Oracle JDK)
- **Git** (latest version)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### **Optional but Recommended**
- **Docker** (for database alternatives)
- **Postman** (for API testing)
- **MySQL Workbench** (for database management)

---

## 🚀 Step-by-Step Setup

### **Step 1: Clone the Repository**

```bash
# Clone the repository
git clone https://github.com/businessprosuite/BusinessProSuiteAPI.git

# Navigate to project directory
cd BusinessProSuiteAPI

# Check project structure
ls -la
```

**Expected output:**
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

### **Step 2: Verify Java Installation**

```bash
# Check Java version
java -version

# Check JAVA_HOME environment variable
echo $JAVA_HOME
```

**Expected output:**
```
openjdk version "17.0.x" 2023-xx-xx
OpenJDK Runtime Environment (build 17.0.x+xx)
OpenJDK 64-Bit Server VM (build 17.0.x+xx, mixed mode, sharing)
```

**If Java 17 is not installed:**

#### **macOS (using Homebrew):**
```bash
# Install Homebrew if not already installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 17
brew install openjdk@17

# Set JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc
```

#### **Ubuntu/Debian:**
```bash
# Update package index
sudo apt update

# Install Java 17
sudo apt install openjdk-17-jdk

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc
```

#### **Windows:**
1. Download Java 17 from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://adoptium.net/)
2. Install the downloaded package
3. Set JAVA_HOME environment variable in System Properties

### **Step 3: Configure Application Properties**

The application uses **H2 database by default** for development, so no additional database setup is required.

#### **Review Configuration (Optional)**
```bash
# View current configuration
cat src/main/resources/application.yml
```

**Key configurations:**
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

### **Step 4: Build and Run the Application**

#### **Option A: Using Gradle Wrapper (Recommended)**
```bash
# Make gradlew executable (Linux/macOS)
chmod +x gradlew

# Build the application
./gradlew build

# Run the application
./gradlew bootRun
```

#### **Option B: Using Your Local Gradle Installation**
```bash
# Build the application
gradle build

# Run the application
gradle bootRun
```

#### **Option C: Using JAR File**
```bash
# Build JAR file
./gradlew build

# Run the JAR
java -jar build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar
```

### **Step 5: Verify Application Startup**

**Watch for successful startup logs:**
```
2025-06-03 10:30:00.000  INFO --- [           main] c.b.api.BusinessProSuiteApiApplication   : Started BusinessProSuiteApiApplication in 15.234 seconds
2025-06-03 10:30:00.000  INFO --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
```

**Test basic connectivity:**
```bash
# Test health endpoint
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP","components":{"db":{"status":"UP","details":{"database":"H2","validationQuery":"isValid()"}}}}
```

### **Step 6: Access Development Tools**

#### **Swagger UI (API Documentation)**
```
URL: http://localhost:8080/swagger-ui/index.html
```

#### **H2 Database Console**
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)
```

#### **Health Check Endpoint**
```
URL: http://localhost:8080/actuator/health
```

---

## 🧪 Testing the Setup

### **Step 1: Register a Test User**
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "TestPass123!",
    "fullName": "Test User",
    "companyName": "Test Company",
    "companyEmail": "company@test.com",
    "companyAddress": "123 Test Street",
    "companyPhone": "+1-555-0000",
    "countryCode": "US"
  }'
```

### **Step 2: Login and Get JWT Token**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "TestPass123!"
  }'
```

**Save the token from the response:**
```bash
export JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### **Step 3: Test Protected Endpoints**
```bash
# Get companies
curl -X GET http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $JWT_TOKEN"

# Create a company
curl -X POST http://localhost:8080/api/v1/companies \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "configCompanyId": 1,
    "name": "My Test Company",
    "address": "456 Business Avenue",
    "phone": "+1-555-1234",
    "email": "contact@mytestcompany.com",
    "taxId": "12-3456789",
    "countryCode": "US"
  }'
```

---

## 🔧 Development Configuration

### **IDE Setup**

#### **IntelliJ IDEA**
1. **Import Project:**
   - File → Open → Select `BusinessProSuiteAPI` folder
   - Choose "Import Gradle project"
   - Use Gradle wrapper

2. **Configure JDK:**
   - File → Project Structure → Project → Project SDK → Select Java 17

3. **Enable Annotation Processing:**
   - Settings → Build → Compiler → Annotation Processors → Enable

#### **Eclipse**
1. **Import Project:**
   - File → Import → Existing Gradle Project
   - Select `BusinessProSuiteAPI` folder

2. **Configure JDK:**
   - Project Properties → Java Build Path → Modulepath → Add Library → JRE System Library → Java 17

#### **VS Code**
1. **Install Extensions:**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Gradle for Java

2. **Open Project:**
   - File → Open Folder → Select `BusinessProSuiteAPI`

### **Environment Variables (Optional)**

Create a `.env` file in the project root:
```bash
# JWT Configuration
JWT_SECRET=your-custom-secret-key-here
JWT_EXPIRATION=86400000

# Database Configuration (if using MySQL)
DB_HOST=localhost
DB_PORT=3306
DB_NAME=businessprosuite
DB_USERNAME=root
DB_PASSWORD=your-password

# Logging Level
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_BUSINESSPROSUITE=DEBUG
```

**Load environment variables:**
```bash
# Linux/macOS
export $(cat .env | xargs)

# Windows PowerShell
Get-Content .env | ForEach-Object { $name, $value = $_.Split('='); Set-Item -Path "env:$name" -Value $value }
```

---

## 🗄️ Database Alternatives

### **Option 1: H2 Database (Default - No Setup Required)**
- **Pros**: Zero configuration, fast startup, perfect for development
- **Cons**: In-memory only, data lost on restart
- **Usage**: Already configured and working

### **Option 2: MySQL Database**

#### **Install MySQL using Docker (Recommended)**
```bash
# Run MySQL container
docker run -d \
  --name businessprosuite-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=businessprosuite \
  -e MYSQL_USER=dbuser \
  -e MYSQL_PASSWORD=dbpassword \
  -p 3306:3306 \
  mysql:8.0

# Verify container is running
docker ps
```

#### **Update application.yml for MySQL**
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

#### **Install MySQL Locally**

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
1. Download MySQL installer from [mysql.com](https://dev.mysql.com/downloads/installer/)
2. Run installer and follow setup wizard
3. Choose "Developer Default" setup type

---

## 🐛 Troubleshooting

### **Common Issues and Solutions**

#### **Issue 1: Port 8080 Already in Use**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change port in application.yml
server:
  port: 8081
```

#### **Issue 2: Java Version Mismatch**
```bash
# Check current Java version
java -version

# Check available Java versions (macOS)
/usr/libexec/java_home -V

# Switch Java version (macOS)
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

#### **Issue 3: Gradle Build Fails**
```bash
# Clean build
./gradlew clean

# Build with debug info
./gradlew build --debug

# Build without tests
./gradlew build -x test
```

#### **Issue 4: H2 Console Not Accessible**
**Check configuration in application.yml:**
```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
```

#### **Issue 5: JWT Token Issues**
```bash
# Generate a new secret key
export JWT_SECRET=$(openssl rand -base64 32)

# Or use a fixed secret for development
export JWT_SECRET="dev-secret-key-12345678901234567890"
```

---

## 🔄 Hot Reload and Development

### **Enable Spring Boot DevTools**
DevTools is already included in the project. It provides:
- Automatic restart when classpath changes
- LiveReload browser extension support
- Enhanced development experience

### **IntelliJ IDEA Configuration**
1. **Enable Build Project Automatically:**
   - Settings → Build → Compiler → Build project automatically

2. **Registry Settings:**
   - Help → Find Action → Registry
   - Enable: `compiler.automake.allow.when.app.running`

### **File Watching**
The application will automatically restart when you:
- Modify Java source files
- Change configuration files
- Update dependencies

---

## 📊 Monitoring and Logs

### **Application Logs**
```bash
# View real-time logs
tail -f app.log

# View logs with grep
grep "ERROR" app.log

# View last 100 lines
tail -100 app.log
```

### **Spring Boot Actuator Endpoints**
```bash
# Health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info

# Environment properties
curl http://localhost:8080/actuator/env

# Metrics
curl http://localhost:8080/actuator/metrics
```

---

## 🚀 Next Steps

### **After Successful Setup**
1. **Explore API Documentation:** http://localhost:8080/swagger-ui/index.html
2. **Test API Endpoints:** Use Postman or curl commands
3. **Review Code Structure:** Familiarize yourself with the codebase
4. **Read Integration Guide:** Check `docs/how-to-use-it/` for client integration examples

### **Development Workflow**
1. **Make Changes:** Modify code in your IDE
2. **Test Locally:** Use Swagger UI or Postman
3. **Run Tests:** `./gradlew test`
4. **Build:** `./gradlew build`
5. **Commit:** Git commit with descriptive message

### **Production Preparation**
1. **Environment Configuration:** Set production environment variables
2. **Database Setup:** Configure production database
3. **Security Review:** Update JWT secrets and security settings
4. **Performance Testing:** Test with realistic load

---

## 📞 Support

### **Getting Help**
- **Documentation:** Check `docs/` folder for comprehensive guides
- **Issues:** Review common troubleshooting steps above
- **Swagger UI:** Use interactive API documentation
- **Community:** Contact development team

### **Useful Commands Reference**
```bash
# Start application
./gradlew bootRun

# Run tests
./gradlew test

# Clean build
./gradlew clean build

# Check dependencies
./gradlew dependencies

# View project tasks
./gradlew tasks
```

---

**Status**: ✅ **Local Setup Guide Complete**  
**Last Updated**: June 3, 2025  
**Tested On**: macOS, Ubuntu, Windows 10+ 