package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssetCategory category;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AssetType type;

    @NotBlank(message = "Brand cannot be blank")
    @Column(nullable = false)
    private String brand;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Asset name cannot be blank")
    @Column(nullable = false)
    private String name;

    private String location;

    @Column(unique = true,name = "serial_number")
    private String serialNumber;

    @Column(nullable = false,name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(nullable = false,name = "warranty_end_date")
    private LocalDateTime warrantyEndDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    @Column(name="image_path")
    private String imagePath;
}
