// src/main/java/com/businessprosuite/api/dto/company/CompanyDTO.java
package com.businessprosuite.api.dto.company;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDTO {
    private Integer id;
    
    @NotNull(message = "El ID de configuración de empresa es obligatorio")
    private Integer configCompanyId;
    
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String name;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String address;
    
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "El teléfono debe tener entre 8 y 15 dígitos")
    private String phone;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @NotBlank(message = "El ID fiscal es obligatorio")
    @Size(max = 50, message = "El ID fiscal no puede exceder 50 caracteres")
    private String taxId;
    
    @NotBlank(message = "El código de país es obligatorio")
    @Size(min = 2, max = 3, message = "El código de país debe tener entre 2 y 3 caracteres")
    private String countryCode;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
