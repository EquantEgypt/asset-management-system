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
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Base64;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
//@AutoConfigureMockMvc
//@EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
//@Transactional
//public class TypeControllerIntegrationTest {
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
//    private TypeRepository typeRepository;
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
//        typeRepository.deleteAll();
//        categoryRepository.deleteAll();
//        roleRepository.deleteAll();
//        departmentRepository.deleteAll();
//
//        Department department = createDepartment("IT");
//
//        Role adminRole = createRole("ADMIN");
//        Role employeeRole = createRole("EMPLOYEE");
//
//        AssetCategory category = createCategory("Electronics");
//        createType("Laptop", category);
//
//        User admin = createUser("admin@orange.com", "admin", "Admin User", "Password123##", adminRole, department);
//        User employee = createUser("employee@orange.com", "employee", "Employee User", "Password123##", employeeRole, department);
//
//        adminAuthHeader = buildAuthHeader(admin.getEmail(), "Password123##");
//        employeeAuthHeader = buildAuthHeader(employee.getEmail(), "Password123##");
//    }
//
//    @Test
//    void getAllTypes_AsAdmin_ShouldSucceed() throws Exception {
//        mockMvc.perform(get("/api/types")
//                        .header("Authorization", adminAuthHeader))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAllTypes_AsEmployee_ShouldBeForbidden() throws Exception {
//        mockMvc.perform(get("/api/types")
//                        .header("Authorization", employeeAuthHeader))
//                .andExpect(status().isForbidden());
//    }
//
//    // Helper methods
//    private Department createDepartment(String name) {
//        Department dept = new Department();
//        dept.setDepartmentName(name);
//        return departmentRepository.save(dept);
//    }
//
//    private Role createRole(String roleType) {
//        Role role = new Role();
//        role.setRoleName(roleType);
//        return roleRepository.save(role);
//    }
//
//    private AssetCategory createCategory(String name) {
//        AssetCategory category = new AssetCategory();
//        category.setCategoryName(name);
//        return categoryRepository.save(category);
//    }
//
//    private AssetType createType(String typeName, AssetCategory category) {
//        AssetType type = new AssetType();
//        type.setName(typeName);
//        type.setCategory(category);
//        return typeRepository.save(type);
//    }
//
//    private User createUser(String email, String username, String fullName, String password, Role role, Department dept) {
//        User user = new User();
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setFullName(fullName);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(role);
//        user.setDepartment(dept);
//        user.setIsActive(true);
//        return userRepository.save(user);
//    }
//
//    private String buildAuthHeader(String email, String rawPassword) {
//        String credentials = email + ":" + rawPassword;
//        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
//    }
//}