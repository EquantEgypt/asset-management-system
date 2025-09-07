package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.*;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Category;
import org.orange.oie.internship2025.assetmanagementsystem.entity.Type;
import org.orange.oie.internship2025.assetmanagementsystem.entity.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssetDto {
    private Long assetId;
    private String assetName;
    private String assetDescription;
    private Category category;
    private Type type;
    private User assignedUser;
    private String status;
}
