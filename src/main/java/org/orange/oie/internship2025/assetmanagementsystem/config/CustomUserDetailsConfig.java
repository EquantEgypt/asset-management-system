package org.orange.oie.internship2025.assetmanagementsystem.config;

import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.UserReposetries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailsConfig {

    private final UserReposetries userRepository;

    public CustomUserDetailsConfig(UserReposetries userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().getRoleType())
                    .build();
        };
    }
}
