package org.orange.oie.internship2025.assetmanagementsystem.dto;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String departmentName;

}
