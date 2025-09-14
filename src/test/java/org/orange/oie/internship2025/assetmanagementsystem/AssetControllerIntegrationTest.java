package org.orange.oie.internship2025.assetmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
@Transactional
public class AssetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private AssetRepository assetRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TypeRepository typeRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private Category testCategory;
    private Type testType;
    private String adminAuthHeader;
    private String employeeAuthHeader;

    @BeforeEach
    void setUp() {
        Department department = createDepartment("IT");
        Role adminRole = createRole("Admin");
        Role employeeRole = createRole("Employee");

        testCategory = createCategory("Electronics");
        testType = createType("Laptop");

        User admin = createUser("admin@orange.com", "admin", "Password123##", adminRole, department);
        User employee = createUser("employee@orange.com", "employee", "Password123##", employeeRole, department);

        adminAuthHeader = buildBasicAuthHeader(admin.getEmail(), "Password123##");
        employeeAuthHeader = buildBasicAuthHeader(employee.getEmail(), "Password123##");
    }

    private Department createDepartment(String name) {
        Department dept = new Department();
        dept.setDepartmentName(name);
        return departmentRepository.save(dept);
    }

    private Role createRole(String type) {
        Role role = new Role();
        role.setRoleType(type);
        return roleRepository.save(role);
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

    private Category createCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return categoryRepository.save(category);
    }

    private Type createType(String name) {
        Type type = new Type();
        type.setTypeName(name);
        return typeRepository.save(type);
    }

    private String buildBasicAuthHeader(String email, String password) {
        String credentials = email + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    @Test
    void addAsset_AsAdmin_ShouldSucceed() throws Exception {
        String assetJson = String.format("""
        {
          "assetName": "New Laptop",
          "assetDescription": "Standard issue laptop",
          "brand": "Dell",
          "categoryId": %d,
          "typeId": %d,
          "quantity": 10
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/api/assets")
                        .header("Authorization", adminAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assetName").value("New Laptop"))
                .andExpect(jsonPath("$.brand").value("Dell"));

        assertThat(assetRepository.findAll()).hasSize(1);
    }

    @Test
    void addAsset_AsEmployee_ShouldBeForbidden() throws Exception {
        String assetJson = String.format("""
        {
          "assetName": "Another Laptop",
          "assetDescription": "For employee",
          "brand": "HP",
          "categoryId": %d,
          "typeId": %d,
          "quantity": 5
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/api/assets")
                        .header("Authorization", employeeAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void addAsset_WithMissingName_ShouldReturnBadRequest() throws Exception {
        String assetJson = String.format("""
        {
          "assetDescription": "A laptop with no name",
          "brand": "Lenovo",
          "categoryId": %d,
          "typeId": %d,
          "quantity": 3
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/api/assets")
                        .header("Authorization", adminAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("assetName: Asset name cannot be empty"));
    }

    @Test
    void getAllAssets_AsAdmin_ShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/assets")
                        .header("Authorization", adminAuthHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllAssets_AsEmployee_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/assets")
                        .header("Authorization", employeeAuthHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    void addAsset_WithInvalidQuantity_ShouldReturnBadRequest() throws Exception {
        String assetJson = String.format("""
        {
          "assetName": "Laptop Bad Quantity",
          "assetDescription": "Invalid stock count",
          "brand": "Asus",
          "categoryId": %d,
          "typeId": %d,
          "quantity": -5
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/api/assets")
                        .header("Authorization", adminAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("quantity: quantity cannot be negative"));
    }
}