package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetStatus;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssetDto {
    private Long assetId;
    private String assetName;
    private String brand;
    private String assetDescription;
    private AssetCategory category;
    private AssetType type;
    private String location;
    private String serialNumber;
    private String purchaseDate;
    private String warrantyEndDate;
    private AssetStatus status;
    private String imagePath;
}
