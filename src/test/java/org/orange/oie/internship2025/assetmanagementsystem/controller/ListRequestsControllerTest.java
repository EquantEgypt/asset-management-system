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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListRequestsControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getPendingRequests_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            mockMvc.perform(get("/request"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }
    }



    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getRequestsHistory_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            mockMvc.perform(get("/request"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }
    }


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"USER"})
    void getMyRequests_shouldReturnUserOwnRequests() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("USER"));

            mockMvc.perform(get("/request"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }
    }
    private User getLoggedInUserByRole(String roleName) {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setEmail("admin@example.com");

        Department dept = new Department();
        dept.setId(1L);
        dept.setName("IT");
        mockUser.setDepartment(dept);

        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);
        mockUser.setRole(role);

        return mockUser;
    }
}
