#!/bin/bash

# =========================================================================
# SCRIPT DE CONFIGURACIÓN - ENTORNO DE MODULARIZACIÓN BUSINESSPROSUITE API
# =========================================================================

echo "🔧 CONFIGURANDO ENTORNO DE MODULARIZACIÓN"
echo "=========================================="

# 1. VERIFICAR PRERREQUISITOS
echo "📋 Verificando prerrequisitos..."

# Verificar que estamos en el directorio correcto
if [ ! -f "build.gradle" ] || [ ! -d "src/main/java/com/businessprosuite/api" ]; then
    echo "❌ Error: No se detectó un proyecto BusinessProSuiteAPI válido"
    echo "   Asegúrate de ejecutar desde el directorio raíz del proyecto"
    exit 1
fi

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java no está instalado o no está en PATH"
    exit 1
fi
echo "✅ Java: $(java -version 2>&1 | head -n 1)"

# Verificar Gradle
if ! command -v ./gradlew &> /dev/null; then
    echo "❌ Error: Gradle wrapper no encontrado"
    exit 1
fi
echo "✅ Gradle: $(./gradlew --version | grep Gradle)"

# Verificar Python (para análisis de dependencias)
if command -v python3 &> /dev/null; then
    echo "✅ Python3: $(python3 --version)"
    
    # Verificar/instalar dependencias Python
    echo "📦 Instalando dependencias Python para análisis..."
    python3 -c "import matplotlib, networkx, seaborn, numpy" 2>/dev/null || {
        echo "⚠️  Instalando dependencias faltantes de Python..."
        pip3 install matplotlib networkx seaborn numpy 2>/dev/null || {
            echo "⚠️  No se pudieron instalar dependencias Python automáticamente"
            echo "   Instala manualmente: pip3 install matplotlib networkx seaborn numpy"
        }
    }
else
    echo "⚠️  Python3 no encontrado - análisis de dependencias deshabilitado"
fi

# 2. CREAR ESTRUCTURA DE DIRECTORIOS
echo ""
echo "📁 Creando estructura de directorios de trabajo..."

mkdir -p modularization-reports
mkdir -p modularization-backups
mkdir -p modularization-logs

# 3. HACER EJECUTABLES LOS SCRIPTS
echo "⚙️  Configurando permisos de scripts..."

chmod +x modularization-scripts/phase1-setup.sh
chmod +x modularization-scripts/dependency-analysis.py

# Crear script de análisis rápido
cat > quick-analysis.sh << 'EOF'
#!/bin/bash

echo "🔍 EJECUTANDO ANÁLISIS RÁPIDO DE MODULARIDAD"
echo "============================================"

# Análisis básico de estructura
echo "📊 Analizando estructura actual..."
find src/main/java/com/businessprosuite/api -type d -name "*" | grep -E "(model|dto|service|controller|repository)" | sort

echo ""
echo "📈 Conteo de archivos por módulo:"
for module in config company customer finance hr inventory asset leasing workflow security analytics audit user; do
    count=$(find src/main/java/com/businessprosuite/api -name "*" -path "*/$module/*" -type f | wc -l)
    if [ $count -gt 0 ]; then
        printf "%-12s: %3d archivos\n" "$module" "$count"
    fi
done

echo ""
echo "🔍 Detectando God Objects potenciales..."
grep -r "ConfigCompany" src/main/java/com/businessprosuite/api --include="*.java" | wc -l | xargs echo "ConfigCompany referencias:"
grep -r "Company.*get" src/main/java/com/businessprosuite/api --include="*.java" | wc -l | xargs echo "Company accesos:"

echo ""
echo "💡 Para análisis completo ejecuta:"
echo "   python3 modularization-scripts/dependency-analysis.py"
EOF

chmod +x quick-analysis.sh

# Crear script principal de modularización
cat > start-modularization.sh << 'EOF'
#!/bin/bash

echo "🚀 INICIADOR DE MODULARIZACIÓN - BUSINESSPROSUITE API"
echo "===================================================="

