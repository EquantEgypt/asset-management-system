package org.orange.oie.internship2025.assetmanagementsystem.service;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable); // Use pageable parameter
        return UserMapper.toDtoPage(users); // Use the new method for Page conversion
    }
    public Page<UserDTO> getUserByDepartment(Long  department, Pageable pageable) {
        Page<User> usersbydep =userRepository.findByDepartment_DepartmentId(department , pageable);
        return UserMapper.toDtoPage(usersbydep);

    }


}