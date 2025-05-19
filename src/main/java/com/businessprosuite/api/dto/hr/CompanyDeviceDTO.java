package com.businessprosuite.api.dto.hr;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDeviceDTO {
    private Integer id;
    private String location;     // hrDeviceLocation
    private String type;         // deviceType
    private Byte active;         // hrDeviceActive
}
