package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.MiniAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MiniAssetMapper {
    public MiniAssetDTO toDto(Asset asset) {
        if (asset == null) {
            return null;
        }
        MiniAssetDTO miniAssetDTO = new MiniAssetDTO();
        miniAssetDTO.setId(asset.getId());
        miniAssetDTO.setSerialNumber(asset.getSerialNumber());
        miniAssetDTO.setName(asset.getName());
        miniAssetDTO.setType(asset.getType().getName());
        miniAssetDTO.setCategory(asset.getCategory().getName());
        miniAssetDTO.setBrand(asset.getBrand());
        miniAssetDTO.setStatus(asset.getStatus().name());

        if (asset.getAssignments() != null && !asset.getAssignments().isEmpty()) {
            Optional<AssetAssignment> mostRecentAssignment = asset.getAssignments().stream()
                    .max(Comparator.comparing(AssetAssignment::getAssignmentDate));

            mostRecentAssignment.ifPresent(assignment -> {
                miniAssetDTO.setAssignedUser(assignment.getAssignedTo().getUsername());
                miniAssetDTO.setDepartment(assignment.getAssignedTo().getDepartment().getName());
            });
        }

        return miniAssetDTO;
    }

    public List<MiniAssetDTO> toDtoList(List<Asset> assets) {
        if (assets == null) {
            return List.of();
        }
        return assets.stream().map(this::toDto).collect(Collectors.toList());
    }
}