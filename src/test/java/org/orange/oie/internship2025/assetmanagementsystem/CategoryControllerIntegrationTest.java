//package org.orange.oie.internship2025.assetmanagementsystem;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Base64;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
//@AutoConfigureMockMvc
//@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
//@Transactional
//public class CategoryControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private String adminAuthHeader;
//    private String employeeAuthHeader;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
//        departmentRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//        Department department = createDepartment("IT");
//
//        Role adminRole = createRole("ADMIN");
//        Role employeeRole = createRole("EMPLOYEE");
//
//        createCategory("Electronics");
//
//        createUser("admin@orange.com", "Password123##", "admin", "Admin User", adminRole, department);
//        createUser("employee@orange.com", "Password123##", "employee", "Employee User", employeeRole, department);
//
//        adminAuthHeader = buildBasicAuthHeader("admin@orange.com", "Password123##");
//        employeeAuthHeader = buildBasicAuthHeader("employee@orange.com", "Password123##");
//    }
//
//    @Test
//    void getAllCategories_AsAdmin_ShouldSucceed() throws Exception {
//        mockMvc.perform(get("/api/categories")
//                        .header("Authorization", adminAuthHeader))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].categoryName").value("Electronics"));
//    }
//
//    @Test
//    void getAllCategories_AsEmployee_ShouldBeForbidden() throws Exception {
//        mockMvc.perform(get("/api/categories")
//                        .header("Authorization", employeeAuthHeader))
//                .andExpect(status().isForbidden());
//    }
//
//
//    private Department createDepartment(String name) {
//        Department department = new Department();
//        department.setDepartmentName(name);
//        return departmentRepository.save(department);
//    }
//
//    private Role createRole(String name) {
//        Role role = new Role();
//        role.setRoleName(name);
//        return roleRepository.save(role);
//    }
//
//    private AssetCategory createCategory(String name) {
//        AssetCategory category = new AssetCategory();
//        category.setCategoryName(name);
//        return categoryRepository.save(category);
//    }
//
//    private User createUser(String email, String password, String username, String fullName, Role role, Department department) {
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setUsername(username);
//        user.setFullName(fullName);
//        user.setRole(role);
//        user.setDepartment(department);
//        user.setIsActive(true);
//        return userRepository.save(user);
//    }
//
//    private String buildBasicAuthHeader(String username, String password) {
//        String credentials = username + ":" + password;
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//        return "Basic " + encodedCredentials;
//    }
//}