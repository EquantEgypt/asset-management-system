package org.orange.oie.internship2025.assetmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.enums.AssetStatus;

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
    private String description;//details

    @NotBlank(message = "Asset name cannot be blank")
    @Column(nullable = false)
    private String name;

    private String location;

    @Column(unique = true,name = "serial_number")
    private String serialNumber;//details
    

    @Column(nullable = false,name = "purchase_date")
    private LocalDate purchaseDate;//details

    @Column(nullable = false,name = "warranty_end_date")
    private LocalDate warrantyEndDate;//details

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private AssetStatus status = AssetStatus.AVAILABLE;

    @Column(name="image_path")
    private String imagePath;//details


    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AssetAssignment> assignments;
}