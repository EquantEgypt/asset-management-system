package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Category;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Type;
import org.orange.oie.internship2025.assetmanagementsystem.repository.CategoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TypeRepository typeRepository;

    public AssetDto toDto(Asset asset) {
        if (asset == null) {
            return null;
        }

        AssetDto assetDto = new AssetDto();
        assetDto.setAssetId(asset.getAssetId());
        assetDto.setAssetName(asset.getAssetName());
        assetDto.setBrand(asset.getBrand());
        assetDto.setAssetDescription(asset.getAssetDescription());
        assetDto.setCategory(asset.getCategory());
        assetDto.setType(asset.getType());
        assetDto.setAllStock(asset.getAllStock());
        assetDto.setNumberOfAvailableToAssign(asset.getNumberOfAvailableToAssign());
        assetDto.setNumberOfMaintenance(asset.getNumberOfMaintenance());
        assetDto.setNumberOfRetired(asset.getNumberOfRetired());

        return assetDto;
    }

    public Asset toEntity(AssetRequestDto assetDto) {
        if (assetDto == null) {
            return null;
        }

        Asset asset = new Asset();
        asset.setAssetName(assetDto.getAssetName());
        asset.setBrand(assetDto.getBrand());
        asset.setAssetDescription(assetDto.getAssetDescription());
        asset.setAllStock(assetDto.getAllStock());
        asset.setNumberOfAvailableToAssign(assetDto.getNumberOfAvailableToAssign());
        asset.setNumberOfMaintenance(assetDto.getNumberOfMaintenance());
        asset.setNumberOfRetired(assetDto.getNumberOfRetired());

        Category category = categoryRepository.findById(assetDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Type type = typeRepository.findById(assetDto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found"));

        asset.setCategory(category);
        asset.setType(type);
        return asset;
    }
}
