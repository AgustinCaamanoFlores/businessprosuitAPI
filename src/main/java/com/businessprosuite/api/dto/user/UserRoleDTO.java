package com.businessprosuite.api.dto.user;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDTO {
    private Integer id;
    private String name;
    private Set<Integer> userIds;
}
