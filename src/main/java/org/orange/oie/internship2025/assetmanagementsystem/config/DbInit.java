package org.orange.oie.internship2025.assetmanagementsystem.config;

import java.time.LocalDate;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0) {
            roleRepository.save(new Role(null,"ADMIN"));
            roleRepository.save(new Role(null,"EMPLOYEE"));
            roleRepository.save(new Role(null,"DEPARTMENT_MANAGER"));
            roleRepository.save(new Role(null,"IT"));
        }
        if(categoryRepository.count() == 0) {
            categoryRepository.save(new AssetCategory(null,"Electronics"));
        }
        if(typeRepository.count() == 0) {
            AssetCategory electronicsCategory = categoryRepository.findByName("Electronics");
            if (electronicsCategory != null) {
                typeRepository.save(new AssetType(null, electronicsCategory, "Laptop"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Monitor"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Keyboard"));
                typeRepository.save(new AssetType(null, electronicsCategory, "Mouse"));
            }
        }
        if(departmentRepository.count() == 0) {
            departmentRepository.save(new Department(null,"T1"));
            departmentRepository.save(new Department(null,"T2"));
            departmentRepository.save(new Department(null,"T3"));
            departmentRepository.save(new Department(null,"T4"));
        }
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            Department dept = departmentRepository.findByName("T1");
            User user = new User(null, "ADMIN", "ahmed", "admin@orange.com",
                 passwordEncoder.encode("admin123"),dept ,adminRole,"012456789"
             ,LocalDate.of(2000, 12, 12),true
             ,LocalDate.of(2000, 12, 13),LocalDate.of(2000, 12, 14));


            userRepository.save(user);

        }
    }
}
