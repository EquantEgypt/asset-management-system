package org.orange.oie.internship2025.assetmanagementsystem.service;

import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssignedAssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.specification.AssignedAssetSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AssignedAssetServiceImpl {
    @Autowired
    private AssignedAssetRepository assignedAssetRepository;
    
    public List<AssignedAsset> getAllAssignedAssets() {
        return assignedAssetRepository.findAll();
    }

    public List<AssignedAsset> searchAssignedAssetsByAssetName(String assetName) {
        return assignedAssetRepository.findAll(AssignedAssetSpecification.hasAssetName(assetName));
    }
    
}
