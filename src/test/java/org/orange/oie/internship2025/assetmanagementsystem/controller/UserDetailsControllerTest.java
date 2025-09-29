package org.orange.oie.internship2025.assetmanagementsystem.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDetailsControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getUserDetails_withExistingUser_shouldReturnUser.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getUserDetails_withExistingUser_shouldReturnUser() throws Exception {
        mockMvc.perform(get("/userdetails/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@orange.com"))
                .andExpect(jsonPath("$.fullName").value("System Administrator"))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @DatabaseSetup(value = "/dataset/getUserDetails_withExistingUser_shouldReturnUser.xml", type = DatabaseOperation.CLEAN_INSERT)
    void getUserDetails_withNonExistingUser_shouldReturnError() throws Exception {
        mockMvc.perform(get("/userdetails/{id}", 9999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
