package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private MockedStatic<SecurityUtils> securityUtilsMock;


    @DatabaseSetup(value = "/dataset/getAllUsers_withExistingUsers_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllUsers_withExistingUsers_shouldReturnList() throws Exception {

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            mockMvc.perform(get("/api/users")
                            .param("search", "admin")
                            .param("role", "ADMIN")
                            .param("departmentId", "1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
        }
    }

    @Transactional
    @Test
    @WithMockUser(authorities = {"DEPARTMENT_MANAGER"})
    @DatabaseSetup(value = "/dataset/getAllManagerUsers_withExistingUsers_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getAllManagerUsers_withExistingUsers_shouldReturnList() throws Exception {

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("DEPARTMENT_MANAGER"));

            mockMvc.perform(get("/api/users")   // <--- matches @RequestMapping("/api") + @GetMapping("/users")
                            .param("search", "alice")
                            .param("role", "DEPARTMENT_MANAGER")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
        }

    }

    private User getLoggedInUserByRole(String roleName) {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setEmail("admin@example.com");

        // set department
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("IT");
        mockUser.setDepartment(dept);

        // set role
        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);
        mockUser.setRole(role);
        return mockUser;
    }
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getUserDetails_withExistingUser_shouldReturnUser.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getUserDetails_withExistingUser_shouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/details/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@orange.com"))
                .andExpect(jsonPath("$.fullName").value("System Administrator"))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getUserDetails_withExistingUser_shouldReturnUser.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getUserDetails_withNonExistingUser_shouldReturnError() throws Exception {
        mockMvc.perform(get("/api/users/details/{id}", 9999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
