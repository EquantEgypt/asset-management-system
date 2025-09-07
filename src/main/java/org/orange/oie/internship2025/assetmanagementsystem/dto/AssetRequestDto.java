package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDto {

    @NotBlank(message = "Asset name is required.")
    private String assetName;

    private String assetDescription;

    private Long categoryId;
    private Long typeId;

    @NotBlank(message = "Asset status is required.")
    private String status;
}
