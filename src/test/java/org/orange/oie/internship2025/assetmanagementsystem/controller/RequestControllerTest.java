//package org.orange.oie.internship2025.assetmanagementsystem.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.springtestdbunit.annotation.DatabaseOperation;
//import com.github.springtestdbunit.annotation.DatabaseSetup;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
//import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
//import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class RequestControllerTest{
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @Transactional
//    @WithMockUser(username = "Hoto", authorities = {"ADMIN"})
//    @DatabaseSetup(value = "/dataset/addRequest_withValidRequest_shouldReturnCreatedRequest.xml",
//            type = DatabaseOperation.CLEAN_INSERT)
//    void addRequest_withValidRequest_shouldReturnCreatedRequest() throws Exception {
//        RequestDTO dto = createRequestDTO();
//
//        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
//            mocked.when(SecurityUtils::getCurrentUser)
//                  .thenReturn(getLoggedInUser("ADMIN", 1L, "Hoto", 10L));
//            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
//
//            mockMvc.perform(post("/request")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(dto)))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.assetTypeId", is(1)))
//                    .andExpect(jsonPath("$.requestType", is("NEW")))
//                    .andExpect(jsonPath("$.requester", is("Hoto")));
//        }
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = "EmployeeUser", authorities = {"EMPLOYEE"})
//    @DatabaseSetup(value = "/dataset/addRequest_employeeRequestingForAnotherUser_shouldReturnForbidden.xml",
//            type = DatabaseOperation.CLEAN_INSERT)
//    void addRequest_employeeRequestingForAnotherUser_shouldReturnForbidden() throws Exception {
//        RequestDTO dto = new RequestDTO();
//        dto.setRequesterId(2L); // different user than current mock
//        dto.setAssetTypeId(1L);
//        dto.setRequestType(RequestType.NEW);
//
//        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
//            mocked.when(SecurityUtils::getCurrentUser)
//                  .thenReturn(getLoggedInUser("EMPLOYEE", 1L, "EmployeeUser", 10L));
//            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
//
//            mockMvc.perform(post("/request")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(dto)))
//                    .andExpect(status().isForbidden());
//        }
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = "ManagerUser", authorities = {"DEPARTMENT_MANAGER"})
//    @DatabaseSetup(value = "/dataset/addRequest_managerRequestingForUserInSameDepartment_shouldSucceed.xml",
//            type = DatabaseOperation.CLEAN_INSERT)
//    void addRequest_managerRequestingForUserInSameDepartment_shouldSucceed() throws Exception {
//        RequestDTO dto = new RequestDTO();
//        dto.setRequesterId(2L); // user in same dept
//        dto.setAssetTypeId(1L);
//        dto.setRequestType(RequestType.NEW);
//
//        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
//            mocked.when(SecurityUtils::getCurrentUser)
//                  .thenReturn(getLoggedInUser("DEPARTMENT_MANAGER", 4L, "ManagerUser", 10L));
//            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(4L);
//
//            mockMvc.perform(post("/request")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(dto)))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.requester", is("EmployeeUser")));
//        }
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = "ManagerUser", authorities = {"DEPARTMENT_MANAGER"})
//    @DatabaseSetup(value = "/dataset/addRequest_managerRequestingForUserInOtherDepartment_shouldReturnForbidden.xml",
//            type = DatabaseOperation.CLEAN_INSERT)
//    void addRequest_managerRequestingForUserInOtherDepartment_shouldReturnForbidden() throws Exception {
//        RequestDTO dto = new RequestDTO();
//        dto.setRequesterId(3L); // user in different dept
//        dto.setAssetTypeId(1L);
//        dto.setRequestType(RequestType.NEW);
//
//        try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
//            mocked.when(SecurityUtils::getCurrentUser)
//                  .thenReturn(getLoggedInUser("DEPARTMENT_MANAGER", 4L, "ManagerUser", 10L));
//            mocked.when(SecurityUtils::getCurrentUserId).thenReturn(4L);
//
//            mockMvc.perform(post("/request")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(dto)))
//                    .andExpect(status().isForbidden());
//        }
//    }
//
//    // --- helpers ---
//
//    private RequestDTO createRequestDTO() {
//        RequestDTO dto = new RequestDTO();
//        dto.setRequesterId(1L);
//        dto.setAssetTypeId(1L);
//        dto.setRequestType(RequestType.NEW);
//        return dto;
//    }
//
//    private User getLoggedInUser(String roleName, Long userId, String username, Long departmentId) {
//        User mockUser = new User();
//        mockUser.setId(userId);
//        mockUser.setUsername(username);
//
//        Department dept = new Department();
//        dept.setId(departmentId);
//        mockUser.setDepartment(dept);
//
//        Role role = new Role();
//        role.setName(roleName);
//        mockUser.setRole(role);
//
//        return mockUser;
//    }
//}
