package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AssetAssignmentService {

    AssetAssignment assignAsset(AssetAssignmentRequest request);
}
