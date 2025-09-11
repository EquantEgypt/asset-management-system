package org.orange.oie.internship2025.assetmanagementsystem;

import org.mockito.Mock;
import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
//@AutoConfigureTestWebSecurity
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
//@Transactional
class UserListControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Mock
    private AuthService authService;

    @Autowired
    //private ObjectMapper objectMapper;

    private Department itDepartment;
    private Department hrDepartment;
    private User adminUser;
    private User managerUser;
    private User regularUser;
    private UserDTO adminUserDTO;
    private UserDTO managerUserDTO;
    private UserDTO regularUserDTO;


}