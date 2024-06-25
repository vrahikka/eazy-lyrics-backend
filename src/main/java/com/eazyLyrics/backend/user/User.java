package com.eazyLyrics.backend.user;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;
}
