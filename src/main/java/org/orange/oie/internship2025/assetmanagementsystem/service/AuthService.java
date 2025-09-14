package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;

@Service
public class AuthService {

    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    public final UserDTO authenticateUser(User user) {
        return userMapper.toDto(user);
    }

}