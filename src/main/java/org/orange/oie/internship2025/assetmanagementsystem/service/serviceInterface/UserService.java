package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;


import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    Page<UserDTO> searchUsers(String searchWord, String role, Long departmentId, Pageable pageable);
    UserDetailsDTO getUserDetailsById(long id);
}
