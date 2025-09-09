package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticateUser() {


        User user = SecurityUtils.getCurrentUser();
        UserDTO userDTO = authService.authenticateUser(user);
        return ResponseEntity.ok(userDTO);
    }

}
