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

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = App.class,properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
public class AssetControllerIntegrationTest {

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
    private TypeRepository typeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Category testCategory;
    private Type testType;
    private String adminAuthHeader;
    private String employeeAuthHeader;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();
        categoryRepository.deleteAll();
        typeRepository.deleteAll();

        Department department = new Department();
        department.setDepartmentName("IT");
        departmentRepository.save(department);

        Role adminRole = new Role();
        adminRole.setRoleType("Admin");
        roleRepository.save(adminRole);

        Role employeeRole = new Role();
        employeeRole.setRoleType("Employee");
        roleRepository.save(employeeRole);

        testCategory = new Category();
        testCategory.setCategoryName("Electronics");
        categoryRepository.save(testCategory);

        testType = new Type();
        testType.setTypeName("Laptop");
        typeRepository.save(testType);

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
    void addAsset_AsAdmin_ShouldSucceed() throws Exception {
        String assetJson = String.format("""
        {
          "assetName": "New Laptop",
          "assetDescription": "Standard issue laptop",
          "brand": "Dell",
          "categoryId": %d,
          "typeId": %d,
          "allStock": 10,
          "numberOfAvailableToAssign": 7,
          "numberOfMaintenance": 2,
          "numberOfRetired": 1
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/asset/add")
                        .header("Authorization", adminAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetName").value("New Laptop"));
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
          "allStock": 5,
          "numberOfAvailableToAssign": 5,
          "numberOfMaintenance": 0,
          "numberOfRetired": 0
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/asset/add")
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
          "allStock": 3,
          "numberOfAvailableToAssign": 1,
          "numberOfMaintenance": 1,
          "numberOfRetired": 1
        }
        """, testCategory.getCategoryId(), testType.getTypeId());

        mockMvc.perform(post("/asset/add")
                        .header("Authorization", adminAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assetJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void getAllAssets_AsAdmin_ShouldSucceed() throws Exception {
        mockMvc.perform(get("/asset/all")
                        .header("Authorization", adminAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    void getAllAssets_AsEmployee_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/asset/all")
                        .header("Authorization", employeeAuthHeader))
                .andExpect(status().isForbidden());
    }
}