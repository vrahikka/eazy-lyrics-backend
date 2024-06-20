package com.eazyLyrics.backend.favoriteSong;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteFavoriteSongDTO {
    @NotNull
    private Integer songId;
    @NotBlank
    private String email;

    @Override
    public String toString() {
        return "CreateFavoriteSongDTO{" +
                "songId=" + songId +
                ", email='" + email + '\'' +
                '}';
    }
}
