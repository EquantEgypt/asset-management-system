package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> , JpaSpecificationExecutor<Asset> {
public Asset findByName(String name);
List<Asset> findByStatus(AssetStatus status);

}
