package org.orange.oie.internship2025.assetmanagementsystem;

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

    }

    @Test
    void shouldReturnOkAndUserDTOWhenAuthenticationIsSuccessful() throws Exception {

        String credentials = "ahmed@orange.com:12345678";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ahmed@orange.com"))
                .andExpect(jsonPath("$.role").value("employee"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {

        String badCredentials = "test@orange.com:wrong-password";
        String encodedBadCredentials = Base64.getEncoder().encodeToString(badCredentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedBadCredentials))
                .andExpect(status().isUnauthorized());
    }
}