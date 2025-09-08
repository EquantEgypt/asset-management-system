package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.exception.InvalidAssetException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.springframework.stereotype.Service;

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
        validateAssetRequest(assetDto);

        Asset asset = assetMapper.toEntity(assetDto);
        assetRepository.save(asset);
        return assetMapper.toDto(asset);
    }

    private void validateAssetRequest(AssetRequestDto assetDto) {
        int totalCalculatedStock = assetDto.getNumberOfAvailableToAssign()
                + assetDto.getNumberOfMaintenance()
                + assetDto.getNumberOfRetired();

        if (totalCalculatedStock != assetDto.getAllStock()) {
            throw new InvalidAssetException("The sum of available, maintenance, and retired assets must equal the total stock.");
        }

        if (assetDto.getNumberOfAvailableToAssign() > assetDto.getAllStock()) {
            throw new InvalidAssetException("Available stock cannot be greater than total stock.");
        }

        if (assetDto.getNumberOfMaintenance() > assetDto.getAllStock()) {
            throw new InvalidAssetException("Maintenance stock cannot be greater than total stock.");
        }

        if (assetDto.getNumberOfRetired() > assetDto.getAllStock()) {
            throw new InvalidAssetException("Retired stock cannot be greater than total stock.");
        }
    }
}
