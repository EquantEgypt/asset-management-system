package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.RequestDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset.ResponseDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.RequestMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.RequestRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.UserRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.RequestService;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

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


    @Override
    public ResponseDTO addRequest(RequestDTO requestDTO) {
        if (requestDTO.getRequesterId() == null) {
            requestDTO.setRequesterId(SecurityUtils.getCurrentUserId());
        }

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
    public Page<ResponseDTO> getRequests(Pageable pageable) {
        User currentUser = SecurityUtils.getCurrentUser();
        String role = currentUser.getRole().getName();

        switch (role) {
            case "ADMIN":
                return requestRepository.findAll(pageable).map(mapper::toDTO);
            case "DEPARTMENT_MANAGER":
                List<Long> userIds = userRepository.findAllByDepartment(currentUser.getDepartment())
                        .stream().map(User::getId).collect(Collectors.toList());
                return requestRepository.findAllByRequesterIdIn(userIds, pageable).map(mapper::toDTO);
            default:
                return requestRepository.findAllByRequesterId(currentUser.getId(), pageable).map(mapper::toDTO);
        }
    }
}