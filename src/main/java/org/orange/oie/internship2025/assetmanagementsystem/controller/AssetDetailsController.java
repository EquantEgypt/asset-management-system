package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl.AssetDetailsServiceImpl;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
public class AssetDetailsController {

    @Autowired
    private AssetDetailsService assetDetailsService;

    @GetMapping("/details/{id}")
    public ResponseEntity<AssetDetailsDto> getAssetDetails(@PathVariable Long id) {
        return ResponseEntity.ok(assetDetailsService.getAssetDetails(id));
    }
}