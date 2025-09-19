package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;

import java.time.LocalDateTime;

public class RequestDto {

    @NotBlank(message = "asset id cannot be empty")
    private Long assetId;

    @NotBlank(message = "asset type cannot be empty")
    private AssetType assetType;

    @NotBlank(message = "requester id cannot be empty")
    private Long requesterId;

    @NotBlank(message = "request date cannot be empty")
    private LocalDateTime requestDate;

    @NotBlank(message = "status cannot be empty")
    private RequestStatus status;

    @NotBlank(message = "request type cannot be empty")
    private RequestType requestType;

    private Long approvedById;

    private LocalDateTime approvedDate;
}
