#!/bin/bash

# ========================================================================
# SCRIPT AUTOMATIZADO - FASE 1: PREPARACIÓN INFRAESTRUCTURA MULTI-MODULE
# ========================================================================

set -e  # Exit on any error

echo "🚀 INICIANDO MODULARIZACIÓN BUSINESSPROSUITE API - FASE 1"
echo "============================================================"

# Backup current structure
echo "📦 Creando backup de la estructura actual..."
cp -r src/ backup-src-$(date +%Y%m%d-%H%M%S)/

# 1. CREAR ESTRUCTURA DE DIRECTORIOS
echo "🏗️  Creando estructura de módulos..."

# Shared modules
mkdir -p bps-shared/bps-shared-common/src/main/java/com/businessprosuite/common
mkdir -p bps-shared/bps-shared-common/src/main/resources
mkdir -p bps-shared/bps-shared-common/src/test/java

mkdir -p bps-shared/bps-shared-security/src/main/java/com/businessprosuite/security
mkdir -p bps-shared/bps-shared-security/src/main/resources
mkdir -p bps-shared/bps-shared-security/src/test/java

mkdir -p bps-shared/bps-shared-config/src/main/java/com/businessprosuite/config
mkdir -p bps-shared/bps-shared-config/src/main/resources
mkdir -p bps-shared/bps-shared-config/src/test/java

mkdir -p bps-shared/bps-shared-events/src/main/java/com/businessprosuite/events
mkdir -p bps-shared/bps-shared-events/src/main/resources
mkdir -p bps-shared/bps-shared-events/src/test/java

# Core modules
mkdir -p bps-core/bps-company/src/main/java/com/businessprosuite/company
mkdir -p bps-core/bps-company/src/main/resources
mkdir -p bps-core/bps-company/src/test/java

mkdir -p bps-core/bps-user/src/main/java/com/businessprosuite/user
mkdir -p bps-core/bps-user/src/main/resources
mkdir -p bps-core/bps-user/src/test/java

# Application module
mkdir -p bps-application/src/main/java/com/businessprosuite
mkdir -p bps-application/src/main/resources
mkdir -p bps-application/src/test/java

echo "✅ Estructura de directorios creada"

# 2. CREAR CONFIGURACIONES GRADLE
echo "⚙️  Creando configuraciones Gradle..."

# Root settings.gradle
cat > settings.gradle << 'EOF'
rootProject.name = 'BusinessProSuiteAPI'

// SHARED MODULES
include 'bps-shared:bps-shared-common'
include 'bps-shared:bps-shared-security'
include 'bps-shared:bps-shared-config'
include 'bps-shared:bps-shared-events'

// CORE MODULES
include 'bps-core:bps-company'
include 'bps-core:bps-user'

// MAIN APPLICATION
include 'bps-application'
EOF

# Root build.gradle backup and create new
mv build.gradle build.gradle.backup
cat > build.gradle << 'EOF'
plugins {
    id 'java'
    id 'idea'
    id 'org.springframework.boot' version '3.4.4' apply false
    id 'io.spring.dependency-management' version '1.1.7' apply false
}

