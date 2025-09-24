package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDetailsDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;

public class UserDetailsMapper {
    public static UserDetailsDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(user.getId());
        userDetailsDTO.setEmail(user.getEmail());
        userDetailsDTO.setRole(user.getRole().getName());
        userDetailsDTO.setDepartmentName(user.getDepartment().getName());
        userDetailsDTO.setDepartmentId(user.getDepartment().getId());
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setFullName(user.getFullName());
        userDetailsDTO.setPhone(user.getPhone());
        userDetailsDTO.setHireDate(user.getHireDate());
        userDetailsDTO.setIsActive(user.getIsActive());
        return userDetailsDTO;
    }
}

