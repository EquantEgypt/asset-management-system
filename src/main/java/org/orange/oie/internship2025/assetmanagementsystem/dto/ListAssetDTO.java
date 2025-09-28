package org.orange.oie.internship2025.assetmanagementsystem.dto;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssetCategory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListAssetDTO {
    private Long id;
    private String serialNumber;
    private String name;
    private String type;
    private AssetCategory category;
    private String brand;
    private String status;
    private String assignedUser;
    private String department;

    @Override
    public String toString() {
        return "ListAssetDTO{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", category=" + category +
                ", brand='" + brand + '\'' +
                ", status='" + status + '\'' +
                ", assignedUser='" + assignedUser + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}