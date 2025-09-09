package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.orange.oie.internship2025.assetmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticateUser(Authentication authentication) {
        UserDTO userDTO = authService.authenticateUser(authentication);
        return ResponseEntity.ok(userDTO);
    }

}
