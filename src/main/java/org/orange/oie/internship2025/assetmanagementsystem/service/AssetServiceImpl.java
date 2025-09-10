package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.exception.InvalidAssetException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetMapper;
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
        validateAssetRequest(assetDto);

        Asset asset = assetMapper.toEntity(assetDto);
        assetRepository.save(asset);
        return assetMapper.toDto(asset);
    }

    @Override
    public List<AssetDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateAssetRequest(AssetRequestDto assetDto) {
        int sumOfParts = assetDto.getNumberOfAvailableToAssign()
                + assetDto.getNumberOfMaintenance()
                + assetDto.getNumberOfRetired();

        if (sumOfParts > assetDto.getAllStock()) {
            throw new InvalidAssetException("The sum of available, maintenance, and retired assets cannot exceed the total stock.");
        }
    }
}
