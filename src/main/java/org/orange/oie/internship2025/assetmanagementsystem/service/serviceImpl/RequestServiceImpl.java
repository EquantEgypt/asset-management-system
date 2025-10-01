package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ApproveRequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RejectRequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssignmentMapper;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.RequestMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.RequestService;
import org.orange.oie.internship2025.assetmanagementsystem.specification.RequestSpecifications;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestMapper mapper;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetHistoryRepository assetHistoryRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public ResponseDTO addRequest(RequestDTO requestDTO) {
        if (requestDTO.getRequesterId() == null) {
            requestDTO.setRequesterId(SecurityUtils.getCurrentUserId());
        }
        User user = userRepository.findById(requestDTO.getRequesterId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDTO.getRequesterId()));

        AssetType type = typeRepository.findById(requestDTO.getAssetTypeId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.INVALID_ASSET, "type not found with id: " + requestDTO.getAssetId()));

        if (requestDTO.getAssetId() == null && requestDTO.getRequestType().name().equals(RequestType.MAINTENANCE.name())) {
            throw new BusinessException(ApiReturnCode.BAD_REQUEST, "can't be maintenance without an asset");
        }
        if (requestDTO.getAssetId() != null && !assetRepository.existsById(requestDTO.getAssetId())) {
            throw new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset with id " + requestDTO.getAssetId() + " not found");
        }

        if (requestDTO.getAssetTypeId() != null && !typeRepository.existsById(requestDTO.getAssetTypeId())) {
            throw new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "AssetType with id " + requestDTO.getAssetTypeId() + " not found");
        }

        if (requestDTO.getRequesterId() != null && !userRepository.existsById(requestDTO.getRequesterId())) {
            throw new BusinessException(ApiReturnCode.USER_NOT_EXISTS, "User with id " + requestDTO.getRequesterId() + " not found");
        }
        if (requestDTO.getRequesterId() != SecurityUtils.getCurrentUserId()) {
            if (SecurityUtils.getCurrentUser().getRole().getName().equals("EMPLOYEE")) {
                throw new AccessDeniedException("You are not allowed to perform this operation");
            } else if (SecurityUtils.getCurrentUser().getRole().getName().equals("DEPARTMENT_MANAGER") &&
                    !Objects.equals(SecurityUtils.getCurrentUser().getDepartment().getId(), userRepository.findById(requestDTO.getRequesterId()).get().getDepartment().getId())) {
                throw new AccessDeniedException("You are not allowed to perform this operation");
            }
        }
        AssetRequest assetRequest = mapper.toEntity(requestDTO);
        AssetRequest req = requestRepository.save(assetRequest);
        ResponseDTO response = mapper.toDTO(req);
        return response;
    }

    @Override
    public Page<ResponseDTO> getRequests(List<RequestStatus> statuses, RequestType type,String search,boolean personal, Pageable pageable) {
        User currentUser = SecurityUtils.getCurrentUser();
        String role = currentUser.getRole().getName();
        Specification<AssetRequest> baseSpec = RequestSpecifications.fromFilters(statuses, type, search);
        Specification<AssetRequest> ownSpec =
                (root, query, cb) -> cb.equal(root.get("requester").get("id"), currentUser.getId());
        switch (role) {
            case "ADMIN":
                return requestRepository.findAll(personal ? baseSpec.and(ownSpec) : baseSpec, pageable)
                        .map(mapper::toDTO);
            case "IT":
                if (personal) {
                    return requestRepository.findAll(baseSpec.and(ownSpec), pageable)
                            .map(mapper::toDTO);
                }
                Specification<AssetRequest> itSpec =
                        RequestSpecifications.byReqType(RequestType.MAINTENANCE);
                return requestRepository.findAll(baseSpec.and(itSpec), pageable)
                        .map(mapper::toDTO);
            case "DEPARTMENT_MANAGER":
                List<Long> userIds = userRepository.findAllByDepartment(currentUser.getDepartment())
                        .stream().map(User::getId).collect(Collectors.toList());
                return requestRepository.findAllByRequesterIdIn(userIds, pageable).map(mapper::toDTO);
            default:
                return requestRepository.findAll(baseSpec.and(ownSpec), pageable)
                        .map(mapper::toDTO);
        }
    }
    @Transactional
    public ResponseDTO approveRequest(Long id, ApproveRequestDTO dto) {
        AssetRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Request not found"));

        User user = SecurityUtils.getCurrentUser();
        request.setStatus(RequestStatus.APPROVED);
        request.setRejectionNote(null);
        if (dto.getAssetId() != null && request.getRequestType() == RequestType.NEW) {
            Asset asset = assetRepository.findById(dto.getAssetId())
                    .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));
            request.setAsset(asset);
        }

        if (request.getRequestType() == RequestType.MAINTENANCE && request.getAsset() != null) {
            request.getAsset().setStatus(AssetStatus.UNDER_MAINTENANCE);
            assetRepository.save(request.getAsset());
            // Create history entry
            AssetHistory history = assignmentMapper.toCreateAssetHistory(
                    request.getAsset(),
                    user,
                    AssetStatus.UNDER_MAINTENANCE,
                    request.getNote()
            );
            assetHistoryRepository.save(history);
        }
        request.setApprovedBy(user);
        request.setApprovedDate(LocalDateTime.now());
        return mapper.toDTO(requestRepository.save(request));
    }

    @Transactional
    public ResponseDTO rejectRequest(Long id, RejectRequestDTO dto) {
        AssetRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Request not found"));

        User user = SecurityUtils.getCurrentUser();

        request.setStatus(RequestStatus.REJECTED);
        request.setRejectionNote(dto.getRejectionNote());

        request.setApprovedBy(user);
        request.setApprovedDate(LocalDateTime.now());

        return mapper.toDTO(requestRepository.save(request));
    }
}