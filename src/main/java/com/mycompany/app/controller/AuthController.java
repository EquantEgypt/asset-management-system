package com.mycompany.app.controller;

import com.mycompany.app.dto.UserDTO;
import com.mycompany.app.serivce.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticateUser(Authentication authentication) {
        System.out.println("hello");
        return authService.authenticateUser(authentication);
    }
}
