package com.eazyLyrics.backend.favoriteSong;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeleteFavoriteSongDTO {
    @NotNull
    private Integer songId;
    @NotBlank
    private String email;
    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreateFavoriteSongDTO{" +
                "songId=" + songId +
                ", email='" + email + '\'' +
                '}';
    }
}
