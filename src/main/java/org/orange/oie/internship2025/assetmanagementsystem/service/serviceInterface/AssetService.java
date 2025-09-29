package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {
    AssetDto addAsset(AssetRequestDto assetDto);
    AssetDto updateAsset(Long id, UpdateAssetDto assetDto);
    List<AssetDto> getAllAssets();
    List<AssetDto> getAvailableAsset(String type);
    Page<ListAssetDTO> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable);
}