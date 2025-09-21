package org.orange.oie.internship2025.assetmanagementsystem.dto;
import lombok.Getter;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;

import java.time.LocalDateTime;

@Setter
@Getter
public class ResponseDTO {
    private Long id;
    private Asset asset;
    private Long assetTypeId;
    private String assetTypeName;
    private String requester;
    private LocalDateTime requestDate;
    private RequestStatus status;
    private RequestType requestType;
    private String approvedBy;
    private LocalDateTime approvedDate;
    private String note;
}