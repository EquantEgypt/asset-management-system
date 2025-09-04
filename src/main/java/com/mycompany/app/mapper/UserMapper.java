package com.mycompany.app.mapper;

import com.mycompany.app.dto.UserDTO;
import com.mycompany.app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserid());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setName(user.getName());
        userDTO.setDepartment_id(user.getDepartment_id());
        userDTO.setRole_id(user.getRole_id());
        return userDTO;
    }

}
