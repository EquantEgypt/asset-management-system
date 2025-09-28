package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetHistoryDto;

import java.util.List;

public interface AssetHistoryService {
    List<AssetHistoryDto> getHistoryByAssetId(Long assetId);
}
