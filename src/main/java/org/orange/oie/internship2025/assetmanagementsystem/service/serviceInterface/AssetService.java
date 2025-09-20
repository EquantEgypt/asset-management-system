package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.MiniAssetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {
    AssetDto addAsset(AssetRequestDto assetDto);
    Page<MiniAssetDTO> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable);
    List<AssetDto> getAvailableAsset();

}
