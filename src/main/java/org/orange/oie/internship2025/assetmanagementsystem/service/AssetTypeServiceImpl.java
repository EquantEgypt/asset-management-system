package org.orange.oie.internship2025.assetmanagementsystem.service;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    private final TypeRepository typeRepository;

    public AssetTypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<AssetType> getAllTypes() {
        return typeRepository.findAll();
    }
}