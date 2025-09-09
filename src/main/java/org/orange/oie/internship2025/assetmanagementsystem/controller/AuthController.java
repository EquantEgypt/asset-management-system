package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
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

    private final AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticateUser(Authentication authentication) {
        UserDTO userDTO = authService.authenticateUser(authentication);
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/users")
    public List<UserDTO> getAllUsers(Authentication authentication) {
        UserDTO userDTO1 = authService.authenticateUser(authentication);
        String role = userDTO1.getRole();
        if (role.equals("Admin") ) {
            return userService.getAllUsers();

        } else if (role.equals("Department_Manager")) {
            Long departmentId = userDTO1.getDepartmentId();
            return ResponseEntity.ok(userService.getAllUsersByDepartment(departmentId)).getBody();
        } else {
            System.out.println("not allowed");
        }

        return null;
    }
}
