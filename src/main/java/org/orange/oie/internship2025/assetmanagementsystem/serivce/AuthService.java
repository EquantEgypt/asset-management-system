package org.orange.oie.internship2025.assetmanagementsystem.serivce;

import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.UserMapper;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.UserReposetries;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserReposetries userReposetries;
    private final UserMapper userMapper;

    public AuthService(UserReposetries userReposetries, UserMapper userMapper) {
        this.userReposetries = userReposetries;
        this.userMapper = userMapper;
    }

    public UserDTO authenticateUser(Authentication authentication) {
        String email = authentication.getName();

        if (!email.toLowerCase().endsWith("@orange.com")) {
            throw new BadCredentialsException("Email must be from @orange.com domain");
        }

        User user = userReposetries.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        return UserMapper.toDto(user);
    }
}
