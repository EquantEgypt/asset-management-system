package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;

public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long>, JpaSpecificationExecutor<AssetAssignment> {

    boolean existsByAssetAndAssignedToAndStatus(Asset asset, User user, AssignmentStatus assignmentStatus);
    AssetAssignment findByAssetAndStatus(Asset asset,AssignmentStatus status);
    boolean existsByAssetAndStatus(Asset asset, AssignmentStatus assignmentStatus);
}
