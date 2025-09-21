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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
public class AssetAssignmentServiceImpl implements AssetAssignmentService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;
    private final AssetHistoryRepository assetHistoryRepository;
    private final AssignmentMapper assignmentMapper;
    public AssetAssignmentServiceImpl(AssetRepository assetRepository,
                                      AssetAssignmentRepository assetAssignmentRepository,
                                      UserRepository userRepository,
                                      AssetHistoryRepository assetHistoryRepository,
                                      AssignmentMapper assignmentMapper) {
        this.assetRepository = assetRepository;
        this.assetAssignmentRepository = assetAssignmentRepository;
        this.userRepository = userRepository;
        this.assetHistoryRepository=assetHistoryRepository;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public ResponseEntity<ApiResponse> assignAsset(AssetAssignmentRequest request) {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.USER_NOT_EXISTS, "User not found"));
        validateAssetAssignable(asset);

        AssetAssignment assignment = assignmentMapper.toAssignAsset(request, asset, user);
        assetAssignmentRepository.save(assignment);
        AssetHistory history=assignmentMapper.toCreateAssetHistory(asset, user, AssetStatus.ASSIGNED,request.getNote());
        assetHistoryRepository.save(history);
        return ResponseEntity.ok(new ApiResponse(ApiReturnCode.SUCCESS, null, "Asset assigned successfully", null)
        );
    }

    private void validateAssetAssignable(Asset asset) {
        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Asset is not available");
        }
    }




}

