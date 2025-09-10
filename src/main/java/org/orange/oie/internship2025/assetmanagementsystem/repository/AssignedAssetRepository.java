package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
@Repository
public interface AssignedAssetRepository extends JpaRepository<AssignedAsset, Long>, JpaSpecificationExecutor<AssignedAsset> {
    
}
