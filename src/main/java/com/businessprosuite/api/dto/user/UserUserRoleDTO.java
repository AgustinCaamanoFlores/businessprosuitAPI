package com.businessprosuite.api.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUserRoleDTO {
    private Integer userId;
    private Integer roleId;
}
