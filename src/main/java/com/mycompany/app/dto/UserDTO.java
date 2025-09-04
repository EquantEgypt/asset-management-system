package com.mycompany.app.dto;

import com.mycompany.app.enums.Role;
import com.mycompany.app.validation.NoHtml;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private String email;
    private Long id;
    private Role role;
    private String name;
    long department_id;
    long role_id;
}

