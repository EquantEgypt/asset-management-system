package org.orange.oie.internship2025.assetmanagementsystem.specification;
import jakarta.persistence.criteria.JoinType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RequestSpecifications {
    public static Specification<AssetRequest> byReqType(RequestType type){
        return(root,query,cb)->
                cb.equal(root.get("requestType"),type);
    }
    public static Specification<AssetRequest> byStatuses(List<RequestStatus> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("status").in(statuses);
        };
    }

    public static Specification<AssetRequest> byAssetTypeOrAssetNameOrRequester(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + search.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.join("assetType", JoinType.LEFT).get("name")), likePattern),
                    cb.like(cb.lower(root.join("asset", JoinType.LEFT).get("name")), likePattern),
                    cb.like(cb.lower(root.join("requester", JoinType.LEFT).get("email")), likePattern)
            );
        };
    }

}

