package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AssetAssignmentService {

    ResponseEntity<ApiResponse> assignAsset(AssetAssignmentRequest request);
}
