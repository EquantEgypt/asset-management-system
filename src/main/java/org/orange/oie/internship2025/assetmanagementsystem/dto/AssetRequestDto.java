package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetRequestDto {

    @NotBlank(message = "Asset name cannot be empty")
    private String assetName;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    private String assetDescription;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Type id is required")
    private Long typeId;

    @NotNull(message = "All stock is required")
    @PositiveOrZero(message = "All stock cannot be negative")
    private Integer allStock;

    @NotNull(message = "Number of available to assign is required")
    @PositiveOrZero(message = "Number of available to assign cannot be negative")
    private Integer numberOfAvailableToAssign;

    @NotNull(message = "Number of maintenance is required")
    @PositiveOrZero(message = "Number of maintenance cannot be negative")
    private Integer numberOfMaintenance;

    @NotNull(message = "Number of retired is required")
    @PositiveOrZero(message = "Number of retired cannot be negative")
    private Integer numberOfRetired;
}
