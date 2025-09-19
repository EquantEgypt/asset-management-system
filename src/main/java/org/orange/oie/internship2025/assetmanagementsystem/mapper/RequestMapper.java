package org.orange.oie.internship2025.assetmanagementsystem.mapper;

import org.orange.oie.internship2025.assetmanagementsystem.dto.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.ResponseDTO;
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
    TypeRepository typeRepository;
    @Autowired
    AssetRepository assetRepository;

    public AssetRequest toEntity(RequestDTO dto) {
        AssetType type = typeRepository.findByName(dto.getAssetType());
        Optional<User> user = userRepository.findById(dto.getRequesterId());
        if (dto == null) {
            return null;
        }
        AssetRequest entity = new AssetRequest();
        if (dto.getAssetId() != null) {
            Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
            entity.setAsset(asset.get());
        }
        entity.setRequester(user.get());
        entity.setRequestDate(LocalDateTime.now());
        entity.setAssetType(type);
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
        dto.setAssetType(entity.getAssetType().getName());
        dto.setRequester(entity.getRequester().getUsername());
        dto.setRequestDate(entity.getRequestDate());
        dto.setStatus(entity.getStatus());
        dto.setRequestType(entity.getRequestType());
        return dto;
    }
}