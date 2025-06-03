package com.businessprosuite.api.dto.customer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Integer id;
    
    @NotNull(message = "El ID de configuración de empresa es obligatorio")
    private Integer configCompanyId;
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String name;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "El teléfono debe tener entre 8 y 15 dígitos")
    private String phone;
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String address;
    
    @Size(max = 50, message = "El ID fiscal no puede exceder 50 caracteres")
    private String taxId;
    
    @NotNull(message = "El ID de empresa es obligatorio")
    private Integer companyId;
    
    @NotBlank(message = "El código de país es obligatorio")
    @Size(min = 2, max = 3, message = "El código de país debe tener entre 2 y 3 caracteres")
    private String countryCode;
    
    private LocalDateTime regDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
