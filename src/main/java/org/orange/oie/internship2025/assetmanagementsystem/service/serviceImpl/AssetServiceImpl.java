package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.*;
import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.ListAssetDTOMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.*;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetService;
import org.orange.oie.internship2025.assetmanagementsystem.specification.AssetSpecification;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final ListAssetDTOMapper mapper;
    private final CategoryRepository categoryRepository;
    private final TypeRepository typeRepository;

    public AssetServiceImpl(AssetRepository assetRepository,
                            AssetMapper assetMapper,
                            ListAssetDTOMapper listAssetDTOMapper,
                            AssetHistoryRepository assetHistoryRepository,
                            CategoryRepository categoryRepository,
                            TypeRepository typeRepository) {

        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.mapper = listAssetDTOMapper;
        this.assetHistoryRepository = assetHistoryRepository;
        this.categoryRepository = categoryRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public AssetDto addAsset(AssetRequestDto assetDto) {

        if (assetRepository.existsBySerialNumber(assetDto.getSerialNumber())) {
            throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Another asset with this serial number already exists.");
        }

        // Validate category existence
        if (!categoryRepository.existsById(assetDto.getCategoryId())) {
            throw new BusinessException(ApiReturnCode.BAD_REQUEST, "Category not found");
        }

        // Validate type existence and related to category
        AssetType type = typeRepository.findById(assetDto.getTypeId())
                .orElseThrow(() -> new BusinessException(ApiReturnCode.BAD_REQUEST, "Type not found"));

        if (!type.getCategory().getId().equals(assetDto.getCategoryId())) {
            throw new BusinessException(ApiReturnCode.BAD_REQUEST, "Type does not belong to the specified category.");
        }

        Asset asset = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(asset);
        return assetMapper.toDto(savedAsset);
    }

    @Override
    public AssetDto updateAsset(Long id, UpdateAssetDto assetDto) {
        Asset existingAsset = assetRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));

        // validation
        validateUpdateAssetDTO(assetDto, existingAsset);

        Asset assetToUpdate = assetMapper.toEntity(assetDto);

        //  set the id to ensure an update not an insert
        assetToUpdate.setId(id);

        Asset updatedAsset = assetRepository.save(assetToUpdate);

        return assetMapper.toDto(updatedAsset);
    }

    private void validateUpdateAssetDTO(UpdateAssetDto assetDto, Asset existingAsset) {
        // Validate serial number
        if (assetDto.getSerialNumber() != null && !assetDto.getSerialNumber().equals(existingAsset.getSerialNumber())) {
            if (assetRepository.existsBySerialNumber(assetDto.getSerialNumber())) {
                throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Another asset with this serial number already exists.");
            }
        }

        // Validate category existence
            if (!categoryRepository.existsById(assetDto.getCategoryId())) {
                throw new BusinessException(ApiReturnCode.BAD_REQUEST, "Category not found");
            }

        // Validate type existence and related to category
            AssetType type = typeRepository.findById(assetDto.getTypeId())
                    .orElseThrow(() -> new BusinessException(ApiReturnCode.BAD_REQUEST, "Type not found"));

            if (!type.getCategory().getId().equals(assetDto.getCategoryId())) {
                throw new BusinessException(ApiReturnCode.BAD_REQUEST, "Type does not belong to the specified category.");
            }
    }

    @Override
    public Page<ListAssetDTO> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable) {
        Specification<Asset> spec = AssetSpecification.buildSpecification(filterDTO,
                SecurityUtils.getCurrentUser());
        Page<Asset> assets = assetRepository.findAll(spec, pageable);
        return assets.map(mapper::toDto);
    }

    @Override
    public List<AssetDto> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(AssetMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<AssetDto> getAvailableAsset(String type) {
        Specification<Asset> spec = AssetSpecification.availableByType(type);
        List<Asset> availableAssets = assetRepository.findAll(spec);
        return assetMapper.toDtoList(availableAssets);
    }

    @Override
    public AssetDetailsDto getAssetDetails(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND,
                        "Asset not found with id " + id));

        return assetMapper.toDetailsDto(asset);
    }


}