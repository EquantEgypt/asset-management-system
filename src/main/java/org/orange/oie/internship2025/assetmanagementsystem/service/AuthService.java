package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserMapper userMapper;

    private final UserMapper userMapper;


    public final UserDTO authenticateUser(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        return UserMapper.toDto(user);
    }

//    public List<UserDTO> getEmployeeByDepartment(String departmentName) {
//        User department = DepartmentRepository.findByDepartment(departmentName);
//        return department.getEmployees();
//    }




}
