package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AssetDetailsMapper {

    public AssetDetailsDto toDetailsDto(Asset asset) {
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
            User assignedUser = userAssignment.getAssignedTo();
            dto.setAssignedToId(assignedUser.getId());
            dto.setAssignedToName(assignedUser.getUsername());
            dto.setAssignedToEmail(assignedUser.getEmail());
        }

        return dto;
    }
}
