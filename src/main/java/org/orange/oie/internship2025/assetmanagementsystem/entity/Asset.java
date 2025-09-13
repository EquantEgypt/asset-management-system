package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @NotBlank(message = "Asset name cannot be blank")
    @Column(nullable = false)
    private String assetName;

    @NotBlank(message = "Brand cannot be blank")
    @Column(nullable = false)
    private String brand;

    @Column(columnDefinition = "TEXT")
    private String assetDescription;

    @ManyToOne
    @NotNull(message = "Asset category is required")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @NotNull(message = "Asset type is required")
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @NotNull
    @Min(0)
    private Integer allStock;

    @NotNull
    @Min(0)
    private Integer numberOfAvailableToAssign;

    @NotNull
    @Min(0)
    private Integer numberOfMaintenance;

    @NotNull
    @Min(0)
    private Integer numberOfRetired;
}
