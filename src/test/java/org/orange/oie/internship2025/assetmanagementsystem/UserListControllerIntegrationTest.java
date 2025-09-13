package org.orange.oie.internship2025.assetmanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.RoleRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.http.MediaType;

import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestWebSecurity
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
//@Transactional
class UserListControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;
    private String adminAuthHeader;
private String managerAuthHeader;
    private String userAuthHeader;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
        Department dep = new Department();
        dep.setDepartmentName("testing_team");
        departmentRepository.save(dep);
        //other department for test
        Department dep2 = new Department();
        dep2.setDepartmentName("other_team");
        departmentRepository.save(dep2);

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@orange.com");
        admin.setPassword(passwordEncoder.encode("Password123##"));
        admin.setDepartment(dep);
        Role AdminRole = new Role();
        AdminRole.setRoleType("Admin");
        roleRepository.save(AdminRole);
        admin.setRole(AdminRole);
        userRepository.save(admin);

        User manager = new User();
        manager.setUsername("Baher manager");
        manager.setEmail("baher.M@orange.com");
        manager.setPassword(passwordEncoder.encode("Password123##"));
        manager.setDepartment(dep);
        Role ManagerRole = new Role();
        ManagerRole.setRoleType("Department_Manager");
        roleRepository.save(ManagerRole);
        manager.setRole(ManagerRole);
        userRepository.save(manager);


        User otherUser = new User();
        otherUser.setUsername("user employee");
        otherUser.setEmail("employee@orange.com");
        otherUser.setPassword(passwordEncoder.encode("Password123##"));
        otherUser.setDepartment(dep2);
        Role employeeRole = new Role();
        employeeRole.setRoleType("Employee");
        roleRepository.save(employeeRole);
        otherUser.setRole(employeeRole);
        userRepository.save(otherUser);

        String adminCredentials = "admin@orange.com:Password123##";
        adminAuthHeader = "Basic " + Base64.getEncoder().encodeToString(adminCredentials.getBytes());
        String managerCredentials = "baher.M@orange.com:Password123##";
        managerAuthHeader = "Basic " + Base64.getEncoder().encodeToString(managerCredentials.getBytes());
        String userCredentials = "employee@orange.com:Password123##";
        userAuthHeader = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());

    }

    @Test
    void getAllUsersForRoleAdmin() throws Exception {

        mockMvc.perform(get("/api/users")
                        .header("Authorization", adminAuthHeader)

                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].email").value("admin@orange.com"))
                .andExpect(jsonPath("$.content[1].email").value("baher.M@orange.com"));
    }


    @Test
    void managerCanOnlySeeOwnDepartmentUsers() throws Exception {

        mockMvc.perform(get("/api/users")
                        .header("Authorization", managerAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].departmentName").value("testing_team"))
                .andExpect(jsonPath("$.content[1].departmentName").value("testing_team"));
    }
    @Test
    void OtherUsersGetEmptyResults() throws Exception {

        mockMvc.perform(get("/api/users")
                        .header("Authorization", userAuthHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

@Test
    void SearchByNameOrEmailSuccefull() throws Exception{
    mockMvc.perform(get("/api/users")
                    .param("search", "Baher")
                    .header("Authorization", adminAuthHeader)

                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].email").value("baher.M@orange.com"))
            .andExpect(jsonPath("$.content[0].username").value("Baher manager"));
}
    @Test
    void FilterByRoleSuccefull() throws Exception{
        mockMvc.perform(get("/api/users")
                        .param("role", "Department_Manager")
                        .header("Authorization", adminAuthHeader)

                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].email").value("baher.M@orange.com"))
                .andExpect(jsonPath("$.content[0].username").value("Baher manager"));
    }
    @Test
    void FilterByDepSuccefull() throws Exception{
        mockMvc.perform(get("/api/users")
                        .param("departmentId", "2")
                        .header("Authorization", adminAuthHeader)

                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].email").value("employee@orange.com"))
                .andExpect(jsonPath("$.content[0].username").value("user employee"));

    }
@Test
    void PaginationSuccefull() throws Exception{
    mockMvc.perform(get("/api/users")
                    .param("page", "1")
                    .param("size", "1")
                    .header("Authorization", adminAuthHeader)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].email").value("baher.M@orange.com"))
            .andExpect(jsonPath("$.content[0].username").value("Baher manager"));
}
}