package org.orange.oie.internship2025.assetmanagementsystem.specification;

import java.util.*;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class AssignedAssetSpecification {

    public static Specification<AssignedAsset> withFilter(AssignedAssetFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getAssetName() != null) {
                predicates.add(cb.like(
    root.join("asset").get("assetName"), 
    "%" + filter.getAssetName() + "%"
));

            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filter.getType()));
            }
            if (filter.getCategory() != null) {
                predicates.add(cb.like(root.get("category"), "%" + filter.getCategory() + "%"));
            }
            if (filter.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
            }
            if (filter.getAssignedUser() != null) {
                predicates.add(cb.equal(root.get("assignedUser"), filter.getAssignedUser()));
            }
            if (filter.getDepartment() != null) {
                predicates.add(cb.equal(root.get("department"), filter.getDepartment()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
