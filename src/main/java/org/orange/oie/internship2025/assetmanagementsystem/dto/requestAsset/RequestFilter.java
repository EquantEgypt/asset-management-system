package org.orange.oie.internship2025.assetmanagementsystem.dto.requestAsset;

import lombok.Getter;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;

@Setter
@Getter
public class RequestFilter {
    private String status;
    private RequestType type;
    private Long departmentId;
    private String search;
}
