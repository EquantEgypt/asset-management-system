package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
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

        AssetCategory category = categoryRepository.getById(assetDto.getCategoryId());
        AssetType type = typeRepository.getById(assetDto.getTypeId());

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

    public Asset toEntity(UpdateAssetDto assetDto) {
        if (assetDto == null) {
            return null;
        }
        Asset asset = new Asset();
        asset.setName(assetDto.getName());
        asset.setBrand(assetDto.getBrand());
        asset.setDescription(assetDto.getAssetDescription());

        AssetCategory category = categoryRepository.getById(assetDto.getCategoryId());
        AssetType type = typeRepository.getById(assetDto.getTypeId());

        asset.setCategory(category);
        asset.setType(type);

        asset.setLocation(assetDto.getLocation());
        asset.setSerialNumber(assetDto.getSerialNumber());
        asset.setPurchaseDate(assetDto.getPurchaseDate().toLocalDate());
        asset.setWarrantyEndDate(assetDto.getWarrantyEndDate().toLocalDate());
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

    public AssetDetailsDto toDetailsDto(Asset asset) {
        AssetDetailsDto dto = new AssetDetailsDto();
        dto.setAssetId(asset.getId());
        dto.setAssetName(asset.getName());
        dto.setBrand(asset.getBrand());
        dto.setAssetDescription(asset.getDescription());
        dto.setCategoryName(asset.getCategory().getName());
        dto.setTypeName(asset.getType().getName());
        dto.setLocation(asset.getLocation());
        dto.setSerialNumber(asset.getSerialNumber());
        dto.setPurchaseDate(asset.getPurchaseDate().toString());
        dto.setWarrantyEndDate(asset.getWarrantyEndDate().toString());
        dto.setStatus(asset.getStatus());
        dto.setImagePath(asset.getImagePath());

        if (asset.getAssignments() != null && !asset.getAssignments().isEmpty()) {
            var userAssignment = asset.getAssignments().get(asset.getAssignments().size() - 1);
            User assignedUser = userAssignment.getAssignedTo();
            dto.setAssignedToId(assignedUser.getId());
            dto.setAssignedToName(assignedUser.getUsername());
            dto.setAssignedToEmail(assignedUser.getEmail());
        }

        return dto;
    }

    public listAssetHistoryResponseDto toHistoryDto(AssetHistory history) {
        listAssetHistoryResponseDto dto = new listAssetHistoryResponseDto();
        dto.setId(history.getId());
        dto.setAssetName(history.getAsset().getName());
        dto.setNote(history.getNote());
        dto.setTimestamp(history.getTimestamp());
        dto.setStatus(history.getStatus());
        if (history.getUser() != null) {
            dto.setAssignedTo(history.getUser().getUsername());
        } else {
            dto.setAssignedTo(null);
        }

        return dto;
    }

}
