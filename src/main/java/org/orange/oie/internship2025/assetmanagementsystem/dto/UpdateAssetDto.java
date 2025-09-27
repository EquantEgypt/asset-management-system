package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateAssetDto {
    private String name;
    private String brand;
    private String assetDescription;
    private Long categoryId;
    private Long typeId;
    private String location;
    private String serialNumber;
    private LocalDateTime purchaseDate;
    private LocalDateTime warrantyEndDate;
    private AssetStatus status;
    private String imagePath;
}
