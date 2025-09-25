package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.repository.CategoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AssetMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TypeRepository typeRepository;
    public static AssetDto toDto(Asset asset) {
        if (asset == null) {
            return null;
        }

        AssetDto assetDto = new AssetDto();
        assetDto.setAssetId(asset.getId());
        assetDto.setAssetName(asset.getName());
        assetDto.setImagePath(asset.getImagePath());
        assetDto.setLocation(asset.getLocation());
        assetDto.setSerialNumber(asset.getSerialNumber());
        assetDto.setPurchaseDate(asset.getPurchaseDate().toString());
        assetDto.setWarrantyEndDate(asset.getWarrantyEndDate().toString());
        assetDto.setStatus(asset.getStatus());
        assetDto.setBrand(asset.getBrand());
        assetDto.setAssetDescription(asset.getDescription());
        assetDto.setCategory(asset.getCategory());
        assetDto.setType(asset.getType());

        return assetDto;
    }

    public Asset toEntity(AssetRequestDto assetDto) {
        if (assetDto == null) {
            return null;
        }

        Asset asset = new Asset();
        asset.setName(assetDto.getName());
        asset.setBrand(assetDto.getBrand());
        asset.setDescription(assetDto.getAssetDescription());

        AssetCategory category = categoryRepository.findById(assetDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        AssetType type = typeRepository.findById(assetDto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found"));

        asset.setCategory(category);
        asset.setType(type);

        asset.setLocation(assetDto.getLocation());
        asset.setSerialNumber(assetDto.getSerialNumber());
        asset.setPurchaseDate(assetDto.getPurchaseDate().toLocalDate());
        asset.setWarrantyEndDate(assetDto.getWarrantyEndDate().toLocalDate());
        asset.setStatus(assetDto.getStatus());
        asset.setImagePath(assetDto.getImagePath());
        return asset;
    }

    public static List<AssetDto> toDtoList(List<Asset> assetsList) {
        if (assetsList == null) {
            return Collections.emptyList();
        }

        List<AssetDto> dtoList = new ArrayList<>();
        for (Asset asset : assetsList) {
            dtoList.add(toDto(asset));
        }
           return dtoList;
    }
}
