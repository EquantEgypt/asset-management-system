package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiniAssetDTO {
    private Long id;
    private String name;
    private String type;
    private String category;
    private String brand;
    private String status;
    private String assignedUser;
    private String department;
}
