package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(String roleType);
}
