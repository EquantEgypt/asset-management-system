package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

import org.enums.AssetStatus;

@Entity
@Table(name = "assigned_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignedAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User assignedUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateAssigned;
}
