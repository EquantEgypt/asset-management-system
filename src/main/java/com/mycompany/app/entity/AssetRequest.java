package com.mycompany.app.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_request")
public class AssetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "request_date", insertable = false, updatable = false)
    private LocalDateTime requestDate;

    @Column(nullable = false)
    private String type; // instead of ENUM

    @Column(nullable = false)
    private String status; // instead of ENUM
}
