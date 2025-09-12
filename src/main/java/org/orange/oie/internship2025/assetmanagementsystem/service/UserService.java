package org.orange.oie.internship2025.assetmanagementsystem.service;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.specification.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Page<UserDTO> searchUsers(String username,String email,String role, Long departmentId, Pageable pageable) {
        Specification<User> spec = Specification.allOf(); // initializes an empty specification i can use .and(...) calls with

        if (username != null && !username.trim().isEmpty()) {
            spec = spec.and(UserSpecifications.hasName(username));
        }
        if (role != null && !role.trim().isEmpty()) {
            spec = spec.and(UserSpecifications.hasRole(role));
        }
        if (email != null && !email.trim().isEmpty()) {
            spec = spec.and(UserSpecifications.hasEmail(email));
        }
        if (departmentId != null) {
            spec = spec.and(UserSpecifications.inDepartment(departmentId));
        }
        Page<User> users = userRepository.findAll(spec, pageable);
        return UserMapper.toDtoPage(users);
    }
}