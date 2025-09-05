package org.orange.oie.internship2025.assetmanagementsystem.serivce;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.UserReposetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
private  final UserReposetries userReposetries;
    @Autowired
private  final UserMapper userMapper;

    public AuthService(UserReposetries userReposetries, UserMapper userMapper) {
        this.userReposetries = userReposetries;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserDTO> authenticateUser(Authentication authentication) {
        String email=authentication.getName();
        User user=userReposetries.findByEmail(email) .orElseThrow(() -> new RuntimeException("User not found"));;

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

}
