package org.orange.oie.internship2025.assetmanagementsystem.repository;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<AssetType, Long> {
    public AssetType findByName(String name);
    List<AssetType> findByCategoryId(Long categoryId);
}
