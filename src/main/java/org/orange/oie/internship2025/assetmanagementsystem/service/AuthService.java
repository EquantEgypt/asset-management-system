package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    public final UserDTO authenticateUser(Authentication authentication) {
        CustomUserDetails userdetails = (CustomUserDetails) authentication.getPrincipal();
        return userMapper.toDto(userdetails.getUser());
    }

}
