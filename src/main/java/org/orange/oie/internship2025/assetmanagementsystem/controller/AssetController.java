package org.orange.oie.internship2025.assetmanagementsystem.controller;

import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetService;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetTypeService;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;
    private final AssetTypeService typeService;
    private final CategoryService categoryService;

    public AssetController(AssetService assetService , AssetTypeService typeService, CategoryService categoryService) {
        this.assetService = assetService;
        this.typeService = typeService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AssetDto> addAsset(@Valid @RequestBody AssetRequestDto assetRequestDto) {
        AssetDto dto = assetService.addAsset(assetRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AssetDto>> getAllAssets(){
        List<AssetDto> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/types")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AssetType> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AssetCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
