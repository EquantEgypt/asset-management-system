package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ApproveRequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RejectRequestDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RequestActionsControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void approveRequest_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ApproveRequestDTO dto = new ApproveRequestDTO();

            mockMvc.perform(put("/requests/1/approve")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("APPROVED"))
                    .andExpect(jsonPath("$.rejectionNote").isEmpty());
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void approveRequest_withAssetId_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            ApproveRequestDTO dto = new ApproveRequestDTO();
            dto.setAssetId(5L);

            mockMvc.perform(put("/requests/2/approve")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("APPROVED"))
                    .andExpect(jsonPath("$.assetId").value(5));
        }
    }


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void approveRequest_notFound_shouldReturnNotFound() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ApproveRequestDTO dto = new ApproveRequestDTO();

            mockMvc.perform(put("/requests/999/approve")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Request not found"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "employee", authorities = {"EMPLOYEE"})
    void approveRequest_asEmployee_shouldReturnForbidden() throws Exception {
        ApproveRequestDTO dto = new ApproveRequestDTO();

        mockMvc.perform(put("/requests/1/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void rejectRequest_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            RejectRequestDTO dto = new RejectRequestDTO();
            dto.setRejectionNote("Not available");

            mockMvc.perform(put("/requests/2/reject")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("REJECTED"))
                    .andExpect(jsonPath("$.rejectionNote").value("Not available"));
        }
    }


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void rejectRequest_notFound_shouldReturnNotFound() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            RejectRequestDTO dto = new RejectRequestDTO();
            dto.setRejectionNote("Not available");

            mockMvc.perform(put("/requests/999/reject")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Request not found"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "employee", authorities = {"EMPLOYEE"})
    void rejectRequest_asEmployee_shouldReturnForbidden() throws Exception {
        RejectRequestDTO dto = new RejectRequestDTO();
        dto.setRejectionNote("Not available");

        mockMvc.perform(put("/requests/1/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
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