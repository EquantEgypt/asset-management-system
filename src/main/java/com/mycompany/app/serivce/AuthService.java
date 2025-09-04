package com.mycompany.app.serivce;

import com.mycompany.app.dto.UserDTO;
import com.mycompany.app.entity.User;
import com.mycompany.app.mapper.UserMapper;
import com.mycompany.app.reposetries.UserReposetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
