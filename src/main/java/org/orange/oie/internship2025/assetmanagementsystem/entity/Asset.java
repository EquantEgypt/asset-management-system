package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @NotBlank(message = "Asset name cannot be blank")
    @Column(nullable = false)
    private String assetName;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    @NotNull(message = "Asset status cannot be null")
    @NotBlank(message = "Asset status cannot be blank")
    @Column(nullable = false)
    private String status;
}
