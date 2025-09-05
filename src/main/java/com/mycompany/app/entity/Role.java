package com.mycompany.app.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Data
@Setter
@Getter

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_Id;

    @Column(nullable = false, unique = true)
    private String role_Type;
}
