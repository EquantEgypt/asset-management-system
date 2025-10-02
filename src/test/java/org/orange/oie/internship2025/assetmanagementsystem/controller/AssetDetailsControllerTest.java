package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssetDetailsControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getAssetDetails_withExistingAsset_shouldReturnAssetDetails.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getAssetDetails_withExistingAsset_shouldReturnAssetDetails() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/assets/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        AssetDetailsDto assetDetails = objectMapper.readValue(jsonResponse, AssetDetailsDto.class);

        assertThat(assetDetails).isNotNull();
        assertThat(assetDetails.getAssetName()).isEqualTo("Laptop");
        assertThat(assetDetails.getBrand()).isEqualTo("Dell");
        assertThat(assetDetails.getCategoryName()).isEqualTo("Electronics");
        assertThat(assetDetails.getTypeName()).isEqualTo("Laptop");
        assertThat(assetDetails.getAssignedToName()).isEqualTo("testuser");
    }
}