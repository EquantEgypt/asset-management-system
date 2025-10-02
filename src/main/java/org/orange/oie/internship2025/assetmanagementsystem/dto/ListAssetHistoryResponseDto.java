package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;

import java.time.LocalDateTime;
@Setter
@Getter
public class ListAssetHistoryResponseDto {
    private Long id;
    private String assetName;
    private String assignedTo;
    private String note;
    private LocalDateTime timestamp;
    private AssetStatus status;
}