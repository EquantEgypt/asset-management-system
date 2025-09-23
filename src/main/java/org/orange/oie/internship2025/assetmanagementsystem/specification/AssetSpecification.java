package org.orange.oie.internship2025.assetmanagementsystem.specification;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetAssignment;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AssetSpecification {

    public static Specification<Asset> buildSpecification(
            AssignedAssetFilterDTO filter, User currentUser) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Asset, AssetAssignment> assignmentJoin = root.join("assignments", JoinType.LEFT);
            Join<AssetAssignment, User> userJoin = assignmentJoin.join("assignedTo", JoinType.LEFT);

            // Role-based filtering
            String role = currentUser.getRole().getName();
            switch (role) {
                case "DEPARTMENT_MANAGER":
                    predicates.add(cb.equal(userJoin.get("department").get("id"), currentUser.getDepartment().getId()));
                    break;
                case "EMPLOYEE":
                    predicates.add(cb.equal(userJoin.get("id"), currentUser.getId()));
                    break;
            }

            // Add normal filters
            if (filter.getAssetName() != null && !filter.getAssetName().isBlank()) {
                predicates.add(cb.like(root.get("name"), "%" + filter.getAssetName() + "%"));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getType() != null && !filter.getType().isBlank()) {
                predicates.add(cb.equal(root.get("type").get("name"), filter.getType()));
            }
            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                predicates.add(cb.like(root.get("category").get("name"), "%" + filter.getCategory() + "%"));
            }
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
            }
            if (filter.getAssignedUser() != null && !filter.getAssignedUser().isBlank()) {
                predicates.add(cb.like(userJoin.get("username"), "%" + filter.getAssignedUser() + "%"));
            }
            if (filter.getDepartment() != null && !filter.getDepartment().isBlank()) {
                predicates.add(cb.equal(userJoin.get("department").get("name"), filter.getDepartment()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
