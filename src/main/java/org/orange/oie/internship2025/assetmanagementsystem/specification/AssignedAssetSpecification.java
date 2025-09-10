package org.orange.oie.internship2025.assetmanagementsystem.specification;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.springframework.data.jpa.domain.Specification;

public class AssignedAssetSpecification {
    public static Specification<AssignedAsset> hasAssetName(String assetName) {
        return (root, query, builder) -> builder.like(builder.lower(root
        .get("asset")
        .get("assetName")),
                "%" + assetName.toLowerCase() + "%");
    }

}
