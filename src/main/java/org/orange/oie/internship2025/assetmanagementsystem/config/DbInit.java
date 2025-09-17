package org.orange.oie.internship2025.assetmanagementsystem.config;

import java.time.LocalDate;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssignmentStatus;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetAssignmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.CategoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.RoleRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Profile({ "dev", "test" })
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbInit implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final TypeRepository typeRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final AssetRepository assetRepository;
    @Autowired
    private final AssetAssignmentRepository assetAssingnmentRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void insertRole() {

        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, "ADMIN"));
            roleRepository.save(new Role(null, "EMPLOYEE"));
            roleRepository.save(new Role(null, "DEPARTMENT_MANAGER"));
            roleRepository.save(new Role(null, "IT"));
        }
    }

    public void insertCategory() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new AssetCategory(null, "Electronics"));
        }
    }

    public void insertType() {
        if (typeRepository.count() == 0) {
            AssetCategory electronicsCategory = categoryRepository.findByName("Electronics");
            if (electronicsCategory != null) {
                typeRepository.save(new AssetType(null, electronicsCategory, "Laptop"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Monitor"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Keyboard"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Mouse"));
            }
        }
    }

    public void insertDepartment() {
        if (departmentRepository.count() == 0) {
            departmentRepository.save(new Department(null, "T1"));
            departmentRepository.save(new Department(null, "T2"));
            departmentRepository.save(new Department(null, "T3"));
            departmentRepository.save(new Department(null, "T4"));
        }
    }

    public void insertUser() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            Department department = departmentRepository.findByName("T1");
            User user = new User(null, "Hoto", "ahmed Samir", "admin@orange.com",
                    passwordEncoder.encode("admin123"), department, adminRole, "012456789", LocalDate.of(2000, 12, 12), true,
                    LocalDate.of(2000, 12, 13), LocalDate.of(2000, 12, 14));

            userRepository.save(user);

        }
    }

    public void insertAsset() {
        if (assetRepository.count() == 0) {
            Asset asset = new Asset();
            asset.setId(null);
            asset.setName("Latitude 5123");
            asset.setSerialNumber("1234567890");
            asset.setPurchaseDate(LocalDate.of(2020, 1, 1));
            asset.setWarrantyEndDate(LocalDate.of(2025, 1, 1));
            asset.setStatus(AssetStatus.ASSIGNED);
            asset.setBrand("Dell");
            asset.setDescription("Laptop with 8GB RAM and 256GB SSD");
            asset.setCategory(categoryRepository.findByName("Electronics"));
            asset.setType(typeRepository.findByName("Laptop"));
            asset.setLocation("Room 101");
            asset.setImagePath("https://example.com/laptop.jpg");
            assetRepository.save(asset);
        }
    }

    public void insertAssetAssignment() {
        if (assetAssingnmentRepository.count() == 0) {
            Asset asset = assetRepository.findByName("Latitude 5123");
            User user = userRepository.findByUsername("Hoto");
            assetAssingnmentRepository.save(new AssetAssignment(null, asset, user,
                    LocalDate.of(2020, 1, 1), AssignmentStatus.ACTIVE, LocalDate.of(2020, 1, 1), "ok"));
        }
    }

    @Override
    public void run(String... args) {
        assetAssingnmentRepository.deleteAll();

        userRepository.deleteAll();
        
        assetRepository.deleteAll();
        
        typeRepository.deleteAll();
        
        categoryRepository.deleteAll();

        roleRepository.deleteAll();

        departmentRepository.deleteAll();



        insertRole();
        insertCategory();
        insertType();
        insertDepartment();
        insertUser();
        insertAsset();
        insertAssetAssignment();

    }
}
