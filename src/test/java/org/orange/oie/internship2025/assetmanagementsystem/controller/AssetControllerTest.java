package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AssetControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeAll
    public static void initTest() throws SQLException {

    }

    @DatabaseSetup(value = "/dataset/getAllAssets_withExistingAssets_shouldReturnList.xml", type = DatabaseOperation.INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllAssets_withExistingAssets_shouldReturnList() throws Exception {

        mockMvc.perform(get("/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].assetName").value("Asset 1"))
                .andExpect(jsonPath("$[1].assetName").value("Asset 2"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/addAsset_withValidRequest_shouldReturnCreatedAsset.xml", type = DatabaseOperation.INSERT)
    void addAsset_withValidRequest_shouldReturnCreatedAsset() throws Exception {
        AssetRequestDto assetRequestDto = createAssetRequest();

        mockMvc.perform(post("/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assetRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assetName", is("Laptop")))
                .andExpect(jsonPath("$.brand", is("Dell")))
                .andExpect(jsonPath("$.serialNumber", is("SN12345")))
                .andExpect(jsonPath("$.status", is("AVAILABLE")));
    }

    private AssetRequestDto createAssetRequest() {
        AssetRequestDto dto = new AssetRequestDto();
        dto.setName("Laptop");
        dto.setBrand("Dell");
        dto.setAssetDescription("Dell Latitude");
        dto.setCategoryId(1L);
        dto.setTypeId(1L);
        dto.setLocation("Cairo HQ");
        dto.setSerialNumber("SN12345");
        dto.setPurchaseDate(LocalDateTime.of(2023, 1, 15, 10, 0));
        dto.setWarrantyEndDate(LocalDateTime.of(2025, 1, 15, 10, 0));
        dto.setStatus(AssetStatus.AVAILABLE);
        dto.setImagePath("images/laptop.png");

        return dto;
    }

    @DatabaseSetup(value = "/dataset/getAllTypes_withExistingTypes_shouldReturnList.xml", type = DatabaseOperation.INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllTypes_withExistingTypes_shouldReturnList() throws Exception {

        MvcResult result = mockMvc.perform(get("/assets/types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<AssetType> types = objectMapper.readValue(
                jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, AssetType.class)
        );

        assertThat(types).hasSize(2);
        assertThat(types.get(0).getName()).isEqualTo("Laptop");
        assertThat(types.get(1).getName()).isEqualTo("Monitor");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getAllCategories_withExistingCategories_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void getAllCategories_withExistingCategories_shouldReturnList() throws Exception {
        MvcResult result = mockMvc.perform(get("/assets/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<AssetCategory> categories = objectMapper.readValue(
                jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, AssetCategory.class)
        );

        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).getName()).isEqualTo("Hardware");
        assertThat(categories.get(1).getName()).isEqualTo("Software");
    }

}

