package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.orange.oie.internship2025.assetmanagementsystem.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {
     List<Department> getAllDepartments();
}
