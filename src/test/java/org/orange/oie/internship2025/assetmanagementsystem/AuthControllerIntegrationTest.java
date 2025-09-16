package org.orange.oie.internship2025.assetmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.RoleRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@EntityScan(basePackageClasses = { User.class, Role.class, Department.class })
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();

        Department department = new Department();
        department.setDepartmentName("IT");
        departmentRepository.save(department);

        Role role = new Role();
        role.setRoleName("Employee");
        roleRepository.save(role);

        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setUsername("testuser");
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        user.setHireDate(null);
        user.setPhone("0123123123123");
        user.setFullName("Test User");
        user.setIsActive(true);
        user.setRole(role);
        user.setDepartment(department);
        userRepository.save(user);
    }

    @Test
    void shouldReturnOkAndUserDTOWhenAuthenticationIsSuccessful() throws Exception {
        String credentials = "test@orange.com:password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@orange.com"))
                .andExpect(jsonPath("$.role").value("Employee"));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        String badCredentials = "test@orange.com:wrong-password";
        String encodedBadCredentials = Base64.getEncoder().encodeToString(badCredentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedBadCredentials))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenEmailDomainIsNotOrange() throws Exception {

        String credentials = "test@gmail.com:password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isUnauthorized());
    }
        @Test
    void shouldReturnUnauthorizedWhenEmailisNull() throws Exception {

        String credentials = ":password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isUnauthorized());
    }
            @Test
    void shouldReturnUnauthorizedWhenEmailisNotAUser() throws Exception {

        String credentials = "notTest@orange.com:password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isUnauthorized());
    }
}
