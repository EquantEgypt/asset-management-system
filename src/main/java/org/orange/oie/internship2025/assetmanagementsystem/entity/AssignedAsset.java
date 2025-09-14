package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assigned_assets", uniqueConstraints = @UniqueConstraint(columnNames = {"asset_id", "status"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignedAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateAssigned;
}
