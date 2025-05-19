// src/main/java/com/businessprosuite/api/dto/UserDto.java
package com.businessprosuite.api.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Integer id;
    private Integer companyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer residenceCountryId;
    private Byte consent;
    private LocalDateTime consentDate;
    private String address;
    private String idNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
