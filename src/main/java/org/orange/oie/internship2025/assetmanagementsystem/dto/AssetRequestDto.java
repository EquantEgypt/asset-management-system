package org.orange.oie.internship2025.assetmanagementsystem.dto;

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
    private String assetName;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    private String assetDescription;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Type id is required")
    private Long typeId;

    @NotNull(message = "quantity is required")
    @PositiveOrZero(message = "quantity cannot be negative")
    private Integer quantity;

}
