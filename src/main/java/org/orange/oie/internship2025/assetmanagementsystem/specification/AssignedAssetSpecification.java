package org.orange.oie.internship2025.assetmanagementsystem.specification;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.stream.Collectors;

public class AssignedAssetSpecification {

    public static Specification<AssignedAsset> buildSpecification(
            AssignedAssetFilterDTO filter, User currentUser) {

        // Start with role-based filtering
        Specification<AssignedAsset> spec = roleBasedSpec(currentUser);

        // Add normal filters
        if (filter.getAssetName() != null && !filter.getAssetName().isBlank()) {
            spec = spec.and(assetNameContains(filter.getAssetName()));
        }
        if (filter.getStatus() != null) {
            spec = spec.and(statusEquals(filter.getStatus()));
        }
        if (filter.getType() != null && !filter.getType().isBlank()) {
            spec = spec.and(typeEquals(filter.getType()));
        }
        if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
            spec = spec.and(categoryContains(filter.getCategory()));
        }
        if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
            spec = spec.and(brandEquals(filter.getBrand()));
        }
        if (filter.getAssignedUser() != null && !filter.getAssignedUser().isBlank()) {
            spec = spec.and(assignedUserContains(filter.getAssignedUser()));
        }
        if (filter.getDepartment() != null && !filter.getDepartment().isBlank()) {
            spec = spec.and(departmentEquals(filter.getDepartment()));
        }

        return spec;
    }

    private static Specification<AssignedAsset> roleBasedSpec(User user) {
        String role = user.getRole().getRoleType();

        switch (role) {
            case "DEPARTMENT_MANAGER":
                Set<Long> deptIds = user.getDepartment() == null ? Set.of()
                        : Set.of(user.getDepartment().getDepartmentId());
                return (root, query, cb) ->
                        root.join("assignedUser").get("department").get("departmentId").in(deptIds);
            default:
                return (root, query, cb) -> cb.disjunction();
        }
    }

    // --- Individual filter specifications ---
    private static Specification<AssignedAsset> assetNameContains(String value) {
        return (root, query, cb) -> cb.like(root.join("asset").get("assetName"), "%" + value + "%");
    }

    private static Specification<AssignedAsset> statusEquals(String value) {
        return (root, query, cb) -> cb.equal(root.get("status"), value);
    }

    private static Specification<AssignedAsset> typeEquals(String value) {
        return (root, query, cb) -> cb.equal(root.join("asset").get("type").get("typeName"), value);
    }

    private static Specification<AssignedAsset> categoryContains(String value) {
        return (root, query, cb) -> cb.like(root.join("asset").get("category").get("categoryName"), "%" + value + "%");
    }

    private static Specification<AssignedAsset> brandEquals(String value) {
        return (root, query, cb) -> cb.equal(root.join("asset").get("brand"), value);
    }

    private static Specification<AssignedAsset> assignedUserContains(String value) {
        return (root, query, cb) -> cb.like(root.join("assignedUser").get("username"), "%" + value + "%");
    }

    private static Specification<AssignedAsset> departmentEquals(String value) {
        return (root, query, cb) -> cb.equal(root.join("assignedUser").get("department").get("departmentName"), value);
    }
}
