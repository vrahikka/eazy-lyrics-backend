package com.eazyLyrics.backend.roles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
public class Roles {
    @Id
//    @SequenceGenerator(
//            name = "roles_sequence",
//            sequenceName = "roles_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = SEQUENCE,
//            generator = "roles_sequence"
//    )
    Long id;
    String name;
}
