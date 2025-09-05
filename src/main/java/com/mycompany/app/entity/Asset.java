package com.mycompany.app.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "assets")
@Data

public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asset_Id;

    private String asset_Name;

    @Column(columnDefinition = "TEXT")
    private String asset_Description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Assigned user

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "asset")
    private List<AssetRequest> requests;
}

