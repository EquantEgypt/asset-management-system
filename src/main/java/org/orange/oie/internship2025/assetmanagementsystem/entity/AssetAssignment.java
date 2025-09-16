package org.orange.oie.internship2025.assetmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id", nullable = false)
    private User assignedTo;

    @NotNull
    @Column(nullable = false,name = "assignment_date")
    private LocalDateTime assignmentDate;

    @NotNull
    @Column(nullable = false,name = "status")
    private AssignmentStatus status;


    @Column(name="return_date")
    private LocalDateTime returnDate;

    @Column(columnDefinition = "TEXT")
    private String note;
}
