package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDetailsDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserDetailsMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserDetailsRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.UsersDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UsersDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImp(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetailsDTO getUserDetailsById(long id) {
        User user = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        return UserDetailsMapper.toDto(user);
    }
}
