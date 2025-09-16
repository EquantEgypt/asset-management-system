package org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;

import java.util.List;

public interface AssetService {
    AssetDto addAsset(AssetRequestDto assetDto);
    List<AssetDto> getAllAssets();
}
