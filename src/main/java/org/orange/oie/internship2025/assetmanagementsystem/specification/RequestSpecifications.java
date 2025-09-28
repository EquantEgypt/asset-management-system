package org.orange.oie.internship2025.assetmanagementsystem.specification;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetRequest;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;
import org.springframework.data.jpa.domain.Specification;

public class RequestSpecifications {
    public static Specification<AssetRequest> byReqType(RequestType type){
        return(root,query,cb)->
                cb.equal(root.get("requestType"),type);
    }
    public static Specification<AssetRequest> byStatus(RequestStatus status){
        return(root,query,cb)->
                cb.equal(root.get("status"),status);
    }
    public static Specification<AssetRequest> byAssetTypeOrAssetNameOrRequesterSimple(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("assetType").get("name")), likePattern),
                    cb.and(
                            cb.isNotNull(root.get("asset")),
                            cb.like(cb.lower(root.get("asset").get("name")), likePattern)
                    ),
                    cb.like(cb.lower(root.get("requester").get("email")), likePattern)
            );
        };
    };
    }

