//package org.orange.oie.internship2025.assetmanagementsystem;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
//import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import org.springframework.http.MediaType;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.hamcrest.Matchers.hasSize;
//
//@SpringBootTest
////@AutoConfigureTestWebSecurity
//@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.properties")
////@Transactional
//class UserListControllerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Mock
//    private AuthService authService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//        departmentRepository.deleteAll();
//
//        Department dep = new Department();
//        dep.setDepartmentName("testing_team");
//        departmentRepository.save(dep);
//
//        User admin = new User();
//        admin.setUsername("meke admin");
//        admin.setEmail("meke_admin@example.com");
//        //admin.setRole("Admin");
//        admin.setDepartment(dep);
//        userRepository.save(admin);
//
//        User manager = new User();
//        manager.setUsername("Baher manager");
//        manager.setEmail("baher.M@example.com");
//        //manager.setRole('Department_Manager');
//        manager.setDepartment(dep);
//        userRepository.save(manager);
//    }
//
//    @Test
//    @WithMockUser(roles="Admin")
//    void getAllUsersForRoleAminSuccessfully() throws Exception {
//
//        mockMvc.perform(get("/get/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(5)))
//                .andExpect(jsonPath("$.size").value(5))
//                .andExpect(jsonPath("$.page").value(0));
//    }
//
//    void getAllUsersForRoleManagerSuccessfully()throws Exception {
//    }
//
//
//
//}