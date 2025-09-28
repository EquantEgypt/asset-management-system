package org.orange.oie.internship2025.assetmanagementsystem.controller;
import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset-assignments")

public class AssetAssignmentController {
    private final AssetAssignmentService assetAssignmentService;
    public AssetAssignmentController( AssetAssignmentService assetAssignmentService) {
        this.assetAssignmentService=assetAssignmentService;
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('IT')")
    public ResponseEntity assignAsset(
            @Valid  @RequestBody AssetAssignmentRequest request
    ) {
        assetAssignmentService.assignAsset(request);
        return ResponseEntity.ok("Asset Assigned Successfully");
    }
}