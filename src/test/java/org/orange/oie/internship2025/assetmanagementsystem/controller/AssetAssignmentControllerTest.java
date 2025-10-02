package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssetAssignmentControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DatabaseSetup(value = "/dataset/assignAsset_withValidData.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "MARYIAM", authorities = {"IT"})
    void assignAsset_shouldReturnSuccess() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            AssetAssignmentRequest request = new AssetAssignmentRequest();
            request.setAssetId(1L);
            request.setUserId(2L);
            request.setCategoryId(1L);
            request.setTypeId(1L);
            request.setNote("Assigning Laptop to maryiam");
            mockMvc.perform(post("/asset-assignments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Asset Assigned Successfully"));        }
    }
    @DatabaseSetup(value = "/dataset/assignAsset_withValidData.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "MARYIAM", authorities = {"IT"})
    void assignAsset_withAlreadyAssignedAssets_shouldReturnError() throws Exception {
        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(getLoggedInUserByRole("IT"));

            AssetAssignmentRequest request = new AssetAssignmentRequest();
            request.setAssetId(2L);
            request.setUserId(2L);
            request.setCategoryId(1L);
            request.setTypeId(1L);
            request.setNote("Assigning Chair to maryiam");

            mockMvc.perform(post("/asset-assignments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.data").isEmpty())
                    .andExpect(jsonPath("$.error").value("ASSET_ALREADY_EXISTS"))
                    .andExpect(jsonPath("$.code").value(100))                     .andExpect(jsonPath("$.message").value("Asset is not available"));
        }
    }

    private User getLoggedInUserByRole(String roleName) {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("MARYIAM");
        mockUser.setFullName("MARY");
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