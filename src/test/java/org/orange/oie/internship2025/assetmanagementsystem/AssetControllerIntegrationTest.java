//package org.orange.oie.internship2025.assetmanagementsystem;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Base64;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
//@AutoConfigureMockMvc
//@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
//@Transactional
//public class AssetControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AssetRepository assetRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private TypeRepository typeRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private AssetCategory testCategory;
//    private AssetType testType;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//        departmentRepository.deleteAll();
//        roleRepository.deleteAll();
//
//        Department department = new Department();
//        department.setDepartmentName("IT");
//        departmentRepository.save(department);
//
//        Role adminRole = new Role();
//        adminRole.setRoleName("ADMIN");
//        roleRepository.save(adminRole);
//
//        User adminUser = new User();
//        adminUser.setEmail("test@orange.com");
//        adminUser.setPassword(passwordEncoder.encode("password123"));
//        adminUser.setUsername("testuser");
//        adminUser.setCreatedAt(null);
//        adminUser.setUpdatedAt(null);
//        adminUser.setHireDate(null);
//        adminUser.setPhone("0123123123123");
//        adminUser.setFullName("Test User");
//        adminUser.setIsActive(true);
//        adminUser.setRole(adminRole);
//        adminUser.setDepartment(department);
//        userRepository.save(adminUser);
//
//        Role employeeRole = new Role();
//        employeeRole.setRoleName("EMPLOYEE");
//        roleRepository.save(employeeRole);
//
//        User employeeUser = new User();
//        employeeUser.setEmail("testE@orange.com");
//        employeeUser.setPassword(passwordEncoder.encode("password123"));
//        employeeUser.setUsername("testemployee");
//        employeeUser.setCreatedAt(null);
//        employeeUser.setUpdatedAt(null);
//        employeeUser.setHireDate(null);
//        employeeUser.setPhone("0123123123124");
//        employeeUser.setFullName("Test Employee");
//        employeeUser.setIsActive(true);
//        employeeUser.setRole(employeeRole);
//        employeeUser.setDepartment(department);
//        userRepository.save(employeeUser);
//
//        testCategory = createCategory("Electronics");
//        testType = createType("Laptop");
//    }
//
//    private AssetCategory createCategory(String name) {
//        AssetCategory category = new AssetCategory();
//        category.setCategoryName(name);
//        return categoryRepository.save(category);
//    }
//
//    private AssetType createType(String name) {
//        AssetCategory category = categoryRepository.findAll().get(0);
//        AssetType type = new AssetType();
//        type.setName(name);
//        type.setCategory(category);
//        return typeRepository.save(type);
//    }
//
//    @Test
//    void addAsset_AsAdmin_ShouldSucceed() throws Exception {
//
//        String credentials = "test@orange.com:password123";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        String assetJson = String.format("""
//            {
//              "categoryId": %d,
//              "typeId": %d,
//              "brand": "Dell",
//              "description": "High-performance business laptop",
//              "assetName": "Dell Latitude 5420",
//              "location": "HQ - IT Department",
//              "serialNumber": "SN-DL5420-0001",
//              "purchaseDate": "2024-05-01T10:00:00",
//              "warrantyEndDate": "2027-05-01T10:00:00",
//              "status": "Available",
//              "imagePath": "/uploads/assets/dell-latitude-5420.png"
//            }
//            """, testCategory.getCategoryId(), testType.getId());
//
//        mockMvc.perform(post("/api/assets")
//                        .header("Authorization", "Basic " + encodedCredentials)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(assetJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.assetName").value("Dell Latitude 5420"))
//                .andExpect(jsonPath("$.brand").value("Dell"));
//
//        assertThat(assetRepository.findAll()).hasSize(1);
//    }
//
//    @Test
//    void addAsset_AsEmployee_ShouldBeForbidden() throws Exception {
//        String credentials = "testE@orange.com:password123";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        String assetJson = String.format("""
//           {
//              "categoryId": %d,
//              "typeId": %d,
//              "brand": "Dell",
//              "description": "High-performance business laptop",
//              "assetName": "Dell Latitude 5420",
//              "location": "HQ - IT Department",
//              "serialNumber": "SN-DL5420-0001",
//              "purchaseDate": "2024-05-01T10:00:00",
//              "warrantyEndDate": "2027-05-01T10:00:00",
//              "status": "Available",
//              "imagePath": "/uploads/assets/dell-latitude-5420.png"
//            }
//            """, testCategory.getCategoryId(), testType.getId());
//
//        mockMvc.perform(post("/api/assets")
//                        .header("Authorization", "Basic " + encodedCredentials)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(assetJson))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void addAsset_WithMissingName_ShouldReturnBadRequest() throws Exception {
//                String credentials = "test@orange.com:password123";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        String assetJson = String.format("""
//                {
//                  "categoryId": 1,
//                  "typeId": 1,
//                  "brand": "Dell",
//                  "description": "High-performance business laptop",
//                  "location": "HQ - IT Department",
//                  "serialNumber": "SN-DL5420-0001",
//                  "purchaseDate": "2024-05-01T10:00:00",
//                  "warrantyEndDate": "2027-05-01T10:00:00",
//                  "status": "Available",
//                  "imagePath": "/uploads/assets/dell-latitude-5420.png"
//                }
//                """, testCategory.getCategoryId(), testType.getId());
//
//        mockMvc.perform(post("/api/assets")
//                .header("Authorization", "Basic " + encodedCredentials)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(assetJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
//                .andExpect(jsonPath("$.message").value("assetName: Asset name cannot be empty"));
//    }
//
//    @Test
//    void getAllAssets_AsAdmin_ShouldSucceed() throws Exception {
//                String credentials = "test@orange.com:password123";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        mockMvc.perform(get("/api/assets")
//                .header("Authorization", "Basic " + encodedCredentials))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray());
//    }
//
//    @Test
//    void getAllAssets_AsEmployee_ShouldBeForbidden() throws Exception {
//                String credentials = "testE@orange.com:password123";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        mockMvc.perform(get("/api/assets")
//                .header("Authorization", "Basic " + encodedCredentials))
//                .andExpect(status().isForbidden());
//    }
//
//}