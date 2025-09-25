//package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;
//
//import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetAssignmentRequest;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetHistory;
//import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
//import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
//import org.orange.oie.internship2025.assetmanagementsystem.enums.AssignmentStatus;
//import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiResponse;
//import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
//import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetAssignmentRepository;
////import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetHistoryRepository;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
//import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
//import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetAssignmentService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Transactional
//@Service
//public class AssetAssignmentServiceImpl implements AssetAssignmentService {
//
//    private final UserRepository userRepository;
//    private final AssetRepository assetRepository;
//    private final AssetAssignmentRepository assetAssignmentRepository;
//   // private final AssetHistoryRepository assetHistoryRepository;
//
//    public AssetAssignmentServiceImpl(AssetRepository assetRepository,
//                                      AssetAssignmentRepository assetAssignmentRepository,
//                                      UserRepository userRepository,
//                                      AssetHistoryRepository assetHistoryRepository) {
//        this.assetRepository = assetRepository;
//        this.assetAssignmentRepository = assetAssignmentRepository;
//        this.userRepository = userRepository;
//        //this.assetHistoryRepository=assetHistoryRepository;
//    }
//
//    @Override
//    public ResponseEntity<ApiResponse> assignAsset(AssetAssignmentRequest request) {
//        Asset asset = assetRepository.findById(request.getAssetId())
//                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new BusinessException(ApiReturnCode.USER_NOT_EXISTS, "User not found"));
//        validateAssetAssignable(asset);
//        asset.setStatus(AssetStatus.ASSIGNED);
//        assetRepository.save(asset);
//        AssetAssignment assignment = new AssetAssignment();
//        assignment.setAsset(asset);
//        assignment.setAssignedTo(user);
//        assignment.setStatus(AssignmentStatus.ACTIVE);
//        assignment.setAssignmentDate(
//                request.getAssignmentDate()
//        );
//        assignment.setReturnDate(request.getReturnDate());
//        assignment.setNote(request.getNote());
//        assetAssignmentRepository.save(assignment);
//        createAssetHistory(asset, user, AssetStatus.ASSIGNED,request.getNote());
//        return ResponseEntity.ok(new ApiResponse(ApiReturnCode.SUCCESS, null, "Asset assigned successfully", null)
//        );
//    }
//
//    private void validateAssetAssignable(Asset asset) {
//        if (asset.getStatus() != AssetStatus.AVAILABLE) {
//            throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Asset is not available");
//        }
//    }
//    private void createAssetHistory(Asset asset, User user, AssetStatus status, String note) {
//        AssetHistory history = new AssetHistory();
//        history.setAsset(asset);
//        history.setUser(user);
//        history.setStatus(status);
//        history.setNote(note);
//        history.setTimestamp(LocalDateTime.now());
//       // assetHistoryRepository.save(history);
//    }
//
//
//}
//
