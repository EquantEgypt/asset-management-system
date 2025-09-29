package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^SN-[A-Z0-9-]+$", message = "Serial number must follow the format SN-XXXX")
    private String serialNumber;
    private LocalDateTime purchaseDate;
    private LocalDateTime warrantyEndDate;
    private String imagePath;
}
