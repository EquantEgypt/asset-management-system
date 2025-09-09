package org.orange.oie.internship2025.assetmanagementsystem.service;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDtoList(users);
    }
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    //@Transactional ( readOnly = true)
//    public List<UserDTO> getAllUsersByDepartment(Long depId) {
//        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.department.departmentName = :dep", User.class).setParameter("dep", departmentName).getResultList();
//
//        return UserMapper.toDtoList(users);
//        List<User> users = userRepository. findByDepartment_DepartmentId(depId);
//        return UserMapper.toDtoList(users);
//    }

}