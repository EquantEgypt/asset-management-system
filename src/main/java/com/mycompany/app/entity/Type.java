package com.mycompany.app.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "type")
@Data

public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long type_Id;

    @Column(nullable = false, unique = true)
    private String type_Name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "type")
    private List<Asset> assets;
}
