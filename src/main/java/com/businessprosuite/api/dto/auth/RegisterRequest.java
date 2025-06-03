package com.businessprosuite.api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    
    // Datos del usuario
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 45, message = "El nombre de usuario debe tener entre 3 y 45 caracteres")
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "La contraseña debe contener al menos: 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial")
    private String password;
    
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 255, message = "El nombre completo no puede exceder 255 caracteres")
    private String fullName;
    
    // Datos de la empresa
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 255, message = "El nombre de la empresa no puede exceder 255 caracteres")
    private String companyName;
    
    @NotBlank(message = "El email de la empresa es obligatorio")
    @Email(message = "El email de la empresa debe tener un formato válido")
    @Size(max = 100, message = "El email de la empresa no puede exceder 100 caracteres")
    private String companyEmail;
    
    @NotBlank(message = "La dirección de la empresa es obligatoria")
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String companyAddress;
    
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "El teléfono debe tener entre 8 y 15 dígitos")
    private String companyPhone;
    
    @Size(max = 50, message = "El ID fiscal no puede exceder 50 caracteres")
    private String companyTaxId;
    
    @NotBlank(message = "El código de país es obligatorio")
    @Size(min = 2, max = 3, message = "El código de país debe tener entre 2 y 3 caracteres")
    private String countryCode;
    
    // Configuración opcional
    @Builder.Default
    private String baseCurrency = "EUR";
    
    @Builder.Default
    private String region = "EU";
} 