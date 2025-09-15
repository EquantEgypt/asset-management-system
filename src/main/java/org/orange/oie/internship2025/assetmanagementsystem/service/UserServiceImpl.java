package org.orange.oie.internship2025.assetmanagementsystem.service;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.specification.UserSpecifications;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static org.orange.oie.internship2025.assetmanagementsystem.model.errors.ApiReturnCode.USER_NOT_EXISTS;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    public Page<UserDTO> searchUsers(String searchWord, String role, Long departmentId, Pageable pageable) {
        User user = validateUser();

        String userRole = user.getRole().getRoleType();

        if ("Department_Manager".equals(userRole))
            departmentId = user.getDepartment().getDepartmentId();


        Specification<User> spec = Specification.allOf();

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            spec = spec.and(UserSpecifications.hasNameOrEmail(searchWord));
        }
        if (role != null && !role.trim().isEmpty()) {
            spec = spec.and(UserSpecifications.hasRole(role));
        }

        if (departmentId != null) {
            spec = spec.and(UserSpecifications.inDepartment(departmentId));
        }
        Page<User> users = userRepository.findAll(spec, pageable);
        return UserMapper.toDtoPage(users);

    }

    private User validateUser() {
        User user = SecurityUtils.getCurrentUser();
        if (user == null) {
            throw new BusinessException(USER_NOT_EXISTS, "user is not authenticated");
        }
        return user;
    }
}
