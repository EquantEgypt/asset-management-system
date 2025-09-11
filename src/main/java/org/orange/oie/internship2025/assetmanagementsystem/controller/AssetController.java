package org.orange.oie.internship2025.assetmanagementsystem.controller;

import jakarta.validation.Valid;

import org.apache.catalina.security.SecurityUtil;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.service.AssetService;
import org.orange.oie.internship2025.assetmanagementsystem.service.AssignedAssetServiceImpl;
import org.orange.oie.internship2025.assetmanagementsystem.service.AssignedAssetServiceProxy;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    private final AssetService assetService;

    @Autowired
    private AssignedAssetServiceProxy assignedAssetServiceProxy;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<AssetDto> addAsset(@Valid @RequestBody AssetRequestDto assetRequestDto){
        AssetDto dto =  assetService.addAsset(assetRequestDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<AssetDto>> getAllAssets(){
        List<AssetDto> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }
    
    @GetMapping
    public ResponseEntity<List<AssignedAsset>> getFilteredAsset(AssignedAssetFilterDTO filterDTO) {

        List<AssignedAsset> assets = assignedAssetServiceProxy.checkForAuthorization(filterDTO);
        return ResponseEntity.ok(assets);
    }
    


}
