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
    long id;

    @Column
    String email;

    @Column
    String password;

    @Column
    String username;

    @Column
    @Enumerated(EnumType.STRING)
    Role role;
}

