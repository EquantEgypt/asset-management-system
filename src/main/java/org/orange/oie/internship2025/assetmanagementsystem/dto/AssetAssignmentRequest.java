package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssignmentStatus;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssetAssignmentRequest {
    @NotNull
    private Long assetId;
    @NotNull
    private Long userId;
    private String note;
    @NotNull
    private Long typeId;
    @NotNull
    private Long categoryId;

}