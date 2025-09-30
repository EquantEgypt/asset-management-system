package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetAssignmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetHistoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
class AssetControllerUnassignTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetAssignmentRepository assetAssignmentRepository;

    @Autowired
    private AssetHistoryRepository assetHistoryRepository;

    // 1. Happy Path - Admin unassigns successfully
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/unassignAsset/asset-assigned.xml", type = DatabaseOperation.CLEAN_INSERT)
    void unassignAsset_asAdmin_success() throws Exception {
        User mockUser = new User();
        mockUser.setId(2L);
        mockUser.setEmail("admin@test.com");
        mockUser.setUsername("Admin");

        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUser).thenReturn(mockUser);

            mockMvc.perform(post("/asset-assignments/1/unassign"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("AVAILABLE"));
        }
    }

    // 2. Employee tries to unassign (Forbidden)
    @Test
    @WithMockUser(username = "employee", authorities = {"EMPLOYEE"})
    @DatabaseSetup(value = "/dataset/unassignAsset/asset-assigned.xml", type = DatabaseOperation.CLEAN_INSERT)
    void unassignAsset_asEmployee_forbidden() throws Exception {
        mockMvc.perform(post("/asset-assignments/1/unassign"))
                .andExpect(status().isForbidden());
    }

    // 3. IT tries to unassign (Forbidden)
    @Test
    @WithMockUser(username = "it", authorities = {"IT"})
    @DatabaseSetup(value = "/dataset/unassignAsset/asset-assigned.xml", type = DatabaseOperation.CLEAN_INSERT)
    void unassignAsset_asIt_forbidden() throws Exception {
        mockMvc.perform(post("/asset-assignments/1/unassign"))
                .andExpect(status().isForbidden());
    }

    // 4. Non-existing asset
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/unassignAsset/empty-db.xml", type = DatabaseOperation.CLEAN_INSERT)
    void unassignAsset_nonExistingAsset_notFound() throws Exception {
        mockMvc.perform(post("/asset-assignments/999/unassign"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Asset not found"));
    }

    // 5. Asset is AVAILABLE (no active assignment)
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/unassignAsset/asset-available.xml", type = DatabaseOperation.CLEAN_INSERT)
    void unassignAsset_availableAsset_error() throws Exception {
        mockMvc.perform(post("/asset-assignments/2/unassign"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Asset is not assigned to anyone"));
    }
}
