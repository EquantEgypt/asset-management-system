package org.orange.oie.internship2025.assetmanagementsystem.controller;

import jakarta.validation.Valid;
import org.orange.oie.internship2025.assetmanagementsystem.dto.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetService;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetTypeService;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public AssetController(AssetService assetService, AssetTypeService typeService, CategoryService categoryService) {
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AssetDto> updateAsset(@PathVariable Long id, @Valid @RequestBody UpdateAssetDto Dto) {
        AssetDto dto = assetService.updateAsset(id, Dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AssetDto> getAssetById(@PathVariable Long id) {
        AssetDto dto = assetService.getAssetById(id);
        return ResponseEntity.ok(dto);
    }




    @GetMapping("/types")
    public List<AssetType> getAllTypes(
            @RequestParam(required = false) Long categoryId

    ) {
        return typeService.getAllTypes(categoryId);
    }
    @GetMapping("/categories")
    public List<AssetCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping
    public ResponseEntity<Page<ListAssetDTO>> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable) {
        Page<ListAssetDTO> assets = assetService.getFilteredAsset(filterDTO, pageable);
        return ResponseEntity.ok(assets);
    }
    @GetMapping("/available")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('IT')")
    public List<AssetDto> getAvailableAsset(
            @RequestParam(required = false) String type
    ) {
        return assetService.getAvailableAsset(type);
    }
}
