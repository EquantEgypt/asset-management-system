package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetService {
    AssetDto addAsset(AssetRequestDto assetDto);

    List<AssetDto> getAllAssets();

}
