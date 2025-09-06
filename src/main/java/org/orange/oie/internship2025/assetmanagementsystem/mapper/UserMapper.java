package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());

        if (user.getRole() != null) {
            userDTO.setRole(user.getRole());
        }

        if (user.getDepartment() != null) {
            userDTO.setDepartmentName(user.getDepartment().getDepartmentName());
        } else {
            userDTO.setDepartmentName(null);
        }

        return userDTO;
    }
}
