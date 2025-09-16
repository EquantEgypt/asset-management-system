package org.orange.oie.internship2025.assetmanagementsystem.service.serviceImpl;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.repository.CategoryRepository;
import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<AssetCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
}