package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.security.CustomUserDetails;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.orange.oie.internship2025.assetmanagementsystem.service.UserService;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get")

public class UserController {

    private final AuthService authService;

    private final UserService userService;
    public UserController(AuthService authService , UserService userService){  // Remove CustomUserDetails
        this.authService = authService;
        this.userService=userService;
        // Remove this line: this.customUserDetails=customUserDetails;
    }


    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        User user = SecurityUtils.getCurrentUser();
        UserDTO userDTO = authService.authenticateUser(user);

        String role = userDTO.getRole();
        if (role.equals("Admin") ) {
            return userService.getAllUsers();

        } else if (role.equals("Department_Manager")) {
        } else {
            System.out.println("not allowed");
        }

        return null;
    }

}
