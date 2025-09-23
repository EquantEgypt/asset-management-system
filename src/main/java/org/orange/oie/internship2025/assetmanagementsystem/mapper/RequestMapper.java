package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestMapper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AssetRepository assetRepository;

    public AssetRequest toEntity(RequestDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = userRepository.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getRequesterId()));

        AssetRequest entity = new AssetRequest();

        if (dto.getAssetId() != null) {
            Asset asset = assetRepository.findById(dto.getAssetId())
                    .orElseThrow(() -> new RuntimeException("Asset not found with id: " + dto.getAssetId()));
            entity.setAsset(asset);
        }
        entity.setAssetTypeId(dto.getAssetTypeId());
        entity.setRequester(user);
        entity.setRequestDate(LocalDateTime.now());
        entity.setRequestType(dto.getRequestType());
        entity.setStatus(RequestStatus.PENDING);
        return entity;
    }

    public ResponseDTO toDTO(AssetRequest entity) {
        if (entity == null) {
            return null;
        }
        ResponseDTO dto = new ResponseDTO();
        if (entity.getAsset() != null) {
            dto.setAsset(entity.getAsset());
        }
        if (entity.getApprovedBy() != null) {
            dto.setApprovedBy(entity.getApprovedBy().getUsername());
            dto.setApprovedDate(entity.getApprovedDate());
        }
        dto.setId(entity.getId());
        dto.setAssetTypeId(entity.getAssetTypeId());
        dto.setRequester(entity.getRequester().getUsername());
        dto.setRequestDate(entity.getRequestDate());
        dto.setStatus(entity.getStatus());
        dto.setRequestType(entity.getRequestType());
        return dto;
    }
}