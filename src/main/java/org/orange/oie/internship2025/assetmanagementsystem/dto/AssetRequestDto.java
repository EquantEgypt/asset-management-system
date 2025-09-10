package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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

    // Getters and Setters
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Integer getAllStock() {
        return allStock;
    }

    public void setAllStock(Integer allStock) {
        this.allStock = allStock;
    }

    public Integer getNumberOfAvailableToAssign() {
        return numberOfAvailableToAssign;
    }

    public void setNumberOfAvailableToAssign(Integer numberOfAvailableToAssign) {
        this.numberOfAvailableToAssign = numberOfAvailableToAssign;
    }

    public Integer getNumberOfMaintenance() {
        return numberOfMaintenance;
    }

    public void setNumberOfMaintenance(Integer numberOfMaintenance) {
        this.numberOfMaintenance = numberOfMaintenance;
    }

    public Integer getNumberOfRetired() {
        return numberOfRetired;
    }

    public void setNumberOfRetired(Integer numberOfRetired) {
        this.numberOfRetired = numberOfRetired;
    }
}
