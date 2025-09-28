package org.orange.oie.internship2025.assetmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignedAssetFilterDTO {
    private String assetName;
    private String status;
    private String type;
    private String category;
    private String brand;
    private String assignedUser;
    private String department;
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private boolean ascending = true;

    @Override
    public String toString() {
        return "AssignedAssetFilterDTO{" +
                "assetName='" + assetName + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", assignedUser='" + assignedUser + '\'' +
                ", department='" + department + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", sortBy='" + sortBy + '\'' +
                ", ascending=" + ascending +
                '}';
    }
}
