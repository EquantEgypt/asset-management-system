package org.orange.oie.internship2025.assetmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Type;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.RoleRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
@Transactional
public class TypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminAuthHeader;
    private String employeeAuthHeader;

    @BeforeEach
    void setUp() {
        Department department = createDepartment("IT");

        Role adminRole = createRole("Admin");
        Role employeeRole = createRole("Employee");

        createType("Laptop");

        User admin = createUser("admin@orange.com", "admin", "Password123##", adminRole, department);
        User employee = createUser("employee@orange.com", "employee", "Password123##", employeeRole, department);

        adminAuthHeader = buildAuthHeader(admin.getEmail(), "Password123##");
        employeeAuthHeader = buildAuthHeader(employee.getEmail(), "Password123##");
    }

    @Test
    void getAllTypes_AsAdmin_ShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/types")
                        .header("Authorization", adminAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTypes_AsEmployee_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/types")
                        .header("Authorization", employeeAuthHeader))
                .andExpect(status().isForbidden());
    }

    //  Helper methods
    private Department createDepartment(String name) {
        Department dept = new Department();
        dept.setDepartmentName(name);
        return departmentRepository.save(dept);
    }

    private Role createRole(String roleType) {
        Role role = new Role();
        role.setRoleType(roleType);
        return roleRepository.save(role);
    }

    private Type createType(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        return typeRepository.save(type);
    }

    private User createUser(String email, String username, String password, Role role, Department dept) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setDepartment(dept);
        return userRepository.save(user);
    }

    private String buildAuthHeader(String email, String rawPassword) {
        String credentials = email + ":" + rawPassword;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