echo ""
echo "Selecciona una opción:"
echo "1) 🔍 Análisis rápido de estructura actual"
echo "2) 📊 Análisis completo de dependencias (requiere Python)"  
echo "3) 🏗️  Ejecutar Fase 1: Setup infraestructura multi-module"
echo "4) ↩️  Rollback de modularización"
echo "5) ❌ Salir"
echo ""

read -p "Opción [1-5]: " choice

case $choice in
    1)
        echo "🔍 Ejecutando análisis rápido..."
        ./quick-analysis.sh
        ;;
    2)
        echo "📊 Ejecutando análisis completo de dependencias..."
        if command -v python3 &> /dev/null; then
            python3 modularization-scripts/dependency-analysis.py
        else
            echo "❌ Python3 no está disponible"
        fi
        ;;
    3)
        echo "🏗️  Ejecutando Fase 1 de modularización..."
        read -p "⚠️  Esto modificará la estructura del proyecto. ¿Continuar? [y/N]: " confirm
        if [[ $confirm =~ ^[Yy]$ ]]; then
            ./modularization-scripts/phase1-setup.sh
        else
            echo "Operación cancelada"
        fi
        ;;
    4)
        echo "↩️  Ejecutando rollback..."
        if [ -f "rollback-modularization.sh" ]; then
            ./rollback-modularization.sh
        else
            echo "❌ Script de rollback no encontrado"
        fi
        ;;
    5)
        echo "👋 Saliendo..."
        exit 0
        ;;
    *)
        echo "❌ Opción inválida"
        ;;
esac
EOF

chmod +x start-modularization.sh

# 4. CREAR CONFIGURACIÓN GRADLE PARA ANÁLISIS
echo "⚙️  Creando configuración para análisis..."

cat > analyze-dependencies.gradle << 'EOF'
// Task para analizar dependencias entre módulos
task analyzeDependencies {
    doLast {
        println "🔍 ANÁLISIS DE DEPENDENCIAS"
        println "=========================="
        
        // Listar todas las clases Java
        def javaFiles = fileTree(dir: 'src/main/java', include: '**/*.java')
        def modules = ['config', 'company', 'customer', 'finance', 'hr', 'inventory', 
                      'asset', 'leasing', 'workflow', 'security', 'analytics', 'audit']
        
        def dependencies = [:]
        
        javaFiles.each { file ->
            def content = file.text
            def imports = content.findAll(/import com\.businessprosuite\.api\.([^;]+);/) { match, importPath ->
                return importPath
            }
            
            def currentModule = null
            modules.each { module ->
                if (file.path.contains("/${module}/")) {
                    currentModule = module
                }
            }
            
            if (currentModule) {
                if (!dependencies[currentModule]) {
                    dependencies[currentModule] = [] as Set
                }
                
                imports.each { importPath ->
                    modules.each { module ->
                        if (importPath.contains(module) && module != currentModule) {
                            dependencies[currentModule].add(module)
                        }
                    }
                }
            }
        }
        
        println "\n📊 MATRIZ DE DEPENDENCIAS:"
        dependencies.each { module, deps ->
            if (deps.size() > 0) {
                println "${module}: ${deps.join(', ')}"
            }
        }
        
        println "\n⚠️  MÓDULOS CON ALTO ACOPLAMIENTO:"
        dependencies.findAll { module, deps -> deps.size() >= 3 }.each { module, deps ->
            println "🔴 ${module}: ${deps.size()} dependencias"
        }
    }
}
EOF

# 5. CREAR CHECKLIST DE VALIDACIÓN
cat > MODULARIZATION_CHECKLIST.md << 'EOF'
# ✅ CHECKLIST DE MODULARIZACIÓN - BusinessProSuiteAPI

