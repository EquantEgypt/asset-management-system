package org.orange.oie.internship2025.assetmanagementsystem.repository;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<AssetCategory, Long> {
}
