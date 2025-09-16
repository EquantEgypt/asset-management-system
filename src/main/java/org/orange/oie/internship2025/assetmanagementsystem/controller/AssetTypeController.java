package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.service.AssetTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AssetTypeController {

    private final AssetTypeService typeService;

    public AssetTypeController(AssetTypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    @PreAuthorize("hasAuthority('Admin')")
    public List<AssetType> getAllTypes() {
        return typeService.getAllTypes();
    }
}