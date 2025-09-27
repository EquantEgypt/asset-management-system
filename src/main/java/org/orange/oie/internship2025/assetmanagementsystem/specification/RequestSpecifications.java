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
}
