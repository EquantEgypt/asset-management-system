package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.UserService;
import org.orange.oie.internship2025.assetmanagementsystem.specification.UserSpecifications;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    public Page<UserDTO> searchUsers(String searchWord, String role, Long departmentId, Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        String userRole = user.getRole().getName();
        if ("DEPARTMENT_MANAGER".equals(userRole))
            departmentId = user.getDepartment().getId();

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
}
