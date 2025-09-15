package org.orange.oie.internship2025.assetmanagementsystem.service;

import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.mapper.AssignedAssetMapper;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssignedAssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.specification.AssignedAssetSpecification;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;

import static org.orange.oie.internship2025.assetmanagementsystem.specification.AssignedAssetSpecification.buildSpecification;

@Service
public class AssignedAssetServiceImpl {
    @Autowired
    private AssignedAssetRepository assignedAssetRepository;
    @Autowired
    private AssignedAssetMapper mapper;
    
    public List<AssignedAssetDTO> getAllAssignedAssets() {
        List<AssignedAsset> assignedAssets = assignedAssetRepository.findAll();
        return mapper.toDtoList(assignedAssets);

    }

    public Page<AssignedAssetDTO> getFilteredAsset(AssignedAssetFilterDTO filterDTO, Pageable pageable) {
        Specification<AssignedAsset> spec = buildSpecification(filterDTO, SecurityUtils.getCurrentUser());
        Page<AssignedAsset> assignedAssets = assignedAssetRepository.findAll(spec, pageable);
        return assignedAssets.map(mapper::toDto);
    }
    
}
