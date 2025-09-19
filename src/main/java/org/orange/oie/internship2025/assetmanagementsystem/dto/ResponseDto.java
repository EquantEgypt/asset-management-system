package org.orange.oie.internship2025.assetmanagementsystem.dto;
import jakarta.persistence.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Asset;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;

import java.time.LocalDateTime;

public class ResponseDto {
    private Long id;
    private Asset asset;
    private AssetType assetType;
    private User requester;
    private LocalDateTime requestDate;
    private RequestStatus status;
    private RequestType requestType;
    private User approvedBy;
    private LocalDateTime approvedDate;
}
