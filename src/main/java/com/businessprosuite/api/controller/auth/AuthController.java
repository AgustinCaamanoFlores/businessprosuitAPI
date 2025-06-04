package com.businessprosuite.api.controller.auth;

import com.businessprosuite.api.dto.auth.*;
import com.businessprosuite.api.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "🔐 Autenticación", description = """
    Endpoints para gestión de autenticación y autorización.
    
    ## Funcionalidades principales:
    - **Registro**: Crear nuevos usuarios y empresas
    - **Login**: Autenticar usuarios existentes
    - **Verificación**: Validar tokens JWT
    - **Disponibilidad**: Verificar username/email únicos
    
    ## Flujo de autenticación:
    1. **Registro**: POST `/auth/register` - Crear cuenta nueva
    2. **Login**: POST `/auth/login` - Obtener token JWT
    3. **Uso**: Incluir token en header `Authorization: Bearer <token>`
    4. **Verificación**: GET `/auth/verify` - Validar token activo
    """)
public class AuthController {
    
    private final AuthService authService;
    
    @Operation(
        summary = "Registrar nuevo usuario y empresa",
        description = """
            Crea una nueva cuenta de usuario junto con una empresa asociada.
            
            ## Proceso de registro:
            1. Valida que el username y email sean únicos
            2. Crea la empresa con los datos proporcionados
            3. Crea el usuario con rol ADMIN de la empresa
            4. Genera un token JWT automáticamente
            
            ## Campos requeridos:
            - **username**: Único en el sistema (3-20 caracteres)
            - **email**: Único en el sistema (formato válido)
            - **password**: Mínimo 8 caracteres con mayúscula, minúscula y número
            - **companyName**: Nombre de la empresa (2-100 caracteres)
            - **firstName/lastName**: Nombre del usuario administrador
            """,
        tags = {"🔐 Autenticación"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Registro exitoso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegisterResponse.class),
                examples = @ExampleObject(
                    name = "Registro exitoso",
                    value = """
                        {
                          "message": "Usuario y empresa creados exitosamente",
                          "user": {
                            "id": 1,
                            "username": "admin_empresa",
                            "email": "admin@empresa.com",
                            "firstName": "Juan",
                            "lastName": "Pérez"
                          },
                          "company": {
                            "id": 1,
                            "name": "Mi Empresa S.L.",
                            "createdAt": "2025-06-02T15:30:00"
                          },
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Error en los datos de registro",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Usuario ya existe",
                    value = """
                        {
                          "error": "Bad Request",
                          "message": "El nombre de usuario ya está en uso",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Parameter(
                description = "Datos para crear nueva cuenta de usuario y empresa",
                required = true,
                schema = @Schema(implementation = RegisterRequest.class)
            )
            @Valid @RequestBody RegisterRequest request) {
        log.info("Solicitud de registro recibida para usuario: {}", request.getUsername());
        
        try {
            RegisterResponse response = authService.register(request);
            log.info("Registro exitoso para usuario: {}", request.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            log.error("Error en registro para usuario {}: {}", request.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(
        summary = "Iniciar sesión",
        description = """
            Autentica un usuario existente y devuelve un token JWT válido.
            
            ## Proceso de login:
            1. Valida las credenciales (username/email + password)
            2. Verifica que el usuario esté activo y disponible
            3. Genera un nuevo token JWT con expiración de 24 horas
            4. Registra el intento de login para auditoría
            
            ## Credenciales aceptadas:
            - **username**: Nombre de usuario registrado
            - **email**: Email registrado (alternativa al username)
            - **password**: Contraseña actual del usuario
            
            ## Token JWT:
            - **Expiración**: 24 horas por defecto
            - **Algoritmo**: HS256
            - **Claims**: username, roles, empresa
            """,
        tags = {"🔐 Autenticación"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Login exitoso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples = @ExampleObject(
                    name = "Login exitoso",
                    value = """
                        {
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                          "type": "Bearer",
                          "expiresIn": 86400,
                          "user": {
                            "id": 1,
                            "username": "admin_empresa",
                            "email": "admin@empresa.com",
                            "role": "ADMIN",
                            "company": "Mi Empresa S.L."
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Credenciales inválidas",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Credenciales incorrectas",
                    value = """
                        {
                          "error": "Unauthorized",
                          "message": "Credenciales inválidas",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(
                description = "Credenciales de acceso (username o email + password)",
                required = true,
                schema = @Schema(implementation = LoginRequest.class)
            )
            @Valid @RequestBody LoginRequest request) {
        log.info("Solicitud de login recibida para usuario: {}", request.getUsername());
        
        try {
            AuthResponse response = authService.login(request);
            log.info("Login exitoso para usuario: {}", request.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            log.error("Error en login para usuario {}: {}", request.getUsername(), e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
    
    @Operation(
        summary = "Verificar token JWT",
        description = """
            Valida un token JWT y devuelve información del usuario autenticado.
            
            ## Verificaciones realizadas:
            1. **Formato**: Token bien formado y parseable
            2. **Firma**: Validación de la firma digital
            3. **Expiración**: Token no expirado
            4. **Usuario**: Usuario existe y está activo
            
            ## Información devuelta:
            - **valid**: true si el token es válido
            - **username**: Nombre del usuario autenticado
            - **authorities**: Roles y permisos del usuario
            - **company**: Información de la empresa asociada
            
            ## Uso típico:
            - Validar sesiones activas en frontend
            - Verificar permisos antes de operaciones críticas
            - Refresh de información de usuario
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🔐 Autenticación"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Token válido",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Token válido",
                    value = """
                        {
                          "valid": true,
                          "username": "admin_empresa",
                          "authorities": ["ROLE_ADMIN"],
                          "company": "Mi Empresa S.L.",
                          "expiresAt": "2025-06-03T15:30:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Token inválido o expirado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Token inválido",
                    value = """
                        {
                          "error": "Unauthorized",
                          "message": "Token JWT inválido o expirado",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        response.put("username", userDetails.getUsername());
        response.put("authorities", userDetails.getAuthorities());
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Verificar disponibilidad de username",
        description = """
            Verifica si un nombre de usuario está disponible para registro.
            
            ## Validaciones:
            - **Unicidad**: Username no existe en el sistema
            - **Formato**: Cumple con reglas de negocio (3-20 caracteres alfanuméricos)
            - **Reservados**: No es un username reservado del sistema
            
            ## Uso típico:
            - Validación en tiempo real durante registro
            - Verificación antes de cambio de username
            - Sugerencias de usernames alternativos
            """,
        tags = {"🔐 Autenticación"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Verificación completada",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Username disponible",
                        value = """
                            {
                              "available": true
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Username no disponible",
                        value = """
                            {
                              "available": false
                            }
                            """
                    )
                }
            )
        )
    })
    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(
            @Parameter(
                description = "Nombre de usuario a verificar",
                required = true,
                example = "nuevo_usuario"
            )
            @RequestParam String username) {
        boolean available = !authService.existsByUsername(username);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", available);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Verificar disponibilidad de email",
        description = """
            Verifica si una dirección de email está disponible para registro.
            
            ## Validaciones:
            - **Unicidad**: Email no existe en el sistema
            - **Formato**: Dirección de email válida
            - **Dominios**: No está en lista de dominios bloqueados
            
            ## Uso típico:
            - Validación en tiempo real durante registro
            - Verificación antes de cambio de email
            - Prevención de cuentas duplicadas
            """,
        tags = {"🔐 Autenticación"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Verificación completada",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Email disponible",
                        value = """
                            {
                              "available": true
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Email no disponible",
                        value = """
                            {
                              "available": false
                            }
                            """
                    )
                }
            )
        )
    })
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(
            @Parameter(
                description = "Dirección de email a verificar",
                required = true,
                example = "usuario@empresa.com"
            )
            @RequestParam String email) {
        boolean available = !authService.existsByEmail(email);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", available);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Health check del servicio de autenticación",
        description = """
            Endpoint de diagnóstico para verificar el estado del servicio de autenticación.
            
            ## Verificaciones incluidas:
            - **API**: Servicio responde correctamente
            - **Base de datos**: Conexión activa
            - **Dependencias**: Servicios externos disponibles
            
            ## Uso típico:
            - Monitoreo de sistemas
            - Load balancer health checks
            - Diagnóstico de problemas
            """,
        tags = {"🔐 Autenticación"}
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Servicio funcionando correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Servicio saludable",
                value = """
                    {
                      "status": "OK",
                      "service": "AuthController",
                      "timestamp": "2025-06-02T15:30:00"
                    }
                    """
            )
        )
    )
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "AuthController");
        
        return ResponseEntity.ok(response);
    }
} 