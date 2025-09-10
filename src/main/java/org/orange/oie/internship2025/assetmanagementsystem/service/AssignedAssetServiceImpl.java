package org.orange.oie.internship2025.assetmanagementsystem.service;

import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.repository.AssignedAssetRepository;
import org.orange.oie.internship2025.assetmanagementsystem.specification.AssignedAssetSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
@Service
public class AssignedAssetServiceImpl {
    @Autowired
    private AssignedAssetRepository assignedAssetRepository;
    
    public List<AssignedAsset> getAllAssignedAssets() {
        return assignedAssetRepository.findAll();
    }

    // public List<AssignedAsset> searchAssignedAssetsByAssetName(String assetName) {
    //     return assignedAssetRepository.findAll(AssignedAssetSpecification.hasAssetName(assetName));
    // }
//Get request localhost:8080/asset?assetName=dell?type=laptop?category=hardware?brand=dell?status=active?assignedUser=john?department=team1

    public List<AssignedAsset> getFilteredAsset(AssignedAssetFilterDTO filterDTO) {
        return assignedAssetRepository.findAll(AssignedAssetSpecification.withFilter(filterDTO));
    }
    
}
