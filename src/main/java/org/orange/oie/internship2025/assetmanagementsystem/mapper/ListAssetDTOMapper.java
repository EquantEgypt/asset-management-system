package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.ListAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListAssetDTOMapper {
    public ListAssetDTO toDto(Asset asset) {
        if (asset == null) {
            return null;
        }
        ListAssetDTO listAssetDTO = new ListAssetDTO();
        listAssetDTO.setId(asset.getId());
        listAssetDTO.setSerialNumber(asset.getSerialNumber());
        listAssetDTO.setName(asset.getName());
        listAssetDTO.setType(asset.getType().getName());
        listAssetDTO.setCategory(asset.getCategory().getName());
        listAssetDTO.setBrand(asset.getBrand());
        listAssetDTO.setStatus(asset.getStatus().name());

        if (asset.getAssignments() != null && !asset.getAssignments().isEmpty()) {
            Optional<AssetAssignment> mostRecentAssignment = asset.getAssignments().stream()
                    .max(Comparator.comparing(AssetAssignment::getAssignmentDate));

            mostRecentAssignment.ifPresent(assignment -> {
                listAssetDTO.setAssignedUser(assignment.getAssignedTo().getUsername());
                listAssetDTO.setDepartment(assignment.getAssignedTo().getDepartment().getName());
            });
        }

        return listAssetDTO;
    }

    public List<ListAssetDTO> toDtoList(List<Asset> assets) {
        if (assets == null) {
            return List.of();
        }
        return assets.stream().map(this::toDto).collect(Collectors.toList());
    }
}