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

        Department department = new Department();
        department.setDepartmentName("IT");
        departmentReposetries.save(department);

        Role role = new Role();
        role.setRoleType("Employee");
        roleReposetries.save(role);

        User user = new User();
        user.setEmail("test@orange.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setUsername("testuser");
        user.setRole(role);
        user.setDepartment(department);
        userReposetries.save(user);
    }

    @Test
    void shouldReturnOkAndUserDTOWhenAuthenticationIsSuccessful() throws Exception {
        String credentials = "test@orange.com:password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@orange.com"))
                .andExpect(jsonPath("$.role.roleType").value("Employee"));
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
        // create user with non-orange email
        User invalidUser = new User();
        invalidUser.setEmail("test@gmail.com");
        invalidUser.setPassword(passwordEncoder.encode("password123"));
        invalidUser.setUsername("invaliduser");
        invalidUser.setRole(roleReposetries.findAll().iterator().next());
        invalidUser.setDepartment(departmentReposetries.findAll().iterator().next());
        userReposetries.save(invalidUser);

        // try to login with invalid domain email
        String credentials = "test@gmail.com:password123";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        mockMvc.perform(post("/auth/login")
                        .header("Authorization", "Basic " + encodedCredentials))
                .andExpect(status().isUnauthorized());
    }
}
