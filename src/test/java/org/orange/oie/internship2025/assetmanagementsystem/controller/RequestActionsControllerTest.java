package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
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
    void respondToRequest_approve_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ResponseDTO response = new ResponseDTO();
            response.setId(1L);
            response.setStatus(RequestStatus.APPROVED);

            mockMvc.perform(put("/requests/response")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("APPROVED"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void respondToRequest_reject_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("ADMIN"));

            ResponseDTO response = new ResponseDTO();
            response.setId(2L);
            response.setStatus(RequestStatus.REJECTED);
            response.setRejectionNote("Not available");

            mockMvc.perform(put("/requests/response")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("REJECTED"))
                    .andExpect(jsonPath("$.rejectionNote").value("Not available"));
        }
    }


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void respondToRequest_withNullId_shouldReturnBadRequest() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ResponseDTO response = new ResponseDTO();
            response.setStatus(RequestStatus.APPROVED);

            mockMvc.perform(put("/requests/response")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("id: ID cannot be null"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void respondToRequest_approvedWithRejectionNote_shouldReturnBadRequest() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ResponseDTO response = new ResponseDTO();
            response.setId(1L);
            response.setStatus(RequestStatus.APPROVED);
            response.setRejectionNote("Rejection Note");

            mockMvc.perform(put("/requests/response")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("You Can't Add A Rejection Note to an Accepted Asset"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void respondToRequest_notFound_shouldReturnNotFound() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            ResponseDTO response = new ResponseDTO();
            response.setId(845L);
            response.setStatus(RequestStatus.APPROVED);

            mockMvc.perform(put("/requests/response")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Request not found"));
        }
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "employee", authorities = {"EMPLOYEE"})
    void respondToRequest_asEmployee_shouldReturnForbidden() throws Exception {
        ResponseDTO response = new ResponseDTO();
        response.setId(1L);
        response.setStatus(RequestStatus.APPROVED);

        mockMvc.perform(put("/requests/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
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