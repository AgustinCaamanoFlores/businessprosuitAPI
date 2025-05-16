package com.businessprosuite.api.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registeredAt;
    // Añade aquí otros campos que desees exponer
}