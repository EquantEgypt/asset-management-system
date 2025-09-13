package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import java.util.ArrayList;
import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.springframework.stereotype.Component;

@Component
public class AssignedAssetMapper {
    public AssignedAssetDTO toDto(AssignedAsset assignedAsset) {
        if (assignedAsset == null) {
            return null;
        }

        AssignedAssetDTO dto = new AssignedAssetDTO();
        dto.setId(assignedAsset.getId());
        dto.setAssetName(assignedAsset.getAsset().getAssetName());
        dto.setType(assignedAsset.getAsset().getType().getTypeName());
        dto.setCategory(assignedAsset.getAsset().getCategory().getCategoryName());
        dto.setBrand(assignedAsset.getAsset().getBrand());
        dto.setStatus(assignedAsset.getStatus().name());
        dto.setAssignedUser(assignedAsset.getAssignedUser().getUsername());
        dto.setDepartment(assignedAsset.getAssignedUser().getDepartment().getDepartmentName());

        return dto;
    }
    public List<AssignedAssetDTO> toDtoList(List<AssignedAsset> assignedAssets) {
        if (assignedAssets == null) {
            return null;
        }

        List<AssignedAssetDTO> dtoList = new ArrayList<>();
        for (AssignedAsset assignedAsset : assignedAssets) {
            dtoList.add(toDto(assignedAsset));
        }
        return dtoList;
    }
    
}
