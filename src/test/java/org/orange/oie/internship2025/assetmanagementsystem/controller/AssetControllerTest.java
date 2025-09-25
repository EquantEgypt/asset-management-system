package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.UpdateAssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AssetControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockedStatic<SecurityUtils> securityUtilsMock;


    @BeforeEach
    void setup() {
        securityUtilsMock = Mockito.mockStatic(SecurityUtils.class);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setEmail("admin@example.com");

        // set department
        Department dept = new Department();
        dept.setId(10L);
        dept.setName("IT");
        mockUser.setDepartment(dept);

        // set role
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        mockUser.setRole(role);

        securityUtilsMock.when(SecurityUtils::getCurrentUser).thenReturn(mockUser);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @DatabaseSetup(value = "/dataset/getAllAssets_withExistingAssets_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllAssets_withExistingAssets_shouldReturnList() throws Exception {

        mockMvc.perform(get("/assets/all"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/addAsset_withValidRequest_shouldReturnCreatedAsset.xml", type = DatabaseOperation.CLEAN_INSERT)
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

    @DatabaseSetup(value = "/dataset/getAllTypes_withExistingTypes_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
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

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withValidRequest_shouldReturnUpdatedAsset() throws Exception {
        UpdateAssetDto updateDto = new UpdateAssetDto();
        updateDto.setName("New Laptop Name");
        updateDto.setBrand("HP");
        updateDto.setSerialNumber("SN-LAP-003");

        mockMvc.perform(put("/assets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetName", is("New Laptop Name")))
                .andExpect(jsonPath("$.brand", is("HP")))
                .andExpect(jsonPath("$.serialNumber", is("SN-LAP-003")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withDuplicateSerialNumber_shouldReturnConflict() throws Exception {
        UpdateAssetDto updateDto = new UpdateAssetDto();
        updateDto.setSerialNumber("SN-MON-002");

        mockMvc.perform(put("/assets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isConflict());
    }
}