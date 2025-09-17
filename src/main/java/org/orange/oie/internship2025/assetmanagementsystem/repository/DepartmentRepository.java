package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}
