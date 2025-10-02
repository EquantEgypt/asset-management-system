package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetHistoryDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
import org.springframework.stereotype.Component;

@Component
public class AssetHistoryMapper {

    public AssetHistoryDto toDto(AssetHistory history) {
        AssetHistoryDto dto = new AssetHistoryDto();
        dto.setId(history.getId());
        dto.setAssetName(history.getAsset().getName());
        dto.setNote(history.getNote());
        dto.setTimestamp(history.getTimestamp());
        dto.setStatus(history.getStatus());
        if (history.getUser() != null) {
            dto.setAssignedTo(history.getUser().getUsername());
        } else {
            dto.setAssignedTo(null);
        }

        return dto;
    }
}