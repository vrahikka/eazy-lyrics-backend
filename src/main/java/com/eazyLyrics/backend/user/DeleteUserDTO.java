package com.eazyLyrics.backend.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteUserDTO {
    @NotNull
    private String email;
}
