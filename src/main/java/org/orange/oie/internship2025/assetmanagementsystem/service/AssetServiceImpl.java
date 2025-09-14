package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.model.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public AssetDto addAsset(AssetRequestDto assetDto) {
        Asset asset = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(asset);
        return assetMapper.toDto(savedAsset);
    }

    @Override
    public List<AssetDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

}
