package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiResponse;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssignmentMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetAssignmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetHistoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class AssetAssignmentServiceImpl implements AssetAssignmentService {
   @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AssetRepository assetRepository;
    @Autowired
    private  AssetAssignmentRepository assetAssignmentRepository;
    @Autowired
    private  AssetHistoryRepository assetHistoryRepository;
    @Autowired
    private  AssignmentMapper assignmentMapper;

    @Override
    public AssetAssignment assignAsset(AssetAssignmentRequest request) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.USER_NOT_EXISTS, "User not found"));
        validateAssetAssignable(asset);
        asset.setStatus(AssetStatus.ASSIGNED);
        assetRepository.save(asset);
        AssetAssignment assignment = assignmentMapper.toAssignAsset(request, asset, user);
        assetAssignmentRepository.save(assignment);
        AssetHistory history=assignmentMapper.toCreateAssetHistory(asset, user, AssetStatus.ASSIGNED,request.getNote());
        assetHistoryRepository.save(history);
        return assignment;
    }
    private void validateAssetAssignable(Asset asset) {
        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Asset is not available");
        }
    }
}

