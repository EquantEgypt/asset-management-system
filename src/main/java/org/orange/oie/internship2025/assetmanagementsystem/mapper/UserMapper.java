package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setUsername(user.getUsername());
        userDTO.setDepartmentName(user.getDepartment().getDepartmentName());

        return userDTO;
    }

}
