package org.orange.oie.internship2025.assetmanagementsystem;

import org.enums.AssetStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@EntityScan(basePackageClasses = { User.class, Role.class, Department.class, Category.class })
class AssignedAssetIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssignedAssetRepository assignedAssetRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TypeRepository typeRepository;
    private String itAuthHeader;
    private String employeeAuthHeader;
    private String managerAuthHeader;

    @BeforeEach
    void setUp() {
        assignedAssetRepository.deleteAll();
        assetRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();
        categoryRepository.deleteAll();
        typeRepository.deleteAll();

        // ---- Departments ----
        Department it = new Department();
        it.setDepartmentName("IT");
        departmentRepository.save(it);

        Department hr = new Department();
        hr.setDepartmentName("HR");
        departmentRepository.save(hr);

        // ---- Roles ----
        Role employeeRole = new Role();
        employeeRole.setRoleType("Employee");
        roleRepository.save(employeeRole);

        Role managerRole = new Role();
        managerRole.setRoleType("Department Manager");
        roleRepository.save(managerRole);

        Role itRole = new Role();
        itRole.setRoleType("IT");
        roleRepository.save(itRole);

        // ---- Categories ----
        Category hardware = new Category();
        hardware.setCategoryName("Hardware");
        categoryRepository.save(hardware);

        Category software = new Category();
        software.setCategoryName("Software");
        categoryRepository.save(software);

        // ---- Types ----
        Type laptop = new Type();
        laptop.setTypeName("Laptop");
        typeRepository.save(laptop);

        Type license = new Type();
        license.setTypeName("License");
        typeRepository.save(license);

        // ---- Users ----
        User employee = new User();
        employee.setEmail("emp@orange.com");
        employee.setUsername("emp1");
        employee.setPassword(passwordEncoder.encode("password123"));
        employee.setRole(employeeRole);
        employee.setDepartment(it);
        userRepository.save(employee);

        User manager = new User();
        manager.setEmail("manager@orange.com");
        manager.setUsername("mgr1");
        manager.setPassword(passwordEncoder.encode("password123"));
        manager.setRole(managerRole);
        manager.setDepartment(it);
        userRepository.save(manager);

        User itUser = new User();
        itUser.setEmail("it@orange.com");
        itUser.setUsername("it1");
        itUser.setPassword(passwordEncoder.encode("password123"));
        itUser.setRole(itRole);
        itUser.setDepartment(it);
        userRepository.save(itUser);

        // ---- Auth headers ----
        employeeAuthHeader = "Basic " + Base64.getEncoder()
                .encodeToString("emp@orange.com:password123".getBytes());
        managerAuthHeader = "Basic " + Base64.getEncoder()
                .encodeToString("manager@orange.com:password123".getBytes());
        itAuthHeader = "Basic " + Base64.getEncoder()
                .encodeToString("it@orange.com:password123".getBytes());

        // ---- Assets ----
        Asset dell = new Asset();
        dell.setAssetName("Dell Latitude");
        dell.setBrand("Dell");
        dell.setCategory(hardware);
        dell.setType(laptop);
        dell.setAllStock(10);
        dell.setNumberOfAvailableToAssign(8);
        dell.setNumberOfRetired(1);
        dell.setNumberOfMaintenance(1);
        assetRepository.save(dell);

        Asset photoshop = new Asset();
        photoshop.setAssetName("Photoshop License");
        photoshop.setBrand("Adobe");
        photoshop.setCategory(software);
        photoshop.setType(license);
        photoshop.setAllStock(10);
        photoshop.setNumberOfAvailableToAssign(8);
        photoshop.setNumberOfMaintenance(1);
        photoshop.setNumberOfRetired(1);
        assetRepository.save(photoshop);

        // ---- Assigned Assets ----
        AssignedAsset aa1 = new AssignedAsset();
        aa1.setAsset(dell);
        aa1.setAssignedUser(employee);
        aa1.setStatus(AssetStatus.GOOD);
        aa1.setDateAssigned(new Date());
        assignedAssetRepository.save(aa1);

        AssignedAsset aa2 = new AssignedAsset();
        aa2.setAsset(photoshop);
        aa2.setAssignedUser(employee);
        aa2.setStatus(AssetStatus.UNDER_MAINTENANCE);
        aa2.setDateAssigned(new Date());
        assignedAssetRepository.save(aa2);
    }

    @Test
    void employeeShouldSeeOwnAssetsOnly() throws Exception {
        mockMvc.perform(get("/asset")
                .header("Authorization", employeeAuthHeader)
                .param("assignedUser", "emp1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assignedUser.username").value("emp1"));
    }

    @Test
    void managerShouldSeeDepartmentAssets() throws Exception {
        mockMvc.perform(get("/asset")
                .header("Authorization", managerAuthHeader)
                .param("department", "IT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assignedUser.department.departmentName").value("IT"));
    }

    @Test
    void employeeShouldNotSeeOtherDepartments() throws Exception {
        mockMvc.perform(get("/asset")
                .header("Authorization", employeeAuthHeader)
                .param("department", "HR")) // not allowed
                .andExpect(status().isForbidden());
    }

    @Test
    void filterByStatusWorks() throws Exception {
        mockMvc.perform(get("/asset")
                .header("Authorization", managerAuthHeader)
                .param("status", "UNDER_MAINTENANCE")
                .param("department", "IT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("UNDER_MAINTENANCE"));
    }
}
