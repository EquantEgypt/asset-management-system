package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl.AssetDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AssetDetailsController {
    @Autowired
    private  AssetDetailsServiceImpl assetDetailsService;

    @GetMapping("/api/assets/details/{id}")
    public ResponseEntity<AssetDetailsDto> getAssetDetails(@PathVariable Long id) {
        AssetDetailsDto assetDetails = assetDetailsService.getAssetDetails(id);
        return ResponseEntity.ok(assetDetails);
    }
}
