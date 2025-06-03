package com.businessprosuite.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.version:V0.2}")
    private String appVersion;

    @Value("${app.description:BusinessProSuite API - Sistema integral de gestión empresarial}")
    private String appDescription;

    @Bean
    public OpenAPI businessProSuiteOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        createServer("http://localhost:8080", "Servidor de Desarrollo"),
                        createServer("https://api-dev.businessprosuite.com", "Servidor de Testing"),
                        createServer("https://api.businessprosuite.com", "Servidor de Producción")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createJWTScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("BusinessProSuite API")
                .description("""
                        ## Descripción
                        
                        BusinessProSuite API es un sistema integral de gestión empresarial que proporciona 
                        servicios completos para la administración de:
                        
                        - **🔐 Autenticación y Seguridad**: JWT, roles, permisos
                        - **🏢 Gestión de Empresas**: Información corporativa, sucursales
                        - **👥 Recursos Humanos**: Empleados, nóminas, evaluaciones
                        - **💰 Finanzas**: Contabilidad, facturación, reportes
                        - **📦 Inventario**: Gestión de productos, stock, almacenes
                        - **👤 Gestión de Usuarios**: Perfiles, roles, configuraciones
                        - **📊 Analytics**: Reportes, dashboards, métricas
                        
                        ## Características Técnicas
                        
                        - **Framework**: Spring Boot 3.4.4 + Java 17
                        - **Base de Datos**: MySQL con Flyway migrations
                        - **Seguridad**: Spring Security + JWT stateless
                        - **Documentación**: OpenAPI 3.0 con Swagger UI
                        - **Testing**: JUnit 5 + Mockito + JaCoCo coverage
                        - **Cache**: Caffeine para optimización de performance
                        
                        ## Autenticación
                        
                        La API utiliza autenticación JWT (Bearer Token). Para acceder a los endpoints protegidos:
                        
                        1. Obtén un token JWT usando `/auth/login`
                        2. Incluye el token en el header: `Authorization: Bearer <tu-token>`
                        3. El token expira en 24 horas por defecto
                        
                        ## Códigos de Respuesta
                        
                        - **200 OK**: Operación exitosa
                        - **201 Created**: Recurso creado exitosamente
                        - **400 Bad Request**: Error en los datos enviados
                        - **401 Unauthorized**: Token inválido o expirado
                        - **403 Forbidden**: Sin permisos para la operación
                        - **404 Not Found**: Recurso no encontrado
                        - **500 Internal Server Error**: Error interno del servidor
                        """)
                .version(appVersion)
                .contact(new Contact()
                        .name("BusinessProSuite Development Team")
                        .email("dev@businessprosuite.com")
                        .url("https://businessprosuite.com"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }

    private Server createServer(String url, String description) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(description);
        return server;
    }

    private SecurityScheme createJWTScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("""
                        ## Autenticación JWT
                        
                        Introduce tu token JWT en el siguiente formato:
                        ```
                        Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
                        ```
                        
                        **Nota**: No incluyas la palabra 'Bearer' aquí, se agregará automáticamente.
                        """);
    }
} 