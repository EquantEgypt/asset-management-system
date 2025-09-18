package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssetRequestDto;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.MiniAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.MiniAssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetAssignmentRepository;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.AssetService;
import org.orange.oie.internship2025.assetmanagementsystem.specification.AssetSpecification;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final AssetAssignmentRepository assetAssingnmentRepository;
    private final MiniAssetMapper mapper;

    public AssetServiceImpl(AssetRepository assetRepository,
            AssetMapper assetMapper,
            AssetAssignmentRepository assetAssingnmentRepository,
            MiniAssetMapper miniAssetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.assetAssingnmentRepository = assetAssingnmentRepository;
        this.mapper = miniAssetMapper;
    }

    @Override
    public AssetDto addAsset(AssetRequestDto assetDto) {
        Asset asset = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(asset);
        return assetMapper.toDto(savedAsset);
    }

    @Override
    public List<AssetDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MiniAssetDTO> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable) {
        Specification<AssetAssignment> spec = AssetSpecification.buildSpecification(filterDTO,
                SecurityUtils.getCurrentUser());
        Page<AssetAssignment> assignedAssets = assetAssingnmentRepository.findAll(spec, pageable);
        return assignedAssets.map(mapper::toDto);
    }

}
