package org.orange.oie.internship2025.assetmanagementsystem.dto;

import java.time.LocalDateTime;

import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AssetRequestDto {

    @NotBlank(message = "Asset name cannot be empty")
    private String name;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    private String assetDescription;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Type id is required")
    private Long typeId;

    private String location;

    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;

    private LocalDateTime purchaseDate;

    private LocalDateTime warrantyEndDate;

    @NotNull(message = "Status is required")
    private AssetStatus status;

    private String imagePath;

}
