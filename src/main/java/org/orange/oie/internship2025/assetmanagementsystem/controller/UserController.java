package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.security.CustomUserDetails;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.orange.oie.internship2025.assetmanagementsystem.service.UserService;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long departmentId

    ) {
        User user = SecurityUtils.getCurrentUser();
        UserDTO userDTO = authService.authenticateUser(user);

        String role = userDTO.getRole();
        Long managerDepartmentId = userDTO.getDepartmentId();
        Pageable pageable = PageRequest.of(page, size);

        switch (role) {
            case "Admin":
                return userService.searchUsers(username, departmentId, pageable);

            case "Department_Manager":
                return userService.searchUsers(username, managerDepartmentId, pageable);

            default:
                return Page.empty(pageable);

        }
    }




}


