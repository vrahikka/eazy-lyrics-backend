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
    Long id;
    String email;
    String password;
    Boolean enabled = true;
}
