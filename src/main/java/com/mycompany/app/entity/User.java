package com.mycompany.app.entity;

import com.mycompany.app.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userid;

    @Column
    String name;

    @Column
    String email;

    @Column
    String password;

    @Column
    String username;

    @Column
    @Enumerated(EnumType.STRING)
    Role role;

    @Column
    long department_id;

    @Column
    long role_id;


}