## PRE-MODULARIZACIÓN
- [ ] ✅ Proyecto builds correctamente (`./gradlew build`)
- [ ] ✅ Tests pasan (`./gradlew test`)  
- [ ] ✅ Backup creado de estructura actual
- [ ] ✅ Análisis de dependencias ejecutado
- [ ] ✅ God Objects identificados
- [ ] ✅ Plan de modularización revisado

## FASE 1: INFRAESTRUCTURA MULTI-MODULE
- [ ] 🏗️ Estructura de directorios creada
- [ ] 🏗️ settings.gradle configurado
- [ ] 🏗️ Root build.gradle actualizado  
- [ ] 🏗️ Build.gradle por módulo creado
- [ ] 🏗️ Shared modules configurados
- [ ] 🏗️ Core modules extraídos
- [ ] 🏗️ Application module configurado

### Validaciones Fase 1
- [ ] 🧪 Proyecto compila sin errores
- [ ] 🧪 No hay dependencias circulares
- [ ] 🧪 Tests básicos pasan
- [ ] 🧪 IDE reconoce estructura modular

## FASE 2: BOUNDED CONTEXTS
- [ ] 🎯 Company domain extraído
- [ ] 🎯 User domain extraído  
- [ ] 🎯 Config domain refactorizado
- [ ] 🎯 Event-driven communication implementado
- [ ] 🎯 APIs de módulo definidas

### Validaciones Fase 2
- [ ] 🧪 Cada módulo compila independientemente
- [ ] 🧪 Events fluyen correctamente
- [ ] 🧪 No hay imports directos entre business modules
- [ ] 🧪 Interfaces bien definidas

## FASE 3: BUSINESS MODULES
- [ ] 💼 Finance module extraído
- [ ] 💼 HR module extraído
- [ ] 💼 Inventory module extraído
- [ ] 💼 Customer module extraído
- [ ] 💼 Asset module extraído

### Validaciones Fase 3
- [ ] 🧪 Módulos de negocio independientes
- [ ] 🧪 God Objects fragmentados
- [ ] 🧪 Performance mantenido
- [ ] 🧪 APIs REST funcionando

## POST-MODULARIZACIÓN
- [ ] 🎉 Métricas de acoplamiento mejoradas
- [ ] 🎉 Build time reducido
- [ ] 🎉 Tests por módulo implementados
- [ ] 🎉 Documentation actualizada
- [ ] 🎉 Team alignment en nuevas estructuras

## PREPARACIÓN MICROSERVICIOS
- [ ] 🚀 Database per service strategy definida
- [ ] 🚀 Service discovery preparado
- [ ] 🚀 Inter-service communication patterns
- [ ] 🚀 Monitoring per module
- [ ] 🚀 Independent deployment capability

---

**NOTAS:**
- Ejecutar validaciones después de cada fase
- Rollback disponible en cualquier momento
- Documentar issues y resoluciones
EOF

echo ""
echo "🎉 CONFIGURACIÓN COMPLETADA!"
echo "============================"
echo ""
echo "📂 Estructura creada:"
echo "   ├── modularization-reports/ (reportes de análisis)"
echo "   ├── modularization-backups/ (backups automáticos)"
echo "   ├── modularization-logs/ (logs de proceso)"
echo "   ├── quick-analysis.sh (análisis rápido)"
echo "   ├── start-modularization.sh (script principal)"
echo "   ├── analyze-dependencies.gradle (análisis gradle)"
echo "   └── MODULARIZATION_CHECKLIST.md (checklist)"
echo ""
echo "🚀 PRÓXIMOS PASOS:"
echo "   1. Ejecutar: ./start-modularization.sh"
echo "   2. Comenzar con análisis rápido (opción 1)"
echo "   3. Revisar MODULARIZATION_CHECKLIST.md"
echo "   4. Proceder con Fase 1 cuando esté listo"
echo ""
echo "💡 COMANDOS ÚTILES:"
echo "   ./quick-analysis.sh          # Análisis rápido"
echo "   ./gradlew analyzeDependencies # Análisis con Gradle"
echo "   ./start-modularization.sh    # Menú principal"
EOF 