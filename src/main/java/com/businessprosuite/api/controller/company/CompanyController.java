// src/main/java/com/businessprosuite/api/controller/company/CompanyController.java
package com.businessprosuite.api.controller.company;

import com.businessprosuite.api.dto.company.CompanyDTO;
import com.businessprosuite.api.service.company.CompanyService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "🏢 Gestión de Empresas", description = """
    Endpoints para la administración integral de empresas.
    
    ## Funcionalidades principales:
    - **CRUD Completo**: Crear, leer, actualizar y eliminar empresas
    - **Gestión de Información**: Datos corporativos, contacto, fiscales
    - **Asociaciones**: Usuarios, sucursales, configuraciones
    - **Auditoría**: Tracking de cambios y modificaciones
    
    ## Campos principales:
    - **Información básica**: Nombre, dirección, teléfono, email
    - **Datos fiscales**: ID fiscal, país, configuración legal
    - **Metadatos**: Fechas de creación/modificación, estado
    - **Relaciones**: Usuarios asociados, configuraciones específicas
    """)
public class CompanyController {

    private final CompanyService service;
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @Operation(
        summary = "Listar todas las empresas",
        description = """
            Obtiene una lista completa de todas las empresas registradas en el sistema.
            
            ## Información incluida:
            - **Datos básicos**: ID, nombre, dirección, contacto
            - **Información fiscal**: ID tributario, país de registro
            - **Metadatos**: Fechas de creación y última modificación
            - **Estado**: Empresas activas e inactivas
            
            ## Uso típico:
            - Dashboards administrativos
            - Selección de empresa en formularios
            - Reportes y analytics empresariales
            - Auditorías de sistema
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🏢 Gestión de Empresas"}
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Lista de empresas obtenida exitosamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Lista de empresas",
                value = """
                    [
                      {
                        "id": 1,
                        "name": "TechCorp S.L.",
                        "address": "Calle Mayor 123, Madrid",
                        "phone": "+34 91 123 4567",
                        "email": "contacto@techcorp.es",
                        "taxId": "B12345678",
                        "country": "España",
                        "createdAt": "2025-01-15T10:30:00",
                        "updatedAt": "2025-06-02T15:45:00"
                      },
                      {
                        "id": 2,
                        "name": "Innovate Ltd.",
                        "address": "Innovation Plaza 456, Barcelona",
                        "phone": "+34 93 987 6543",
                        "email": "info@innovate.com",
                        "taxId": "B87654321",
                        "country": "España",
                        "createdAt": "2025-02-20T14:20:00",
                        "updatedAt": "2025-05-30T12:10:00"
                      }
                    ]
                    """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
        summary = "Obtener empresa por ID",
        description = """
            Recupera la información detallada de una empresa específica mediante su ID único.
            
            ## Información detallada incluida:
            - **Datos corporativos**: Nombre legal, dirección fiscal, contacto
            - **Información regulatoria**: ID fiscal, país, cumplimiento legal
            - **Configuraciones**: Preferencias específicas de la empresa
            - **Relaciones**: Usuarios asociados, sucursales vinculadas
            - **Auditoría**: Historial de cambios y modificaciones
            
            ## Casos de uso:
            - Perfiles de empresa en dashboards
            - Edición de información corporativa
            - Generación de documentos legales
            - Integración con sistemas externos
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🏢 Gestión de Empresas"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Empresa encontrada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyDTO.class),
                examples = @ExampleObject(
                    name = "Empresa detallada",
                    value = """
                        {
                          "id": 1,
                          "name": "TechCorp S.L.",
                          "address": "Calle Mayor 123, 28001 Madrid, España",
                          "phone": "+34 91 123 4567",
                          "email": "contacto@techcorp.es",
                          "taxId": "B12345678",
                          "country": "España",
                          "website": "https://techcorp.es",
                          "industry": "Tecnología",
                          "employees": 150,
                          "foundedYear": 2018,
                          "description": "Empresa líder en soluciones tecnológicas innovadoras",
                          "createdAt": "2025-01-15T10:30:00",
                          "updatedAt": "2025-06-02T15:45:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Empresa no encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Empresa no encontrada",
                    value = """
                        {
                          "error": "Not Found",
                          "message": "No se encontró la empresa con ID: 999",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getById(
            @Parameter(
                description = "ID único de la empresa a consultar",
                required = true,
                example = "1"
            )
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
        summary = "Crear nueva empresa",
        description = """
            Registra una nueva empresa en el sistema con toda su información corporativa.
            
            ## Proceso de creación:
            1. **Validación**: Verificación de datos obligatorios y formatos
            2. **Unicidad**: Comprobación de ID fiscal único
            3. **Configuración**: Setup inicial de preferencias por defecto
            4. **Relaciones**: Vinculación con usuario creador
            5. **Auditoría**: Registro de evento de creación
            
            ## Campos obligatorios:
            - **name**: Nombre legal de la empresa (2-100 caracteres)
            - **email**: Email corporativo válido
            - **taxId**: Identificador fiscal único
            - **address**: Dirección fiscal completa
            
            ## Campos opcionales:
            - **phone**: Teléfono de contacto
            - **website**: Sitio web corporativo
            - **industry**: Sector de actividad
            - **description**: Descripción de la empresa
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🏢 Gestión de Empresas"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Empresa creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyDTO.class),
                examples = @ExampleObject(
                    name = "Empresa creada",
                    value = """
                        {
                          "id": 3,
                          "name": "Nueva Empresa S.A.",
                          "address": "Avenida Principal 789, Valencia",
                          "phone": "+34 96 111 2233",
                          "email": "info@nuevaempresa.com",
                          "taxId": "A11223344",
                          "country": "España",
                          "website": "https://nuevaempresa.com",
                          "industry": "Manufactura",
                          "createdAt": "2025-06-02T15:30:00",
                          "updatedAt": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Error en los datos de entrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Datos inválidos",
                    value = """
                        {
                          "error": "Bad Request",
                          "message": "El ID fiscal ya está registrado en el sistema",
                          "details": {
                            "field": "taxId",
                            "rejectedValue": "B12345678",
                            "constraint": "unique"
                          },
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<CompanyDTO> create(
            @Parameter(
                description = "Datos de la nueva empresa a crear",
                required = true,
                schema = @Schema(implementation = CompanyDTO.class)
            )
            @Valid @RequestBody CompanyDTO dto) {
        CompanyDTO created = service.create(dto);
        URI location = URI.create("/api/companies/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(
        summary = "Actualizar empresa existente",
        description = """
            Modifica la información de una empresa existente en el sistema.
            
            ## Proceso de actualización:
            1. **Verificación**: Confirma que la empresa existe
            2. **Validación**: Verifica nuevos datos y constraints
            3. **Merge**: Combina cambios con información existente
            4. **Persistencia**: Guarda cambios en base de datos
            5. **Auditoría**: Registra modificación con timestamp
            
            ## Campos modificables:
            - **Información de contacto**: Dirección, teléfono, email, website
            - **Datos descriptivos**: Nombre, industria, descripción
            - **Configuraciones**: Preferencias específicas
            
            ## Restricciones:
            - **ID**: No modificable una vez creada
            - **TaxID**: Modificable solo bajo condiciones especiales
            - **Fechas**: CreatedAt no modificable, updatedAt automático
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🏢 Gestión de Empresas"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Empresa actualizada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyDTO.class),
                examples = @ExampleObject(
                    name = "Empresa actualizada",
                    value = """
                        {
                          "id": 1,
                          "name": "TechCorp Global S.L.",
                          "address": "Calle Mayor 123, 28001 Madrid, España",
                          "phone": "+34 91 123 4567",
                          "email": "contacto@techcorp-global.es",
                          "taxId": "B12345678",
                          "country": "España",
                          "website": "https://techcorp-global.es",
                          "industry": "Tecnología e Innovación",
                          "description": "Empresa líder global en soluciones tecnológicas",
                          "createdAt": "2025-01-15T10:30:00",
                          "updatedAt": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Empresa no encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Empresa inexistente",
                    value = """
                        {
                          "error": "Not Found",
                          "message": "No se encontró la empresa con ID: 999",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> update(
            @Parameter(
                description = "ID de la empresa a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Integer id,
            @Parameter(
                description = "Nuevos datos de la empresa",
                required = true,
                schema = @Schema(implementation = CompanyDTO.class)
            )
            @Valid @RequestBody CompanyDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
        summary = "Eliminar empresa",
        description = """
            Elimina una empresa del sistema de forma permanente.
            
            ## ⚠️ OPERACIÓN CRÍTICA ⚠️
            
            Esta operación es **IRREVERSIBLE** y tiene impacto en cascada:
            
            ### Datos eliminados automáticamente:
            - **Usuarios asociados**: Todos los usuarios de la empresa
            - **Datos financieros**: Facturas, pagos, reportes
            - **Configuraciones**: Preferencias y settings específicos
            - **Relaciones**: Vínculos con otras entidades del sistema
            
            ### Consideraciones importantes:
            - **Backup**: Asegurar respaldo antes de eliminar
            - **Dependencias**: Verificar que no existan restricciones
            - **Auditoría**: El evento de eliminación queda registrado
            - **Reversión**: No hay manera de deshacer esta operación
            
            ### Uso recomendado:
            - Solo para empresas de prueba o datos incorrectos
            - Considerar "desactivar" en lugar de eliminar
            - Verificar autorización de usuario administrador
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        tags = {"🏢 Gestión de Empresas"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Empresa eliminada exitosamente",
            content = @Content()
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Empresa no encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Empresa inexistente",
                    value = """
                        {
                          "error": "Not Found",
                          "message": "No se encontró la empresa con ID: 999",
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto - No se puede eliminar",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Restricción de integridad",
                    value = """
                        {
                          "error": "Conflict",
                          "message": "No se puede eliminar la empresa porque tiene usuarios activos asociados",
                          "details": {
                            "activeUsers": 15,
                            "pendingInvoices": 3,
                            "suggestion": "Desactive la empresa en lugar de eliminarla"
                          },
                          "timestamp": "2025-06-02T15:30:00"
                        }
                        """
                )
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(
                description = "ID de la empresa a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}