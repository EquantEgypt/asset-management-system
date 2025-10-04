package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ApproveRequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RejectRequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @WithMockUser(username = "Hoto", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
            type = DatabaseOperation.CLEAN_INSERT)
    void addRequest_withValidRequest_shouldReturnCreatedRequest() throws Exception {
        RequestDTO dto = createRequestDTO();

        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser)
                  .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

            mockMvc.perform(post("/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.assetTypeId", is(1)))
                    .andExpect(jsonPath("$.requestType", is("NEW")))
                    .andExpect(jsonPath("$.requester", is("Hoto")));
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "EmployeeUser", authorities = {"EMPLOYEE"})
    @DatabaseSetup(value = "/dataset/addRequest_employeeRequestingForAnotherUser_shouldReturnForbidden.xml",
            type = DatabaseOperation.CLEAN_INSERT)
    void addRequest_employeeRequestingForAnotherUser_shouldReturnForbidden() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setRequesterId(2L); // different user than current mock
        dto.setAssetTypeId(1L);
        dto.setRequestType(RequestType.NEW);

        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser)
                  .thenReturn(getLoggedInUser("EMPLOYEE", 1L, "EmployeeUser", 10L));
            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

            mockMvc.perform(post("/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "ManagerUser", authorities = {"DEPARTMENT_MANAGER"})
    @DatabaseSetup(value = "/dataset/addRequest_managerRequestingForUserInSameDepartment_shouldSucceed.xml",
            type = DatabaseOperation.CLEAN_INSERT)
    void addRequest_managerRequestingForUserInSameDepartment_shouldSucceed() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setRequesterId(2L); // user in same dept
        dto.setAssetTypeId(1L);
        dto.setRequestType(RequestType.NEW);

        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser)
                  .thenReturn(getLoggedInUser("DEPARTMENT_MANAGER", 4L, "ManagerUser", 10L));
            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(4L);

            mockMvc.perform(post("/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.requester", is("EmployeeUser")));
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "ManagerUser", authorities = {"DEPARTMENT_MANAGER"})
    @DatabaseSetup(value = "/dataset/addRequest_managerRequestingForUserInOtherDepartment_shouldReturnForbidden.xml",
            type = DatabaseOperation.CLEAN_INSERT)
    void addRequest_managerRequestingForUserInOtherDepartment_shouldReturnForbidden() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setRequesterId(3L); // user in different dept
        dto.setAssetTypeId(1L);
        dto.setRequestType(RequestType.NEW);

        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser)
                  .thenReturn(getLoggedInUser("DEPARTMENT_MANAGER", 4L, "ManagerUser", 10L));
            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(4L);

            mockMvc.perform(post("/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isForbidden());
        }
    }
@Test
@Transactional
@WithMockUser(username = "Hoto", authorities = {"ADMIN"})
@DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
        type = DatabaseOperation.CLEAN_INSERT)
void addRequest_whenMaintenanceWithoutAsset_shouldReturnBadRequest() throws Exception {
    RequestDTO dto = new RequestDTO();
    dto.setRequesterId(1L);
    dto.setRequestType(RequestType.MAINTENANCE);
    dto.setAssetId(null); // invalid: maintenance requires asset

    try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
        mocked.when(SecurityUtils::getCurrentUser)
              .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
        mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}

@Test
@Transactional
@WithMockUser(username = "Hoto", authorities = {"ADMIN"})
@DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
        type = DatabaseOperation.CLEAN_INSERT)
void addRequest_whenAssetDoesNotExist_shouldReturnNotFound() throws Exception {
    RequestDTO dto = new RequestDTO();
    dto.setRequesterId(1L);
    dto.setRequestType(RequestType.NEW);
    dto.setAssetId(999L); // non-existent
    dto.setAssetTypeId(1L);

    try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
        mocked.when(SecurityUtils::getCurrentUser)
              .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
        mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}

@Test
@Transactional
@WithMockUser(username = "Hoto", authorities = {"ADMIN"})
@DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
        type = DatabaseOperation.CLEAN_INSERT)
void addRequest_whenAssetTypeDoesNotExist_shouldReturnNotFound() throws Exception {
    RequestDTO dto = new RequestDTO();
    dto.setRequesterId(1L);
    dto.setRequestType(RequestType.NEW);
    dto.setAssetTypeId(999L); // non-existent

    try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
        mocked.when(SecurityUtils::getCurrentUser)
              .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
        mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}

@Test
@Transactional
@WithMockUser(username = "Hoto", authorities = {"ADMIN"})
@DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
        type = DatabaseOperation.CLEAN_INSERT)
void addRequest_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
    RequestDTO dto = new RequestDTO();
    dto.setRequesterId(999L); // user not in DB
    dto.setRequestType(RequestType.NEW);
    dto.setAssetTypeId(1L);

    try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
        mocked.when(SecurityUtils::getCurrentUser)
              .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
        mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}


    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    void approveRequest_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("IT", 2L, "it_user", 1L));

            ApproveRequestDTO dto = new ApproveRequestDTO();

            mockMvc.perform(put("/request/1/approve")
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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("ADMIN", 1L, "admin", 1L));

            ApproveRequestDTO dto = new ApproveRequestDTO();
            dto.setAssetId(5L);

            mockMvc.perform(put("/request/2/approve")
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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("IT", 2L, "it_user", 1L));

            ApproveRequestDTO dto = new ApproveRequestDTO();

            mockMvc.perform(put("/request/999/approve")
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

        mockMvc.perform(put("/request/1/approve")
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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("ADMIN", 1L, "admin", 1L));

            RejectRequestDTO dto = new RejectRequestDTO();
            dto.setRejectionNote("Not available");

            mockMvc.perform(put("/request/2/reject")
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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("IT", 2L, "it_user", 1L));

            RejectRequestDTO dto = new RejectRequestDTO();
            dto.setRejectionNote("Not available");

            mockMvc.perform(put("/request/999/reject")
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

        mockMvc.perform(put("/request/1/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getPendingRequests_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("ADMIN", 1L, "admin", 1L));

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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("ADMIN", 1L, "admin", 1L));

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
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUser("USER", 3L, "regular_user", 1L)
            );

            mockMvc.perform(get("/request"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }
    }
    @DatabaseSetup(value = "/dataset/requests_data.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getRequests_withSearchAndFilter_shouldReturnFilteredResults() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(
                    getLoggedInUser("ADMIN", 1L, "admin", 1L)
            );

            mockMvc.perform(get("/request")
                            .param("search", "Dell XPS") // more specific than "Laptop"
                            .param("status", "PENDING")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1))
                    .andExpect(jsonPath("$.content[0].assetName").value("Dell XPS"));
        }
        }

    // --- helpers ---

    private RequestDTO createRequestDTO() {
        RequestDTO dto = new RequestDTO();
        dto.setRequesterId(1L);
        dto.setAssetTypeId(1L);
        dto.setRequestType(RequestType.NEW);
        return dto;
    }

    private User getLoggedInUser(String roleName, Long userId, String username, Long departmentId) {
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername(username);

        Department dept = new Department();
        dept.setId(departmentId);
        mockUser.setDepartment(dept);

        Role role = new Role();
        role.setName(roleName);
        mockUser.setRole(role);

        return mockUser;
    }
}
