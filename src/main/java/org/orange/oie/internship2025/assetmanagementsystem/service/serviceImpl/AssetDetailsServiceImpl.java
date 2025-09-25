package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetDetailsServiceImpl implements AssetDetailsService {
    @Autowired
    private  AssetRepository assetRepository;
    @Autowired
    private UserRepository userRepository;

    public AssetDetailsDto getAssetDetails(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id " + id));

        AssetDetailsDto dto = new AssetDetailsDto();
        dto.setAssetId(asset.getId());
        dto.setAssetName(asset.getName());
        dto.setBrand(asset.getBrand());
        dto.setAssetDescription(asset.getDescription());
        dto.setCategoryName(asset.getCategory().getName());
        dto.setTypeName(asset.getType().getName());
        dto.setLocation(asset.getLocation());
        dto.setSerialNumber(asset.getSerialNumber());
        dto.setPurchaseDate(asset.getPurchaseDate().toString());
        dto.setWarrantyEndDate(asset.getWarrantyEndDate().toString());
        dto.setStatus(asset.getStatus());
        dto.setImagePath(asset.getImagePath());



        if (asset.getAssignments() != null && !asset.getAssignments().isEmpty()) {
            var userAssignment = asset.getAssignments().get(asset.getAssignments().size() - 1);
            User userAssignment2 = userAssignment.getAssignedTo();
            dto.setAssignedToId(userAssignment2.getId());
            dto.setAssignedToName(userAssignment2.getUsername());
            dto.setAssignedToEmail(userAssignment2.getEmail());
        }

        return dto;
    }
}
