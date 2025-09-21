package org.orange.oie.internship2025.assetmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;

public interface AssetAssignmentRepository
        extends JpaRepository<AssetAssignment, Long>, JpaSpecificationExecutor<AssetAssignment> {

}
