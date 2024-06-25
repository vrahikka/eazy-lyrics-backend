package com.eazyLyrics.backend.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
