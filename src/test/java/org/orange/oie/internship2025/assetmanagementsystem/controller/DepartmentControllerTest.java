package org.orange.oie.internship2025.assetmanagementsystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentControllerTest extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DatabaseSetup(value = "/dataset/getAllDepartments_withExistingDepartments_shouldReturnList.xml", type = DatabaseOperation.CLEAN_INSERT)
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getAllDepartments_withExistingDepartments_shouldReturnList() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<AssetType> types = objectMapper.readValue(
                jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, AssetType.class)
        );

        assertThat(types).hasSize(2);
        AssertionsForClassTypes.assertThat(types.get(0).getName()).isEqualTo("IT");
        AssertionsForClassTypes.assertThat(types.get(1).getName()).isEqualTo("VOID");
    }

}
