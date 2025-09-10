package org.orange.oie.internship2025.assetmanagementsystem.service;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable); // Use pageable parameter
        return UserMapper.toDtoPage(users); // Use the new method for Page conversion
    }
    public List<UserDTO> getUserByDepartment(Long  department) {
        List<User> usersbydep =userRepository.findByDepartment_DepartmentId(department);
        return UserMapper.toDtoList(usersbydep);

    }


}