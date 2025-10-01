package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDetailsDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetDetailsMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetDetailsServiceImpl implements AssetDetailsService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetDetailsMapper assetDetailsMapper;

    @Override
    public AssetDetailsDto getAssetDetails(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND,
                        "Asset not found with id " + id));

        return assetDetailsMapper.toDetailsDto(asset);
    }
}
