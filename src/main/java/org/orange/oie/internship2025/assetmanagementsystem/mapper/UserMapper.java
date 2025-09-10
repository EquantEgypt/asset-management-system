package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        userDTO.setRole(user.getRole().getRoleType());
        userDTO.setDepartmentName(user.getDepartment().getDepartmentName());
        userDTO.setDepartmentId(user.getDepartment().getDepartmentId());

        return userDTO;
    }

    public static List<UserDTO> toDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : users) {
            dtoList.add(toDto(user));
        }
        return dtoList;
    }
    public static Page<UserDTO> toDtoPage(Page<User> userPage) {
        if (userPage == null) {
            return Page.empty();
        }

        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : userPage.getContent()) {
            dtoList.add(toDto(user));
        }

        return new PageImpl<>(dtoList, userPage.getPageable(), userPage.getTotalElements());
    }

}
