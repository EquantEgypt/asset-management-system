// package org.orange.oie.internship2025.assetmanagementsystem;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
// import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
// import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
// import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
// import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.Base64;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest(classes = App.class, properties = "spring.profiles.active=test")
// @AutoConfigureMockMvc
// @EntityScan(basePackages = "org.orange.oie.internship2025.assetmanagementsystem.entity")
// @Transactional
// public class CategoryControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private RoleRepository roleRepository;

//     @Autowired
//     private DepartmentRepository departmentRepository;

//     @Autowired
//     private CategoryRepository categoryRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     private String adminAuthHeader;
//     private String employeeAuthHeader;

//     @BeforeEach
//     void setUp() {
//         userRepository.deleteAll();
//         roleRepository.deleteAll();
//         departmentRepository.deleteAll();
//         categoryRepository.deleteAll();
//         roleRepository.deleteAll();
//         Department department = createDepartment("IT");

//         Role adminRole = createRole("Admin");
//         Role employeeRole = createRole("Employee");

//         createCategory("Electronics");

//         createUser("admin@orange.com", "Password123##", "admin", adminRole, department);
//         createUser("employee@orange.com", "Password123##", "employee", employeeRole, department);

//         adminAuthHeader = buildBasicAuthHeader("admin@orange.com", "Password123##");
//         employeeAuthHeader = buildBasicAuthHeader("employee@orange.com", "Password123##");
//     }

//     @Transactional
//     @Test
//     void getAllCategories_AsAdmin_ShouldSucceed() throws Exception {
//         mockMvc.perform(get("/api/categories")
//                         .header("Authorization", adminAuthHeader))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].categoryName").value("Electronics"));
//     }

//     @Transactional
//     @Test
//     void getAllCategories_AsEmployee_ShouldBeForbidden() throws Exception {
//         mockMvc.perform(get("/api/categories")
//                         .header("Authorization", employeeAuthHeader))
//                 .andExpect(status().isForbidden());
//     }

// }
