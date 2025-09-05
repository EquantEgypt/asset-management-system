package org.orange.oie.internship2025.assetmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.DepartmentReposetries;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.RoleReposetries;
import org.orange.oie.internship2025.assetmanagementsystem.reposetries.UserReposetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserReposetries userReposetries;

    @Autowired
    private DepartmentReposetries departmentReposetries;

    @Autowired
    private RoleReposetries roleReposetries;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userReposetries.deleteAll();
        departmentReposetries.deleteAll();
        roleReposetries.deleteAll();
    }

    @Test
    void shouldReturnOkAndUserDTOWhenAuthenticationIsSuccessful() throws Exception {
        Department department = new Department();
        department.setDepartmentName("IT");
        departmentReposetries.save(department);

        Role role = new Role();
        role.setRoleType("Employee");
        roleReposetries.save(role);

        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("correct-password"));
        user.setUsername("testuser");
        user.setRole(role);
        user.setDepartment(department);
        userReposetries.save(user);

        String credentials = "test@orange.com:correct-password";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@orange.com"))
                .andExpect(jsonPath("$.role.roleType").value("Employee"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        Department department = new Department();
        department.setDepartmentName("IT");
        departmentReposetries.save(department);

        Role role = new Role();
        role.setRoleType("Employee");
        roleReposetries.save(role);

        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("correct-password"));
        user.setUsername("testuser");
        user.setRole(role);
        user.setDepartment(department);
        userReposetries.save(user);

        String badCredentials = "test@orange.com:wrong-password";
        String encodedBadCredentials = Base64.getEncoder().encodeToString(badCredentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedBadCredentials))
                .andExpect(status().isUnauthorized());
    }
}