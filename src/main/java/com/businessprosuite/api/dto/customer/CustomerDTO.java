package com.businessprosuite.api.dto.customer;

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
    private Integer configCompanyId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String taxId;
    private Integer companyId;
    private String countryCode;
    private LocalDateTime regDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