allprojects {
    group = 'com.businessprosuite.api'
    version = '0.2.0-SNAPSHOT'
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply plugin: 'java'
    apply plugin: 'idea'
    apply plugin: 'io.spring.dependency-management'
    
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
    
    dependencyManagement {
        imports {
            mavenBom "org.springframework.boot:spring-boot-dependencies:3.4.4"
        }
    }
    
    dependencies {
        compileOnly 'org.projectlombok:lombok:1.18.32'
        annotationProcessor 'org.projectlombok:lombok:1.18.32'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
}

// Configure specific module types
configure(project(':bps-application')) {
    apply plugin: 'org.springframework.boot'
    
    dependencies {
        implementation project(':bps-shared:bps-shared-common')
        implementation project(':bps-shared:bps-shared-security')
        implementation project(':bps-shared:bps-shared-config')
        implementation project(':bps-core:bps-company')
        implementation project(':bps-core:bps-user')
        
        // Original dependencies
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-validation'
        implementation 'org.springframework.boot:spring-boot-starter-security'
        implementation 'org.springframework.boot:spring-boot-starter-actuator'
        implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'
        implementation 'org.flywaydb:flyway-core'
        implementation 'io.micrometer:micrometer-registry-prometheus'
        implementation 'org.springframework.boot:spring-boot-starter-cache'
        implementation 'com.github.ben-manes.caffeine:caffeine'
        implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
        runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
        runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
        implementation 'org.mapstruct:mapstruct:1.5.5.Final'
        annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
        developmentOnly 'org.springframework.boot:spring-boot-devtools'
        runtimeOnly 'com.mysql:mysql-connector-j'
        testImplementation 'org.springframework.security:spring-security-test'
        testImplementation 'org.testcontainers:junit-jupiter'
        testImplementation 'org.testcontainers:mysql'
        testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    }
}

tasks.withType(JavaCompile).configureEach {
    options.annotationProcessorGeneratedSourcesDirectory = file("$buildDir/generated/sources/annotations")
}

sourceSets {
    main {
        java {
            srcDir "$buildDir/generated/sources/annotations"
        }
        resources {
            srcDir "src/main/resources"
        }
    }
}

idea {
    module {
        sourceDirs += file("$buildDir/generated/sources/annotations")
        generatedSourceDirs += file("$buildDir/generated/sources/annotations")
    }
}
EOF

# Create module-specific build.gradle files
echo "📝 Creando build.gradle específicos para cada módulo..."

# bps-shared-common
cat > bps-shared/bps-shared-common/build.gradle << 'EOF'
dependencies {
    api 'org.springframework.boot:spring-boot-starter'
    api 'org.springframework.boot:spring-boot-starter-validation'
    api 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
}
EOF

# bps-shared-security
cat > bps-shared/bps-shared-security/build.gradle << 'EOF'
dependencies {
    implementation project(':bps-shared:bps-shared-common')
    api 'org.springframework.boot:spring-boot-starter-security'
    api 'io.jsonwebtoken:jjwt-api:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
}
EOF

# bps-shared-config
cat > bps-shared/bps-shared-config/build.gradle << 'EOF'
dependencies {
    implementation project(':bps-shared:bps-shared-common')
    api 'org.springframework.boot:spring-boot-starter-data-jpa'
    api 'org.springframework.boot:spring-boot-starter-cache'
    api 'com.github.ben-manes.caffeine:caffeine'
}
EOF

# bps-shared-events
cat > bps-shared/bps-shared-events/build.gradle << 'EOF'
dependencies {
    implementation project(':bps-shared:bps-shared-common')
    api 'org.springframework.boot:spring-boot-starter'
}
EOF

# bps-company
cat > bps-core/bps-company/build.gradle << 'EOF'
dependencies {
    implementation project(':bps-shared:bps-shared-common')
    implementation project(':bps-shared:bps-shared-config')
    implementation project(':bps-shared:bps-shared-events')
    api 'org.springframework.boot:spring-boot-starter-data-jpa'
    api 'org.springframework.boot:spring-boot-starter-web'
}
EOF

# bps-user
cat > bps-core/bps-user/build.gradle << 'EOF'
dependencies {
    implementation project(':bps-shared:bps-shared-common')
    implementation project(':bps-shared:bps-shared-security')
    implementation project(':bps-shared:bps-shared-events')
    api 'org.springframework.boot:spring-boot-starter-data-jpa'
    api 'org.springframework.boot:spring-boot-starter-web'
}
EOF

echo "✅ Configuraciones Gradle creadas"

# 3. MOVER ARCHIVOS COMUNES A SHARED MODULES
echo "📦 Moviendo archivos a módulos compartidos..."

# Move utilities to shared-common
if [ -d "src/main/java/com/businessprosuite/api/util" ]; then
    cp -r src/main/java/com/businessprosuite/api/util/* bps-shared/bps-shared-common/src/main/java/com/businessprosuite/common/
    echo "✅ Utilidades movidas a bps-shared-common"
fi

# Move security to shared-security
if [ -d "src/main/java/com/businessprosuite/api/security" ]; then
    cp -r src/main/java/com/businessprosuite/api/security/* bps-shared/bps-shared-security/src/main/java/com/businessprosuite/security/
    echo "✅ Security movido a bps-shared-security"
fi

# Move config to shared-config (excluding specific business config)
if [ -d "src/main/java/com/businessprosuite/api/config" ]; then
    cp -r src/main/java/com/businessprosuite/api/config/* bps-shared/bps-shared-config/src/main/java/com/businessprosuite/config/
    echo "✅ Config movido a bps-shared-config"
fi

# 4. SETUP COMPANY MODULE STRUCTURE
echo "🏢 Configurando módulo Company..."

# Create Company domain structure
mkdir -p bps-core/bps-company/src/main/java/com/businessprosuite/company/{domain,application,infrastructure}
mkdir -p bps-core/bps-company/src/main/java/com/businessprosuite/company/infrastructure/{persistence,web}
mkdir -p bps-core/bps-company/src/main/java/com/businessprosuite/company/application/{commands,queries}

# Move company-related files
if [ -d "src/main/java/com/businessprosuite/api/model/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/model/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/domain/
fi

if [ -d "src/main/java/com/businessprosuite/api/dto/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/dto/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/infrastructure/web/
fi

if [ -d "src/main/java/com/businessprosuite/api/repository/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/repository/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/infrastructure/persistence/
fi

if [ -d "src/main/java/com/businessprosuite/api/service/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/service/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/application/
fi

if [ -d "src/main/java/com/businessprosuite/api/impl/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/impl/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/application/
fi

if [ -d "src/main/java/com/businessprosuite/api/controller/company" ]; then
    cp -r src/main/java/com/businessprosuite/api/controller/company/* bps-core/bps-company/src/main/java/com/businessprosuite/company/infrastructure/web/
fi

echo "✅ Módulo Company configurado"

# 5. SETUP APPLICATION MODULE
echo "🚀 Configurando módulo de aplicación principal..."

# Move main application class
cp src/main/java/com/businessprosuite/api/BusinessProSuiteApiApplication.java bps-application/src/main/java/com/businessprosuite/
cp -r src/main/resources/* bps-application/src/main/resources/

echo "✅ Módulo de aplicación configurado"

# 6. CREATE VALIDATION SCRIPT
echo "🔍 Creando script de validación..."

cat > validate-modularization.sh << 'EOF'
#!/bin/bash

echo "🔍 VALIDANDO MODULARIZACIÓN..."

# Check if all modules compile
echo "📦 Compilando módulos..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "✅ Todos los módulos compilan correctamente"
else
    echo "❌ Error en compilación de módulos"
    exit 1
fi

# Check for circular dependencies
echo "🔄 Verificando dependencias circulares..."
./gradlew :dependencies --configuration compileClasspath | grep -i "circular"

# Run basic tests
echo "🧪 Ejecutando tests básicos..."
./gradlew test

echo "✅ Validación completada"
EOF

chmod +x validate-modularization.sh

# 7. CREATE ROLLBACK SCRIPT
echo "↩️  Creando script de rollback..."

cat > rollback-modularization.sh << 'EOF'
#!/bin/bash

echo "↩️  EJECUTANDO ROLLBACK DE MODULARIZACIÓN..."

# Restore original build.gradle
if [ -f "build.gradle.backup" ]; then
    mv build.gradle.backup build.gradle
    echo "✅ build.gradle restaurado"
fi

# Remove modular structure
rm -rf bps-shared/ bps-core/ bps-application/
rm -f settings.gradle

# Restore from backup if exists
BACKUP_DIR=$(ls -1 backup-src-* 2>/dev/null | head -1)
if [ -n "$BACKUP_DIR" ]; then
    echo "📦 Restaurando desde backup: $BACKUP_DIR"
    # rm -rf src/
    # mv $BACKUP_DIR src/
fi

echo "↩️  Rollback completado"
EOF

chmod +x rollback-modularization.sh

echo ""
echo "🎉 FASE 1 COMPLETADA EXITOSAMENTE!"
echo "=================================="
echo ""
echo "📁 Estructura creada:"
echo "   ├── bps-shared/ (módulos compartidos)"
echo "   ├── bps-core/ (módulos core)"
echo "   ├── bps-application/ (aplicación principal)"
echo "   ├── settings.gradle (configuración multi-module)"
echo "   └── build.gradle (configuración root)"
echo ""
echo "🔧 Scripts disponibles:"
echo "   ├── validate-modularization.sh (validar estructura)"
echo "   └── rollback-modularization.sh (revertir cambios)"
echo ""
echo "📋 PRÓXIMOS PASOS:"
echo "   1. Ejecutar: ./validate-modularization.sh"
echo "   2. Corregir imports y dependencias si es necesario"
echo "   3. Continuar con Fase 2: Extracción de bounded contexts"
echo ""
echo "⚠️  IMPORTANTE: Revisar y ajustar imports en los archivos movidos" 