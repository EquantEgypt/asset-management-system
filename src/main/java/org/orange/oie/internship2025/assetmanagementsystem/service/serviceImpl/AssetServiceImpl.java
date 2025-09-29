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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final ListAssetDTOMapper mapper;
    private final AssetHistoryRepository assetHistoryRepository;
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

        Asset asset = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(asset);

        // Create and save asset history
        assetHistoryRepository.save(createAssetHistory(savedAsset, "Asset Created"));

        return assetMapper.toDto(savedAsset);
    }

    @Override
    public AssetDto updateAsset(Long id, UpdateAssetDto assetDto) {
        Asset existingAsset = assetRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiReturnCode.ASSET_NOT_FOUND, "Asset not found"));

        // validate the unique data in db before update the asset (serial number)
        validateUpdateAssetDTO(assetDto, existingAsset);

        // apply the updates
        applyUpdatesToAsset(existingAsset, assetDto);

        Asset updatedAsset = assetRepository.save(existingAsset);

        // create and save a history record for the update
        assetHistoryRepository.save(createAssetHistory(updatedAsset, "Asset Updated"));

        return assetMapper.toDto(updatedAsset);
    }

    private void validateUpdateAssetDTO(UpdateAssetDto assetDto, Asset existingAsset) {
        if (assetDto.getSerialNumber() != null && !assetDto.getSerialNumber().equals(existingAsset.getSerialNumber())) {
            if (assetRepository.existsBySerialNumber(assetDto.getSerialNumber())) {
                throw new BusinessException(ApiReturnCode.ASSET_ALREADY_EXISTS, "Another asset with this serial number already exists.");
            }
        }
    }

    private void applyUpdatesToAsset(Asset existingAsset, UpdateAssetDto assetDto) {
        if (assetDto.getName() != null) {
            existingAsset.setName(assetDto.getName());
        }
        if (assetDto.getBrand() != null) {
            existingAsset.setBrand(assetDto.getBrand());
        }
        if (assetDto.getAssetDescription() != null) {
            existingAsset.setDescription(assetDto.getAssetDescription());
        }
        if (assetDto.getLocation() != null) {
            existingAsset.setLocation(assetDto.getLocation());
        }
        if (assetDto.getSerialNumber() != null) {
            existingAsset.setSerialNumber(assetDto.getSerialNumber());
        }
        if (assetDto.getPurchaseDate() != null) {
            existingAsset.setPurchaseDate(assetDto.getPurchaseDate().toLocalDate());
        }
        if (assetDto.getWarrantyEndDate() != null) {
            existingAsset.setWarrantyEndDate(assetDto.getWarrantyEndDate().toLocalDate());
        }
        if (assetDto.getStatus() != null) {
            existingAsset.setStatus(assetDto.getStatus());
        }
        if (assetDto.getImagePath() != null) {
            existingAsset.setImagePath(assetDto.getImagePath());
        }

        if (assetDto.getCategoryId() != null) {
            AssetCategory category = categoryRepository.findById(assetDto.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ApiReturnCode.BAD_REQUEST, "Category not found"));
            existingAsset.setCategory(category);
        }

        if (assetDto.getTypeId() != null) {
            AssetType type = typeRepository.findById(assetDto.getTypeId())
                    .orElseThrow(() -> new BusinessException(ApiReturnCode.BAD_REQUEST, "Type not found"));
            existingAsset.setType(type);
        }
    }


    private AssetHistory createAssetHistory(Asset asset, String note) {
        AssetHistory assetHistory = new AssetHistory();
        assetHistory.setAsset(asset);
        assetHistory.setStatus(asset.getStatus());
        assetHistory.setTimestamp(LocalDateTime.now());
        assetHistory.setUser(SecurityUtils.getCurrentUser());
        assetHistory.setNote(note);
        return assetHistory;
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
}