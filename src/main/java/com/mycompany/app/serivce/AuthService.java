package com.mycompany.app.serivce;

import com.mycompany.app.dto.LoginRequestDTO;
import com.mycompany.app.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<LoginResponseDTO> authenticateUser(@Valid LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(r -> r.replace("ROLE_", ""))
                .findFirst()
                .orElse(null);

        long userId = getUserIdByEmail(userDetails.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(userId, userDetails.getUsername(), role));
    }

    private long getUserIdByEmail(String email) {
        return switch (email) {
            case "admin@orange.com" -> 1L;
            case "manager@orange.com" -> 2L;
            case "it@orange.com" -> 3L;
            case "employee@orange.com" -> 4L;
            default -> 0L;
        };
    }
}
