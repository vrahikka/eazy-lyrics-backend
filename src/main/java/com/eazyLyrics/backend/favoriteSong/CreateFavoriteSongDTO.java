package com.eazyLyrics.backend.favoriteSong;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class CreateFavoriteSongDTO {
    @NotNull
    private Integer songId;
    @NotBlank
    private String email;
    @NotBlank
    private String title;
    @NotBlank
    private String artistName;
    @NotBlank
    private String thumbnailUrl;

    @Override
    public String toString() {
        return "CreateFavoriteSongDTO{" +
                "songId=" + songId +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
