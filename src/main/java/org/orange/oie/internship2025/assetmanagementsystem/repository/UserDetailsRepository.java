package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
}
