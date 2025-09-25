package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetHistoryDto;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetHistoryController {

    @Autowired
    private AssetHistoryService assetHistoryService;

    @GetMapping("/history/{assetId}")
    public ResponseEntity<List<AssetHistoryDto>> getAssetHistoryByAssetId(@PathVariable Long assetId) {
        return ResponseEntity.ok(assetHistoryService.getHistoryByAssetId(assetId));
    }
}
