package com.businessprosuite.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Clase para respuestas estándar exitosas de la API.
 * Proporciona un formato consistente para todas las respuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private Long total;
    private Integer page;
    private Integer size;
    
    /**
     * Crea una respuesta exitosa con datos
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Operación exitosa")
                .data(data)
                .build();
    }
    
    /**
     * Crea una respuesta exitosa con datos y mensaje personalizado
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message(message)
                .data(data)
                .build();
    }
    
    /**
     * Crea una respuesta exitosa para operaciones de creación
     */
    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(201)
                .message("Recurso creado exitosamente")
                .data(data)
                .build();
    }
    
    /**
     * Crea una respuesta exitosa para operaciones de eliminación
     */
    public static <Void> ApiResponse<Void> deleted() {
        return ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(204)
                .message("Recurso eliminado exitosamente")
                .build();
    }
    
    /**
     * Crea una respuesta paginada
     */
    public static <T> ApiResponse<T> paginated(T data, Long total, Integer page, Integer size) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Consulta paginada exitosa")
                .data(data)
                .total(total)
                .page(page)
                .size(size)
                .build();
    }
} 