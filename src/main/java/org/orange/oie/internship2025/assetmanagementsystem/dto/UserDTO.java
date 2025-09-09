package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role; // mapped to RoleType enum in DB
    private String departmentName;  // read-friendly instead of just ID
    private Long departmentId;
}
