package com.mycompany.app.dto;

import com.mycompany.app.enums.Role;
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
    private String email;
    private Role role;
}

