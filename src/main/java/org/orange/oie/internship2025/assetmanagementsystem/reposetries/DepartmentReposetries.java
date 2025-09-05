package org.orange.oie.internship2025.assetmanagementsystem.reposetries;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentReposetries  extends CrudRepository<Department, Long> {
}
