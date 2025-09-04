package com.mycompany.app.controller;

import com.mycompany.app.dto.LoginRequestDTO;
import com.mycompany.app.dto.LoginResponseDTO;
import com.mycompany.app.serivce.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.toLowerCase().startsWith("basic ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String base64Credentials = authHeader.substring(6);
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);

        final String[] values = credentials.split(":", 2);
        if (values.length != 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String email = values[0];
        String password = values[1];

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        return authService.authenticateUser(loginRequest);
    }
}