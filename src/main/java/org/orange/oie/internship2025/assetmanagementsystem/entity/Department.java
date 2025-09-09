package org.orange.oie.internship2025.assetmanagementsystem.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<User> employees;

}