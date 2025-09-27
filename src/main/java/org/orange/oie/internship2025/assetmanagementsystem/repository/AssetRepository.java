package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> , JpaSpecificationExecutor<Asset> {
public Asset findByName(String name);
Optional<Asset> findByIdAndTypeIdAndCategoryId(Long assetId, Long typeId, Long categoryId);
}
