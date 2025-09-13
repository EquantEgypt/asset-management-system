package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignedAssetDTO {
    private Long id;
    private String assetName;
    private String type;
    private String category;
    private String brand;
    private String status;
    private String assignedUser;
    private String department;
    // private String assignedDate;


}
