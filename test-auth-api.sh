#!/bin/bash

# 🧪 Script de Prueba para la API de Autenticación BusinessProSuite
# Ejecutar desde la raíz del proyecto
# Requiere: curl, jq (opcional para formatear JSON)

BASE_URL="http://localhost:8080"

echo "🚀 Iniciando pruebas de la API de Autenticación BusinessProSuite"
echo "=================================================="

# Función para mostrar respuestas formateadas
show_response() {
    if command -v jq &> /dev/null; then
        echo "$1" | jq .
    else
        echo "$1"
    fi
}

# 1. Verificar salud del servicio
echo ""
echo "📡 1. Verificando salud del servicio de autenticación..."
health_response=$(curl -s "$BASE_URL/auth/health")
echo "Respuesta:"
show_response "$health_response"

# 2. Verificar disponibilidad de username
echo ""
echo "👤 2. Verificando disponibilidad de username 'admin'..."
username_response=$(curl -s "$BASE_URL/auth/check-username?username=admin")
echo "Respuesta:"
show_response "$username_response"

# 3. Verificar disponibilidad de email
echo ""
echo "📧 3. Verificando disponibilidad de email 'admin@example.com'..."
email_response=$(curl -s "$BASE_URL/auth/check-email?email=admin@example.com")
echo "Respuesta:"
show_response "$email_response"

# 4. Registrar nuevo usuario y empresa
echo ""
echo "📝 4. Registrando nuevo usuario y empresa..."
register_response=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin_test",
    "email": "admin@testcompany.com", 
    "password": "TestPass123!",
    "fullName": "Administrador Test",
    "companyName": "Test Company S.L.",
    "companyEmail": "contacto@testcompany.com",
    "companyAddress": "Calle Test 123, Madrid",
    "companyPhone": "+34123456789",
    "companyTaxId": "B12345678",
    "countryCode": "ES",
    "baseCurrency": "EUR",
    "region": "EU"
  }')

echo "Respuesta del registro:"
show_response "$register_response"

# Verificar si el registro fue exitoso
if echo "$register_response" | grep -q "Usuario registrado exitosamente"; then
    echo "✅ Registro exitoso!"
    
    # 5. Login con el usuario registrado
    echo ""
    echo "🔐 5. Iniciando sesión con el usuario registrado..."
    login_response=$(curl -s -X POST "$BASE_URL/auth/login" \
      -H "Content-Type: application/json" \
      -d '{
        "username": "admin_test",
        "password": "TestPass123!"
      }')
    
    echo "Respuesta del login:"
    show_response "$login_response"
    
    # Extraer token del response
    if command -v jq &> /dev/null; then
        TOKEN=$(echo "$login_response" | jq -r '.token')
    else
        TOKEN=$(echo "$login_response" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
    fi
    
    if [ "$TOKEN" != "null" ] && [ "$TOKEN" != "" ]; then
        echo "✅ Login exitoso! Token obtenido."
        
        # 6. Verificar token
        echo ""
        echo "🔍 6. Verificando token JWT..."
        verify_response=$(curl -s -X GET "$BASE_URL/auth/verify" \
          -H "Authorization: Bearer $TOKEN")
        
        echo "Respuesta de verificación:"
        show_response "$verify_response"
        
        # 7. Probar endpoints protegidos
        echo ""
        echo "🏢 7. Probando endpoint protegido - Listar empresas..."
        companies_response=$(curl -s -X GET "$BASE_URL/api/companies" \
          -H "Authorization: Bearer $TOKEN")
        
        echo "Respuesta de empresas:"
        show_response "$companies_response"
        
        # 8. Crear nueva empresa usando el token
        echo ""
        echo "🏗️ 8. Creando nueva empresa con token..."
        new_company_response=$(curl -s -X POST "$BASE_URL/api/companies" \
          -H "Authorization: Bearer $TOKEN" \
          -H "Content-Type: application/json" \
          -d '{
            "configCompanyId": 1,
            "name": "Nueva Empresa API Test",
            "address": "Avenida API 456",
            "phone": "+34987654321", 
            "email": "nueva@apitest.com",
            "taxId": "B98765432",
            "countryCode": "ES"
          }')
        
        echo "Respuesta de nueva empresa:"
        show_response "$new_company_response"
        
    else
        echo "❌ Error: No se pudo obtener el token del login"
    fi
    
else
    echo "❌ Error en el registro. Posible causa:"
    echo "- El usuario 'admin_test' ya existe"
    echo "- Datos inválidos"
    echo "- Base de datos no disponible"
    echo "- El país 'ES' no existe en la base de datos"
    
    # Intentar login con usuario existente
    echo ""
    echo "🔄 Intentando login con credenciales existentes..."
    login_existing=$(curl -s -X POST "$BASE_URL/auth/login" \
      -H "Content-Type: application/json" \
      -d '{
        "username": "admin_test",
        "password": "TestPass123!"
      }')
    
    echo "Respuesta del login:"
    show_response "$login_existing"
fi

echo ""
echo "=================================================="
echo "🏁 Pruebas completadas."
echo ""
echo "📋 Endpoints disponibles:"
echo "  POST /auth/register  - Registro de usuario y empresa"
echo "  POST /auth/login     - Login de usuario"
echo "  GET  /auth/verify    - Verificar token JWT"
echo "  GET  /auth/health    - Salud del servicio"
echo "  GET  /auth/check-username - Verificar disponibilidad username"
echo "  GET  /auth/check-email    - Verificar disponibilidad email"
echo ""
echo "🔧 Para ver logs detallados: docker logs businessprosuite-api"
echo "📖 Para más información: ver API_DOCUMENTATION.md" 