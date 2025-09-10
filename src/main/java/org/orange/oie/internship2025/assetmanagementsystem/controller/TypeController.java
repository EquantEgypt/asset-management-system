package org.orange.oie.internship2025.assetmanagementsystem.controller;

import org.orange.oie.internship2025.assetmanagementsystem.entity.Type;
import org.orange.oie.internship2025.assetmanagementsystem.repository.TypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TypeController {

    private final TypeRepository typeRepository;

    public TypeController(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @GetMapping("/types/all")
    @PreAuthorize("hasAuthority('Admin')")
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }
}