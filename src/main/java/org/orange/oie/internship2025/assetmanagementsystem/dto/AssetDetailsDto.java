package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssetDetailsDto {
    private Long assetId;
    private String assetName;
    private String brand;
    private String assetDescription;
    private String categoryName;
    private String typeName;
    private String location;
    private String serialNumber;
    private String purchaseDate;
    private String warrantyEndDate;
    private AssetStatus status;
    private String imagePath;
    private Long assignedToId;
    private String assignedToName;
    private String assignedToEmail;

}