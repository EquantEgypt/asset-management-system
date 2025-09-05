package com.mycompany.app;

import com.mycompany.app.entity.Role;
import com.mycompany.app.entity.User;
import com.mycompany.app.reposetries.UserReposetries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserReposetries userReposetries;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userReposetries.deleteAll();
    }

    @Test
    void shouldReturnOkAndUserDTOWhenAuthenticationIsSuccessful() throws Exception {
        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("correct-password"));
        user.setUsername("testuser");
        user.setRole(new Role((long)1,"Employee"));
        userReposetries.save(user);

        String credentials = "test@orange.com:correct-password";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@orange.com"))
                .andExpect(jsonPath("$.role").value("EMPLOYEE"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("correct-password"));
        user.setUsername("testuser");
        user.setRole(new Role());
        userReposetries.save(user);

        String badCredentials = "test@orange.com:wrong-password";
        String encodedBadCredentials = Base64.getEncoder().encodeToString(badCredentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedBadCredentials))
                .andExpect(status().isUnauthorized());
    }
}