package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import java.util.ArrayList;
import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.dto.MiniAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.springframework.stereotype.Component;
@Component
public class MiniAssetMapper {
    public MiniAssetDTO toDto(AssetAssignment assetAssignment) {
        if (assetAssignment == null) {
            return null;
        }
        MiniAssetDTO miniAssetDTO = new MiniAssetDTO();
        miniAssetDTO.setId(assetAssignment.getId());
        miniAssetDTO.setName(assetAssignment.getAsset().getName());
        miniAssetDTO.setType(assetAssignment.getAsset().getType().getName());
        miniAssetDTO.setCategory(assetAssignment.getAsset().getCategory().getName());
        miniAssetDTO.setBrand(assetAssignment.getAsset().getBrand());
        miniAssetDTO.setStatus(assetAssignment.getStatus().name());
        miniAssetDTO.setAssignedUser(assetAssignment.getAssignedTo().getUsername());
        miniAssetDTO.setDepartment(assetAssignment.getAssignedTo().getDepartment().getName());
        return miniAssetDTO;
    }
    public List<MiniAssetDTO> toDtoList(List<AssetAssignment> assetAssignments) {
        if (assetAssignments == null) {
            return List.of();
        }
        List<MiniAssetDTO> dtoList = new ArrayList<>();
        for (AssetAssignment assetAssignment : assetAssignments) {
            dtoList.add(toDto(assetAssignment));
        }
        return dtoList;
    }
    
}
