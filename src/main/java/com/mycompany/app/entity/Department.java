package com.mycompany.app.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "department")
@Data
@Getter
@Setter

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long department_Id;

    @Column(nullable = false, unique = true)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<User> users;
}
