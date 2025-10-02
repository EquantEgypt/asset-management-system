package org.orange.oie.internship2025.assetmanagementsystem.controller;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ListAssetHistoryResponseDto;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/assets")
public class AssetHistoryController {

    @Autowired
    private AssetHistoryService assetHistoryService;

    @GetMapping("/history/{assetId}")
    public ResponseEntity<Page<ListAssetHistoryResponseDto>> getAssetHistoryByAssetId(
            @PathVariable Long assetId,
            Pageable pageable
    ) {
        Page<ListAssetHistoryResponseDto> historyPage = assetHistoryService.getHistoryByAssetId(assetId, pageable);
        return ResponseEntity.ok(historyPage);
    }
}