package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class UserController {




    @GetMapping("/users")
    public List<UserDTO> getAllUsers(Authentication authentication) {
        UserDTO userDTO1 = authService.authenticateUser(authentication);
        String role = userDTO1.getRole();
        if (role.equals("Admin") ) {
            return userService.getAllUsers();

        } else if (role.equals("Department_Manager")) {
//            Long departmentId = userDTO1.getDepartmentId();
//            return ResponseEntity.ok(userService.getAllUsersByDepartment(departmentId)).getBody();
        } else {
            System.out.println("not allowed");
        }

        return null;
    }

}
