package com.mycompany.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .requestMatchers("/api/manager").hasRole("MANAGER")
                        .requestMatchers("/api/it").hasRole("IT")
                        .requestMatchers("/api/employee").hasAnyRole("EMPLOYEE", "ADMIN", "MANAGER", "IT")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails admin = User.builder().username("admin@orange.com").password(encoder.encode("password123")).roles("ADMIN", "EMPLOYEE").build();
        UserDetails manager = User.builder().username("manager@orange.com").password(encoder.encode("password123")).roles("MANAGER", "EMPLOYEE").build();
        UserDetails it = User.builder().username("it@orange.com").password(encoder.encode("password123")).roles("IT", "EMPLOYEE").build();
        UserDetails employee = User.builder().username("employee@orange.com").password(encoder.encode("password123")).roles("EMPLOYEE").build();
        return new InMemoryUserDetailsManager(admin, manager, it, employee);
    }
}

