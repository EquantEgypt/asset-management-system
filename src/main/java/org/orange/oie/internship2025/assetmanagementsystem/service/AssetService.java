package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;

public interface AssetService {
    AssetDto addAsset(AssetRequestDto assetDto);
}
