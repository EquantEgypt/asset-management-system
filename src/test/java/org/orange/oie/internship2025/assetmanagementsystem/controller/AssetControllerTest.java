package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    @DatabaseSetup(value = "/dataset/getFilteredAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getFilteredAsset_byStatus_shouldReturnMatchingAssets() throws Exception {
        mockMvc.perform(get("/assets")
                        .param("status", "AVAILABLE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("HR Monitor")))
                .andExpect(jsonPath("$.content[1].name", is("Guest Chair")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getFilteredAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getFilteredAsset_byType_shouldReturnMatchingAssets() throws Exception {
        mockMvc.perform(get("/assets")
                        .param("type", "Laptop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Dev Laptop")))
                .andExpect(jsonPath("$.content[1].name", is("Maintenance Laptop")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN", "IT"})
    @DatabaseSetup(value = "/dataset/getAvailableAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getAvailableAsset_noType_shouldReturnAllAvailable() throws Exception {
        mockMvc.perform(get("/assets/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].assetName", is("Available Laptop")))
                .andExpect(jsonPath("$[1].assetName", is("Available Monitor")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "it_user", authorities = {"IT"})
    @DatabaseSetup(value = "/dataset/getAvailableAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getAvailableAsset_byType_shouldReturnMatchingAssets() throws Exception {
        mockMvc.perform(get("/assets/available")
                        .param("type", "Monitor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].assetName", is("Available Monitor")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withExistingSerialNumber_shouldReturnConflict() throws Exception {
        UpdateAssetDto updateAssetDto = createUpdateAssetDto();
        updateAssetDto.setSerialNumber("SN-VALID-002"); // Existing serial number from asset 2

        mockMvc.perform(put("/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAssetDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withInvalidSerialNumberFormat_shouldReturnBadRequest() throws Exception {
        UpdateAssetDto updateAssetDto = createUpdateAssetDto();
        updateAssetDto.setSerialNumber("INVALID-FORMAT");

        mockMvc.perform(put("/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAssetDto)))
                .andExpect(status().isBadRequest());
    }

    private UpdateAssetDto createUpdateAssetDto() {
        UpdateAssetDto dto = new UpdateAssetDto();
        dto.setName("New Laptop Name");
        dto.setBrand("New Brand");
        dto.setAssetDescription("New Description");
        dto.setCategoryId(1L);
        dto.setTypeId(1L);
        dto.setLocation("New Location");
        dto.setSerialNumber("SN-NEW-123");
        dto.setPurchaseDate(LocalDateTime.of(2024, 1, 1, 10, 0));
        dto.setWarrantyEndDate(LocalDateTime.of(2027, 1, 1, 10, 0));
        dto.setImagePath("images/new.png");
        return dto;
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withValidRequest_shouldReturnOk() throws Exception {
        UpdateAssetDto updateAssetDto = createUpdateAssetDto();

        mockMvc.perform(put("/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAssetDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetName", is("New Laptop Name")))
                .andExpect(jsonPath("$.brand", is("New Brand")))
                .andExpect(jsonPath("$.serialNumber", is("SN-NEW-123")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withNonExistentAsset_shouldReturnNotFound() throws Exception {
        UpdateAssetDto updateAssetDto = createUpdateAssetDto();

        mockMvc.perform(put("/assets/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAssetDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/updateAsset_dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    void updateAsset_withInvalidData_shouldReturnBadRequest() throws Exception {
        UpdateAssetDto updateAssetDto = createUpdateAssetDto();
        updateAssetDto.setName(""); // Invalid name

        mockMvc.perform(put("/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAssetDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getAssetDetails_withExistingAsset_shouldReturnAssetDetails.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getAssetDetails_withExistingAsset_shouldReturnAssetDetails() throws Exception {
        MvcResult result = mockMvc.perform(get("/assets/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        AssetDetailsDto assetDetails = objectMapper.readValue(jsonResponse, AssetDetailsDto.class);

        Assertions.assertThat(assetDetails).isNotNull();
        Assertions.assertThat(assetDetails.getAssetName()).isEqualTo("Laptop");
        Assertions.assertThat(assetDetails.getBrand()).isEqualTo("Dell");
        Assertions.assertThat(assetDetails.getCategoryName()).isEqualTo("Electronics");
        Assertions.assertThat(assetDetails.getTypeName()).isEqualTo("Laptop");
        Assertions.assertThat(assetDetails.getAssignedToName()).isEqualTo("testuser");
    }

    @DatabaseSetup(value = "/dataset/assignAsset_withValidData.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "MARYIAM", authorities = {"IT"})
    void assignAsset_shouldReturnSuccess() throws Exception {
        securityUtilsMock.when(SecurityUtils::getCurrentUser)
                .thenReturn(getLoggedInUserByRole("IT"));

        AssetAssignmentRequest request = new AssetAssignmentRequest();
        request.setAssetId(1L);
        request.setUserId(2L);
        request.setCategoryId(1L);
        request.setTypeId(1L);
        request.setNote("Assigning Laptop to maryiam");

        mockMvc.perform(post("/assets/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset Assigned Successfully"));
    }

    @DatabaseSetup(value = "/dataset/assignAsset_withValidData.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "MARYIAM", authorities = {"IT"})
    void assignAsset_withAlreadyAssignedAssets_shouldReturnError() throws Exception {
        securityUtilsMock.when(SecurityUtils::getCurrentUser)
                .thenReturn(getLoggedInUserByRole("IT"));

        AssetAssignmentRequest request = new AssetAssignmentRequest();
        request.setAssetId(2L);
        request.setUserId(2L);
        request.setCategoryId(1L);
        request.setTypeId(1L);
        request.setNote("Assigning Chair to maryiam");

        mockMvc.perform(post("/assets/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").value("ASSET_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.code").value(100))
                .andExpect(jsonPath("$.message").value("Asset is not available"));
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