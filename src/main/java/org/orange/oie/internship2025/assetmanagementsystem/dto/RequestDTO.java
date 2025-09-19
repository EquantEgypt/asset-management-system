package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestStatus;
import org.orange.oie.internship2025.assetmanagementsystem.enums.RequestType;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestDTO {

    private Long assetId;

    @NotBlank(message = "asset type cannot be empty")
    private String assetType;

    private Long requesterId;

    @NotNull(message = "request type cannot be empty")
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
}
