// asset-management-system/src/test/java/org/orange/oie/internship2025/assetmanagementsystem/CategoryControllerIntegrationTest.java
package org.orange.oie.internship2025.assetmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Category;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.CategoryRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminAuthHeader;
    private String employeeAuthHeader;

    @BeforeEach
    void setUp() {
        assetRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();
        categoryRepository.deleteAll();

        Department department = new Department();
        department.setDepartmentName("IT");
        departmentRepository.save(department);

        Role adminRole = new Role();
        adminRole.setRoleType("Admin");
        roleRepository.save(adminRole);

        Role employeeRole = new Role();
        employeeRole.setRoleType("Employee");
        roleRepository.save(employeeRole);

        Category category1 = new Category();
        category1.setCategoryName("Electronics");
        categoryRepository.save(category1);

        User admin = new User();
        admin.setEmail("admin@orange.com");
        admin.setPassword(passwordEncoder.encode("Password123##"));
        admin.setUsername("admin");
        admin.setRole(adminRole);
        admin.setDepartment(department);
        userRepository.save(admin);

        User employee = new User();
        employee.setEmail("employee@orange.com");
        employee.setPassword(passwordEncoder.encode("Password123##"));
        employee.setUsername("employee");
        employee.setRole(employeeRole);
        employee.setDepartment(department);
        userRepository.save(employee);

        String adminCredentials = "admin@orange.com:Password123##";
        adminAuthHeader = "Basic " + Base64.getEncoder().encodeToString(adminCredentials.getBytes());

        String employeeCredentials = "employee@orange.com:Password123##";
        employeeAuthHeader = "Basic " + Base64.getEncoder().encodeToString(employeeCredentials.getBytes());
    }

    @Test
    void getAllCategories_AsAdmin_ShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/category")
                        .header("Authorization", adminAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCategories_AsEmployee_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/category")
                        .header("Authorization", employeeAuthHeader))
                .andExpect(status().isForbidden());
    }
}