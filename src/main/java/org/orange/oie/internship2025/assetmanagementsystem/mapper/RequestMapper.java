package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class RequestMapper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    TypeRepository typeRepository; // Added repository

    public AssetRequest toEntity(RequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AssetRequest entity = new AssetRequest();

        User user = new User();
        user.setId(dto.getRequesterId());
        entity.setRequester(user);
        Asset asset = new Asset();
        asset.setId(dto.getAssetId());
        entity.setAsset(asset);
        AssetType type = new AssetType();
        type.setId(dto.getAssetTypeId());
        entity.setAssetType(type);
        entity.setRequestDate(LocalDateTime.now());
        entity.setRequestType(dto.getRequestType());
        entity.setStatus(RequestStatus.PENDING);
        entity.setNote(dto.getNote());
        return entity;
    }

    public ResponseDTO toDTO(AssetRequest entity) {
        if (entity == null) {
            return null;
        }
        ResponseDTO dto = new ResponseDTO();
        if (entity.getAsset() != null) {
            dto.setAssetId(entity.getAsset().getId());
            dto.setAssetName(entity.getAsset().getName());
        }
        if (entity.getApprovedBy() != null) {
            dto.setApprovedBy(entity.getApprovedBy().getUsername());
            dto.setApprovedDate(entity.getApprovedDate());
        }
        dto.setId(entity.getId());
        dto.setAssetTypeId(entity.getAssetType().getId());

        // Find and set the asset type name
        if (entity.getAssetType().getId() != null) {
            Optional<AssetType> assetType = typeRepository.findById(entity.getAssetType().getId());
            assetType.ifPresent(type -> dto.setAssetTypeName(type.getName()));


            if (entity.getAssetType().getCategory() != null) {
                dto.setCategoryId(entity.getAssetType().getCategory().getId());
                dto.setCategoryName(entity.getAssetType().getCategory().getName());
            }
        }

        dto.setRequester(entity.getRequester().getUsername());
        dto.setRequesterId(entity.getRequester().getId());
        dto.setRequestDate(entity.getRequestDate());
        dto.setStatus(entity.getStatus());
        dto.setRequestType(entity.getRequestType());
        dto.setNote(entity.getNote());
        return dto;
    }
}